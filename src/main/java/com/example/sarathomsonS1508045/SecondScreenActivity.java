package com.example.sarathomsonS1508045;

//
// Name                 __Sara Thomson_______
// Student ID           __S1508045___________
// Programme of Study   __Computing BSc(Hons)
//

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

public class SecondScreenActivity extends AppCompatActivity {

    Toolbar eToolbar;
    TextView details;

    private View secondView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);

        //eToolbar = findViewById(R.id.toolbar);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String s= bundle.getString("Earthquake");
            //eToolbar.setTitle(s);

            details = findViewById(R.id.tvO);
            details.setText(s);



        }

        secondView = findViewById(R.id.secondView);
        secondView.setBackgroundColor(getResources().getColor(R.color.white,null));

    }


}

