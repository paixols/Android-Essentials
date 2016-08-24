// Juan Pablo Anaya
// MDF3 - 201608
// WebDetailFragment

package com.paix.jpam.anayajuan_ce09.webDetail;

import android.content.Context;
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

public class WebDetailFragment extends WebViewFragment {

    //TAG
    private static final String TAG = "WebDetailFragment";

    //Interface
    OnDeleteForm listener;
    /*Properties*/
    private String mFirstName;
    private String mLastName;
    private String mAge;

    //Bundle Constructor
    public WebDetailFragment newInstanceOf(String firstName, String lastName, String age) {
        //New Instance
        WebDetailFragment detailWebViewFragment = new WebDetailFragment();
        //Bundle
        Bundle arguments = new Bundle();
        arguments.putString("firstName_key", firstName);
        arguments.putString("lastName_key", lastName);
        arguments.putString("age_key", age);
        //Set bundle to instance
        detailWebViewFragment.setArguments(arguments);
        //Return Instance
        return detailWebViewFragment;
    }

    //On Attach
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Outer Class Interface
        if (context instanceof OnDeleteForm) {
            listener = (OnDeleteForm) context;
        } else {
            throw new IllegalArgumentException("Please Add Interface");
        }
    }

    //On Create
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //Handle Bundle Information
        Bundle arguments = getArguments();
        if (arguments != null) {
            handleArguments(arguments);
        }
    }

    //On Create View
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_web_detail, container, false);
        WebView webView = (WebView) view.findViewById(R.id.WebView_DetailFrag);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/webdetail.html");//WebDetail html
        Log.i(TAG, "onCreateView: " + mFirstName+"/"+ mLastName+"/"+ mAge);
        webView.addJavascriptInterface(new WebDetailInterface(mFirstName, mLastName, mAge),"Android");
        webView.setWebViewClient(new WebViewClient());
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_detail_webviewfrag, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.MenuItem_Delete:
                //TODO delete record
                listener.deleteFrom();
                return true;
        }
        return false;
    }

    //Handle Arguments
    public void handleArguments(Bundle arguments) {
        mFirstName = arguments.getString("firstName_key");
        mLastName = arguments.getString("lastName_key");
        mAge = arguments.getString("age_key");
        //Dev
        if (mFirstName != null && mLastName != null && mAge != null) {
            Log.i(TAG, "handleArguments: " + mFirstName + "/" + mLastName + "/" + mAge);
        }
    }

}
