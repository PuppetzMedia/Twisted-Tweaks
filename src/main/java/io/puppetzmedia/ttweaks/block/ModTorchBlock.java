package io.puppetzmedia.ttweaks.block;

import io.puppetzmedia.ttweaks.TTLogger;
import io.puppetzmedia.ttweaks.config.TorchConfig;
import io.puppetzmedia.ttweaks.tileentity.TorchTileEntity;
import io.puppetzmedia.ttweaks.tileentity.TorchLitTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TorchBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.FireChargeItem;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ModTorchBlock extends TorchBlock {

	public ModTorchBlock(int lightValue) {
		super(Block.Properties.from(Blocks.TORCH).lightValue(lightValue));
	}
	public ModTorchBlock() {
		super(Block.Properties.from(Blocks.TORCH));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {

		// Disable spawning flame and smoke particles for unlit torches
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
				final TorchTileEntity te = (TorchTileEntity) worldIn.getTileEntity(pos);
				if (te == null)
				{
					TTLogger.debug("onBlockActivated for %s at pos %s failed, no TileEntityTorch found",
							player.getDisplayNameAndUUID(), pos.toString());

					return ActionResultType.FAIL;
				}
				SoundEvent sound;
				// We need to manually handle using up item charge
				if(!isItemFireCharge)
				{
					stack.damageItem(1, player, (p) -> p.sendBreakAnimation(handIn));
					sound = SoundEvents.ITEM_FIRECHARGE_USE;
				}
				else {
					stack.shrink(1);
					sound = SoundEvents.ITEM_FLINTANDSTEEL_USE;
				}
				worldIn.playSound(player, pos, sound, SoundCategory.BLOCKS,
						1.0F, player.getRNG().nextFloat() * 0.4F + 0.8F);

				if (!worldIn.isRemote)
				{
					double litChance = TorchConfig.getLitChance();
					float attempt = worldIn.rand.nextFloat();
					boolean light = !te.hasReachedMaxLitAmount() && attempt <= litChance;

					if (light && TorchTileEntity.lightTorch(worldIn, pos) == ActionResultType.FAIL)
					{
						TTLogger.debug("onBlockActivated for %s at pos %s failed",
								player.getDisplayNameAndUUID(), pos.toString());

						return ActionResultType.FAIL;
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
	public boolean removedByPlayer(BlockState state, World world, BlockPos pos,
								   PlayerEntity player, boolean willHarvest, IFluidState fluid) {

		TileEntity tile = world.getTileEntity(pos);
		if (!world.isRemote && !player.isCreative() && tile instanceof TorchTileEntity)
		{
			boolean torchBurnout = TorchConfig.isEnableTorchBurnout();
			ItemStack stack = new ItemStack(torchBurnout ? ModBlocks.TORCH_UNLIT : ModBlocks.TORCH);

			final double destroyChance = TorchConfig.getPickupDestroyChance();

			// Roll dice to see if torch should be destroyed
			if (destroyChance == 0 || world.rand.nextFloat() >= destroyChance)
			{
				if (torchBurnout) {
					stack.setTagInfo("BlockEntityTag", tile.write(new CompoundNBT()));
				}
				spawnAsEntity(world, pos, stack);
				return true;
			}
		}
		return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
	}

	@Override
	public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos,
							 BlockState state, @Nullable TileEntity te, ItemStack stack) {
		// Don't harvest block,
		// we are already manually spawning it as entity in removedByPlayer
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return lightValue != 0 ? new TorchLitTileEntity() : new TorchTileEntity();
	}

	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {

		if(!TorchConfig.isShowTorchTooltip()) {
			return;
		}
		int litAmount = 0, litTime = 0;

		if(stack.hasTag())
		{
			CompoundNBT blockTags = stack.getTag().getCompound("BlockEntityTag");
			litAmount = blockTags.getInt("lit_amount");
			litTime = blockTags.getInt("lit_time");
		}

		tooltip.add(new StringTextComponent("")
				.appendSibling(new TranslationTextComponent("tt.info.torch.litamount")
						.applyTextStyle(TextFormatting.DARK_PURPLE)).appendText(": " + litAmount));

		tooltip.add(new StringTextComponent("")
				.appendSibling(new TranslationTextComponent("tt.info.torch.littime")
						.applyTextStyle(TextFormatting.DARK_PURPLE)).appendText(": " + litTime));
	}
}
