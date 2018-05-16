package com.example.chris.orar;

import java.io.*;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static com.example.chris.orar.DataBaseHelper.updateDataBase;
import static com.example.chris.orar.Layer.ABSENTE_CURS;
import static com.example.chris.orar.Layer.ABSENTE_LAB;
import static com.example.chris.orar.Layer.ABSENTE_SEMINAR;
import static com.example.chris.orar.Layer.ABSENTE_TOTAL;
import static com.example.chris.orar.Layer.BAIAT;
import static com.example.chris.orar.Layer.CURS;
import static com.example.chris.orar.Layer.LAB;
import static com.example.chris.orar.Layer.MEDIA;
import static com.example.chris.orar.Layer.PREZENTE_CURS;
import static com.example.chris.orar.Layer.PREZENTE_LAB;
import static com.example.chris.orar.Layer.PREZENTE_SEMINAR;
import static com.example.chris.orar.Layer.PREZENTE_TOTAL;
import static com.example.chris.orar.Layer.SEMINAR;
import static com.example.chris.orar.ListObjects.ALL;
import static com.example.chris.orar.MainActivity.currentDate;
import static com.example.chris.orar.MainActivity.currentLesson;
import static com.example.chris.orar.MainActivity.listObjects;

/**
 * Created by Alex on 18.11.2017.
 */
public class ReadTest {

    public static final short BULARU_CRISTIAN = 0;
    public static final short BURAGA_DORIN = 1;
    public static final short BURLACU_DENIS = 2;
    public static final short CEBOTARI_ALEXANDRU = 3;
    public static final short CEBOTARI_RADU = 4;
    public static final short CIOBANU_ALINA = 5;
    public static final short CROITOR_VLAD = 6;
    public static final short DELOV_IURIE = 7;
    public static final short DIMA_ANASTASIA = 8;
    public static final short GROSU_ELENA = 9;
    public static final short NECULII_STELIAN = 10;
    public static final short NOUR_NICOLAE = 11;
    public static final short OSTAPOVICI_IGORI = 12;
    public static final short PAVEL_GHEORGHE = 13;
    public static final short PAVLIUC_CRISTIAN = 14;
    public static final short PRIJILEVSCHI_DUMITRU = 15;
    public static final short ROGOJA_SERGIU = 16;
    public static final short STRATULAT_STEFAN = 17;
    public static final short SANDREANU_VLADISLAV = 18;
    public static final short TINCU_CRISTIAN = 19;
    public static final short TRIFAN_IULIAN = 20;
    public static final short CIORBA_VARVARA = 21;

    private static final short  TEST_ID = 0;
    private static final short  TEST_PERECHI_TOTAL_ZI = 1;
    private static final short  TEST_PRELEGERI_TOTAL_ZI = 2;
    private static final short  TEST_SEMINARE_TOTAL_ZI = 3;
    private static final short  TEST_LAB_TOTAL_ZI = 4;
    private static final short  TEST_NR_PERECHII_REL = 5;
    private static final short  TEST_STATUS = 6;

