package com.example.chris.orar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static com.example.chris.orar.DataBaseHelper.allTablesExists;
import static com.example.chris.orar.DataBaseHelper.closeDataBase;
import static com.example.chris.orar.DataBaseHelper.db;
import static com.example.chris.orar.DataBaseHelper.extractStudentData;
import static com.example.chris.orar.DataBaseHelper.initDataBase;
import static com.example.chris.orar.DataBaseHelper.resetStudentTable;
import static com.example.chris.orar.ListObjects.*;

public class MainActivity extends Activity implements View.OnClickListener {
    public  String months[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December"};
    public String   weeks[] = {"", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    public static GregorianCalendar    currentDate;
    //public GregorianCalendar    todayDate;
    public static GregorianCalendar    startWeek;
    public static ListObjects          listObjects[] = new ListObjects[4];
    public static int                   currentLesson;
    public static short   neuronCount[] = {10, 1};
    public static NeuralNetwork neuralNetwork;
//    public static SQLiteDatabase db;
    private static ImageView    logo;
    private static int MALE_GENDER = R.id.male_gender;
    private static int FEMALE_GENDER = R.id.female_gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ListObjects.create();
        ListItem.create();
        logo = (ImageView)findViewById(R.id.logo_year);
        logo.setOnClickListener(this);
        for (int i = 0; i < 4; i++) {
            listObjects[i] = null;
        }
        try {
            neuralNetwork = new NeuralNetwork(2, neuronCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        currentDate = new GregorianCalendar();
        //todayDate = new GregorianCalendar();
        startWeek = new GregorianCalendar();
        startWeek.setFirstDayOfWeek(Calendar.MONDAY);
        //todayDate.setFirstDayOfWeek(Calendar.MONDAY);
        currentDate.setFirstDayOfWeek(Calendar.MONDAY);
        startWeek.set(2017, Calendar.SEPTEMBER, 4);
        setDate();

        db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        initDataBase();




      /*  db.execSQL("CREATE TABLE IF NOT EXISTS" + TABLE_NAME + " ("
                + ID + INT_TYPE + " NOT NULL, "
                + COLUMN_NAME_TITLE + INT_TYPE + ")");
        refreshCount(db);*/
        //DataBaseTest.createTable();
    }

    @Override
    protected void onStop() {
        super.onStop();
       // closeDataBase();
    }

    private void launchActivity() {
        Intent intent = new Intent(this, StudentList.class);

        startActivity(intent);
    }

    private void launchActivity2() {
        Intent intent = new Intent(this, DataBaseTest.class);

        startActivity(intent);
    }




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void     changeGender(View view) {
        int     id;
        Button  button;

        id = view.getId();
        button = (Button)view;
//        button.setText("Olea olea");
        if (button.getBackground() == getDrawable(R.drawable.border_left_free) && id == MALE_GENDER) {
                button.setBackground(getDrawable(R.drawable.border_left));
                button.setTextColor(Color.parseColor("#ffffff"));

                button = (Button) findViewById(R.id.female_gender);
                button.setBackground(getDrawable(R.drawable.border_right_free));
                button.setTextColor(Color.parseColor("#222222"));
            }
        else if (button.getBackground() == getDrawable(R.drawable.border_right_free) && id == FEMALE_GENDER) {
            button.setBackground(getDrawable(R.drawable.border_right));
            button.setTextColor(Color.parseColor("#ffffff"));

            button = (Button) findViewById(R.id.male_gender);
            button.setBackground(getDrawable(R.drawable.border_left_free));
            button.setTextColor(Color.parseColor("#222222"));
        }
    }

    public static int   COUNT = 0;

    public void setDate() {
        String  date;

        date = weeks[currentDate.get(currentDate.DAY_OF_WEEK)] + ", ";
        date += " " + currentDate.get(currentDate.DATE) + " ";
        date += months[currentDate.get(currentDate.MONTH)];
//        date += " " + getWeekCount();
        setDataText(date);
        showLessons();
    }

    public void setDataText(String value) {
        TextView myAwesomeTextView = (TextView)findViewById(R.id.date);

        myAwesomeTextView.setText(value);
    }
//
//    public void onFuckingClick(View view) {
//
//        //Intent intent = new Intent(this, StudentList.class);
//
//        //startActivity(intent);
//        // Create a Gregorian calendar initialized
//        // with the current date and time in the
//        // default locale and timezone.
//
//        GregorianCalendar gcalendar = new GregorianCalendar();
//        setDate(gcalendar);
//        // Display current time and date information.
//    }
//
//
//}
private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String TABLE_NAME = " touch_count";
    public static final String  COLUMN_NAME_TITLE = "count";
    private static final String ID = "id";
    static boolean firstClick = true;

    public void onButton2Click(View view) {
        launchActivity2();
    }
    public void onButtonClick(View view) {
        //boolean firstClick = true;
        if (firstClick) {
            db.execSQL("CREATE TABLE IF NOT EXISTS" + TABLE_NAME + " ("
                    + ID + INT_TYPE + " NOT NULL, "
                    + COLUMN_NAME_TITLE + INT_TYPE + ")");
            db.execSQL("INSERT INTO" + TABLE_NAME + " VALUES (1, 1);");
            firstClick = false;
        }
        else {

            String updateValues;

            COUNT++;
            updateValues = "UPDATE" + TABLE_NAME + " SET ";
            updateValues += COLUMN_NAME_TITLE + " = " + COUNT;
            updateValues += " WHERE ID = " + 1 + ";";
            db.execSQL(updateValues);
        }
        refreshCount(db);
    }

    public void refreshCount(SQLiteDatabase db) {
        TextView    textView;

        Cursor query = db.rawQuery("SELECT * FROM" + TABLE_NAME + ";", null);
        if(query.moveToFirst()){
            COUNT = query.getInt(1);
        }
        query.close();
        //textView = (TextView)findViewById(R.id.myCircle);
        //textView.setText(COUNT + "");

    }
    public void onObjectClick(View view) {
        //view.setVisibility(View.GONE);
        int     id;


        id = getObjectId(view);
//        LinearLayout button;
//        button = (LinearLayout)findViewById(ListObjects.relativeLayout[id]);
//        if (button.isSelected()){
//            button.setSelected(false);
//        } else {
//            //ib.setSelected(false);
//            //put all the other buttons you might want to disable here...
//            button.setSelected(true);
//        }
        currentLesson = id;
        launchActivity();

        /*
        if (id == -1)
            setDataText("Ubil!");
        else {

            for (int i = 0; i < 4; i++) {
                if (i != id)
                    setObjectVisibility(i, View.VISIBLE);
            }
            setObjectVisibility(id, View.GONE);
        }
        setLessonType(0, "Laborator");
        setLessonType(1, "Curs");
        setLessonType(3, "Seminar");*/
    }

    @SuppressLint("ResourceAsColor")
    public void setLessonType(int id, String type) {
        TextView    textView;

        ListObjects.create();
        textView = (TextView)findViewById(ListObjects.lessonTypeField[id]);
        textView.setText(type);
        if (type == "Curs") {
            //textView.setBackgroundResource(R.color.colourlessonyellow);
            textView.setBackgroundColor(Color.parseColor("#F7A327"));
            //textView.setWidth(30);
        }
        if (type == "Laborator") {
            textView.setBackgroundColor(Color.parseColor("#6554f6"));
            //textView.setWidth(60);
        }
        if (type == "Seminar") {
            textView.setBackgroundColor(Color.parseColor("#57db2b"));
            //textView.setWidth(56);
        }
    }

    public void     showLessons() {
        TextView    text;
        int         aux;
        int     weekCount;

        weekCount = getWeekCount();
        tellMeTheLessons(weekCount);
        for (int i = 0; i < 4; i++) {
            if (listObjects[i] != null) {
                text = (TextView)findViewById(ListObjects.roomValueField[i]);
                text.setText(listObjects[i].room/* + " " + listObjects[i].subgroup*/);
                text = (TextView)findViewById(ListObjects.subgroupValueField[i]);
                if (listObjects[i].subgroup == ListObjects.FIRST) {
                    text.setVisibility(View.VISIBLE);
                    text.setText("1'st subgroup");
                }
                else if (listObjects[i].subgroup == ListObjects.SECOND) {
                    text.setVisibility(View.VISIBLE);
                    text.setText("2'nd subgroup");
                }
                else
                    text.setVisibility(View.GONE);
                text = (TextView)findViewById(ListObjects.lessonNameField[i]);
                aux = listObjects[i].lessonName;
                switch (aux) {
                    case ASDN:
                        text.setText("ASDN");
                        break;
                    case APA:
                        text.setText("APA");
                        break;
                    case POO:
                        text.setText("POO");
                        break;
                    case CDE:
                        text.setText("CDE");
                        break;
                    case MMC:
                        text.setText("MMC");
                        break;
                    case LFA:
                        text.setText("LFA");
                        break;
                }
                text = (TextView)findViewById(ListObjects.lessonTypeField[i]);
                aux = listObjects[i].lessonType;
                switch (aux) {
                    case CURS:
                        text.setText("Lecture");
                        text.setBackgroundColor(Color.parseColor("#F7A327"));
                        break;
                    case SEMINAR:
                        text.setText("Seminar");
                        text.setBackgroundColor(Color.parseColor("#57db2b"));
                        break;
                    case LAB:
                        text.setText("Laboratory");
                        text.setBackgroundColor(Color.parseColor("#6554f6"));
                        break;
                }
                text = (TextView)findViewById(ListObjects.teacherNameField[i]);
                aux = listObjects[i].teacherName;
                switch (aux) {
                    case SUDACEVSCHI:
                        text.setText("Viorica SUDACEVSCHI");
                        break;
                    case MUNTEANU:
                        text.setText("Silvia MUNTEANU");
                        break;
                    case CATRUC:
                        text.setText("Mariana CATRUC");
                        break;
                    case BAGRIN:
                        text.setText("Veronica BAGRIN");
                        break;
                    case KULEV:
                        text.setText("Mihail KULEV");
                        break;
                    case BOTNARU:
                        text.setText("Sorin BOTNARU");
                        break;
                    case MIRONOV:
                        text.setText("Bettin MIRONOV");
                        break;
                    case MAGARIU:
                        text.setText("Nicolae MAGARIU");
                        break;
                    case TUTUNARU:
                        text.setText("Eleonora TUTUNARU");
                        break;
                    case LEAHU:
                        text.setText("Alexei LEAHU");
                        break;
                    case DUCA:
                        text.setText("Ludmila DUCA");
                        break;
                    case COJUHARI:
                        text.setText("Irina COJUHARI");
                        break;

                }
                setObjectVisibility(i, View.VISIBLE);
            }
            else
                setObjectVisibility(i, View.GONE);
        }
    }

    public int  getObjectId(View view) {
        int     id;
        
        id = view.getId();
        for (int i = 0; i < 4; i++) {
            if (id == ListObjects.relativeLayout[i])
                return i;
            if (id == ListObjects.lessonTypeField[i])
                return i;
            if (id == ListObjects.lessonNameField[i])
                return i;
            if (id == ListObjects.teacherNameField[i])
                return i;
            if (id == ListObjects.roomValueField[i])
                return i;
        }
        return -1;
    }

    public void     setObjectVisibility(int id, int visibility) {
        RelativeLayout  layout;
        TextView        text;

        layout = (RelativeLayout)findViewById(ListObjects.relativeLayout[id]);
        layout.setVisibility(visibility);
        text = (TextView)findViewById(ListObjects.lessonTypeField[id]);
        text.setVisibility(visibility);
        text = (TextView)findViewById(ListObjects.lessonNameField[id]);
        text.setVisibility(visibility);
        text = (TextView)findViewById(ListObjects.teacherNameField[id]);
        text.setVisibility(visibility);
        text = (TextView)findViewById(ListObjects.roomValueField[id]);
        text.setVisibility(visibility);

    }

    public static int      getWeekCount() {
        int                 date;
        int                 year;
        int                 month;
        int                 weekCount = 0;
        GregorianCalendar   gregorianCalendar;

        gregorianCalendar = new GregorianCalendar();
        date = currentDate.get(Calendar.DATE);
        month = currentDate.get(Calendar.MONTH);
        year = currentDate.get(Calendar.YEAR);
        gregorianCalendar.set(year, month, date);
        if (currentDate.before(startWeek)) {
            while (gregorianCalendar.before(startWeek)) {
                weekCount--;
                gregorianCalendar.add(Calendar.DAY_OF_YEAR, 7);
            }
            return weekCount;
        }
        else {
            //currentDate.add(Calendar.DAY_OF_YEAR, -7);
            while (gregorianCalendar.after(startWeek)) {
                weekCount++;
                gregorianCalendar.add(Calendar.DAY_OF_YEAR, -7);
            }
            return weekCount;
        }
    }

    public void     showNextDay(View view) {
        currentDate.add(Calendar.DAY_OF_YEAR, 1);
        setDate();
    }

    public void     showPrevDay(View view) {
        currentDate.add(Calendar.DAY_OF_YEAR, -1);
        setDate();
    }

    public void     showTodayDate(View view) {
        currentDate = new GregorianCalendar();
        setDate();
    }

    public void     showLessons(GregorianCalendar gCalendar) {

    }

    @Override
    public void onClick(View v) {
        Intent  intent;

        intent = new Intent(this, add.class);
        startActivity(intent);
    }

    public void changePlace(View view) {
    }
}
