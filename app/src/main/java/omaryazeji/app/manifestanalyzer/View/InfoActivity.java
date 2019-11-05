package omaryazeji.app.manifestanalyzer.View;

import android.content.pm.PackageInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.google.gson.Gson;

import omaryazeji.app.manifestanalyzer.Controller.AppUtility;
import omaryazeji.app.manifestanalyzer.R;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);


        TextView textView = findViewById(R.id.textInfo);

        //ToDo: Set a back button on the top of action bar to back to maim activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //ToDo: Retrieve the content of manifest file from intent
        String ManifestContent = getIntent().getStringExtra("ManifestContent");

        //ToDo: Make the text view scrollable
        textView.setMovementMethod(new ScrollingMovementMethod());


        Gson g = new Gson();
        try {
            PackageInfo p = g.fromJson(ManifestContent, PackageInfo.class);
            ManifestContent = AppUtility.readManifestContent(p);
        }catch (Exception e){

        }

        //ToDo: Set the content to text view
        textView.setText(ManifestContent);
    }
}
