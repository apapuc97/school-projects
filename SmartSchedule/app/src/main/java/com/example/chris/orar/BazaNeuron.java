package com.example.chris.orar;

/**
 * Created by Alexandru Papuc on 19.12.2017.
 */

import android.widget.TextView;

import java.sql.*;


public class BazaNeuron {

    public static final String URL = "jdbc:mysql://localhost:3306/projectx";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "ProjectX_1234";
    public static final String SELECT = "SELECT * FROM student";

    private static Connection connection = null;
    private static Statement statement = null;
    private static boolean conected = false;

    public static void makeConnection() throws SQLException {
        if (conected == false) {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            statement = connection.createStatement();
            conected = true;
        }
    }

    public static float extractStudentDates() throws SQLException {

        makeConnection();
        Statement   statementExtract = connection.createStatement();
        ResultSet   resultSetExtract = statementExtract.executeQuery(SELECT);
        int         aux;
        int         PersonID;

        makeConnection();
        /*
        while (resultSetExtract.next()) {
            PersonID = resultSetExtract.getInt(1);
            if (PersonID == idStudent) {
                return resultSetExtract.getFloat("Media");
            }
        }*/
        return 23f;
    }
}
