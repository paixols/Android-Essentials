// Juan Pablo Anaya
// MDF3 - 201608
// OnPersonClicked

package com.paix.jpam.anayajuan_ce07.list;

import com.paix.jpam.anayajuan_ce07.dataModel.Person;

interface OnPersonClicked {
    //Selected Person from list
    void itemClicked(Person person, int position);

    //To Form
    void toFormForNewPerson();
}
