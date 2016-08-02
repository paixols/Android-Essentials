// Juan Anaya
// MDF3 - 201608
// EditTextHelper
package fullsail.paix.com.AnayaJuan_CE01.Utility;


import android.widget.EditText;

public class EditTextHelper {

    //TAG
    //private static final String TAG = "EditTextHelper";

    //EditText empty check
    public static Boolean isEmpty(EditText editText) {
        return editText.getText().toString().equals("");
    }

}
