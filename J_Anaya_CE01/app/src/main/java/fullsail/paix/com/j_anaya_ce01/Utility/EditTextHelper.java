// Juan Anaya
// MDF3 - CE01
// EditTextHelper
package fullsail.paix.com.j_anaya_ce01.Utility;

import android.widget.EditText;

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
