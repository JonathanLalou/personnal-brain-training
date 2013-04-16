package lalou.jonathan.personnalBrainTrain.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import static lalou.jonathan.personnalBrainTrain.application.PlayMode.survival;

/**
 * Created with IntelliJ IDEA "Leda" 12 CE.
 * User: Jonathan LALOU
 * Date: 15/04/13
 * Time: 18:48
 */
public class SurvivalActivity extends Activity {
    public static final Integer SURVIVAL_ACTIVITY_CODE = 95196;
    private static String TAG = "personnalBrainTrain";
    private PersonnalBrainTrainApplication application;
    private StateMemento stateMemento;

    private PersonnalBrainTrainBusiness personnalBrainTrainBusiness;

    public void onCreate(Bundle savedInstanceState) {
        final List<Contact> contacts;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survival);

        personnalBrainTrainBusiness = new PersonnalBrainTrainBusinessLogic(getContentResolver());

        application = (PersonnalBrainTrainApplication) getApplication();
        stateMemento = application.getStateMemento();
        stateMemento.setPlayMode(survival);

        displayQuestion();
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
                        displayAlertOK();
                        stateMemento.incrementScore();
                        textView.setText("Score: " + stateMemento.getScore());
//                            final Intent intent;
//                            intent = new Intent(view.getContext(), SurvivalActivity.class);
//                            startActivityForResult(intent, SURVIVAL_ACTIVITY_CODE);
                        displayQuestion();
                    } else {
                        Log.i(TAG, "---- Bad answer! ----");
//                        displayAlertKO();
                        setResult(9999);
                        finish();
                    }
                }
            });
        }
        final TextView questionTextView = (TextView) findViewById(R.id.question);
        questionTextView.setText("Whom does the number " + goodAnswer.getPhoneNumber() + " belong to?");

    }

    private void displayAlertOK() {
        AlertDialog alertDialog = new AlertDialog.Builder(SurvivalActivity.this).create();
        // Setting Dialog Title
        alertDialog.setTitle("Good answer!");
        // Setting Dialog Message
        alertDialog.setMessage("You win!");
        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.icon);
        // Setting OK Button
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
                Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }

    private void displayAlertKO() {
        AlertDialog alertDialog = new AlertDialog.Builder(SurvivalActivity.this).create();
        // Setting Dialog Title
        alertDialog.setTitle("Bad answer!");
        // Setting Dialog Message
        alertDialog.setMessage("You lose!");
        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.icon);
        // Setting OK Button
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "KO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "(survival)++++++++ " + requestCode + ", " + resultCode + ", " + data.toString());
        // if the requestCode is the code of the current activity, then propagate it recursively
        // (because for the moment an Activity is created for each question - this should be fixed)
        if (SURVIVAL_ACTIVITY_CODE.equals(requestCode)) {
            setResult(resultCode);
        }
    }


}
