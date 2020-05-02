package io.puppetzmedia.ttweaks.event.modifications;

import io.puppetzmedia.ttweaks.config.ServerConfig;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.CaveSpiderEntity;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber
public class InfestedSpiders
{
    @ObjectHolder("exnihilocreatio:block_infested_leaves")
    public static Block  INFESTED_LEAVES = Blocks.AIR;
    /**
     * This event gets fired when a player breaks a block and, in case the broken block is an
     * infested leaves block, have a chance to spawn a cave spider.
     * @param event The event of the block break.
     */
    @SubscribeEvent
    public static void blockBreak(BlockEvent.BreakEvent event) {
        IWorld world = event.getWorld();

        boolean isLeaves = false;
        if(INFESTED_LEAVES != Blocks.AIR)
        {
            isLeaves = event.getWorld().getBlockState(event.getPos()).getBlock() == INFESTED_LEAVES;
        }
        // If we're on the server side (don't want to spawn a client-only ghost spider) and the
        // player is breaking an infested leaves block, ...
        if (!world.isRemote() && isLeaves) {
            // Get the chance for a spider to spawn from it.
            double chance = ServerConfig.infested_leaves_spider_spawn_chance.get();
            // Generate a random number between 0.0 and 1.0 and see if the player is unlucky. >:)
            if (world.getRandom().nextFloat() < chance) {
                // Create the spider and set its position.
                CaveSpiderEntity spider = new CaveSpiderEntity(EntityType.CAVE_SPIDER, world.getWorld());
                BlockPos pos = event.getPos();
                float yaw = (world.getRandom().nextFloat() * 360.0F); // Get a random rotation.
                spider.setPositionAndRotation(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, yaw, 0.0F);
                // Spawn the spider in the world.
                world.addEntity(spider);
            }
        }
    }
}