package hogent.speechtotext;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;

public class SpeechActivity extends ActionBarActivity {

    private static final int SHOW_SUBACTIVITY = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech);

        View goButton = findViewById(R.id.go_btn);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak...");
                    startActivityForResult(i, SHOW_SUBACTIVITY);
                }
                catch (ActivityNotFoundException e) {
                    TextView result_txt = (TextView) findViewById(R.id.result_txt);
                    result_txt.setText("Voice recognition not supported.");
                    Log.e("onCreate","Voice recognition not supported.");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SHOW_SUBACTIVITY && resultCode == RESULT_OK) {
            TextView result_txt = (TextView) findViewById(R.id.result_txt);
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (results != null) {
                result_txt.setText(results.get(0));
            }
            else {
                Log.e("onActivityResult", "Results empty");
            }
        }
        else {
            Log.e("onActivityResult", "Not ok");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_speech, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
