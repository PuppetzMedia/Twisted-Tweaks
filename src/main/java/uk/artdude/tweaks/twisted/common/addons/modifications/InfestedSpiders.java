package uk.artdude.tweaks.twisted.common.addons.modifications;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import uk.artdude.tweaks.twisted.common.configuration.TTConfiguration;

@Mod.EventBusSubscriber
public class InfestedSpiders
{


    @GameRegistry.ObjectHolder("exnihilocreatio:block_infested_leaves")
    public static Block  INFESTED_LEAVES = Blocks.AIR;
    /**
     * This event gets fired when a player breaks a block and, in case the broken block is an
     * infested leaves block, have a chance to spawn a cave spider.
     * @param event The event of the block break.
     */
    @SubscribeEvent
    public static void blockBreak(BlockEvent.BreakEvent event) {
        World world = event.getWorld();

        boolean isLeaves = false;
        if(INFESTED_LEAVES != Blocks.AIR)
        {
            isLeaves = event.getWorld().getBlockState(event.getPos()).getBlock() == INFESTED_LEAVES;
        }
        // If we're on the server side (don't want to spawn a client-only ghost spider) and the
        // player is breaking an infested leaves block, ...
        if (!world.isRemote && isLeaves) {
            // Get the chance for a spider to spawn from it.
            double chance = TTConfiguration.spawning.spiderInfestedLeavesChance;
            // Generate a random number between 0.0 and 1.0 and see if the player is unlucky. >:)
            if (world.rand.nextFloat() < chance) {
                // Create the spider and set its position.
                EntityCaveSpider spider = new EntityCaveSpider(world);
                BlockPos pos = event.getPos();
                float yaw = (world.rand.nextFloat() * 360.0F); // Get a random rotation.
                spider.setPositionAndRotation(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, yaw, 0.0F);
                // Spawn the spider in the world.
                world.spawnEntity(spider);
            }
        }
    }
}