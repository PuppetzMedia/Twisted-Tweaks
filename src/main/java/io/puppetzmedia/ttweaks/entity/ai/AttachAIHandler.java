package io.puppetzmedia.ttweaks.entity.ai;

import io.puppetzmedia.ttweaks.config.AiConfigSpec;
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
	public static void onEntitySpawn(EntityJoinWorldEvent event)
	{
		if(event.getEntity().getType().isContained(AiConfigSpec.attackBlockMobs))
		{
			CreatureEntity living = (CreatureEntity) event.getEntity();
			BreakBlocksGoal breakBlocks = new BreakBlocksGoal(living);
			FindTargetBlockGoal moveTo = new FindTargetBlockGoal(living);

			living.goalSelector.addGoal((int) (living.goalSelector.getRunningGoals().count() + 1), breakBlocks);//todo
			living.goalSelector.addGoal((int) (living.goalSelector.getRunningGoals().count() + 1), moveTo);
		}
	}
}
