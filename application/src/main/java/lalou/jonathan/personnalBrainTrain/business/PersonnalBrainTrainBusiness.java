package lalou.jonathan.personnalBrainTrain.business;

import lalou.jonathan.personnalBrainTrain.entity.Contact;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA "Leda" 12 CE.
 * User: Jonathan LALOU
 * Date: 12/04/13
 * Time: 09:27
 */
public interface PersonnalBrainTrainBusiness {
    Map<String, String> extractNamesAndPhoneNumbers();

    List<Contact> getContacts();
}