    //public static int[][] test = new int[22][35];
    private static int[][]      testDataMatrix = new int[22][TEST_STATUS + 1];
    public static int           contor_id = 0;
    public static int           contor_file = 0;
    public static int           citeste_filenou = 0;
    public static int           studentsCount = 0;
    public static String[][]    studentNameData =  null;
    public static float[][]     studentDataMatrix = null;
 /*   public static String[][]    studentNameData =  {{"Bularu", "Cristian"},
                                                    {"Buraga", "Dorin"},
                                                    {"Burlacu", "Denis"},
                                                    {"Cebotari", "Alexandru"},
                                                    {"Cebotari", "Radu"},
                                                    {"Ciobanu", "Alina"},
                                                    {"Croitor", "Vlad"},
                                                    {"Delov", "Iurie"},
                                                    {"Dima", "Anastasia"},
                                                    {"Grosu", "Elena"},
                                                    {"Neculii", "Stelian"},
                                                    {"Nour", "Nicolae"},
                                                    {"Ostapovici", "Igori"},
                                                    {"Pavel", "Gheorghe"},
                                                    {"Pavliuc", "Cristian"},
                                                    {"Prijilevschi", "Dumitru"},
                                                    {"Rogoja", "Sergiu"},
                                                    {"Stratulat", "Stefan"},
                                                    {"Sandreanu", "Vladislav"},
                                                    {"Tincu", "Cristian"},
                                                    {"Trifan", "Iulian"},
                                                    {"Ciorba", "Varvara"}};
    public static float[][]     studentDataMatrix = {
            {7.1f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 4.0f, 51.0f, 3.0f, 27.0f, 1.0f, 6.0f, 0.0f, 18.0f, 0.0f, 1.0f},
            {9.08f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 54.0f, 0.0f, 30.0f, 1.0f, 6.0f, 0.0f, 18.0f, 0.0f, 1.0f},
            {7.99f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 18.0f, 37.0f, 12.0f, 18.0f, 6.0f, 1.0f, 0.0f, 18.0f, 0.0f, 1.0f},
            {5.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 52.0f, 3.0f, 29.0f, 1.0f, 7.0f, 0.0f, 16.0f, 2.0f, 0.0f, 1.0f},
            {9.27f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 5.0f, 50.0f, 4.0f, 26.0f, 1.0f, 6.0f, 0.0f, 18.0f, 0.0f, 1.0f},
            {7.81f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 32.0f, 23.0f, 17.0f, 13.0f, 2.0f, 5.0f, 13.0f, 5.0f, 1.0f, 0.0f},
            {6.7f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 55.0f, 0.0f, 30.0f, 0.0f, 7.0f, 0.0f, 18.0f, 0.0f, 0.0f, 1.0f},
            {6.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 48.0f, 7.0f, 23.0f, 7.0f, 7.0f, 0.0f, 18.0f, 0.0f, 0.0f, 1.0f},
            {9.24f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 6.0f, 49.0f, 2.0f, 28.0f, 3.0f, 4.0f, 1.0f, 17.0f, 1.0f, 0.0f},
            {8.8f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 55.0f, 0.0f, 30.0f, 0.0f, 7.0f, 0.0f, 18.0f, 1.0f, 0.0f},
            {6.9f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 2.0f, 53.0f, 1.0f, 29.0f, 1.0f, 6.0f, 0.0f, 18.0f, 0.0f, 1.0f},
            {5.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 55.0f, 0.0f, 30.0f, 0.0f, 7.0f, 0.0f, 18.0f, 0.0f, 0.0f, 1.0f},
            {5.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 53.0f, 1.0f, 29.0f, 0.0f, 7.0f, 0.0f, 17.0f, 1.0f, 0.0f, 1.0f},
            {9.46f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 3.0f, 51.0f, 2.0f, 27.0f, 1.0f, 6.0f, 0.0f, 18.0f, 0.0f, 1.0f},
            {8.58f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 7.0f, 47.0f, 5.0f, 24.0f, 2.0f, 5.0f, 0.0f, 18.0f, 0.0f, 1.0f},
            {7.1f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 6.0f, 48.0f, 4.0f, 25.0f, 2.0f, 5.0f, 0.0f, 18.0f, 0.0f, 1.0f},
            {6.6f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 52.0f, 2.0f, 27.0f, 2.0f, 7.0f, 0.0f, 18.0f, 0.0f, 0.0f, 1.0f},
            {9.41f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 54.0f, 0.0f, 29.0f, 0.0f, 7.0f, 0.0f, 18.0f, 0.0f, 1.0f},
            {6.4f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 14.0f, 40.0f, 11.0f, 18.0f, 2.0f, 5.0f, 1.0f, 17.0f, 0.0f, 1.0f},
            {7.56f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 2.0f, 52.0f, 1.0f, 28.0f, 0.0f, 7.0f, 1.0f, 17.0f, 0.0f, 1.0f},
            {7.2f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 14.0f, 40.0f, 9.0f, 20.0f, 2.0f, 5.0f, 3.0f, 15.0f, 0.0f, 1.0f},
            {7.8f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 9.0f, 45.0f, 7.0f, 22.0f, 1.0f, 6.0f, 1.0f, 17.0f, 1.0f, 0.0f}};*/

    //private static float[]    resultVector = new float[ID + 1];
    private static float[]      resultVector = new float[27 + 1];
    private static int          currentId = 0;
    private static int          nrStudenti = 0;



    private static final short TIP_PERECHE = 16;
    private static final short ZI_SAPT = 17;
    private static final short NR_PERECHII_A = 18;
    private static final short OBIECT = 19;
    private static final short PROF = 20;
    private static final short T_PERECHI_TOTAL_ZI = 21;
    private static final short T_PRELEGERI_TOTAL_ZI = 22;
    private static final short T_SEMINARE_TOTAL_ZI = 23;
    private static final short T_LAB_TOTAL_ZI = 24;
    private static final short T_NR_PERECHII_REL = 25;
    public static final short T_STATUS = 26;
    public static final short T_ID = 27;

    private static final short  status = 20;

    private static void readStudentData() throws SQLException {
        for (int i = MEDIA; i <= BAIAT ; i++) {
            resultVector[i] = studentDataMatrix[currentId][i];
        }
        //System.out.println("ID = " + currentId + " : " + resultVector[BAIAT]);
    }

