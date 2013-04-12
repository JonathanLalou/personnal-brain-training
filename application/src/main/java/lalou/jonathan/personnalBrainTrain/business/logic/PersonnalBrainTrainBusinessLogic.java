package lalou.jonathan.personnalBrainTrain.business.logic;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;
import lalou.jonathan.personnalBrainTrain.business.PersonnalBrainTrainBusiness;
import lalou.jonathan.personnalBrainTrain.entity.Contact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public List<Contact> getContacts() {
        final List<Contact> answer;
        final Cursor cursor;

        answer = new ArrayList<Contact>();

        cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, "RANDOM() LIMIT 3");
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

        Collections.shuffle(answer);
        return answer;
    }

}
