package io.puppetzmedia.ttweaks.achievement;

import net.minecraft.advancements.CriteriaTriggers;

/**
 * Created by Sam on 3/03/2018.
 */
public class TTTriggers
{
	public static CustomTrigger TRIGGER_BURN_ACID = new CustomTrigger("burn_acid");
	public static CustomTrigger TRIGGER_BLIND_ACID = new CustomTrigger("blind_acid");

	public static void init() {
		CriteriaTriggers.register(TRIGGER_BURN_ACID);
		CriteriaTriggers.register(TRIGGER_BLIND_ACID);
	}
}
