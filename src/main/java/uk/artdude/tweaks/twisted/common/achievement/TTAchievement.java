package uk.artdude.tweaks.twisted.common.achievement;

import net.minecraft.init.Items;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

public class TTAchievement {

    public static AchievementPage page;

    public static Achievement acidRain;

    /**
     * This is the main function call to this class.
     */
    public static void init() {
        acidRain = new Achievement("twistedtweaks.acidRain", "twistedtweaks.acidRain", 0, 0, Items.bucket, null).registerStat();
        // Set the achievement page up with the achievement we want to add.
        page = new AchievementPage("TwistedTweaks", acidRain);
        // Register the achievement page to the game.
        AchievementPage.registerAchievementPage(page);
    }
}