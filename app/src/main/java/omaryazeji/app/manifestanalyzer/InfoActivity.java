package omaryazeji.app.manifestanalyzer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.EditText;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView editText = findViewById(R.id.textInfo);

        String ManifestInfo = getIntent().getStringExtra("ManifestInfo");
        editText.setMovementMethod(new ScrollingMovementMethod());
        editText.setText(ManifestInfo);
    }
}
