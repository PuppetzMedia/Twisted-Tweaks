package uk.artdude.tweaks.twisted.common.blocks.tileentity;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;

public class TileEntityTwistedTorch extends TileEntity
{
	private int litAmount;
	private int litTime;

	public TileEntityTwistedTorch()
	{

	}

	public void readFromNBT(CompoundNBT compound)
	{
		super.readFromNBT(compound);

		litAmount = compound.getInteger("lit_amount");
		litTime  = compound.getInteger("lit_time");
	}

	public CompoundNBT writeToNBT(CompoundNBT compound)
	{
		compound.setInteger("lit_amount", litAmount);
		compound.setInteger("lit_time", litTime);

		return super.writeToNBT(compound);
	}

	public void setLitAmount(int amt)
	{
		this.litAmount = amt;
	}

	public void setLitTime(int time)
	{
		this.litTime = time;
	}

	public int getLitTime()
	{
		return litTime;
	}

	public int getLitAmount()
	{
		return litAmount;
	}
}
