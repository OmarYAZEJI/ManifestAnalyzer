package omaryazeji.app.manifestanalyzer.Interfaces;
import android.content.Context;
import java.util.List;

import omaryazeji.app.manifestanalyzer.Models.Application;

public interface IAnalyzer {
     //ToDo: Get all installed application on the device
     List<Application> getAllInstalledAppsList(Context context);
     //ToDo: Read the manifest file of the selected app
     String readManifestFileContent(String sourceDir, Context context);
}
