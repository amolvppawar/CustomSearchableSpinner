package com.lithium.customsearchablespinner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.lithium.searchablespinner.SearchableSpinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SearchableSpinner.simpleToast(this,"test");
    }
}