package com.example.chris.orar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.example.chris.orar.MainActivity.currentLesson;
import static com.example.chris.orar.MainActivity.listObjects;
import static com.example.chris.orar.MainActivity.neuralNetwork;
import static com.example.chris.orar.ReadTest.*;
import static com.example.chris.orar.ReadTest.T_ID;
import static com.example.chris.orar.ReadTest.updateStudentData;

public class StudentList extends AppCompatActivity {
    private static final short  RED = 0;
    private static final short  GREEN = 1;
    private static final short  YELLOW = 2;
    private static final short  READ_MODE = 0;
    private static final short  MODIFY_MODE = 1;

    public static ListItem  allItems[] = new ListItem[22];
    public final int        maxItems = 22;
    public static int       viewMode;
    public static int       studentsCount;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orar);
        try {
            showThePresence();
        } catch (Exception e) {
            e.printStackTrace();
        }
        viewMode = READ_MODE;
        showMode();
/*
        setVisibility(0, View.GONE);
        setProcent(1, 45);
        setName(1, "Papuc Alexandru");*/
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void showThePresence() throws Exception {
        float[]     input;
        int         id;
        float       result;
        int         i;
        String      name = "";

        if (listObjects[currentLesson].subgroup == ListObjects.ALL)
            studentsCount = ReadTest.CIORBA_VARVARA + 1;
        else
            studentsCount = ReadTest.NECULII_STELIAN + 1;
        for (i = 0; i < studentsCount; i++) {
            result = (neuralNetwork.getResult()[0] * 100);
            input = neuralNetwork.getInput();
            //updateStudentData();
            id = (int) input[T_ID];
            setProcent(i, result);
            switch (id) {
                case BULARU_CRISTIAN:
                    name = "Bularu Cristian";
                    break;
                case BURAGA_DORIN:
                    name = "Buraga Dorin";
                    break;
                case BURLACU_DENIS:
                    name = "Burlacu Denis";
                    break;
                case CEBOTARI_ALEXANDRU:
                    name = "Cebotari Alexandru";
                    break;
                case CEBOTARI_RADU:
                    name = "Cebotari Radu";
                    break;
                case CIOBANU_ALINA:
                    name = "Ciobanu Alina";
                    break;
                case CROITOR_VLAD:
                    name = "Croitor Vlad";
                    break;
                case DELOV_IURIE:
                    name = "Delov Iurie";
                    break;
                case DIMA_ANASTASIA:
                    name = "Dima Anastasia";
                    break;
                case GROSU_ELENA:
                    name = "Grosu Elena";
                    break;
                case NECULII_STELIAN:
                    name = "Neculii Stelian";
                    break;
                case NOUR_NICOLAE:
                    name = "Nour Nicolae";
                    break;
                case OSTAPOVICI_IGORI:
                    name = "Ostapovici Igori";
                    break;
                case PAVEL_GHEORGHE:
                    name = "Pavel Gheorghe";
                    break;
                case PAVLIUC_CRISTIAN:
                    name = "Pavliuc Cristian";
                    break;
                case PRIJILEVSCHI_DUMITRU:
                    name = "Prijilevschi Dumitru";
                    break;
                case ROGOJA_SERGIU:
                    name = "Rogoja Sergiu";
                    break;
                case STRATULAT_STEFAN:
                    name = "Stratulat Stefan";
                    break;
                case SANDREANU_VLADISLAV:
                    name = "Sandreanu Vladislav";
                    break;
                case TINCU_CRISTIAN:
                    name = "Tincu Cristian";
                    break;
                case TRIFAN_IULIAN:
                    name = "Trifan Iulian";
                    break;
                case CIORBA_VARVARA:
                    name = "Ciorba Varvara";
                    break;
            }
            setName(i, name);
            setVisibility(i, VISIBLE);
        }
        for (i = studentsCount; i <= CIORBA_VARVARA; i++) {
            setVisibility(i, View.GONE);
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void cancelButtonClick(View view) {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        view.startAnimation(shake);
        if (viewMode == MODIFY_MODE)
            viewMode = READ_MODE;

        showMode();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void modifyActionButton(View view) throws Exception {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        view.startAnimation(shake);
        if (viewMode == READ_MODE)
            viewMode = MODIFY_MODE;
        else {
            Context         context;
            CharSequence    text;
            float           input[];
            float           trainResults[];
            int             duration;
            int             start;
            int             end;
            int             i;
            Toast           toast;
            CheckBox        checkBox;

            viewMode = READ_MODE;
            if (listObjects[currentLesson].subgroup == ListObjects.ALL) {
                start = ReadTest.BULARU_CRISTIAN;
                end = ReadTest.CIORBA_VARVARA;
            }
            else if (listObjects[currentLesson].subgroup == ListObjects.FIRST) {
                start = ReadTest.BULARU_CRISTIAN;
                end = ReadTest.NECULII_STELIAN;
            }
            else {
                start = ReadTest.NOUR_NICOLAE;
                end = ReadTest.CIORBA_VARVARA;
            }
            trainResults = new float[1];
            for (i = start; i <= end; i++) {

                input = ReadTest.getInput(i - start);
                checkBox = (CheckBox)findViewById(ListItem.checkBox[i - start]);
                if (checkBox.isChecked())
                    trainResults[0] = 1f;
                else
                    trainResults[0] = 0f;
                setStatus((int)trainResults[0]);
                updateStudentData();
//                if (i != end)
//                    ReadTest.citeste_filenou = 1;
//                neuralNetwork.backPropForSet(trainResults);
                neuralNetwork.getResult(input);
                neuralNetwork.backPropForStep(trainResults);

            }
            showThePresence();
            saveStudentUpdates();
            context = getApplicationContext();
            text = "Done";
            duration = Toast.LENGTH_SHORT;
            toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        showMode();
        /*
        id = view.getId();
        for (i = 0; i <= CIORBA_VARVARA; i++)
            if (ListItem.checkBox[i] == id) {
                position = i;
                break;
            }
        input = ReadTest.getInput(position);
        result = neuralNetwork.getResult(input);
        button = (Button) findViewById(id);
        button.setText("" + (int) (result[0] * 100) + " " + position);

        if (result[0] * 100 < 50)
            trueResults[0] = 1;
        else
            trueResults[0] = 0;*/
        //setStatus((int)trueResults[0]);
        /*
        updateStudentData();
        //trueResults[0] = input[T_STATUS];
        neuralNetwork.backPropForStep(trueResults);
        showThePresence();*/
    }

    public void     onLayoutClick(View view) {
        int             id;
        int             i;
        CheckBox        checkBox;

        if (viewMode == READ_MODE)
            return;
        id = view.getId();
        for (i = 0; i < maxItems; i++) {
            if (ListItem.layer[i] == id || ListItem.procent[i] == id)
                break;
        }
        checkBox = (CheckBox)findViewById(ListItem.checkBox[i]);
        if (checkBox.isChecked())
            checkBox.setChecked(false);
        else
            checkBox.setChecked(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void showMode() {
        int             i;
        CheckBox        checkBox;
        Button          cancelButton;
        TextView        textView;
        LinearLayout    layout;

        if (viewMode == READ_MODE) {
            for (i = 0; i < studentsCount; i++) {
                checkBox = (CheckBox)findViewById(ListItem.checkBox[i]);
                checkBox.setVisibility(GONE);
                cancelButton = (Button)findViewById(R.id.cancelButton);
                cancelButton.setVisibility(GONE);
                layout = (LinearLayout)findViewById(ListItem.layer[i]);
                layout.setEnabled(false);
            }
        }
        else {
            for (i = 0; i < studentsCount; i++) {
                checkBox = (CheckBox)findViewById(ListItem.checkBox[i]);
                cancelButton = (Button)findViewById(R.id.cancelButton);
                cancelButton.setVisibility(VISIBLE);

                textView = (TextView) findViewById(ListItem.procent[i]);
                double  probability;
                String  string = (String) textView.getText();

                string = string.replace('%', '0');
                probability = Double.parseDouble(string);

                if (probability >= 50)
                    checkBox.setChecked(true);
                else
                    checkBox.setChecked(false);
                checkBox.setVisibility(VISIBLE);
                layout = (LinearLayout)findViewById(ListItem.layer[i]);
                layout.setEnabled(true);
            }

        }
    }

    public void setVisibility(int id, int visibility) {
        LinearLayout    linearLayout;
        Button          button;

        ListItem.create();
        linearLayout = (LinearLayout) findViewById(ListItem.layer[id]);
        button = (Button)findViewById(ListItem.checkBox[id]);
        linearLayout.setVisibility(visibility);
        button.setVisibility(visibility);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setColor(int id, int color) {
        TextView    textView;

        ListItem.create();
        textView = (TextView) findViewById(ListItem.procent[id]);
        if (color == GREEN)
            //textView.setBackgroundColor(Color.parseColor("#50E2A0"));
            textView.setBackground(getDrawable(R.drawable.free_circle_green));
        else if (color == YELLOW)
            textView.setBackground(getDrawable(R.drawable.free_circle_yellow));
        else if (color == RED)
            textView.setBackground(getDrawable(R.drawable.free_circle_red));
            //textView.setBackgroundColor(Color.parseColor("#EB8686"));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setProcent(int id, int procent) {
        TextView    textView;

        ListItem.create();
        textView = (TextView) findViewById(ListItem.procent[id]);
        textView.setText("" + procent);
        if (procent >= 80)
            setColor(id, GREEN);
        else if (procent >= 20)
            setColor(id, YELLOW);
        else
            setColor(id, RED);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("DefaultLocale")
    public void setProcent(int id, float procent) {
        TextView    textView;
        String      text;

        text = String.format("%.1f", procent) + "%";
        ListItem.create();
        textView = (TextView) findViewById(ListItem.procent[id]);
        textView.setText(text);
        if (procent >= 70f)
            setColor(id, GREEN);
        else if (procent >= 30f)
            setColor(id, YELLOW);
        else
            setColor(id, RED);
    }

    public void setName(int id, String name) {
        TextView    textView;

        ListItem.create();
        textView = (TextView) findViewById(ListItem.name[id]);
        textView.setText(name);
    }
}
