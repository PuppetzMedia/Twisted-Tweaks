package uk.artdude.tweaks.twisted.common.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvents;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import uk.artdude.tweaks.twisted.TwistedTweaks;
import uk.artdude.tweaks.twisted.common.blocks.tileentity.TileEntityTwistedTorch;
import uk.artdude.tweaks.twisted.common.blocks.tileentity.TileEntityTwistedTorchLit;
import uk.artdude.tweaks.twisted.common.configuration.TTConfiguration;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockTwistedTorch extends TorchBlock implements ITileEntityProvider
{
	private boolean lit;

	public BlockTwistedTorch(boolean lit)
	{
		super();
		setCreativeTab(TwistedTweaks.creativeTab);

		this.lit = lit;
		if(lit)
			setLightLevel(1);
		else
			setLightLevel(0);

	}
	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		if(!lit)
			return new TileEntityTwistedTorch();

		return new TileEntityTwistedTorchLit();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, BlockState state, PlayerEntity playerIn, Hand hand, Direction facing, float hitX, float hitY, float hitZ)
	{
		ItemStack stack = playerIn.getHeldItem(hand);

		if(stack.getItem() instanceof FlintAndSteelItem || stack.getItem() instanceof ItemFireball)
		{
			if(!lit)
			{
				boolean damage = true;
				if(stack.getItem() instanceof ItemFireball)
					damage = false;

				if(damage)
				{
					stack.damageItem(1, playerIn);
				}
				else
				{
					stack.shrink(1);
				}

				TileEntityTwistedTorch te = (TileEntityTwistedTorch) worldIn.getTileEntity(pos);
				int litTime = te.getLitTime();
				int litAmt = te.getLitAmount();
				float litChance = TTConfiguration.torch.litChance;
				float attempt = worldIn.rand.nextFloat();

				worldIn.playSound(playerIn, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, playerIn.getRNG().nextFloat() * 0.4F + 0.8F);
				if(!worldIn.isRemote && litAmt < TTConfiguration.torch.maxLitAmount && attempt <= litChance)
				{
					worldIn.setBlockState(pos, TTBlocks.TWISTED_TORCH.getDefaultState().withProperty(FACING, state.getValue(FACING)));

					TileEntityTwistedTorchLit newTe = (TileEntityTwistedTorchLit) worldIn.getTileEntity(pos);
					newTe.setLitTime(litTime);
					newTe.setLitAmount(litAmt);
				}

				if(worldIn.isRemote)
				{
					double x = (double)pos.getX() + 0.5D;
					double y = (double)pos.getY() + 0.7D;
					double z = (double)pos.getZ() + 0.5D;
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x, y, z, 0.0D, 0.0D, 0.0D);
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, BlockState state)
	{
		TileEntity tile = world.getTileEntity(pos);
		if(tile != null && tile instanceof TileEntityTwistedTorch)
		{

			TileEntityTwistedTorch te = (TileEntityTwistedTorch) tile;

			if(te.getLitAmount() + 1 >= TTConfiguration.torch.maxLitAmount)
				return;

			if(world.isAirBlock(pos) && !world.isRemote)
			{
				ItemStack stack = new ItemStack(TTConfiguration.torch.enableTorchBurnout ? TTBlocks.TWISTED_UNLIT_TORCH : Blocks.TORCH);
				if(TTConfiguration.torch.enableTorchBurnout)
				{
					CompoundNBT tags = new CompoundNBT();
					te.writeToNBT(tags);
					stack.setTagInfo("BlockEntityTag", tags);
				}
				spawnAsEntity(world, pos, stack);
			}
		}
	}

	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, BlockState state, float chance, int fortune)
	{
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void randomDisplayTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		if(lit)
			super.randomDisplayTick(stateIn, worldIn, pos, rand);
	}

	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced)
	{
		if(TTConfiguration.torch.showTorchTooltip)
		{
			int litAmount = 0;
			int litTime = 0;

			if(stack.hasTagCompound())
			{
				CompoundNBT blockTags = stack.getTagCompound().getCompoundTag("BlockEntityTag");
				litAmount = blockTags.getInteger("lit_amount");
				litTime = blockTags.getInteger("lit_time");
			}
			tooltip.add(TextFormatting.DARK_PURPLE + I18n.format("tt.info.torch.litamount") + ": " + litAmount);
			tooltip.add(TextFormatting.DARK_PURPLE + I18n.format("tt.info.torch.littime") + ": " + litTime);
		}
	}
}
