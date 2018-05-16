package com.example.chris.orar;

import java.sql.SQLException;
import java.util.Random;


public class Layer {
    // Date ce se contin in baza de date Student
    static final short MEDIA = 0;
    static final short LUCRU = 1;
    static final short CAMIN = 2;
    static final short ORAS = 3;
    static final short OUT_ORAS = 4;
    static final short PARTENER = 5;
    static final short ABSENTE_TOTAL = 6;
    static final short PREZENTE_TOTAL = 7;
    static final short ABSENTE_CURS = 8;
    static final short PREZENTE_CURS = 9;
    static final short ABSENTE_SEMINAR = 10;
    static final short PREZENTE_SEMINAR = 11;
    static final short ABSENTE_LAB = 12;
    static final short PREZENTE_LAB = 13;
    static final short FATA = 14;
    static final short BAIAT = 15;

    static final short CURS = 16;
    static final short SEMINAR = 17;
    static final short LAB = 18;
    static final short LUNI = 19;
    static final short MARTI = 20;
    static final short MIERCURI = 21;
    static final short JOI = 22;
    static final short VINERI = 23;

    static final short PERECHI_TOTAL_ZI = 24;
    static final short PRELEGERI_TOTAL_ZI = 25;
    static final short SEMINARE_TOTAL_ZI = 26;
    static final short LAB_TOTAL_ZI = 27;
    static final short NR_PERECHII_REL = 28;

    static final short NR_PERECHII_ABS = 29;
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

    static final short STATUS = 48; //prezent/absent
    static final short ID = 49;

