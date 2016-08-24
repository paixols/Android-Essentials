// Juan Pablo Anaya
// MDF3 - 201608
// WebFormFragment

package com.paix.jpam.anayajuan_ce09.webForm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebViewFragment;

import com.paix.jpam.anayajuan_ce09.R;

public class WebFormFragment extends WebViewFragment {
    
    //TAG
    //private static final String TAG = "WebFormFragment";

    //On Create View
    /*Called to instantiate the View. Creates and returns the Web View*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //Set the View
        View view = inflater.inflate(R.layout.fragment_web_form, container, false);
        //Set the WebView
        WebView webView = (WebView) view.findViewById(R.id.WebView_FormFrag);
        webView.getSettings().setJavaScriptEnabled(true);//Enable Javascript
        webView.loadUrl("file:///android_asset/webform.html");//WebForm html
        //WebView JavaScrip Interface
        webView.addJavascriptInterface(new WebFormInterface(getActivity()), "Android");
        //WebView Client
        webView.setWebViewClient(new WebViewClient());
        return view;
    }
}
