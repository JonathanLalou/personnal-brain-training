package lalou.jonathan.personnalBrainTrain.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import lalou.jonathan.personnalBrainTrain.R;
import lalou.jonathan.personnalBrainTrain.application.PersonnalBrainTrainApplication;
import lalou.jonathan.personnalBrainTrain.application.StateMemento;

public class WelcomeActivity extends Activity {
    public static final Integer WELCOME_ACTIVITY_CODE = 541321;
    private ImageButton questionImageButton;

    private PersonnalBrainTrainApplication application;
    private StateMemento stateMemento;

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

        questionImageButton = (ImageButton) findViewById(R.id.imageButton);
        questionImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final Intent intent;
                intent = new Intent(view.getContext(), QuestionActivity.class);
                startActivityForResult(intent, WELCOME_ACTIVITY_CODE);
            }
        });
        final Button survivalButton;
        final Button countdownButton;

        survivalButton = (Button) findViewById(R.id.survival);
        survivalButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final Intent intent;
                intent = new Intent(view.getContext(), SurvivalActivity.class);
                startActivityForResult(intent, WELCOME_ACTIVITY_CODE);
            }
        });

        countdownButton = (Button) findViewById(R.id.countdownTextView);
        countdownButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final Intent intent;
                intent = new Intent(view.getContext(), CountdownActivity.class);
                startActivityForResult(intent, WELCOME_ACTIVITY_CODE);
            }
        });

        application = (PersonnalBrainTrainApplication) getApplication();
        stateMemento = application.getStateMemento();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == WELCOME_ACTIVITY_CODE) {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            switch (stateMemento.getPlayMode()) {
                case survival:
                    adb.setTitle("You've lost... ");
                    break;
                case countdown:
                    adb.setTitle("Countdown is over... ");
                case timeAttack:
                case training:
            }
            adb.setMessage("Your score: " + stateMemento.getScore());
            adb.setPositiveButton("Ok", null);
            adb.show();
            stateMemento.reset();
        } else {
            Log.e(TAG, "(welcome)++++++++ There may be an issue anywhere... :-/");
        }
    }
}

