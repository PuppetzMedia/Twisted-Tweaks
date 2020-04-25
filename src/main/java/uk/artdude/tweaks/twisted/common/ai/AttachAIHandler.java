package uk.artdude.tweaks.twisted.common.ai;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import uk.artdude.tweaks.twisted.common.configuration.TTConfiguration;

/**
 * Created by Sam on 21/03/2018.
 */
@Mod.EventBusSubscriber
public class AttachAIHandler
{
	@SubscribeEvent
	public static void onEntitySpawn(EntityJoinWorldEvent event)
	{
		if(event.getEntity() instanceof CreatureEntity)
		{
			for(String s : TTConfiguration.ai.attackBlockMobs)
			{
				if(EntityList.getClass(new ResourceLocation(s)) == event.getEntity().getClass())
				{
					CreatureEntity living = (CreatureEntity) event.getEntity();
					EntityAIBreakBlocks breakBlocks = new EntityAIBreakBlocks(living);
					EntityAIFindTargetBlock moveTo = new EntityAIFindTargetBlock(living);

					living.tasks.addTask(living.tasks.taskEntries.size() + 1, breakBlocks);
					living.tasks.addTask(living.tasks.taskEntries.size() + 1, moveTo);
				}
			}
		}
	}
}
