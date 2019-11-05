package omaryazeji.app.manifestanalyzer.Interfaces;
import android.content.Context;
import java.util.List;

import omaryazeji.app.manifestanalyzer.Models.Application;

public interface IAnalyzer {
     List<Application> getAllInstalledAppsList(Context context); //Get all installed application on the device
     String getManifestFileContent(String sourceDir, Context context); //Read the manifest file of the selected app
}
