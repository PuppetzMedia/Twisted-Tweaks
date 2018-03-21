package uk.artdude.tweaks.twisted.common.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
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
		if(event.getEntity() instanceof EntityCreature)
		{
			for(String s : TTConfiguration.ai.attackBlockMobs)
			{
				if(EntityList.getClass(new ResourceLocation(s)) == event.getEntity().getClass())
				{
					EntityCreature living = (EntityCreature) event.getEntity();
					EntityAIBreakBlocks breakBlocks = new EntityAIBreakBlocks(living);
					EntityAIFindTargetBlock moveTo = new EntityAIFindTargetBlock(living);

					System.out.println("Found: " + s);
					living.tasks.addTask(living.tasks.taskEntries.size() + 1, breakBlocks);
					living.tasks.addTask(living.tasks.taskEntries.size() + 1, moveTo);

				}
			}
		}
	}
}
