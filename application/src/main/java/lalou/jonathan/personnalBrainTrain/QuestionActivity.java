package lalou.jonathan.personnalBrainTrain;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
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

    public void onCreate(Bundle savedInstanceState) {
        final Map<String, String> namesAndPhones;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);

        namesAndPhones = extractNamesAndPhoneNumbers();
        final List<String> names;
        final Button[] buttons = new Button[]{
                (Button) findViewById(R.id.button0),
                (Button) findViewById(R.id.button1),
                (Button) findViewById(R.id.button2),
        };
        names = new ArrayList<String>(namesAndPhones.keySet());
        // TODO use a random access rather than magic numbers
        for (int i = 0; i < 3; i++) {
            buttons[i].setText(names.get(i));
        }


    }

    /**
     * gets a map of names and phone numbers
     *
     * @return
     */
    private Map<String, String> extractNamesAndPhoneNumbers() {
        // keys: names; values: phone number
        // TODO should be a Map<String, List<String>>
        final Map<String, String> answer;
        final ContentResolver cr;
        final Cursor cur;

        answer = new HashMap<String, String>();

        cr = getContentResolver();
        cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    final Cursor pCur;
                    pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    Log.i(TAG, "*** Contact: " + name);
                    while (pCur.moveToNext()) {
                        final String phoneNumber;
                        phoneNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.i(TAG, "Contact: " + name + ", phone: " + phoneNumber);
                        answer.put(name, phoneNumber);
                    }
                    pCur.close();
                }
            }
        }
        return answer;
    }


}