package com.example.paix.myretrofitexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.paix.myretrofitexample.gerit.Controller;

/**
 *
 * Tutorial & Inspiration
 * http://www.vogella.com/tutorials/Retrofit/article.html
 *
 * API Testing
 * https://jsonplaceholder.typicode.com/
 *
 * */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        //Start the API Call
        Controller controller = new Controller();
        controller.start();


    }
}
