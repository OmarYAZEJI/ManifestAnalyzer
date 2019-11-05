package omaryazeji.app.manifestanalyzer.Presenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

import omaryazeji.app.manifestanalyzer.Controller.AnalyzerController;
import omaryazeji.app.manifestanalyzer.Model.Application;
import omaryazeji.app.manifestanalyzer.R;

public class MainActivity extends AppCompatActivity {
    Spinner spinner;
    AnalyzerController utility = new AnalyzerController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spInstalledApp);

        populateInstalledAppSpinner();
    }

    //ToDo: The method will be used to fill the spinner with all installed app on device
    public void populateInstalledAppSpinner() {

        //ToDo: Get all installed app list
        List<Application> spinnerArray = utility.getAllInstalledAppsList(this);

        //ToDo: Set adapter to the spinner
        final ArrayAdapter<Application> adapter = new ArrayAdapter<>(
                this, android.R.layout.select_dialog_item, spinnerArray);
        spinner.setAdapter(adapter);
    }

    //ToDo: Click event for ManifestAnalyzer button
    public void OnClickManifestAnalyzer(View view) {
        //ToDo: Get selected app form the spinner
        Application application = (Application) spinner.getSelectedItem();

        //ToDo: Get selected app directory and read the content of the manifest file
        String manifestInfo =  utility.readManifestFileContent(application.Dir, getApplicationContext());

        //ToDo:  Send the content to info activity
        Intent intent = new Intent(this, InfoActivity.class);
        intent.putExtra("ManifestContent", manifestInfo);
        startActivity(intent);
    }
}
