// Juan Pablo Anaya
// MDF3 - 201608
// WebFormInterface

package com.paix.jpam.anayajuan_ce09.webForm;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.paix.jpam.anayajuan_ce09.dataModel.BaseballPlayer;

class WebFormInterface {
    //TAG
    //private static final String TAG = "WebFormInterface";
    private final Context context;
    private OnFormSaved listener;

    //Constructor
    public WebFormInterface(Context context) {
        this.context = context;
        //Interface
        if (context instanceof OnFormSaved) {
            listener = (OnFormSaved) context;
        } else {
            throw new IllegalArgumentException("Please Add Interface");
        }
    }

    @JavascriptInterface
    public void getFormInfo(String firstName, String lastName, int age) {
        //Check Empty Fields
        if (isEmpty(firstName) || isEmpty(lastName)) {
            Toast.makeText(context, "No Empty Fields", Toast.LENGTH_SHORT).show();
            return;
        }
        listener.saveFormData(new BaseballPlayer(firstName, lastName, age));
    }

    //Check Empty Fields on WebView
    private static Boolean isEmpty(String inputText) {
        return inputText.equals("");
    }

}