    public static void setStatus(int status) {
        if (status == 0 || status == 1)
            resultVector[T_STATUS] = status;
    }

    public static void updateStudentData() throws SQLException {
        if (studentDataMatrix == null)
            readStudentData();
        if (resultVector[T_STATUS] == 0 && resultVector[TIP_PERECHE] == CURS) {
            studentDataMatrix[currentId][ABSENTE_TOTAL]++;
            studentDataMatrix[currentId][ABSENTE_CURS]++;
        }
        if (resultVector[T_STATUS] == 1 && resultVector[TIP_PERECHE] == CURS)  {
            studentDataMatrix[currentId][PREZENTE_TOTAL]++;
            studentDataMatrix[currentId][PREZENTE_CURS]++;
        }
        if (resultVector[T_STATUS] == 0 && resultVector[TIP_PERECHE] == SEMINAR)  {
            studentDataMatrix[currentId][ABSENTE_TOTAL]++;
            studentDataMatrix[currentId][ABSENTE_SEMINAR]++;
        }
        if (resultVector[T_STATUS] == 1 && resultVector[TIP_PERECHE] == SEMINAR)  {
            studentDataMatrix[currentId][PREZENTE_TOTAL]++;
            studentDataMatrix[currentId][PREZENTE_SEMINAR]++;
        }
        if (resultVector[T_STATUS] == 0 && resultVector[TIP_PERECHE] == LAB)  {
            studentDataMatrix[currentId][ABSENTE_TOTAL]++;
            studentDataMatrix[currentId][ABSENTE_LAB]++;
        }
        if (resultVector[T_STATUS] == 1 && resultVector[TIP_PERECHE] == LAB)  {
            studentDataMatrix[currentId][PREZENTE_TOTAL]++;
            studentDataMatrix[currentId][PREZENTE_LAB]++;
        }
        //updateStudentByID(t, studentDataMatrix[currentId]);
    }

    private static int countLines(String filename) throws IOException {
        InputStream inputStream = new BufferedInputStream(new FileInputStream(filename));
        try {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = inputStream.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } finally {
            inputStream.close();
        }
    }


    public static void  saveStudentUpdates() throws SQLException {
        if (studentDataMatrix != null)
            updateDataBase();
    }


    public static void      read_matrix() throws FileNotFoundException {
        int     profesor = 0;
        int     obiect = 0;
        int     ziSapt = 0;
        int     nrPerechiiAbs = 0;
        int     nrPerechiiRel;
        int     tipPereche = 0;
        int     perechiTotalZi;
        int     prelegeriTotalZi;
        int     seminareTotalZi;
        int     labTotalZi;
        int     row = -1;
        int     i;
        int     start;
        int     end;

        //file1 = "C:\\Users\\apapu\\Documents\\UTM\\ProjectX\\Tests.01\\Test_" + contor_file + ".txt";
        try {
            tipPereche = listObjects[currentLesson].lessonType;
            ziSapt = currentDate.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY + Layer.LUNI;
            obiect = listObjects[currentLesson].lessonName;
            profesor = listObjects[currentLesson].teacherName;
            nrPerechiiAbs = currentLesson + 1;
            if (listObjects[currentLesson].subgroup == ListObjects.ALL) {
                start = BULARU_CRISTIAN;
                end = CIORBA_VARVARA;
            }
            else if (listObjects[currentLesson].subgroup == ListObjects.FIRST) {
                start = BULARU_CRISTIAN;
                end = NECULII_STELIAN;
            }
            else {
                start = NOUR_NICOLAE;
                end = CIORBA_VARVARA;
            }
            nrStudenti = end - start + 1;
            for (row = 0; row < nrStudenti; row++) {
                testDataMatrix[row][TEST_ID] = row + start;
                perechiTotalZi = 0;
                prelegeriTotalZi = 0;
                seminareTotalZi = 0;
                labTotalZi = 0;
                nrPerechiiRel = 0;
                for (i = 0; i < 4; i++) {
                    if (listObjects[i] != null) {
                        if (listObjects[i].subgroup == listObjects[currentLesson].subgroup || listObjects[i].subgroup == ListObjects.ALL) {
                            perechiTotalZi++;
                            if (listObjects[i].lessonType == ListObjects.CURS)
                                prelegeriTotalZi++;
                            if (listObjects[i].lessonType == ListObjects.SEMINAR)
                                seminareTotalZi++;
                            if (listObjects[i].lessonType == ListObjects.LAB)
                                labTotalZi++;
                            if (currentLesson >= i)
                                nrPerechiiRel++;
                        }
                    }
                }
                testDataMatrix[row][TEST_PERECHI_TOTAL_ZI] = perechiTotalZi;
                testDataMatrix[row][TEST_PRELEGERI_TOTAL_ZI] = prelegeriTotalZi;
                testDataMatrix[row][TEST_SEMINARE_TOTAL_ZI] = seminareTotalZi;
                testDataMatrix[row][TEST_LAB_TOTAL_ZI] = labTotalZi;
                testDataMatrix[row][TEST_NR_PERECHII_REL] = nrPerechiiRel;
                //testDataMatrix[row][TEST_STATUS] = sc.nextInt();
            }
        } catch (Exception ex) {
            citeste_filenou = -1;
            return;
        }
        citeste_filenou = 1;/*
        for (int i = MEDIA; i <= ID; i++) {
            resultVector[i] = 0;*/
        for (i = MEDIA; i <= T_ID; i++) {
            resultVector[i] = 0;
        }
/*
        resultVector[tipPereche] = 1;
        resultVector[ziSapt] = 1;
        resultVector[NR_PERECHII_ABS] = nrPerechiiAbs;
        resultVector[obiect] = 1;
        resultVector[profesor] = 1;*/
        resultVector[TIP_PERECHE] = tipPereche;
        resultVector[ZI_SAPT] = ziSapt;
        resultVector[NR_PERECHII_A] = nrPerechiiAbs;
        resultVector[OBIECT] = obiect;
        resultVector[PROF] = profesor;

    }

