package uk.artdude.tweaks.twisted.common.achievement;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import uk.artdude.tweaks.twisted.TwistedTweaks;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Sam on 3/03/2018.
 */
public class TTTriggers
{
	public static CustomTrigger TRIGGER_BURN_ACID = new CustomTrigger("burn_acid");
	public static CustomTrigger TRIGGER_BLIND_ACID = new CustomTrigger("blind_acid");

	public static void init()
	{
		registerAdvancementTrigger(TRIGGER_BURN_ACID);
		registerAdvancementTrigger(TRIGGER_BLIND_ACID);
	}

	//reflection bs
	private static Method CriterionRegister;
	public static <T extends ICriterionInstance> ICriterionTrigger<T> registerAdvancementTrigger(ICriterionTrigger<T> trigger) {
		if(CriterionRegister == null) {
			CriterionRegister = ReflectionHelper.findMethod(CriteriaTriggers.class, "register", "func_192118_a", ICriterionTrigger.class);
			CriterionRegister.setAccessible(true);
		}
		try
		{
			trigger = (ICriterionTrigger<T>) CriterionRegister.invoke(null, trigger);
		}
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			TwistedTweaks.logger.error("Failed to register trigger " + trigger.getId() + "!");
			e.printStackTrace();
		}
		return trigger;
	}

}
