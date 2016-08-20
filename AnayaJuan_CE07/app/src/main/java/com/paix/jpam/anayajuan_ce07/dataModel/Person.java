// Juan Pablo Anaya
// MDF3 - 201608
// Person

package com.paix.jpam.anayajuan_ce07.dataModel;

import java.io.Serializable;

public class Person implements Serializable {

    /*Properties*/
    String firstName;
    String lastName;
    int age;

    /*Constructor*/
    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    /*Getters*/
    //Get FirstName
    public String getFirstName() {
        return firstName;
    }

    //Get LastName
    public String getLastName() {
        return lastName;
    }

    //Get Age
    public int getAge() {
        return age;
    }

}
