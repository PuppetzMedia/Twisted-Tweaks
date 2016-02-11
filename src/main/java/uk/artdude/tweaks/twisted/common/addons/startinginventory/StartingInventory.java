package uk.artdude.tweaks.twisted.common.addons.startinginventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.SaveHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import uk.artdude.tweaks.twisted.TwistedTweaks;
import uk.artdude.tweaks.twisted.common.configuration.ConfigurationHelper;
import uk.artdude.tweaks.twisted.common.util.TTUtilities;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class StartingInventory {

    private static List<StartingItem> items = new ArrayList<StartingItem>();

    public static void init() {
        for (String item : ConfigurationHelper.startingItems) {
            String[] parts = item.split(":");
            int quantity = Integer.valueOf(parts[0]);
            int meta = -1;
            TwistedTweaks.logger.info("TTTTTTTTTTT " + item);
            if (parts.length >= 4) {
                meta = Integer.valueOf(parts[3]);
            }
            if (quantity > 0 && quantity <= 64) {
                items.add(new StartingItem(quantity, parts[1], parts[2], meta));
            }
        }
    }

    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!isPlayerNewToWorld(event.player)) {
            return;
        }
        createPlayerFile(event.player);
        addItems(event.player);
    }

    public static boolean isPlayerNewToWorld(EntityPlayer player) {
        MinecraftServer server = MinecraftServer.getServer();
        SaveHandler saveHandler = (SaveHandler) server.worldServerForDimension(0).getSaveHandler();
        File dir = new File(saveHandler.getWorldDirectory(), "/twisted-tweaks-inv");
        return !dir.exists() || !(new File(dir, player.getGameProfile().getName() + ".tw")).exists();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static boolean createPlayerFile(EntityPlayer player) {
        MinecraftServer server = MinecraftServer.getServer();
        SaveHandler saveHandler = (SaveHandler) server.worldServerForDimension(0).getSaveHandler();
        File dir = new File(saveHandler.getWorldDirectory(), "/twisted-tweaks-inv");
        if (!dir.exists() && !dir.mkdir()) {
            return false;
        }
        File saveFile = new File(dir, player.getGameProfile().getName() + ".tw");
        try  {
            saveFile.createNewFile();
            PrintWriter printWriter = new PrintWriter(new FileWriter(saveFile));
            printWriter.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean addItems(EntityPlayer player) {
        for (StartingItem item : items) {
            ItemStack itemStack;
            if (item.meta != -1) {
                itemStack = new ItemStack(TTUtilities.getItem(item.modId, item.item), item.quantity, item.meta);
            } else {
                itemStack = new ItemStack(TTUtilities.getItem(item.modId, item.item), item.quantity);
            }
            if (itemStack.getItem() == null) {
                TwistedTweaks.logger.error("The item " + item.modId + ":" + item.item + " was not found in the game or is invalid! Please check your config.");
                continue;
            }
            player.inventory.addItemStackToInventory(itemStack);
        }
        return true;
    }
}
