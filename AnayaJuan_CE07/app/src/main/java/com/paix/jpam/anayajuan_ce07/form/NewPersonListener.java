// Juan Pablo Anaya
// MDF3 - 201608
// NewPersonListener

package com.paix.jpam.anayajuan_ce07.form;


import com.paix.jpam.anayajuan_ce07.dataModel.Person;

interface NewPersonListener {

    //Send new Person to be saved on Form Activity
    void saveNewPersonFromForm(Person person);
}
