package omaryazeji.app.manifestanalyzer.Controller;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;

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

    public static String readManifestContent(PackageInfo packageInfo){
        String result = "";

        result += "Package name:" + packageInfo.packageName + "\n\n";
        result += "Version Name:" + packageInfo.versionName + "\n\n";
        result += "minSdkVersion:" + packageInfo.applicationInfo.minSdkVersion + "\n\n";
        result += "Data Dir:" + packageInfo.applicationInfo.dataDir + "\n\n";
        result += "First Install Time:" + packageInfo.firstInstallTime + "\n\n";
        result += "Last Update Time:" + packageInfo.lastUpdateTime + "\n\n";


        if(packageInfo.requestedPermissions != null & packageInfo.requestedPermissions.length > 0){
            result += "----------------- Requested Permissions:  " + packageInfo.requestedPermissions.length +" -----------------------" +"\n\n";

            for(String requestedPermissions : packageInfo.requestedPermissions){
                result += "* " + requestedPermissions + "\n";
            }

            result += "-------------------------------------------------------------------------------------- "+"\n\n";
        }

        if(packageInfo.activities != null & packageInfo.activities.length > 0){
            result += "----------------- Activities:  " + packageInfo.activities.length +" ----------------------------------------------" +"\n\n";

            for(ActivityInfo activityInfo : packageInfo.activities){
                result += "* " + activityInfo.name + "\n";
            }

            result += "-------------------------------------------------------------------------------------- "+"\n\n";
        }
        if(packageInfo.providers != null & packageInfo.providers.length > 0){
            result += "----------------- Providers:  " + packageInfo.providers.length +" ----------------------------------------------" +"\n\n";

            for(ProviderInfo providerInfo : packageInfo.providers){
                result += "* " + providerInfo.name + "\n";
            }

            result += "-------------------------------------------------------------------------------------- "+"\n\n";
        }
        if(packageInfo.services != null & packageInfo.services.length > 0){
            result += "----------------- Services:  " + packageInfo.services.length +" ----------------------------------------------" +"\n\n";

            for(ServiceInfo serviceInfo : packageInfo.services){
                result += "* " + serviceInfo.name + "\n";
            }

            result += "-------------------------------------------------------------------------------------- "+"\n\n";
        }
        return result;
    }
}
