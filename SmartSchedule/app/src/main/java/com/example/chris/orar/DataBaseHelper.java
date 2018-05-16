package com.example.chris.orar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

import static android.content.Context.*;
import static com.example.chris.orar.MainActivity.neuronCount;
import static com.example.chris.orar.ReadTest.T_STATUS;
import static com.example.chris.orar.ReadTest.studentDataMatrix;
import static com.example.chris.orar.ReadTest.studentNameData;
import static com.example.chris.orar.ReadTest.studentsCount;
import static com.example.chris.orar.Layer.Layer1;
import static com.example.chris.orar.Layer.Layer2;

/**
 * Created by Alexandru Papuc on 14.01.2018.
 */

public class DataBaseHelper {
    public static SQLiteDatabase    db = null;
    private static final String     TEXT_TYPE = " TEXT";
    private static final String     INT_TYPE = " INTEGER";
    public static final String      STUDENT_TABLE = "student";
    public static final String      LAYER1_TABLE = "layer1";
    public static final String      LAYER2_TABLE = "layer2";
    public static final String[][]  defaultStudentNameData =  {{"Bularu", "Cristian"},
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
    private static float[][]        defaultStudentDataMatrix = {
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
            {7.8f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 9.0f, 45.0f, 7.0f, 22.0f, 1.0f, 6.0f, 1.0f, 17.0f, 1.0f, 0.0f}};
    private static float            defaultLayer1[][] = {{-0.2220266f, -0.16524756f, -0.032239117f, 0.15500018f, -0.015478629f, 0.052935716f, 0.15174733f, -0.212474f, -0.1450328f, -0.060605094f, -0.08981019f, 0.17116092f, 0.14099862f, -0.13522658f, -0.100132346f, 0.0137383565f, -0.24014169f, 0.05066135f, -0.07760279f, -0.29460922f, 0.090803f, 0.15837057f, -0.1393692f, 0.05692685f, -0.02418665f, 0.124944136f, -0.5182242f},
            {0.06175338f, -0.17705871f, 0.09374907f, 0.116000354f, 0.08031211f, -0.2720267f, -0.42762813f, 0.44845107f, -0.11784535f, 0.31489244f, 0.038335543f, -0.055666056f, -0.09971687f, 0.3008852f, -0.25785026f, 0.058693327f, -0.04764486f, 0.024128601f, 0.07925691f, 0.25836495f, -0.2526558f, -0.2163055f, -0.020988133f, -0.042483572f, -0.119560935f, 0.25538194f, -0.6588976f},
            {0.02975096f, -0.16730972f, 0.16675304f, -0.080707625f, 0.20569353f, 0.08104387f, -0.29162136f, 0.014475116f, -0.23036891f, 0.24606805f, 0.13342327f, 0.016557403f, -0.2938785f, -0.15160395f, 0.05283947f, 0.1753111f, 0.09706025f, -0.06509124f, -0.17017423f, -0.2219099f, -0.05223378f, 0.040706888f, -0.14743635f, -0.16393578f, 0.19684494f, 0.093379684f, -0.52273464f},
            {-0.09058792f, 0.08998928f, 0.20344025f, -0.012638663f, -0.17790551f, 0.10027584f, -3.765624E-4f, -0.06853857f, 0.07007934f, -0.2295835f, -0.014355753f, -0.21265435f, -0.23964325f, -0.037606888f, -0.042702217f, 0.047955364f, 0.16509125f, -0.11807122f, -0.07812571f, 0.09781579f, 0.2565457f, -0.13987303f, 0.13790643f, 0.18225405f, 0.092295475f, -0.1596275f, -0.06768477f},
            {0.26792198f, -0.20137316f, 0.025515925f, -0.13121733f, 0.086450025f, -0.1932124f, -0.49480078f, 0.5301589f, -0.0926981f, 0.53994644f, -0.056607746f, -0.12777117f, -0.36548138f, 0.09647688f, -0.21449871f, -0.13973753f, 0.036380414f, 0.21572691f, 0.042270865f, -0.14842305f, -0.10122455f, 0.027681516f, 0.12615353f, 0.05597006f, 0.17716865f, 0.011596415f, -0.20313852f},
            {0.10580413f, -0.15184075f, -0.15854922f, -0.14195804f, 0.17991175f, 0.006041525f, 0.13513039f, -0.07003424f, 0.16564919f, -0.024873337f, -0.09971642f, 0.18300632f, -0.06895765f, -0.09312948f, 0.0971919f, -0.21284659f, -0.17966625f, 0.10816979f, -0.045242164f, 0.016764563f, 0.19489732f, 0.1735305f, 0.26171175f, 0.077495724f, 0.0018742108f, 0.18660049f, -0.3280922f},
            {-0.010824561f, 0.1961189f, 0.1499745f, -0.0528656f, 0.112916775f, -0.1827918f, -0.17156097f, -0.0042550736f, -0.07247467f, 0.18507883f, -0.04426843f, 0.0035758223f, 0.14145784f, -0.082137965f, 0.18474032f, 0.07732596f, 0.15663075f, -0.09278803f, 0.12107847f, 0.25133476f, 0.15058258f, -0.20081875f, 0.10735721f, 0.18475717f, -0.14557497f, -0.17306814f, -0.123302415f},
            {-0.19071344f, 0.12302425f, -0.09970145f, 0.17317063f, -4.834974E-4f, 0.037106916f, -0.07350729f, 0.19259368f, 0.11984942f, -0.14817004f, -0.1604858f, 0.018621657f, 0.17913963f, -0.098318905f, -0.07397122f, -0.08805928f, 0.17929776f, -0.22181097f, 0.11811667f, 0.20237651f, 0.11022671f, 0.17695406f, 0.0836327f, 0.026732884f, 0.17393619f, 0.14011873f, -0.19390175f},
            {-0.099472985f, -0.12982531f, -0.014965592f, 0.1611046f, -0.008867736f, -0.24883796f, -0.5422628f, 0.64877856f, -0.15749717f, 0.48819044f, -0.21764879f, 0.23617283f, 0.023882454f, 0.12762403f, 0.019736324f, -0.014376032f, -0.15132703f, -0.06404285f, -0.009853302f, 0.03424972f, -0.036742553f, -0.1495135f, -0.21199872f, 0.19485609f, -0.17363705f, 0.053146128f, -0.043432847f},
            {-0.19682966f, -0.021900866f, 0.10569637f, 0.115755334f, 0.18181908f, 0.22353129f, -0.16822764f, -0.05927687f, -0.057156492f, -0.07731625f, -0.12017668f, 0.08683665f, 0.124331765f, -0.18668047f, 0.03308977f, -0.21395393f, -0.05822033f, 0.09981532f, -0.21359871f, -0.24191698f, 0.039570738f, 0.19800054f, -0.026691098f, 0.098656826f, -0.12312802f, 0.056092363f, -0.06738264f}};
    private static float            defaultLayer2[][] = {{0.13445884f, 1.2033983f, 0.36423346f, -0.06546954f, 1.1221769f, -0.04449837f, -0.6555853f, -0.16109897f, 1.5132751f, 0.19252843f, -0.7396095f} };

    public static void      initDataBase() {
        if (allTablesExists() == false)
            resetDataBase();
        else {
            extractStudentData();
            extractLayersData();
        }
    }
    
    public static void      closeDataBase() {
        updateDataBase();
    }

    public static void      updateDataBase() {
        updateStudent();
        updateLayers();
    }
    
    public static void      resetDataBase() {
        resetStudentTable();
        resetLayerTables();                
    }
    
    public static void      updateStudent() {
        int         idStudent;
        Cursor      resultSetUpdate;
        String      updateCommand;

        resultSetUpdate = db.rawQuery("SELECT * FROM " + STUDENT_TABLE + ";", null);
        while (resultSetUpdate.moveToNext()) {
            idStudent = resultSetUpdate.getInt(0);
            updateCommand = "UPDATE " + STUDENT_TABLE + " SET";
            updateCommand += " Absente_Total = " + studentDataMatrix[idStudent][Layer.ABSENTE_TOTAL];
            updateCommand += " , Prezente_Total = " + studentDataMatrix[idStudent][Layer.PREZENTE_TOTAL];
            updateCommand += " , Absente_Curs = " + studentDataMatrix[idStudent][Layer.ABSENTE_CURS];
            updateCommand += " , Prezente_Curs = " + studentDataMatrix[idStudent][Layer.PREZENTE_CURS];
            updateCommand += " , Absente_Seminar = " + studentDataMatrix[idStudent][Layer.ABSENTE_SEMINAR];
            updateCommand += " , Prezente_Seminar = " + studentDataMatrix[idStudent][Layer.PREZENTE_SEMINAR];
            updateCommand += " , Prezente_Laborator = " + studentDataMatrix[idStudent][Layer.PREZENTE_LAB];
            updateCommand += " , Absente_Laborator = " + studentDataMatrix[idStudent][Layer.ABSENTE_LAB];
            if (studentDataMatrix[idStudent][Layer.BAIAT] == 1)
                updateCommand += Layer.BAIAT;
            else
                updateCommand += Layer.FATA;

            updateCommand += " WHERE id = " + idStudent;
            db.execSQL(updateCommand);
        }
    }
    
    public  static void     createStudentTable() {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + STUDENT_TABLE + "\n" +
                "(\n" +
                "  ID INT(11) PRIMARY KEY NOT NULL,\n" +
                "  Nume VARCHAR(20) NOT NULL,\n" +
                "  Prenume VARCHAR(20) NOT NULL,\n" +
                "  Media FLOAT NOT NULL,\n" +
                "  Lucru INT(2) DEFAULT '0' NOT NULL,\n" +
                "  Locul INT(11) NOT NULL,\n" +
                "  Partener INT(11) NOT NULL,\n" +
                "  Absente_Total INT(11) NOT NULL,\n" +
                "  Prezente_Total INT(11) NOT NULL,\n" +
                "  Absente_Curs INT(11) NOT NULL,\n" +
                "  Prezente_Curs INT(11) NOT NULL,\n" +
                "  Absente_Seminar INT(11) NOT NULL,\n" +
                "  Prezente_Seminar INT(11) NOT NULL,\n" +
                "  Absente_Laborator INT(11) NOT NULL,\n" +
                "  Prezente_Laborator INT(11) NOT NULL,\n" +
                "  Gen INT(11) NOT NULL\n" +
                ");");
    }

