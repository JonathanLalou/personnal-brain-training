package lalou.jonathan.personnalBrainTrain.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import lalou.jonathan.personnalBrainTrain.R;
import lalou.jonathan.personnalBrainTrain.application.PersonnalBrainTrainApplication;
import lalou.jonathan.personnalBrainTrain.application.StateMemento;
import lalou.jonathan.personnalBrainTrain.business.PersonnalBrainTrainBusiness;
import lalou.jonathan.personnalBrainTrain.business.logic.PersonnalBrainTrainBusinessLogic;
import lalou.jonathan.personnalBrainTrain.entity.Contact;

import java.util.Collections;
import java.util.List;

import static lalou.jonathan.personnalBrainTrain.application.PlayMode.countdown;

/**
 * Created with IntelliJ IDEA "Leda" 12 CE.
 * User: Jonathan LALOU
 * Date: 17/04/13
 * Time: 15:30
 */
public class CountdownActivity extends Activity {
    public static final Integer COUNTDOWN_ACTIVITY_CODE = 951;
    public static final int COUNT_DOWN_INTERVAL = 1000;
    // should be 30s in prod ; 10 in tests
    public static final int COUNTDOWN_SPAN = 10000;
    private static String TAG = "personnalBrainTrain";
    private PersonnalBrainTrainApplication application;
    private StateMemento stateMemento;
    private TextView countdownTextView;

    private PersonnalBrainTrainBusiness personnalBrainTrainBusiness;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.countdown);

        personnalBrainTrainBusiness = new PersonnalBrainTrainBusinessLogic(getContentResolver());

        application = (PersonnalBrainTrainApplication) getApplication();
        stateMemento = application.getStateMemento();
        stateMemento.setPlayMode(countdown);

        displayQuestion();
        countdownTextView = (TextView) findViewById(R.id.countdownTextView);
        startCountdown();
    }

    private void startCountdown() {
        if (null != countdownTextView) {
            new CountDownTimer(COUNTDOWN_SPAN, COUNT_DOWN_INTERVAL) {

                public void onTick(long millisUntilFinished) {
                    countdownTextView.setText("seconds remaining: " + millisUntilFinished / 1000);
                }

                public void onFinish() {
                    countdownTextView.setText("done!");
                    Toast.makeText(getApplicationContext(), "Timeup!", Toast.LENGTH_SHORT).show();
                    setResult(9999);
                    finish();
                }
            }.start();
        } else {
            Log.e(TAG, "big problem... element was not retrieved!!");
        }
    }

    private void displayQuestion() {
        // FIXME very ugly
        final List<Contact> contacts;
        contacts = personnalBrainTrainBusiness.getContacts();
        final Contact goodAnswer;
        goodAnswer = contacts.get(0);
        Collections.shuffle(contacts);

        final Button[] buttons = new Button[]{
                (Button) findViewById(R.id.button0),
                (Button) findViewById(R.id.button1),
                (Button) findViewById(R.id.button2),
        };
        final TextView textView;
        textView = (TextView) findViewById(R.id.score);
        textView.setText("Score: " + stateMemento.getScore());

        for (int i = 0; i < 3; i++) {
            final Button button = buttons[i];
            button.setText(contacts.get(i).getName());
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (button.getText().equals(goodAnswer.getName())) {
                        stateMemento.incrementScore();
                        Toast.makeText(getApplicationContext(), "Good!", Toast.LENGTH_SHORT).show();
                        textView.setText("Score: " + stateMemento.getScore());
                        displayQuestion();
                    } else {
                        Log.i(TAG, "---- Bad answer! ----");
                        Toast.makeText(getApplicationContext(), "Fail...", Toast.LENGTH_SHORT).show();
                        setResult(9999);
                        finish();
                    }
                }
            });
        }
        final TextView questionTextView = (TextView) findViewById(R.id.question);
        questionTextView.setText("Whom does the number " + goodAnswer.getPhoneNumber() + " belong to?");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "(survival)++++++++ " + requestCode + ", " + resultCode + ", " + data.toString());
        // if the requestCode is the code of the current activity, then propagate it recursively
        // (because for the moment an Activity is created for each question - this should be fixed)
        if (COUNTDOWN_ACTIVITY_CODE.equals(requestCode)) {
            setResult(resultCode);
        }
    }


}
