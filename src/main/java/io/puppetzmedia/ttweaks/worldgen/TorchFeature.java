package io.puppetzmedia.ttweaks.worldgen;

import com.mojang.serialization.Codec;
import io.puppetzmedia.ttweaks.block.LitTorchBlock;
import io.puppetzmedia.ttweaks.core.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.NoOpFeature;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

/**
 * Add this feature to biomes in which you want vanilla torch blocks replaced with
 * {@link LitTorchBlock ModTorchBlock} instances.
 *
 * This should be done from {@code FMLCommonSetupEvent} in the pre-initialization phase
 *
 * @author MattCzyr
 * @see <a href="https://git.io/JfO80">RealisticTorches - TorchFeature.java</a>
 */
@mcp.MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class TorchFeature extends NoOpFeature {


	public TorchFeature(Codec<NoFeatureConfig> p_i231973_1_) {
		super(p_i231973_1_);
	}

	@Override
	public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {

		int startX = pos.getX();
		int startZ = pos.getZ();

		for (int x = 0; x < 16; x++)
		{
			for (int y = 0; y < world.getHeight(Heightmap.Type.MOTION_BLOCKING,pos).getY(); y++)
			{
				for (int z = 0; z < 16; z++)
				{
					BlockPos replacePos = new BlockPos(startX + x, y, startZ + z);
					BlockState state = world.getBlockState(replacePos);
					Block block = state.getBlock();

					if (block == Blocks.TORCH) {
						world.setBlockState(replacePos, RegistryHandler.ModBlocks.TORCH.getDefaultState(), 3);
					}
					else if (block == Blocks.WALL_TORCH)
					{
						Direction direction = state.get(BlockStateProperties.HORIZONTAL_FACING);
						world.setBlockState(replacePos, RegistryHandler.ModBlocks.WALL_TORCH.getDefaultState()
								.with(BlockStateProperties.HORIZONTAL_FACING, direction), 3);
					}
				}
			}
		} return true;
	}
}
