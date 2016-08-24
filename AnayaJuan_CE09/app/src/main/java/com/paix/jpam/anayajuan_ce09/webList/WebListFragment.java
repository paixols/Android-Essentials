// Juan Pablo Anaya
// MDF3 - 201608
// WebListFragment

package com.paix.jpam.anayajuan_ce09.webList;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebViewFragment;

import com.paix.jpam.anayajuan_ce09.R;

public class WebListFragment extends WebViewFragment {
    
    //TAG
    private static final String TAG = "WebListFragment";

    /*Properties*/
    OnNewForm listener;

    //On Attach
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Interface
        if (context instanceof OnNewForm) {
            listener = (OnNewForm) context;
        } else {
            throw new IllegalArgumentException("Please Add Interface");
        }

    }

    //On Create
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    //On Create View
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //Return Custom WebView
        return inflater.inflate(R.layout.fragment_web_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Set the WebView
        final WebView webView = (WebView) view.findViewById(R.id.WebView_ListFrag);
        webView.getSettings().setJavaScriptEnabled(true);//Enable Javascript
        webView.loadUrl("file:///android_asset/weblist.html");
        //WebView Javascript Interface
        webView.addJavascriptInterface(new WebListInterface(getActivity()), "Android");
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("paix://")) {
                    Log.i(TAG, "shouldOverrideUrlLoading: " + "CUSTOM SCHEMA !" + url);
                    //Get parameters from URI
                    Uri uri = Uri.parse(url);
                    String firstName = uri.getQueryParameter("first");
                    String lastName = uri.getQueryParameter("last");
                    String age = uri.getQueryParameter("age");
                    listener.newDetail(firstName, lastName, age);
                    return true;
                }
                Log.i(TAG, "shouldOverrideUrlLoading: " + "NO CUSTOM SCHEMA!");
                return false;
            }
        });
    }

    /*Menu*/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_list_webviewfrag, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.MenuItem_NewForm:
                //ListWebViewFrag - > ListActivity Interface
                listener.newFrom(); //Transition to Form Activity
                return true;
        }
        return false;
    }
}