    public static void     resetStudentTable() {
        deleteTable(STUDENT_TABLE);
        createStudentTable();
        studentsCount = 22;
        insertStudentData(defaultStudentDataMatrix, defaultStudentNameData);
        extractStudentData();
    }

    public static void      insertStudentData(float studentData[][], String studentName[][]) {
        int     i;
        String  updateCommand;
        
        for (i = 0; i < studentsCount; i++) {
            updateCommand = "INSERT INTO " + STUDENT_TABLE + " VALUES (" + i;
            updateCommand += ", '" + studentName[i][0] + "'";
            updateCommand += ", '" + studentName[i][1] + "'";
            updateCommand += ", " + studentData[i][Layer.MEDIA];
            updateCommand += ", " + studentData[i][Layer.LUCRU];
            updateCommand += ", ";
            if (studentData[i][Layer.CAMIN] == 1)
                updateCommand += Layer.CAMIN;
            else if (studentData[i][Layer.ORAS] == 1)
                updateCommand += Layer.ORAS;
            else
                updateCommand += Layer.OUT_ORAS;
            updateCommand += ", " + studentData[i][Layer.PARTENER];
            updateCommand += ", " + studentData[i][Layer.ABSENTE_TOTAL];
            updateCommand += ", " + studentData[i][Layer.PREZENTE_TOTAL];
            updateCommand += ", " + studentData[i][Layer.ABSENTE_CURS];
            updateCommand += ", " + studentData[i][Layer.PREZENTE_CURS];
            updateCommand += ", " + studentData[i][Layer.ABSENTE_SEMINAR];
            updateCommand += ", " + studentData[i][Layer.PREZENTE_SEMINAR];
            updateCommand += ", " + studentData[i][Layer.PREZENTE_LAB];
            updateCommand += ", " + studentData[i][Layer.ABSENTE_LAB];
            updateCommand += ", ";
            if (studentData[i][Layer.BAIAT] == 1)
                updateCommand += Layer.BAIAT;
            else
                updateCommand += Layer.FATA;
            updateCommand += ");";
            db.execSQL(updateCommand);
        }
    }

