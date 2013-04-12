package lalou.jonathan.personnalBrainTrain.business.logic;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;
import lalou.jonathan.personnalBrainTrain.business.PersonnalBrainTrainBusiness;
import lalou.jonathan.personnalBrainTrain.entity.Contact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        final Cursor cur;

        answer = new HashMap<String, String>();

        // XXX check if possible to replace "RANDOM()" with "RANDOM() LIMIT 3"
        cur = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, "RANDOM() LIMIT 3");
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

    public List<Contact> getContacts() {
        final List<Contact> answer;
        final Cursor cursor;

        answer = new ArrayList<Contact>();

        cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
//        cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, "RANDOM() LIMIT 3");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                final Contact contact;
                contact = new Contact();
                final String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                final String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
// TODO add mail
//                final String email = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.Data.MIMETYPE.));
                contact.setId(id);
                contact.setName(name);
                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    final Cursor phoneCursor;
                    phoneCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    Log.i(TAG, "*** Contact: " + name);
                    while (phoneCursor.moveToNext()) {
                        final String phoneNumber;
                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.i(TAG, "Contact: " + name + ", phone: " + phoneNumber);
                        contact.setPhoneNumber(phoneNumber);
                    }
                    answer.add(contact);
                    phoneCursor.close();
                }
            }
        }

        return answer;
    }

}
