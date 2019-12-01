package uk.artdude.tweaks.twisted.common.blocks.tileentity;

import net.minecraft.util.ITickable;
import uk.artdude.tweaks.twisted.common.blocks.TTBlocks;
import uk.artdude.tweaks.twisted.common.configuration.TTConfiguration;

public class TileEntityTwistedTorchLit extends TileEntityTwistedTorch implements ITickable
{
	private boolean cachedCanSeeSky = false;

	@Override
	public void update()
	{
		if(!TTConfiguration.torch.enableTorchBurnout)
			return;

		int litTime = getLitTime();

		if(TTConfiguration.torch.rainExtinguish && litTime % 200 == 0)
			cachedCanSeeSky = world.canSeeSky(pos.up(1));

		litTime++;
		setLitTime(litTime);

		if(litTime >= TTConfiguration.torch.maxLitTime)
		{
			kill();
		}

		if(TTConfiguration.torch.rainExtinguish && cachedCanSeeSky && world.isRaining())
		{
			kill();
		}
	}

	private void kill()
	{
		int newLitAmount = getLitAmount() + 1;
		boolean destroy = false;

		if(TTConfiguration.torch.destroyChance > 0)
		{
			float destroyChance =TTConfiguration.torch.destroyChance;
			float amt = world.rand.nextFloat();
			if(amt < destroyChance)
			{
				destroy = newLitAmount > TTConfiguration.torch.maxLitAmount || !TTConfiguration.torch.onlyDestroyUnusable;
			}
		}

		if(newLitAmount >= TTConfiguration.torch.maxLitAmount && TTConfiguration.torch.alwaysDestroyUnusable)
		{
			destroy = true;
		}

		if(destroy)
		{
			world.setBlockToAir(pos);
			return;
		}

		world.setBlockState(pos, TTBlocks.TWISTED_UNLIT_TORCH.getDefaultState());

		TileEntityTwistedTorch te = new TileEntityTwistedTorch();
		te.setLitAmount(getLitAmount() + 1);
		te.setLitTime(0);

		world.setTileEntity(pos, te);
	}

}