    private static final float  trainSpeed = 0.2f; // 28 = 89.5 %
                                                        // 0.285 = 87.5
    public float    input[];
    public float    output[];
    static short    hidenLayerOutput[];
    public float    weights[][] = null;
    public float    deltaWeights[][] = null;
    public float    delta[] = null;
    short           index;
    public short    layerNumber = 1;
    public short    neuronNumber = 10;
    public short    inputDataNumber = 48;
    //public static float   outputError;
    public static float   inputError;
    public static float   outputWeightsError[];
    public static float   inputWeightsError[];
    public static float     Layer1[][] = null;
    public static float     Layer2[][] = null;
//    private static float Layer1[][] = {{-0.2220266f, -0.16524756f, -0.032239117f, 0.15500018f, -0.015478629f, 0.052935716f, 0.15174733f, -0.212474f, -0.1450328f, -0.060605094f, -0.08981019f, 0.17116092f, 0.14099862f, -0.13522658f, -0.100132346f, 0.0137383565f, -0.24014169f, 0.05066135f, -0.07760279f, -0.29460922f, 0.090803f, 0.15837057f, -0.1393692f, 0.05692685f, -0.02418665f, 0.124944136f, -0.5182242f},
//            {0.06175338f, -0.17705871f, 0.09374907f, 0.116000354f, 0.08031211f, -0.2720267f, -0.42762813f, 0.44845107f, -0.11784535f, 0.31489244f, 0.038335543f, -0.055666056f, -0.09971687f, 0.3008852f, -0.25785026f, 0.058693327f, -0.04764486f, 0.024128601f, 0.07925691f, 0.25836495f, -0.2526558f, -0.2163055f, -0.020988133f, -0.042483572f, -0.119560935f, 0.25538194f, -0.6588976f},
//            {0.02975096f, -0.16730972f, 0.16675304f, -0.080707625f, 0.20569353f, 0.08104387f, -0.29162136f, 0.014475116f, -0.23036891f, 0.24606805f, 0.13342327f, 0.016557403f, -0.2938785f, -0.15160395f, 0.05283947f, 0.1753111f, 0.09706025f, -0.06509124f, -0.17017423f, -0.2219099f, -0.05223378f, 0.040706888f, -0.14743635f, -0.16393578f, 0.19684494f, 0.093379684f, -0.52273464f},
//            {-0.09058792f, 0.08998928f, 0.20344025f, -0.012638663f, -0.17790551f, 0.10027584f, -3.765624E-4f, -0.06853857f, 0.07007934f, -0.2295835f, -0.014355753f, -0.21265435f, -0.23964325f, -0.037606888f, -0.042702217f, 0.047955364f, 0.16509125f, -0.11807122f, -0.07812571f, 0.09781579f, 0.2565457f, -0.13987303f, 0.13790643f, 0.18225405f, 0.092295475f, -0.1596275f, -0.06768477f},
//            {0.26792198f, -0.20137316f, 0.025515925f, -0.13121733f, 0.086450025f, -0.1932124f, -0.49480078f, 0.5301589f, -0.0926981f, 0.53994644f, -0.056607746f, -0.12777117f, -0.36548138f, 0.09647688f, -0.21449871f, -0.13973753f, 0.036380414f, 0.21572691f, 0.042270865f, -0.14842305f, -0.10122455f, 0.027681516f, 0.12615353f, 0.05597006f, 0.17716865f, 0.011596415f, -0.20313852f},
//            {0.10580413f, -0.15184075f, -0.15854922f, -0.14195804f, 0.17991175f, 0.006041525f, 0.13513039f, -0.07003424f, 0.16564919f, -0.024873337f, -0.09971642f, 0.18300632f, -0.06895765f, -0.09312948f, 0.0971919f, -0.21284659f, -0.17966625f, 0.10816979f, -0.045242164f, 0.016764563f, 0.19489732f, 0.1735305f, 0.26171175f, 0.077495724f, 0.0018742108f, 0.18660049f, -0.3280922f},
//            {-0.010824561f, 0.1961189f, 0.1499745f, -0.0528656f, 0.112916775f, -0.1827918f, -0.17156097f, -0.0042550736f, -0.07247467f, 0.18507883f, -0.04426843f, 0.0035758223f, 0.14145784f, -0.082137965f, 0.18474032f, 0.07732596f, 0.15663075f, -0.09278803f, 0.12107847f, 0.25133476f, 0.15058258f, -0.20081875f, 0.10735721f, 0.18475717f, -0.14557497f, -0.17306814f, -0.123302415f},
//            {-0.19071344f, 0.12302425f, -0.09970145f, 0.17317063f, -4.834974E-4f, 0.037106916f, -0.07350729f, 0.19259368f, 0.11984942f, -0.14817004f, -0.1604858f, 0.018621657f, 0.17913963f, -0.098318905f, -0.07397122f, -0.08805928f, 0.17929776f, -0.22181097f, 0.11811667f, 0.20237651f, 0.11022671f, 0.17695406f, 0.0836327f, 0.026732884f, 0.17393619f, 0.14011873f, -0.19390175f},
//            {-0.099472985f, -0.12982531f, -0.014965592f, 0.1611046f, -0.008867736f, -0.24883796f, -0.5422628f, 0.64877856f, -0.15749717f, 0.48819044f, -0.21764879f, 0.23617283f, 0.023882454f, 0.12762403f, 0.019736324f, -0.014376032f, -0.15132703f, -0.06404285f, -0.009853302f, 0.03424972f, -0.036742553f, -0.1495135f, -0.21199872f, 0.19485609f, -0.17363705f, 0.053146128f, -0.043432847f},
//            {-0.19682966f, -0.021900866f, 0.10569637f, 0.115755334f, 0.18181908f, 0.22353129f, -0.16822764f, -0.05927687f, -0.057156492f, -0.07731625f, -0.12017668f, 0.08683665f, 0.124331765f, -0.18668047f, 0.03308977f, -0.21395393f, -0.05822033f, 0.09981532f, -0.21359871f, -0.24191698f, 0.039570738f, 0.19800054f, -0.026691098f, 0.098656826f, -0.12312802f, 0.056092363f, -0.06738264f}};
//    private static float Layer2[][] = {{0.13445884f, 1.2033983f, 0.36423346f, -0.06546954f, 1.1221769f, -0.04449837f, -0.6555853f, -0.16109897f, 1.5132751f, 0.19252843f, -0.7396095f} };

    public      Layer(int layerNumber, int neuronNumber, int inputDataNumber) {
        this.layerNumber = (short)layerNumber;
        this.neuronNumber = (short)neuronNumber;
        this.inputDataNumber = (short)inputDataNumber;
    }

    public void setInput(float[] input) {
        this.input = input;
    }

    public void backPropagationForLayer2(float status) throws SQLException {
        int     i;
        //float[]   weights;
        float   outputError;

        outputWeightsError = new float[inputDataNumber + 1];
        inputWeightsError = new float[inputDataNumber + 1];
        readWeights();
        //weights = extractNeuron("Layer" + layerNumber, 1, inputDataNumber + 1);
        outputError = (status - output[0]) * sigmoidDerivate(output[0]);
        for (i = 0; i < inputDataNumber; i++) {
            outputWeightsError[i] = trainSpeed * outputError * input[i];
            inputWeightsError[i] = outputError * weights[0][i] * sigmoidDerivate(input[i]);
        }
        outputWeightsError[inputDataNumber] = trainSpeed * outputError;
        for (i = 0; i <= inputDataNumber; i++)
            weights[0][i] += outputWeightsError[i];
        //changeNeuron("Layer" + layerNumber, 1, weights);
        //inputError *= sigmoidDerivate(input)
    }

