// Juan Anaya
// MDF3 - 201608
// Person

package fullsail.paix.com.AnayaJuan_CE01.DataModel;

import java.io.Serializable;

public class Person implements Serializable {

    //TAG
    //private static final String TAG = "Person";

    /*Properties*/
    private final String firstName;
    private final String lastName;
    private final int age;

    /*Constructor*/
    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    /*Getters*/
    //First Name
    public String getFirstName() {
        return firstName;
    }
    //Last Name
    public String getLastName() {
        return lastName;
    }
    //Age
    public int getAge() {
        return age;
    }
}
