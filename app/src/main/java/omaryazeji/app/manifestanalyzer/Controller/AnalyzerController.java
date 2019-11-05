package omaryazeji.app.manifestanalyzer.Controller;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import omaryazeji.app.manifestanalyzer.Interfaces.IAnalyzer;
import omaryazeji.app.manifestanalyzer.Model.Application;

public class AnalyzerController implements IAnalyzer {

    //TODO: This method will be used to get a list of all installed apps.
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

    //ToDo: Read the manifest file of the selected app
    @Override
    public String readManifestFileContent(String sourceDir, Context context) {
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
            return AppUtility.formatJsonObject(gson.toJson(pkgInfo));

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

}
