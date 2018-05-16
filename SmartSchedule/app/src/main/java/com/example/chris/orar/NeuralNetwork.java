package com.example.chris.orar;

import java.sql.SQLException;

import static com.example.chris.orar.ReadTest.T_STATUS;
import static com.example.chris.orar.ReadTest.updateStudentData;

/**
 * Created by Alex on 22.11.2017.
 */
public class NeuralNetwork {
    public Layer    layer[] = null;
    public int      layerCount;
    public float    trainSpeed = 0.2f;
    static int      lastTest = -1;

    public NeuralNetwork(int layerCount, short neuronCount[]) throws Exception {
        this.layerCount = layerCount;
        layer = new Layer[layerCount];
        for (short i = 0; i < layerCount; i++) {
            if (i >= 1)
                layer[i] = new Layer(i + 1, neuronCount[i], neuronCount[i - 1]);
            else
                layer[i] = new Layer(i + 1, neuronCount[i], T_STATUS);
            layer[i].deleteLayer();
            //layer[i].initLayer();
        }
    }

    public float[]      getResult() throws Exception {
        float   input[];

        if (layer == null)
            return null;
        input = ReadTest.getOneTest();
        for (int i = 0; i < layerCount; i++) {
            layer[i].setInput(input);
            input = layer[i].getResult();
        }
        return input;
    }

    public float[]      getResult(float input[]) throws Exception {
        if (layer == null)
            return null;
        for (int i = 0; i < layerCount; i++) {
            layer[i].setInput(input);
            input = layer[i].getResult();
        }
        return input;
    }

    public void         backPropagation(float[] trueResults) {
        //int     layer;
        int     neuron;
        int     currentLayer;
        int     lastLayer;
        int     neuronChildren;
        float   output;
        float   sum;

        lastLayer = layerCount - 1;
        if (layer[lastLayer].delta == null)
            layer[lastLayer].delta = new float[layer[lastLayer].neuronNumber];
        for (neuron = 0; neuron < layer[lastLayer].neuronNumber; neuron++) {
            output = layer[lastLayer].output[neuron];
            layer[lastLayer].delta[neuron] = output * (1 - output) * (trueResults[neuron] - output);
        }

        for (currentLayer = lastLayer - 1; currentLayer >= 0; currentLayer--) {
            if (layer[currentLayer].delta == null)
                layer[currentLayer].delta = new float[layer[currentLayer].neuronNumber];
            for (neuron = 0; neuron < layer[currentLayer].neuronNumber; neuron++) {
                sum = 0;
                for (neuronChildren = 0; neuronChildren < layer[currentLayer + 1].neuronNumber; neuronChildren++) {
                    sum += layer[currentLayer + 1].delta[neuronChildren] * layer[currentLayer + 1].weights[neuronChildren][neuron];
                }
                output = layer[currentLayer].output[neuron];
                layer[currentLayer].delta[neuron] = output * (1 - output) * sum;
            }
        }

    }

    public void     backPropForStep(float[] trueResults) {
        int     currentLayer;
        int     lastLayer;

        lastLayer = layerCount - 1;
        backPropagation(trueResults);
        for (currentLayer = 0; currentLayer <= lastLayer; currentLayer++) {
            layer[currentLayer].updateWeightsforStep();
        }
    }

    public void     backPropForSet(float[] trueResults) {
        int         currentLayer;
        int         lastLayer;

        lastLayer = layerCount - 1;
        if (ReadTest.citeste_filenou == 0) {
            for (currentLayer = 0; currentLayer <= lastLayer; currentLayer++) {
                layer[currentLayer].updateWeights();
            }
        }
        backPropagation(trueResults);
        for (currentLayer = 0; currentLayer <= lastLayer; currentLayer++) {
            layer[currentLayer].updateDeltaWeightsforSet();
        }

    }

    public void checkPowerOfNetwork() throws Exception {
        float output[];
        float input[];
        int   correctResults = 0;
        int   last_test;
        int   last_id;

        last_id = ReadTest.contor_id;
        last_test = ReadTest.contor_file;
        for (int i = 0; i < 200; i++) {
            output = getResult();
            //error = Math.abs(layer1.input[STATUS] - output);
            //squareError += error;
            if (output[0] >= 0.5f && layer[0].input[T_STATUS] == 1)
                correctResults++;
            if (output[0] < 0.5f && layer[0].input[T_STATUS] == 0)
                correctResults++;

        }
        ReadTest.contor_id = last_id;
        ReadTest.contor_file = last_test;
        ReadTest.citeste_filenou = 0;
//        System.out.println("Correct results = " + correctResults);
//        System.out.println("Probabilitatea = " + ((float)correctResults / 200f));
        System.out.println(((float)correctResults / 200f));
    }

    public float[]  getInput() {
        if (layer != null)
            return layer[0].input;
        return null;
    }

    public void trainNetwork(int numberOfTests) throws Exception {
        float   input[];
        float   trueResults[] = new float[1];

        for (int j = 1; j <= numberOfTests; j++) {
//            if (j % 100 == 0)
//                System.out.println("STEP = " + j);
            //input = getOneTest();
            getResult();
            input = getInput();
            updateStudentData();
            trueResults[0] = input[T_STATUS];
            /*
            layer[1].backPropagationForLayer2(trueResults[0]);
            layer[0].backPropagationForLayer1();*/
            backPropForStep(trueResults);
            //backPropForSet(trueResults);
        }
    }

    public void saveWeights() throws SQLException {
        for (int i = 0; i < layerCount; i++) {
            layer[i].saveWeights();
        }
    }
}
