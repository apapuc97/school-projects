package com.example.chris.orar;

import java.util.Calendar;

import static com.example.chris.orar.MainActivity.*;
/**
 * Created by Alexandru Papuc on 04.01.2018.
 */

public class ListObjects {
    static final short CURS = 16;
    static final short SEMINAR = 17;
    static final short LAB = 18;

    static final short ASDN = 30;
    static final short APA = 31;
    static final short POO = 32;
    static final short CDE = 33;
    static final short MMC = 34;
    static final short LFA = 35;

    static final short SUDACEVSCHI = 36;
    static final short MUNTEANU = 37;
    static final short CATRUC = 38;
    static final short BAGRIN = 39;
    static final short KULEV = 40;
    static final short BOTNARU = 41;
    static final short MIRONOV = 42;
    static final short MAGARIU = 43;
    static final short TUTUNARU = 44;
    static final short LEAHU = 45;
    static final short DUCA = 46;
    static final short COJUHARI = 47;

    static final short ALL = 0;
    static final short FIRST = 1;
    static final short SECOND = 2;



    public static int       relativeLayout[];
    public static int       lessonTypeField[];
    public static int       lessonNameField[];
    public static int       teacherNameField[];
    public static int       roomValueField[];
    public static int       subgroupValueField[];
    public static boolean   create = false;
    public short     lessonType;
    public short     lessonName;
    public short     teacherName;
    public short     subgroup;
    public String    room;


    public static void create() {
        if (create == false) {
            create = true;
            relativeLayout = new int[4];
            relativeLayout[0] = R.id.relative0;
            relativeLayout[1] = R.id.relative1;
            relativeLayout[2] = R.id.relative2;
            relativeLayout[3] = R.id.relative3;
            lessonTypeField = new int[4];
            lessonTypeField[0] = R.id.lessonType0;
            lessonTypeField[1] = R.id.lessonType1;
            lessonTypeField[2] = R.id.lessonType2;
            lessonTypeField[3] = R.id.lessonType3;
            lessonNameField = new int[4];
            lessonNameField[0] = R.id.lessonName0;
            lessonNameField[1] = R.id.lessonName1;
            lessonNameField[2] = R.id.lessonName2;
            lessonNameField[3] = R.id.lessonName3;
            teacherNameField = new int[4];
            teacherNameField[0] = R.id.teacher0;
            teacherNameField[1] = R.id.teacher1;
            teacherNameField[2] = R.id.teacher2;
            teacherNameField[3] = R.id.teacher3;
            roomValueField = new int[4];
            roomValueField[0] = R.id.room0;
            roomValueField[1] = R.id.room1;
            roomValueField[2] = R.id.room2;
            roomValueField[3] = R.id.room3;
            subgroupValueField = new int[4];
            subgroupValueField[0] = R.id.subgroup0;
            subgroupValueField[1] = R.id.subgroup1;
            subgroupValueField[2] = R.id.subgroup2;
            subgroupValueField[3] = R.id.subgroup3;
        }
    }


