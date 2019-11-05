package omaryazeji.app.manifestanalyzer.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class AppUtility {
    //TODO: This will reformat a json object.
    public static String formatJsonObject(String jsonString) {
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(jsonString).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String formattedJsonObject = gson.toJson(json);

        return formattedJsonObject;
    }
}
