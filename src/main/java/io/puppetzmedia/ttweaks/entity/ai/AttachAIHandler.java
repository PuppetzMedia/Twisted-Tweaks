package io.puppetzmedia.ttweaks.entity.ai;

import io.puppetzmedia.ttweaks.config.ServerConfig;
import net.minecraft.entity.CreatureEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Created by Sam on 21/03/2018.
 */
@Mod.EventBusSubscriber
public class AttachAIHandler
{
	@SubscribeEvent
	public static void onEntitySpawn(EntityJoinWorldEvent event) {
		if( ServerConfig.aiAttackBlocks.get() && event.getEntity().getType().isContained(ServerConfig.attackBlockMobs) && !event.getWorld().isRemote) {
			CreatureEntity living = (CreatureEntity) event.getEntity();
			BreakBlocksGoal breakBlocks = new BreakBlocksGoal(ServerConfig.attackableBlocks,living, 1.0D, 3);
			living.goalSelector.addGoal(4, breakBlocks);//todo
		}
	}
}
