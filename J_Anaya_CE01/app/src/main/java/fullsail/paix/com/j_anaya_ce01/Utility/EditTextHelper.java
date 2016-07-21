// Juan Anaya
// MDF3 - CE01
// EditTextHelper
package fullsail.paix.com.j_anaya_ce01.Utility;

import android.os.Bundle;
import android.widget.EditText;

import fullsail.paix.com.j_anaya_ce01.DataModel.Person;

public class EditTextHelper {

    //TAG
    private static final String TAG = "EditTextHelper";

    //EditText empty check
    public static Boolean isEmpty(EditText editText) {
        if (editText.getText().toString().equals("")) {
            return true;
        } else {
            return false;
        }
    }

}
