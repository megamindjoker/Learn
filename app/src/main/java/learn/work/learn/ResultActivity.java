package learn.work.learn;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by rakshak on 1/3/17.
 */
public class ResultActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firebase);
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("blah blah blah");

    }
}