    public static float[] getOneTest() throws FileNotFoundException, SQLException {
        int t;

        t = contor_id;
        if (citeste_filenou == 0) {
            read_matrix();
        }
        if (citeste_filenou == -1) {
            System.out.println("Error: Out of tests!");
            System.out.println("Test_" + contor_file);
            System.exit(1);
        }
        currentId = testDataMatrix[t][TEST_ID];
        readStudentData();
        /*resultVector[PERECHI_TOTAL_ZI] = testDataMatrix[t][TEST_PERECHI_TOTAL_ZI];
        resultVector[PRELEGERI_TOTAL_ZI] = testDataMatrix[t][TEST_PRELEGERI_TOTAL_ZI];
        resultVector[SEMINARE_TOTAL_ZI] = testDataMatrix[t][TEST_SEMINARE_TOTAL_ZI];
        resultVector[LAB_TOTAL_ZI] = testDataMatrix[t][TEST_LAB_TOTAL_ZI];
        resultVector[NR_PERECHII_REL] = testDataMatrix[t][TEST_NR_PERECHII_REL];
        resultVector[ID] = contor_id;
        resultVector[STATUS] = testDataMatrix[t][TEST_STATUS];    */


        resultVector[T_PERECHI_TOTAL_ZI] = testDataMatrix[t][TEST_PERECHI_TOTAL_ZI];
        resultVector[T_PRELEGERI_TOTAL_ZI] = testDataMatrix[t][TEST_PRELEGERI_TOTAL_ZI];
        resultVector[T_SEMINARE_TOTAL_ZI] = testDataMatrix[t][TEST_SEMINARE_TOTAL_ZI];
        resultVector[T_LAB_TOTAL_ZI] = testDataMatrix[t][TEST_LAB_TOTAL_ZI];
        resultVector[T_NR_PERECHII_REL] = testDataMatrix[t][TEST_NR_PERECHII_REL];
        resultVector[T_ID] = testDataMatrix[t][TEST_ID];
        resultVector[T_STATUS] = testDataMatrix[t][TEST_STATUS];

        contor_id++;
        if (contor_id == nrStudenti) {
            contor_file++;
            contor_id = 0;
            citeste_filenou = 0;
        }

        return resultVector;
    }
    
    public static float[]   getInput(int position) throws FileNotFoundException, SQLException {

        read_matrix();
        contor_id = position;
        citeste_filenou = 1;
        getOneTest();
        //contor_id = 0;
        citeste_filenou = 0;
        /*
        resultVector[T_PERECHI_TOTAL_ZI] = testDataMatrix[position][TEST_PERECHI_TOTAL_ZI];
        resultVector[T_PRELEGERI_TOTAL_ZI] = testDataMatrix[position][TEST_PRELEGERI_TOTAL_ZI];
        resultVector[T_SEMINARE_TOTAL_ZI] = testDataMatrix[position][TEST_SEMINARE_TOTAL_ZI];
        resultVector[T_LAB_TOTAL_ZI] = testDataMatrix[position][TEST_LAB_TOTAL_ZI];
        resultVector[T_NR_PERECHII_REL] = testDataMatrix[position][TEST_NR_PERECHII_REL];
        resultVector[T_ID] = testDataMatrix[position][TEST_ID];
        resultVector[T_STATUS] = testDataMatrix[position][TEST_STATUS];*/
        return resultVector;
    }

}
