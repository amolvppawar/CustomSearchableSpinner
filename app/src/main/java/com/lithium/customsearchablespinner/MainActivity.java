package com.lithium.customsearchablespinner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.lithium.searchablespinner.searchable.SearchableSpinner;


public class MainActivity extends AppCompatActivity {

    SearchableSpinner searchableSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}