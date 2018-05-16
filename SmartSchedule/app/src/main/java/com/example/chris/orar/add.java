package com.example.chris.orar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.chris.orar.R;

public class add extends Activity {

    private static int  MALE_GENDER = R.id.male_gender;
    private static int  FEMALE_GENDER = R.id.female_gender;
    private static int  DORM_PLACE = R.id.dormPlace;
    private static int  CITY_PLACE = R.id.cityPlace;
    private static int  SUBURB_PLACE = R.id.suburbPlace;
    private static int  activeGender;
    private static int  activePlace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        activeGender = MALE_GENDER;
        activePlace = DORM_PLACE;
        // functie de reset
    }

    public void onClick(View v) {
        Intent intent;

        intent = new Intent(this, add.class);
        startActivity(intent);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void     changeGender(View view) {
        int     id;
        Button  button;

        id = view.getId();
        button = (Button)view;
        if (button.getBackground() == getDrawable(R.drawable.border_right_free))
            button.setText("Olea olea");
        if (button.getBackground() == getDrawable(R.drawable.border_left_free))
            button.setText("Olea olea");

        if (activeGender == FEMALE_GENDER && id == MALE_GENDER) {
            activeGender = MALE_GENDER;
            button.setBackground(getDrawable(R.drawable.border_left));
            button.setTextColor(Color.parseColor("#ffffff"));

            button = (Button) findViewById(FEMALE_GENDER);
            button.setBackground(getDrawable(R.drawable.border_right_free));
            button.setTextColor(Color.parseColor("#222222"));
        }
        else if (activeGender == MALE_GENDER && id == FEMALE_GENDER) {
            activeGender = FEMALE_GENDER;
            button.setBackground(getDrawable(R.drawable.border_right));
            button.setTextColor(Color.parseColor("#ffffff"));

            button = (Button) findViewById(MALE_GENDER);
            button.setBackground(getDrawable(R.drawable.border_left_free));
            button.setTextColor(Color.parseColor("#222222"));
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void     changePlace(View view) {
        int     id;

        id = view.getId();
        if (activePlace != id) {
                activePlace = id;
                setPlace();
        }
    }

    public void     clickSubmitButton(View view) {
        Context context;
        CharSequence    text;
        int             duration;
        Toast toast;

        context = getApplicationContext();
        text = "Done";
        duration = Toast.LENGTH_SHORT;
        toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void     setPlace() {
        Button  button;

        if (activePlace == DORM_PLACE) {
            button = (Button) findViewById(DORM_PLACE);
            button.setBackground(getDrawable(R.drawable.border_left));
            button.setTextColor(Color.parseColor("#ffffff"));

            button = (Button) findViewById(CITY_PLACE);
            button.setBackground(getDrawable(R.drawable.free_border));
            button.setTextColor(Color.parseColor("#222222"));

            button = (Button) findViewById(SUBURB_PLACE);
            button.setBackground(getDrawable(R.drawable.border_right_free));
            button.setTextColor(Color.parseColor("#222222"));
        }
        else if (activePlace == CITY_PLACE) {
            button = (Button) findViewById(DORM_PLACE);
            button.setBackground(getDrawable(R.drawable.border_left_free));
            button.setTextColor(Color.parseColor("#222222"));

            button = (Button) findViewById(CITY_PLACE);
            button.setBackground(getDrawable(R.drawable.border_center));
            button.setTextColor(Color.parseColor("#ffffff"));

            button = (Button) findViewById(SUBURB_PLACE);
            button.setBackground(getDrawable(R.drawable.border_right_free));
            button.setTextColor(Color.parseColor("#222222"));
        }
        else if (activePlace == SUBURB_PLACE) {
            button = (Button) findViewById(DORM_PLACE);
            button.setBackground(getDrawable(R.drawable.border_left_free));
            button.setTextColor(Color.parseColor("#222222"));


            button = (Button) findViewById(CITY_PLACE);
            button.setBackground(getDrawable(R.drawable.free_border));
            button.setTextColor(Color.parseColor("#222222"));

            button = (Button) findViewById(SUBURB_PLACE);
            button.setBackground(getDrawable(R.drawable.border_right));
            button.setTextColor(Color.parseColor("#ffffff"));
        }

    }
}
