package omaryazeji.app.manifestanalyzer;
import android.content.Context;

import java.util.List;
import java.util.stream.Stream;

public interface IAnalyzer {
     List<Application> getAllInstalledAppsList(Context context); //Get all installed application on the device
     String getManifestFileOf(String sourceDir,Context context); //Read the manifest file of the selected app
}