    public static void     extractStudentData() {
        int         idStudent;
        Cursor      resultSetExtract;
        int         aux;

        resultSetExtract = db.rawQuery("SELECT * FROM " + STUDENT_TABLE + ";", null);
        studentsCount = resultSetExtract.getCount();
        studentDataMatrix = new float[studentsCount][Layer.BAIAT + 1];
        studentNameData = new String [studentsCount][2];
        while (resultSetExtract.moveToNext()) {
            idStudent = resultSetExtract.getInt(0);
            studentNameData[idStudent][0] = resultSetExtract.getString(1);
            studentNameData[idStudent][1] = resultSetExtract.getString(2);

            studentDataMatrix[idStudent][Layer.MEDIA] = resultSetExtract.getFloat(3);
            studentDataMatrix[idStudent][Layer.LUCRU] = resultSetExtract.getFloat(4);
            studentDataMatrix[idStudent][Layer.CAMIN] = 0;
            studentDataMatrix[idStudent][Layer.ORAS] = 0;
            studentDataMatrix[idStudent][Layer.OUT_ORAS] = 0;
            aux = resultSetExtract.getInt(5);
            studentDataMatrix[idStudent][aux] = 1f;
            studentDataMatrix[idStudent][Layer.PARTENER] = resultSetExtract.getFloat(6);
            studentDataMatrix[idStudent][Layer.ABSENTE_TOTAL] = resultSetExtract.getFloat(7);
            studentDataMatrix[idStudent][Layer.PREZENTE_TOTAL] = resultSetExtract.getFloat(8);
            studentDataMatrix[idStudent][Layer.ABSENTE_CURS] = resultSetExtract.getFloat(9);
            studentDataMatrix[idStudent][Layer.PREZENTE_CURS] = resultSetExtract.getFloat(10);
            studentDataMatrix[idStudent][Layer.ABSENTE_SEMINAR] = resultSetExtract.getFloat(11);
            studentDataMatrix[idStudent][Layer.PREZENTE_SEMINAR] = resultSetExtract.getFloat(12);
            studentDataMatrix[idStudent][Layer.ABSENTE_LAB] = resultSetExtract.getFloat(13);
            studentDataMatrix[idStudent][Layer.PREZENTE_LAB] = resultSetExtract.getFloat(14);
            aux = resultSetExtract.getInt(15);
            studentDataMatrix[idStudent][Layer.FATA] = 0;
            studentDataMatrix[idStudent][Layer.BAIAT] = 0;
            studentDataMatrix[idStudent][aux] = 1f;
        }
        resultSetExtract.close();
    }

