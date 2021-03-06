package me.kyllian.webhost.files;

import com.google.common.io.ByteStreams;
import me.kyllian.webhost.WebhostPluginBungee;

import java.io.*;

public class FileLoaderBungee {

    public static void ensureIndexPopulated(WebhostPluginBungee plugin, File dataFolder) {
        if (!dataFolder.exists()) dataFolder.mkdir();
        saveResource(plugin, dataFolder, "config.yml");
        File htmlFolder = new File(dataFolder, "html");
        if (!htmlFolder.exists()) { // Default page does not exist, just generate a new one
            plugin.getLogger().info("Website not found... Populating now");
            htmlFolder.mkdirs();
            new File(dataFolder, "html/assets").mkdir();
            new File(dataFolder, "html/assets/css").mkdir();
            new File(dataFolder, "html/assets/img").mkdir();
            new File(dataFolder, "html/assets/js").mkdir();
            saveResource(plugin, dataFolder, "html/assets/css/style.css");
            saveResource(plugin, dataFolder, "html/assets/img/background.png");
            saveResource(plugin, dataFolder, "html/assets/js/main.js");
            saveResource(plugin, dataFolder, "html/index.html");
            plugin.getLogger().info("Site populated...");
            // Am I too lazy to make this dynamic? Yes
            // Will I ever change this site? Probably not
        }
    }

    public static void saveResource(WebhostPluginBungee plugin, File dataFolder, String fileName) {
        File file = new File(dataFolder, fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
                try (InputStream is = plugin.getResourceAsStream(fileName);
                     OutputStream os = new FileOutputStream(file)) {
                    ByteStreams.copy(is, os);
                }
            } catch (IOException e) {
                throw new RuntimeException("Unable to create file", e);
            }
        }
    }

}
