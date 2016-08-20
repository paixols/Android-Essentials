// Juan Pablo Anaya
// MDF3 - 201608
// FieldsCheck

package com.paix.jpam.anayajuan_ce07.utilities;

import android.widget.EditText;

public class FieldsCheck {

    /*Check For Empty TextFields*/
    public static Boolean isEmpty(EditText editText) {
        return editText.getText().toString().equals("");
    }
}
