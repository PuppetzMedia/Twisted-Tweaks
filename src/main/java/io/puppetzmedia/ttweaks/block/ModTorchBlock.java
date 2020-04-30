package io.puppetzmedia.ttweaks.block;

import io.puppetzmedia.ttweaks.TTLogger;
import io.puppetzmedia.ttweaks.config.TorchConfig;
import io.puppetzmedia.ttweaks.tileentity.TileEntityTorch;
import io.puppetzmedia.ttweaks.tileentity.TileEntityTorchLit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TorchBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FireChargeItem;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class ModTorchBlock extends TorchBlock implements ITileEntityOwner {

	public ModTorchBlock(int lightValue) {
		super(Block.Properties.from(Blocks.TORCH).lightValue(lightValue));
	}
	public ModTorchBlock() {
		super(Block.Properties.from(Blocks.TORCH));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {

		// Disable spawning flame and smoke particles if no light
		if (lightValue != 0) {
			super.animateTick(stateIn, worldIn, pos, rand);
		}
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {

		final ItemStack stack = player.getHeldItem(handIn);
		final Item item = stack.getItem();

		boolean isItemFireCharge = item instanceof FireChargeItem;
		if (item instanceof FlintAndSteelItem || isItemFireCharge)
		{
			if (lightValue == 0)
			{
				if(!isItemFireCharge) {
					stack.damageItem(1, player, (p) -> p.sendBreakAnimation(handIn));
				}
				else stack.shrink(1);

				final TileEntityTorch te = (TileEntityTorch) worldIn.getTileEntity(pos);
				if (te == null)
				{
					TTLogger.debug("onBlockActivated for %s at pos %s failed, no TileEntityTorch found",
							player.getDisplayNameAndUUID(), pos.toString());

					return ActionResultType.FAIL;
				}
				worldIn.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS,
						1.0F, player.getRNG().nextFloat() * 0.4F + 0.8F);

				if (!worldIn.isRemote)
				{
					double litChance = TorchConfig.getLitChance();
					float attempt = worldIn.rand.nextFloat();

					if (!te.hasReachedMaxLitAmount() && attempt <= litChance) {
						if (TileEntityTorch.lightTorch(worldIn, pos) == ActionResultType.FAIL)
						{
							TTLogger.debug("onBlockActivated for %s at pos %s failed",
									player.getDisplayNameAndUUID(), pos.toString());

							return ActionResultType.FAIL;
						}
					}
				}
				else {
					worldIn.addParticle(ParticleTypes.SMOKE,
							(double)pos.getX() + 0.5D, (double)pos.getY() + 0.7D,
							(double)pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
				}
				return ActionResultType.SUCCESS;
			}
			return ActionResultType.PASS;
		}
		return ActionResultType.PASS;
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return lightValue != 0 ? new TileEntityTorchLit() : new TileEntityTorch();
	}
}
