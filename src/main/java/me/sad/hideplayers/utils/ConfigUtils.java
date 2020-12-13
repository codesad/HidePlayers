package me.sad.hideplayers.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import me.sad.hideplayers.HidePlayers;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigUtils {
    public static void getConfig() throws IOException {
        File config = new File("config/hideplayers.json");
        if (config.exists()) {
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = (JsonObject) parser.parse(new FileReader("config/hideplayers.json"));
            for (JsonElement element : jsonObject.getAsJsonArray("players")) {
                HidePlayers.players.add(element.getAsString());
            }
        } else {
            writeConfig();
        }
    }

    public static void writeConfig() throws IOException {
        JsonWriter writer = new JsonWriter(new FileWriter("config/hideplayers.json"));
        writer.beginObject();
        writer.name("toggled").value(HidePlayers.toggled);
        writer.name("players");
        writer.beginArray();
        for (String player : HidePlayers.players) {
            writer.value(player);
        }
        writer.endArray();
        writer.endObject();
        writer.close();
    }
}
