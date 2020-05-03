package io.puppetzmedia.ttweaks.block;

import io.puppetzmedia.ttweaks.tileentity.UnlitTorchTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

public class UnlitWallTorchBlock extends WallTorchBlock {

	public UnlitWallTorchBlock(Properties properties) {
		super(properties);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		// Disable spawning flame and smoke particles for unlit torches
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new UnlitTorchTileEntity();
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
