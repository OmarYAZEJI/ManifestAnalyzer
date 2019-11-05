package omaryazeji.app.manifestanalyzer.Utility;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import omaryazeji.app.manifestanalyzer.Interfaces.IAnalyzer;
import omaryazeji.app.manifestanalyzer.Models.Application;

public class AppUtil implements IAnalyzer {


    //TODO: The method will be used to get a list of all installed apps.
    @Override
    public List<Application> getAllInstalledAppsList(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<Application> applicationList = new ArrayList<>();
        applicationList.add(new Application("Please select an application", "TEST"));

        List<ApplicationInfo> packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo packageInfo : packages) {
            String appName = (String) packageManager.getApplicationLabel(packageInfo);
            applicationList.add(new Application(appName, packageInfo.packageName));
        }

        return applicationList;
    }

    @Override
    public String getManifestFileContent(String sourceDir, Context context) {
        StringBuilder result = new StringBuilder();
        Gson gson = new Gson();

        int flags = PackageManager.GET_ACTIVITIES
                | PackageManager.GET_CONFIGURATIONS
                | PackageManager.GET_DISABLED_COMPONENTS
                | PackageManager.GET_GIDS | PackageManager.GET_INSTRUMENTATION
                | PackageManager.GET_INTENT_FILTERS
                | PackageManager.GET_PERMISSIONS | PackageManager.GET_PROVIDERS
                | PackageManager.GET_RECEIVERS | PackageManager.GET_SERVICES
                | PackageManager.GET_SIGNATURES;
        try {
            PackageInfo pkgInfo = context.getPackageManager().getPackageInfo(sourceDir, flags);
            return formatJsonObject(gson.toJson(pkgInfo));

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    public static String formatJsonObject(String jsonString) {
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(jsonString).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String formatedJsonObject = gson.toJson(json);

        return formatedJsonObject;
    }
}
