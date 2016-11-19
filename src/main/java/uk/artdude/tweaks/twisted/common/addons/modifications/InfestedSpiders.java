package uk.artdude.tweaks.twisted.common.addons.modifications;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import uk.artdude.tweaks.twisted.common.configuration.ConfigurationHelper;

public class InfestedSpiders {

    /**
     * This event gets fired when a player breaks a block and, in case the broken block is an
     * infested leaves block, have a chance to spawn a cave spider.
     * @param event The event of the block break.
     */
    @SubscribeEvent
    public void blockBreak(BlockEvent.BreakEvent event) {
        World world = event.getWorld();
        String blockRegistryName = event.getState().getBlock().getRegistryName().toString();
        boolean isInfestedLeaves = blockRegistryName.equals("exnihiloomnia:infested_leaves");
        // If we're on the server side (don't want to spawn a client-only ghost spider) and the
        // player is breaking an infested leaves block, ...
        if (!world.isRemote && isInfestedLeaves) {
            // Get the chance for a spider to spawn from it.
            double chance = ConfigurationHelper.infestedLeavesSpiderChance;
            // Generate a random number between 0.0 and 1.0 and see if the player is unlucky. >:)
            if (world.rand.nextFloat() < chance) {
                // Create the spider and set its position.
                EntityCaveSpider spider = new EntityCaveSpider(world);
                BlockPos pos = event.getPos();
                float yaw = (world.rand.nextFloat() * 360.0F); // Get a random rotation.
                spider.setPositionAndRotation(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, yaw, 0.0F);
                // Spawn the spider in the world.
                world.spawnEntityInWorld(spider);
            }
        }
    }
}