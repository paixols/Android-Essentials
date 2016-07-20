// Juan Anaya
// MDF3 - CE01
// Person

package fullsail.paix.com.j_anaya_ce01.DataModel;

import java.io.Serializable;

public class Person implements Serializable {

    //TAG
    private static final String TAG = "Person";

    /*Properties*/
    private String firstName;
    private String lastName;
    private int age;

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
