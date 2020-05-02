package io.puppetzmedia.ttweaks.event.modifications;

import io.puppetzmedia.ttweaks.TwistedTweaks;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.block.Block;
import net.minecraftforge.event.world.BlockEvent;

@Mod.EventBusSubscriber
public class XPVoid {

    // Set the ArrayList which will contain the blocks to prevent XP from dropping from.
    public static Tag<Block> blockList = new BlockTags.Wrapper(new ResourceLocation(TwistedTweaks.MODID,"xp_disabled"));

    /**
     * This event gets fired on the block break event which will void any XP dropping to the player.
     * @param event The event of the block break.
     */
    @SubscribeEvent
    public static void blockBreak(BlockEvent.BreakEvent event) {
        // Set a block variable for easy referencing.
        Block blockMined = event.getState().getBlock();
        // For each of the blocks in the config list see if the block being mined is in the config.
            // If the current block being mined is in the config list, prevent any XP from dropping from the ore.
            if (blockMined.isIn(blockList)) {
                // Set the XP to drop to 0 (None)
                event.setExpToDrop(0);
            }
    }
}