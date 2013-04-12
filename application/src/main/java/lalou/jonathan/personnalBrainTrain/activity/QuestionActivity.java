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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
        final Map<String, String> namesAndPhones;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);
        personnalBrainTrainBusiness = new PersonnalBrainTrainBusinessLogic(getContentResolver());

        namesAndPhones = personnalBrainTrainBusiness.extractNamesAndPhoneNumbers();
        final List<String> names;
        names = new ArrayList<String>(namesAndPhones.keySet()).subList(0, 3);
        final String goodAnswer = names.get(0);
        final String phoneNumber = namesAndPhones.get(goodAnswer);
        Collections.shuffle(names);
        // TODO use a random access rather than magic numbers
        final Button[] buttons = new Button[]{
                (Button) findViewById(R.id.button0),
                (Button) findViewById(R.id.button1),
                (Button) findViewById(R.id.button2),
        };
        for (int i = 0; i < 3; i++) {
            final Button button = buttons[i];
            button.setText(names.get(i));
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (button.getText().equals(goodAnswer)) {
                        displayAlertOK();
                    }
                }
            });
        }

        final TextView textView = (TextView) findViewById(R.id.question);
        textView.setText("Whom does the number " + phoneNumber + " belong to?");
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
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
                Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }


}