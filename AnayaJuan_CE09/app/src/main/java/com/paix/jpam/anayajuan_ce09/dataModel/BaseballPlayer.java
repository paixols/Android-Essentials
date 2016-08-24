// Juan Pablo Anaya
// MDF3 - 201608
// BaseballPlayer

package com.paix.jpam.anayajuan_ce09.dataModel;

import java.io.Serializable;

public class BaseballPlayer implements Serializable {

    /*Properties*/
    String firstName;
    String lastName;
    int age;
    /*Constructor*/

    public BaseballPlayer(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
    /*Getters*/

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }    /*Local Schema URL Generator*/

    /*URL*/
    public String localSchemaUrl() {
        String ageAsString = String.valueOf(age);
        return "paix://details-screen?first=" + firstName.trim() + "&last=" + lastName.trim() + "&age=" + ageAsString.trim();
    }


}