    public static void     tellMeTheLessons(int weekCount) {
        int     dayOfWeek;

        dayOfWeek = currentDate.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY || weekCount < 1) {
            for (int i = 0; i < 4; i++) {
                listObjects[i] = null;
            }
            return;
        }
        while (weekCount > 4)
            weekCount -= 4;
        if (dayOfWeek == Calendar.MONDAY) {
            if (listObjects[0] == null)
                listObjects[0] = new ListObjects();
            listObjects[0].lessonType = CURS;
            listObjects[0].lessonName = CDE;
            listObjects[0].teacherName = MIRONOV;
            listObjects[0].room = "3-3";
            listObjects[0].subgroup = ALL;

            if (listObjects[1] == null)
                listObjects[1] = new ListObjects();
            listObjects[1].lessonType = CURS;
            listObjects[1].lessonName = MMC;
            listObjects[1].teacherName = TUTUNARU;
            listObjects[1].room = "3-3";
            listObjects[1].subgroup = ALL;

            if (listObjects[2] == null)
                listObjects[2] = new ListObjects();
            if (listObjects[3] == null)
                listObjects[3] = new ListObjects();
            listObjects[2].lessonType = LAB;
            listObjects[3].lessonType = LAB;

            if (weekCount == 1 || weekCount == 2) {
                listObjects[2].subgroup = FIRST;
                listObjects[3].subgroup = FIRST;
            }
            else {
                listObjects[2].subgroup = SECOND;
                listObjects[3].subgroup = SECOND;
            }
            if (weekCount == 1 || weekCount == 3) {
                listObjects[2].lessonName = LFA;
                listObjects[2].teacherName = DUCA;
                listObjects[2].room = "503";
                listObjects[3].lessonName = LFA;
                listObjects[3].teacherName = DUCA;
                listObjects[3].room = "503";
            }
            else {
                listObjects[2].lessonName = ASDN;
                listObjects[2].teacherName = MUNTEANU;
                listObjects[2].room = "217";
                listObjects[3].lessonName = ASDN;
                listObjects[3].teacherName = MUNTEANU;
                listObjects[3].room = "217";
            }
        }
        else if (dayOfWeek == Calendar.TUESDAY) {
            if (listObjects[0] == null)
                listObjects[0] = new ListObjects();
            listObjects[0].lessonType = CURS;
            listObjects[0].lessonName = POO;
            listObjects[0].teacherName = KULEV;
            listObjects[0].room = "3-3";
            listObjects[0].subgroup = ALL;

            if (listObjects[1] == null)
                listObjects[1] = new ListObjects();
            listObjects[1].lessonType = CURS;
            listObjects[1].lessonName = APA;
            listObjects[1].teacherName = CATRUC;
            listObjects[1].room = "3-3";
            listObjects[1].subgroup = ALL;

            if (listObjects[2] == null)
                listObjects[2] = new ListObjects();
            if (weekCount == 1 || weekCount == 3) {
                listObjects[2].lessonType = LAB;
                listObjects[2].lessonName = APA;
                listObjects[2].teacherName = BAGRIN;
                listObjects[2].room = "114";
                listObjects[2].subgroup = ALL;
                listObjects[3] = null;
            }
            else {
                if (listObjects[3] == null)
                    listObjects[3] = new ListObjects();
                listObjects[2].lessonType = LAB;
                listObjects[2].lessonName = MMC;
                listObjects[2].teacherName = TUTUNARU;
                listObjects[2].room = "503";
                listObjects[3].lessonType = LAB;
                listObjects[3].lessonName = MMC;
                listObjects[3].teacherName = TUTUNARU;
                listObjects[3].room = "503";
                if (weekCount == 2) {
                    listObjects[2].subgroup = FIRST;
                    listObjects[3].subgroup = FIRST;
                } else {
                    listObjects[2].subgroup = SECOND;
                    listObjects[3].subgroup = SECOND;
                }
            }
        }
        else if (dayOfWeek == Calendar.WEDNESDAY) {
            if (listObjects[0] == null)
                listObjects[0] = new ListObjects();
            listObjects[0].lessonType = LAB;
            listObjects[0].lessonName = CDE;
            listObjects[0].teacherName = MAGARIU;
            listObjects[0].room = "406";

            if (listObjects[1] == null)
                listObjects[1] = new ListObjects();
            listObjects[1].lessonType = LAB;
            listObjects[1].lessonName = CDE;
            listObjects[1].teacherName = MAGARIU;
            listObjects[1].room = "406";
            if (weekCount == 1 || weekCount == 3) {
                listObjects[1].subgroup = FIRST;
                listObjects[0].subgroup = FIRST;
            }
            else {
                listObjects[1].subgroup = SECOND;
                listObjects[0].subgroup = SECOND;
            }

            if (listObjects[2] == null)
                listObjects[2] = new ListObjects();
            listObjects[2].lessonType = CURS;
            listObjects[2].lessonName = ASDN;
            listObjects[2].teacherName = SUDACEVSCHI;
            listObjects[2].room = "3-3";
            listObjects[2].subgroup = ALL;

            if (listObjects[3] == null)
                listObjects[3] = new ListObjects();
            listObjects[3].lessonType = SEMINAR;
            listObjects[3].lessonName = LFA;
            listObjects[3].teacherName = DUCA;
            listObjects[3].room = "512";
            listObjects[3].subgroup = ALL;
        }
        else if (dayOfWeek == Calendar.THURSDAY) {
            if (listObjects[0] == null)
                listObjects[0] = new ListObjects();
            listObjects[0].lessonType = LAB;
            listObjects[0].lessonName = POO;
            listObjects[0].teacherName = BOTNARU;
            listObjects[0].room = "503";

            if (listObjects[1] == null)
                listObjects[1] = new ListObjects();
            listObjects[1].lessonType = LAB;
            listObjects[1].lessonName = POO;
            listObjects[1].teacherName = BOTNARU;
            listObjects[1].room = "503";
            if (weekCount == 2 || weekCount == 4) {
                listObjects[1].subgroup = FIRST;
                listObjects[0].subgroup = FIRST;
            }
            else {
                listObjects[1].subgroup = SECOND;
                listObjects[0].subgroup = SECOND;
            }

            if (listObjects[2] == null)
                listObjects[2] = new ListObjects();
            listObjects[2].lessonType = CURS;
            listObjects[2].lessonName = CDE;
            listObjects[2].teacherName = MIRONOV;
            listObjects[2].room = "3-3";
            listObjects[2].subgroup = ALL;

            if (listObjects[3] == null)
                listObjects[3] = new ListObjects();
            listObjects[3].lessonType = CURS;
            listObjects[3].lessonName = MMC;
            listObjects[3].teacherName = LEAHU;
            listObjects[3].room = "3-3";
            listObjects[3].subgroup = ALL;
        }
        else if (dayOfWeek == Calendar.FRIDAY) {
            if (listObjects[0] == null)
                listObjects[0] = new ListObjects();
            listObjects[0].lessonType = LAB;
            listObjects[0].lessonName = MMC;
            listObjects[0].teacherName = LEAHU;
            listObjects[0].room = "407";
            if (weekCount == 1 || weekCount == 3)
                listObjects[0].subgroup = SECOND;
            else
                listObjects[0].subgroup = FIRST;

            if (listObjects[1] == null)
                listObjects[1] = new ListObjects();
            listObjects[1].lessonType = CURS;
            listObjects[1].lessonName = LFA;
            listObjects[1].teacherName = COJUHARI;
            listObjects[1].room = "3-3";
            listObjects[1].subgroup = ALL;

            if (listObjects[2] == null)
                listObjects[2] = new ListObjects();
            listObjects[2].lessonType = SEMINAR;
            if (weekCount == 1 || weekCount == 3) {
                listObjects[2].lessonName = POO;
                listObjects[2].teacherName = BOTNARU;
            }
            else {
                listObjects[2].lessonName = APA;
                listObjects[2].teacherName = BAGRIN;
            }
            listObjects[2].room = "512";
            listObjects[2].subgroup = ALL;

            if (weekCount == 1 || weekCount == 3) {
                if (listObjects[3] == null)
                    listObjects[3] = new ListObjects();
                listObjects[3].lessonType = SEMINAR;
                listObjects[3].lessonName = ASDN;
                listObjects[3].teacherName = MUNTEANU;
                listObjects[3].room = "312";
                listObjects[3].subgroup = ALL;
            }
            else {
                listObjects[3] = null;
            }
        }
    }


}
