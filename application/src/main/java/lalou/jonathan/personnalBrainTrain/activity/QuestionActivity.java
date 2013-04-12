package lalou.jonathan.personnalBrainTrain.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import lalou.jonathan.personnalBrainTrain.R;
import lalou.jonathan.personnalBrainTrain.business.PersonnalBrainTrainBusiness;
import lalou.jonathan.personnalBrainTrain.business.logic.PersonnalBrainTrainBusinessLogic;
import lalou.jonathan.personnalBrainTrain.entity.Contact;

import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA "Leda" 12 CE.
 * User: Jonathan LALOU
 * Date: 11/04/13
 * Time: 17:04
 */
public class QuestionActivity extends Activity {
    private static String TAG = "personnalBrainTrain";

    private PersonnalBrainTrainBusiness personnalBrainTrainBusiness;

    public void onCreate(Bundle savedInstanceState) {
        final List<Contact> contacts;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);

        personnalBrainTrainBusiness = new PersonnalBrainTrainBusinessLogic(getContentResolver());

        contacts = personnalBrainTrainBusiness.getContacts();
        Collections.shuffle(contacts);
        final Contact goodAnswer;
        // TODO case when there are less than 3 answers
        goodAnswer = contacts.get(0);

        final Button[] buttons = new Button[]{
                (Button) findViewById(R.id.button0),
                (Button) findViewById(R.id.button1),
                (Button) findViewById(R.id.button2),
        };
        for (int i = 0; i < 3; i++) {
            final Button button = buttons[i];
            button.setText(contacts.get(i).getName());
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (button.getText().equals(goodAnswer.getName())) {
                        displayAlertOK();
                    } else {
                        displayAlertKO();
                    }
                }
            });
        }

        final TextView textView = (TextView) findViewById(R.id.question);
        textView.setText("Whom does the number " + goodAnswer.getPhoneNumber() + " belong to?");
    }

    private void displayAlertOK() {
        AlertDialog alertDialog = new AlertDialog.Builder(QuestionActivity.this).create();
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
        AlertDialog alertDialog = new AlertDialog.Builder(QuestionActivity.this).create();
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


}