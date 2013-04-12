package lalou.jonathan.personnalBrainTrain.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import lalou.jonathan.personnalBrainTrain.R;

public class WelcomeActivity extends Activity {
    private ImageButton imageButton;

    private static String TAG = "personnalBrainTrain";

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.welcome);
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final Intent intent;
                intent = new Intent(view.getContext(), QuestionActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

}

