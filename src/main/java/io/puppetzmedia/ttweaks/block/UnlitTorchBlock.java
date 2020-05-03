package io.puppetzmedia.ttweaks.block;

import io.puppetzmedia.ttweaks.TTLogger;
import io.puppetzmedia.ttweaks.config.TorchConfig;
import io.puppetzmedia.ttweaks.tileentity.UnlitTorchTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.TorchBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FireChargeItem;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.BarrelTileEntity;
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

public class UnlitTorchBlock extends TorchBlock {

	public UnlitTorchBlock(Properties properties) {
		super(properties);
	}

	@Override
	//@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		// Disable spawning flame and smoke particles for unlit torches
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {

		final ItemStack stack = player.getHeldItem(handIn);
		final Item item = stack.getItem();

		boolean isItemFireCharge = item instanceof FireChargeItem;
		if (item instanceof FlintAndSteelItem || isItemFireCharge) {
			final UnlitTorchTileEntity te = (UnlitTorchTileEntity) worldIn.getTileEntity(pos);
			if (te == null) {
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

				if (light && UnlitTorchTileEntity.lightTorch(worldIn, pos) == ActionResultType.FAIL)
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

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new UnlitTorchTileEntity();
	}

	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {

		if(!TorchConfig.isShowTorchTooltip()) {
			return;
		}
		int litAmount = 0, litTime = 0;

		if(stack.hasTag()) {
			CompoundNBT blockTags = stack.getTag().getCompound("data");
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

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity livingEntity, ItemStack stack) {
		if (stack.hasTag()) {
			TileEntity tileEntity = world.getTileEntity(pos);
			if (tileEntity instanceof UnlitTorchTileEntity) {
				CompoundNBT nbt = stack.getTag().getCompound("data");
				UnlitTorchTileEntity unlit = (UnlitTorchTileEntity)tileEntity;
				unlit.setLitAmount(nbt.getInt("lit_amount"));
				unlit.setLitTime(nbt.getInt("lit_time"));
			}
		}
	}
}
