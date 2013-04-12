package lalou.jonathan.personnalBrainTrain.business.logic;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;
import lalou.jonathan.personnalBrainTrain.business.PersonnalBrainTrainBusiness;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA "Leda" 12 CE.
 * User: Jonathan LALOU
 * Date: 12/04/13
 * Time: 09:24
 */
public class PersonnalBrainTrainBusinessLogic implements PersonnalBrainTrainBusiness {
    private static String TAG = "personnalBrainTrainBusinessLogic";
    private ContentResolver contentResolver;

    public PersonnalBrainTrainBusinessLogic(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    /**
     * gets a map of names and phone numbers
     *
     * @return
     */
    public Map<String, String> extractNamesAndPhoneNumbers() {
        // keys: names; values: phone number
        // TODO should be a Map<String, List<String>>
        final Map<String, String> answer;
        final ContentResolver cr;
        final Cursor cur;

        answer = new HashMap<String, String>();

        cur = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    final Cursor pCur;
                    pCur = contentResolver.query(
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
