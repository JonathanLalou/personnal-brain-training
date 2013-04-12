package lalou.jonathan.personnalBrainTrain.entity;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA "Leda" 12 CE.
 * User: Jonathan LALOU
 * Date: 12/04/13
 * Time: 09:21
 */
public class Contact implements Serializable {
    private String name;

    //    TODO to be replaced with
    // private List<String> phoneNumbers
    private String phoneNumber;

    private String address;

    private String email;
    //    TODO to be replaced with
    // private List<String> emails;

    // TODO replace with Date???
    private String birthday;

    public Contact() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
