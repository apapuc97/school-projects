package com.example.chris.orar;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Alexandru Papuc on 04.01.2018.
 */

public class ListItem extends AppCompatActivity{
    //TextView            procent;
    /*TextView            name;
    Button              checkBox;
    LinearLayout        frame;*/
    public static int   procent[]; /*= {R.id.procent0, R.id.procent1, R.id.procent2, R.id.procent3,
                                R.id.procent4, R.id.procent5, R.id.procent6, R.id.procent7,
                                R.id.procent8, R.id.procent9, R.id.procent10, R.id.procent11,
                                R.id.procent12, R.id.procent13, R.id.procent14, R.id.procent15,
                                R.id.procent16, R.id.procent17, R.id.procent18, R.id.procent19,
                                R.id.procent20, R.id.procent21};*/
    public static int   name[];/* = {R.id.name0, R.id.name1, R.id.name2, R.id.name3,
                                R.id.name4, R.id.name5, R.id.name6, R.id.name7,
                                R.id.name8, R.id.name9, R.id.name10, R.id.name11,
                                R.id.name12, R.id.name13, R.id.name14, R.id.name15,
                                R.id.name16, R.id.name17, R.id.name18, R.id.name19,
                                R.id.name20, R.id.name21};*/
;
    public static int checkBox[];/* = {R.id.modify0, R.id.modify1, R.id.modify2, R.id.modify3,
                                R.id.modify4, R.id.modify5, R.id.modify6, R.id.modify7,
                                R.id.modify8, R.id.modify9, R.id.modify10, R.id.modify11,
                                R.id.modify12, R.id.modify13, R.id.modify14, R.id.modify15,
                                R.id.modify16, R.id.modify17, R.id.modify18, R.id.modify19,
                                R.id.modify20, R.id.modify21};*/
;
    public static int   layer[];/*[] = {R.id.layer0, R.id.layer1, R.id.layer2, R.id.layer3,
                                R.id.layer4, R.id.layer5, R.id.layer6, R.id.layer7,
                                R.id.layer8, R.id.layer9, R.id.layer10, R.id.layer11,
                                R.id.layer12, R.id.layer13, R.id.layer14, R.id.layer15,
                                R.id.layer16, R.id.layer17, R.id.layer18, R.id.layer19,
                                R.id.layer20, R.id.layer21};*/
    public static boolean  created = false;
   /* public ListItem(int i) {
        procent = (TextView)(findViewById)
    }*/

   public static void  create() {
       if (created == false) {
           created = true;
           procent = new int[22];
           procent[0] = R.id.procent0;
           procent[1] = R.id.procent1;
           procent[2] = R.id.procent2;
           procent[3] = R.id.procent3;
           procent[4] = R.id.procent4;
           procent[5] = R.id.procent5;
           procent[6] = R.id.procent6;
           procent[7] = R.id.procent7;
           procent[8] = R.id.procent8;
           procent[9] = R.id.procent9;
           procent[10] = R.id.procent10;
           procent[11] = R.id.procent11;
           procent[12] = R.id.procent12;
           procent[13] = R.id.procent13;
           procent[14] = R.id.procent14;
           procent[15] = R.id.procent15;
           procent[16] = R.id.procent16;
           procent[17] = R.id.procent17;
           procent[18] = R.id.procent18;
           procent[19] = R.id.procent19;
           procent[20] = R.id.procent20;
           procent[21] = R.id.procent21;
           name = new int[22];
           name[0] = R.id.name0;
           name[1] = R.id.name1;
           name[2] = R.id.name2;
           name[3] = R.id.name3;
           name[4] = R.id.name4;
           name[5] = R.id.name5;
           name[6] = R.id.name6;
           name[7] = R.id.name7;
           name[8] = R.id.name8;
           name[9] = R.id.name9;
           name[10] = R.id.name10;
           name[11] = R.id.name11;
           name[12] = R.id.name12;
           name[13] = R.id.name13;
           name[14] = R.id.name14;
           name[15] = R.id.name15;
           name[16] = R.id.name16;
           name[17] = R.id.name17;
           name[18] = R.id.name18;
           name[19] = R.id.name19;
           name[20] = R.id.name20;
           name[21] = R.id.name21;
           layer = new int[22];
           layer[0] = R.id.layer0;
           layer[1] = R.id.layer1;
           layer[2] = R.id.layer2;
           layer[3] = R.id.layer3;
           layer[4] = R.id.layer4;
           layer[5] = R.id.layer5;
           layer[6] = R.id.layer6;
           layer[7] = R.id.layer7;
           layer[8] = R.id.layer8;
           layer[9] = R.id.layer9;
           layer[10] = R.id.layer10;
           layer[11] = R.id.layer11;
           layer[12] = R.id.layer12;
           layer[13] = R.id.layer13;
           layer[14] = R.id.layer14;
           layer[15] = R.id.layer15;
           layer[16] = R.id.layer16;
           layer[17] = R.id.layer17;
           layer[18] = R.id.layer18;
           layer[19] = R.id.layer19;
           layer[20] = R.id.layer20;
           layer[21] = R.id.layer21;
           checkBox = new int[22];
           checkBox[0] = R.id.modify0;
           checkBox[1] = R.id.modify1;
           checkBox[2] = R.id.modify2;
           checkBox[3] = R.id.modify3;
           checkBox[4] = R.id.modify4;
           checkBox[5] = R.id.modify5;
           checkBox[6] = R.id.modify6;
           checkBox[7] = R.id.modify7;
           checkBox[8] = R.id.modify8;
           checkBox[9] = R.id.modify9;
           checkBox[10] = R.id.modify10;
           checkBox[11] = R.id.modify11;
           checkBox[12] = R.id.modify12;
           checkBox[13] = R.id.modify13;
           checkBox[14] = R.id.modify14;
           checkBox[15] = R.id.modify15;
           checkBox[16] = R.id.modify16;
           checkBox[17] = R.id.modify17;
           checkBox[18] = R.id.modify18;
           checkBox[19] = R.id.modify19;
           checkBox[20] = R.id.modify20;
           checkBox[21] = R.id.modify21;
       }
   }
}
