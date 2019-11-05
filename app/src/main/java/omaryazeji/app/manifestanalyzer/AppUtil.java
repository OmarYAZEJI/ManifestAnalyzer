package omaryazeji.app.manifestanalyzer;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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
    public String getManifestFileOf(String sourceDir, Context context) {
        StringBuilder result = new StringBuilder();
        try {
            PackageInfo pkgInfo = context.getPackageManager().getPackageInfo(sourceDir, 0);

            for(Field field : pkgInfo.getClass().getDeclaredFields()){
                Object value = field.get(pkgInfo);
                Object key = field.getName();
                Object type = field.getGenericType();
                System.out.print(type);
            }
            result.append("PackageName = " + pkgInfo.packageName + "\n");
            result.append("VersionCode = " + pkgInfo.versionCode + "\n");
            result.append("VersionName = " + pkgInfo.versionName + "\n");

            if (pkgInfo.permissions != null && pkgInfo.permissions.length > 0) {
                result.append("Permissions = " + pkgInfo.permissions.length + "\n");
                for (PermissionInfo permissionInfo : pkgInfo.permissions) {
                    result.append("Permission = " + permissionInfo.name + "\n");
                }
            } else {
                result.append("Permissions = " + 0 + "\n");
            }

            if (pkgInfo.activities != null && pkgInfo.activities.length > 0) {
                result.append("Activities = " + pkgInfo.activities.length + "\n");
                for (ActivityInfo activityInfo : pkgInfo.activities) {
                    result.append("Activity = " + activityInfo.name + "\n");
                }
            } else {
                result.append("Activities = " + 0 + "\n");
            }

            Gson gson = new Gson();

            String jsonStr = gson.toJson(pkgInfo);



            return toPrettyFormat(jsonStr);


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String toPrettyFormat(String jsonString)
    {
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(jsonString).getAsJsonObject();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gson.toJson(json);

        return prettyJson;
    }
}