    public static void     deleteTable(String tableName) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
    }


    public static boolean   allTablesExists() {
        Cursor      resultSetExtract;

        createStudentTable();
        resultSetExtract = db.rawQuery("SELECT * FROM " + STUDENT_TABLE + ";", null);
        if (resultSetExtract.moveToNext()) {
            resultSetExtract.close();
            return true;
        }

        createLayerTables();
        resultSetExtract = db.rawQuery("SELECT * FROM " + LAYER1_TABLE + ";", null);
        if (resultSetExtract.moveToNext()) {
            resultSetExtract.close();
            return true;
        }
        resultSetExtract = db.rawQuery("SELECT * FROM " + LAYER2_TABLE + ";", null);
        if (resultSetExtract.moveToNext()) {
            resultSetExtract.close();
            return true;
        }
        resultSetExtract.close();
        return false;
    }

    public static void      createLayerTables() {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + LAYER1_TABLE + "\n" +
                "(\n" +
                "    ID INT(11) PRIMARY KEY NOT NULL,\n" +
                "    Field1 DOUBLE,\n" +
                "    Field2 DOUBLE,\n" +
                "    Field3 DOUBLE,\n" +
                "    Field4 DOUBLE,\n" +
                "    Field5 DOUBLE,\n" +
                "    Field6 DOUBLE,\n" +
                "    Field7 DOUBLE,\n" +
                "    Field8 DOUBLE,\n" +
                "    Field9 DOUBLE,\n" +
                "    Field10 DOUBLE,\n" +
                "    Field11 DOUBLE,\n" +
                "    Field12 DOUBLE,\n" +
                "    Field13 DOUBLE,\n" +
                "    Field14 DOUBLE,\n" +
                "    Field15 DOUBLE,\n" +
                "    Field16 DOUBLE,\n" +
                "    Field17 DOUBLE,\n" +
                "    Field18 DOUBLE,\n" +
                "    Field19 DOUBLE,\n" +
                "    Field20 DOUBLE,\n" +
                "    Field21 DOUBLE,\n" +
                "    Field22 DOUBLE,\n" +
                "    Field23 DOUBLE,\n" +
                "    Field24 DOUBLE,\n" +
                "    Field25 DOUBLE,\n" +
                "    Field26 DOUBLE,\n" +
                "    Field27 DOUBLE\n" +
                ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + LAYER2_TABLE + "\n" +
                "(\n" +
                "    ID INT(11) PRIMARY KEY NOT NULL,\n" +
                "    Field1 DOUBLE,\n" +
                "    Field2 DOUBLE,\n" +
                "    Field3 DOUBLE,\n" +
                "    Field4 DOUBLE,\n" +
                "    Field5 DOUBLE,\n" +
                "    Field6 DOUBLE,\n" +
                "    Field7 DOUBLE,\n" +
                "    Field8 DOUBLE,\n" +
                "    Field9 DOUBLE,\n" +
                "    Field10 DOUBLE,\n" +
                "    Field11 DOUBLE\n" +
                ")");
    }

    public static void      insertLayerData(float layer1[][], float layer2[][]) {
        int     i;
        int     j;
        String  updateCommand;

        for (i = 0; i < neuronCount[0]; i++) {
            updateCommand = "INSERT INTO " + LAYER1_TABLE + " VALUES (" + i;
            for (j = 0; j <= T_STATUS; j++) {
                updateCommand += ", " + layer1[i][j];
            }
            updateCommand += ");";
            db.execSQL(updateCommand);
        }
        for (i = 0; i < neuronCount[1]; i++) {
            updateCommand = "INSERT INTO " + LAYER2_TABLE + " VALUES (" + i;
            for (j = 0; j <= neuronCount[0]; j++) {
                updateCommand += ", " + layer2[i][j];
            }
            updateCommand += ");";
            db.execSQL(updateCommand);
        }
    }

    public static void     resetLayerTables() {
        deleteTable(LAYER1_TABLE);
        deleteTable(LAYER2_TABLE);
        createLayerTables();
        insertLayerData(defaultLayer1, defaultLayer2);
        extractLayersData();
    }
    
    public static void     extractLayersData() {
        int         idNeuron;
        Cursor      resultSetExtract;
        int         j;
        int         aux;

        resultSetExtract = db.rawQuery("SELECT * FROM " + LAYER1_TABLE + ";", null);
        Layer1 = new float[neuronCount[0]][T_STATUS + 1];
        while (resultSetExtract.moveToNext()) {
            idNeuron = resultSetExtract.getInt(0);
            for (j = 0; j <= T_STATUS; j++) {
                Layer1[idNeuron][j] = resultSetExtract.getFloat(j + 1);
            }
        }
        resultSetExtract.close();
        resultSetExtract = db.rawQuery("SELECT * FROM " + LAYER2_TABLE + ";", null);
        Layer2 = new float[neuronCount[1]][neuronCount[0] + 1];
        while (resultSetExtract.moveToNext()) {
            idNeuron = resultSetExtract.getInt(0);
            for (j = 0; j <= neuronCount[0]; j++) {
                Layer2[idNeuron][j] = resultSetExtract.getFloat(j + 1);
            }
        }
        resultSetExtract.close();
    }


    public static void      updateLayers() {
        int         idNeuron;;
        Cursor      resultSetUpdate;
        String      updateCommand;
        int         j;

        resultSetUpdate = db.rawQuery("SELECT * FROM " + LAYER1_TABLE + ";", null);
        while (resultSetUpdate.moveToNext()) {
            idNeuron = resultSetUpdate.getInt(0);
            updateCommand = "UPDATE " + LAYER1_TABLE + " SET";
            updateCommand += " Field1 = " + Layer1[idNeuron][0];
            for (j = 1; j <= T_STATUS; j++) {
                updateCommand += " , Field" + (j + 1) + " = " + Layer1[idNeuron][j];
            }
            updateCommand += " WHERE id = " + idNeuron;
            db.execSQL(updateCommand);
        }
        resultSetUpdate = db.rawQuery("SELECT * FROM " + LAYER2_TABLE + ";", null);
        while (resultSetUpdate.moveToNext()) {
            idNeuron = resultSetUpdate.getInt(0);
            updateCommand = "UPDATE " + LAYER2_TABLE + " SET";
            updateCommand += " Field1 = " + Layer2[idNeuron][0];
            for (j = 0; j <= neuronCount[0]; j++) {
                updateCommand += " , Field" + (j + 1) + " = " + Layer2[idNeuron][j];
            }
            updateCommand += " WHERE id = " + idNeuron;
            db.execSQL(updateCommand);
        }
    }
}