    public void backPropagationForLayer1() throws SQLException {
        //float   weights[];
        int     j;

        for (int i = 0; i < neuronNumber; i++) {
            readWeights();
            //weights = extractNeuron("Layer" + layerNumber, i + 1, inputDataNumber + 1);
            for (j = 0; j < inputDataNumber; j++)
                weights[i][j] += trainSpeed * inputWeightsError[i] * input[j];
            weights[i][inputDataNumber] += trainSpeed * inputWeightsError[i];
            //changeNeuron("Layer" + layerNumber, i + 1, weights);
        }
    }

    public void readWeights() throws SQLException {
        if (deltaWeights == null) {
            deltaWeights = new float[neuronNumber][inputDataNumber + 1];
            for (int i = 1; i <= neuronNumber; i++) {
                for (int j = 0; j <= inputDataNumber; j++) {
                    deltaWeights[i - 1][j] = 0;
                }
            }
        }
        if (layerNumber == 1)
            weights = Layer1;
        else
            weights = Layer2;
    }

    public void saveWeights() throws SQLException {
        /*if (weights != null) {
            BazaNeuron.changeLayer("layer" + layerNumber, neuronNumber, inputDataNumber + 1, weights);
        }*/
    }

    public void deleteLayer() throws SQLException {
        /*weights = null;
        deleteDataBase("layer" + layerNumber);*/
    }
/*
    public void initLayer () throws Exception {
        Random  random = new Random();
        float   beta;
        float   vectorNorme;
        int     j;

        weights = new float[neuronNumber][inputDataNumber + 1];
        deltaWeights = new float[neuronNumber][inputDataNumber + 1];

        for (int i = 1; i <= neuronNumber; i++) {
            vectorNorme = 0;
            for (j = 0; j < inputDataNumber; j++) {
                deltaWeights[i - 1][j] = 0;
                weights[i - 1][j] = -0.5f + random.nextFloat();
                vectorNorme += (float)Math.pow(weights[i - 1][j], 2);
            }
            vectorNorme = (float)Math.sqrt(vectorNorme);
            for (j = 0; j < inputDataNumber; j++) {
                weights[i - 1][j] *= beta / vectorNorme;
            }
            deltaWeights[i - 1][inputDataNumber] = 0;
            weights[i - 1][inputDataNumber] = -beta + random.nextFloat() % (2 * beta);
        }
    }*/

    public float   sigmoidFunction(float x) {
        return (1 / (1 + (float)Math.exp(-x)));
    }

    public static float   sigmoidDerivate(float x) {
        return (x * (1 - x));
    }

    public float[] getResult() throws Exception {
        float   output[] = new float[neuronNumber];
        float   result;
        //float   weights[] = null;
        int     j;

        if (weights == null || deltaWeights == null) {
            try {
                readWeights();
            }
            catch (Exception e1) {
               // initLayer();
            }
        }
        for (int i = 0; i < neuronNumber; i++) {
            result = weights[i][inputDataNumber];
            for (j = 0; j < inputDataNumber; j++) {
                result += weights[i][j] * input[j];
            }
            output[i] = sigmoidFunction(result);
        }
        this.output = output;
        return output;
    }

    public void     updateWeightsforStep() {
        int     neuron;
        int     inputs;

        for (neuron = 0; neuron < neuronNumber; neuron++) {
            for (inputs = 0; inputs < inputDataNumber; inputs++) {
                weights[neuron][inputs] += trainSpeed * delta[neuron] * /*output[neuron]*/ input[inputs];
            }
            weights[neuron][inputDataNumber] += trainSpeed * delta[neuron];
        }
    }

    public void     updateDeltaWeightsforSet() {
        int     neuron;
        int     inputs;

        for (neuron = 0; neuron < neuronNumber; neuron++) {
            for (inputs = 0; inputs < inputDataNumber; inputs++) {
                deltaWeights[neuron][inputs] *= trainSpeed;
                deltaWeights[neuron][inputs] += (1 - trainSpeed) * delta[neuron] * 0.06 * /*output[neuron]*/ input[inputs];
            }
            deltaWeights[neuron][inputDataNumber] *= trainSpeed;
            deltaWeights[neuron][inputDataNumber] += (1 - trainSpeed) * delta[neuron] * 0.06 /*output[neuron]*/;
            //weights[neuron][inputDataNumber] += trainSpeed * delta[neuron];
        }
    }

    public void     updateWeights() {
        int     neuron;
        int     inputs;

        for (neuron = 0; neuron < neuronNumber; neuron++) {
            for (inputs = 0; inputs <= inputDataNumber; inputs++) {
                weights[neuron][inputs] += deltaWeights[neuron][inputs];
            }
            //weights[neuron][inputDataNumber] += trainSpeed * delta[neuron];
        }
    }
}