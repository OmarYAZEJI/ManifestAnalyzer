package omaryazeji.app.manifestanalyzer.Activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

import omaryazeji.app.manifestanalyzer.Utility.AppUtil;
import omaryazeji.app.manifestanalyzer.Models.Application;
import omaryazeji.app.manifestanalyzer.R;

public class MainActivity extends AppCompatActivity {
    Spinner spinner;
    final AppUtil utility = new AppUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spInstalledApp);

        populateInstalledAppSpinner();
    }

    //ToDo: The method will be used to fill the spinner with all installed app on device
    public void populateInstalledAppSpinner() {

        List<Application> spinnerArray = utility.getAllInstalledAppsList(this);

        final ArrayAdapter<Application> adapter = new ArrayAdapter<>(
                this, android.R.layout.select_dialog_item, spinnerArray);

        spinner.setAdapter(adapter);
    }

    public void OnClickManifestAnalyzer(View view) {
        Application application = (Application) spinner.getSelectedItem();
        String manifestInfo =  utility.getManifestFileContent(application.Dir, getApplicationContext());

        Intent intent = new Intent(this, InfoActivity.class);
        intent.putExtra("ManifestInfo", manifestInfo);
        startActivity(intent);
    }
}
