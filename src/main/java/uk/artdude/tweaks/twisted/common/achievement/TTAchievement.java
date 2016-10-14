package uk.artdude.tweaks.twisted.common.achievement;

import net.minecraft.init.Items;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

public class TTAchievement {

    // Set the achievement page variable.
    public static AchievementPage page;

    // Set the achievement variables.
    public static Achievement acidRainFall;
    public static Achievement acidBlind;

    /**
     * This is the main function call to this class.
     */
    public static void init() {
        // Create our achievements.
        acidRainFall = new Achievement("tweaks.twisted.acid.rain", "tweaks.twisted.acid.rain", 0, 0, Items.BUCKET, null).registerStat();
        acidBlind = new Achievement("tweaks.twisted.acid.burn", "tweaks.twisted.acid.burn", 0, -2, Items.SPIDER_EYE, acidRainFall).registerStat();
        // Set the achievement page up with the achievement we want to add.
        page = new AchievementPage("Twisted Tweaks", acidRainFall, acidBlind);
        // Register the achievement page to the game.
        AchievementPage.registerAchievementPage(page);
    }
}