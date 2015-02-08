package uk.artdude.tweaks.twisted.common.addons.acidrain;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import org.apache.logging.log4j.Level;
import uk.artdude.tweaks.twisted.TwistedTweaks;
import uk.artdude.tweaks.twisted.common.configuration.ConfigurationHelper;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class AcidRainCore {

    // Set up the world directory variable.
    public static File worldDirectory;
    // This HashMap stores extra data to whether acid rain is enabled in a world.
    private static HashMap<Integer, Boolean> rainTracking = new HashMap<Integer, Boolean>();
    // This HashMap stores the worlds which are added on world tick, and then enables use to toggle acid rain on or off.
    private static HashMap<Integer, Boolean> worldTracking = new HashMap<Integer, Boolean>();

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        // If the event phase is not equal to the start of the phase return.
        if (event.phase != Phase.START) {
            return;
        }
        // Get the world information.
        World world = event.world;
        // Set the dimension ID for the current world.
        Integer dimensionID = world.provider.dimensionId;
        // We only want to run this code on the server side of things.
        if (world.isRemote) {
            return;
        }
        if (world.provider.hasNoSky) {
            return;
        }
        /* Check to see if the boolean value is different to the actual value in game.
        (I.E. If the boolean does is not valid anymore)
        */
        if (worldTracking.get(dimensionID) == null || rainTracking.get(dimensionID) == null || rainTracking.get(dimensionID) != world.isRaining()) {
            // Set the current state of the world which is raining.
            rainTracking.put(dimensionID,  world.isRaining());
            // Set the value of whether it is raining in the world.
            worldTracking.put(dimensionID, world.isRaining() && world.rand.nextFloat() < ConfigurationHelper.acidRainChance);
        }
    }

    /**
     * This function checks to see if the world argument passed has acid rain active.
     * @param world The world you want to check for if it is raining acid.
     * @return boolean
     */
    public static boolean getIsAcidRain(World world) {
        return worldTracking.get(world.provider.dimensionId) == null || worldTracking.get(world.provider.dimensionId);
    }

    @SuppressWarnings({"ignored", "ResultOfMethodCallIgnored"})
    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        // Get the world information.
        World world = event.world;
        // Check that the world directory is null and that the code is being fired server side.
        if (!world.isRemote && worldDirectory == null) {
            // Get the server object.
            MinecraftServer server = MinecraftServer.getServer();
            /*
            If the client is running the server (I.E. Playing single player) set the world directory to the saves folder,
            otherwise get the location of the servers world directory.
            */
            if (TwistedTweaks.proxy.isClient()) {
                worldDirectory = server.getFile("saves/" + server.getFolderName());
            } else {
                worldDirectory = server.getFile(server.getFolderName());
            }
            // Create the save directory location if it not found.
            new File(worldDirectory, "data/").mkdirs();
            // Try to load the saved data.
            loadWorldData(new File(worldDirectory, "data/ttAcidRain"));
        }
    }

    @SuppressWarnings({"ignored", "ResultOfMethodCallIgnored"})
    @SubscribeEvent
    public void onWorldSave(WorldEvent.Save event) {
        // Get the world information.
        World world = event.world;
        /*
        Check that the world directory is null and that the code is being fired server side, and that the dimension is "0" as this
        stops the file being saved per world save.
        */
        if (!world.isRemote && worldDirectory != null && world.provider.dimensionId == 0) {
            // Create the save directory location if it not found.
            new File(worldDirectory, "data/").mkdirs();
            // Save the current Acid rain settings to the data file.
            saveWorldData(new File(worldDirectory, "data/ttAcidRain"));
        }
    }

    @SuppressWarnings({"ignored", "ResultOfMethodCallIgnored"})
    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {
        if(!event.world.isRemote && worldDirectory != null && !MinecraftServer.getServer().isServerRunning()) {
            // Create the save directory location if it not found.
            new File(worldDirectory, "data/").mkdirs();
            // Save the current Acid rain settings to the data file.
            saveWorldData(new File(worldDirectory, "data/ttAcidRain"));
            // Null the world directory.
            worldDirectory = null;
            // Clear the rain tracking HashMap.
            rainTracking.clear();
            // Clear the world tracking HashMap.
            worldTracking.clear();
        }
    }

    /**
     * This function loads data from the data file in the world data directory to load the states of the Acid rain.
     * @param file The file name + location to save the data too.
     */
    @SuppressWarnings("unchecked")
    public static void loadWorldData(File file) {
        try {
            // If the file does not exist create one.
            if (!file.exists()) {
                // Try to create the file, if it errors throw an exception.
                if (!file.createNewFile()) {
                    throw new Exception("Failed to create the world data file");
                }
            }
            // Create our file input stream.
            FileInputStream fileInputStream = new FileInputStream(file);
            // If there is no data to load from the file return out of the function.
            if (fileInputStream.available() <= 0) {
                fileInputStream.close();
                return;
            }
            // Create our object input stream.
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            // Get our arrayList from the data file.
            ArrayList<HashMap<Integer, Boolean>> arrayList = (ArrayList<HashMap<Integer, Boolean>>)objectInputStream.readObject();
            // Set the rain tracking from the data loaded from the file.
            rainTracking = arrayList.get(0);
            // Set the world tracking from the data loaded from the file.
            worldTracking = arrayList.get(1);
            // Close the object input stream.
            objectInputStream.close();
            // Close the file input stream.
            fileInputStream.close();
        } catch (Exception err) {
            TwistedTweaks.logger.log(Level.ERROR, "There was a problem loading the data file.", err);
        }
    }

    /**
     * This function saves data to an arrayList which is then saved to a data file in the world data directory.
     * @param file The file name + location to save the data too.
     */
    public static void saveWorldData(File file) {
        try {
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    throw new Exception("Failed to create the save file.");
                }
            }
            // Create our file output stream.
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            // Create our object output stream.
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            // Create the arrayList which we will use to save our data to.
            ArrayList<HashMap<Integer, Boolean>> arrayList = new ArrayList<HashMap<Integer, Boolean>>();
            // Add the rain tracking HashMap to the arrayList.
            arrayList.add(rainTracking);
            // Add the world tracking HashMap to the arrayList.
            arrayList.add(worldTracking);
            // Write the arrayList to the data file.
            objectOutputStream.writeObject(arrayList);
            // Close the object output stream.
            objectOutputStream.close();
            // Close the file output stream.
            fileOutputStream.close();
        } catch(Exception err) {
            TwistedTweaks.logger.log(Level.ERROR, "There was a problem saving the data file.", err);
        }
    }
}