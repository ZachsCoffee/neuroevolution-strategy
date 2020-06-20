/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machine_learning.mlp.backprop;

import machine_learning.functions.Function;
import machine_learning.mlp.NetworkLayer;
import machine_learning.mlp.NeuralNetwork;
import utils.maths.MinMax;

/**
 *
 * @author arx-dev-3a-19
 */
public class MLPBackpropagation extends NeuralNetwork {
    private final double[] TRAINING_TARGETS;
    private final double[][] TRAINING_SET;
    
    private double[] networkOutput;
    private double[][] signalsError;
    
    public MLPBackpropagation(NeuralNetwork neuralNetwork, double[][] trainingSet, double[] trainingTargets){
        this(getNetworkLayers(neuralNetwork), trainingSet, trainingTargets);
    }
    
    public MLPBackpropagation(NetworkLayer[] networkLayers, double[][] trainingSet, double[] trainingTargets){
        super(networkLayers, 1);
        
//        if (learnRate < 0 || learnRate > 1) throw new IllegalArgumentException(
//                "learnRate must be a number between 0 and 1, learnRate="+learnRate
//        );
        if (trainingSet == null) throw new IllegalArgumentException(
                "trainingSet not null"
        );
        if (trainingTargets == null) throw new IllegalArgumentException(
                "trainingTargets not null"
        );
        
//        this.LEARN_RATE = learnRate;
        this.TRAINING_SET = trainingSet;
        this.TRAINING_TARGETS = trainingTargets;
//        
        signalsError = new double[layers.length][];
//        neurosOutput = new double[layers.length][];
//        
        for (int i=0; i<layers.length; i++){
            signalsError[i] = new double[layers[i].getNeuronsCount()];
//            neurosOutput[i] = new double[layers[i].getNeuronsCount()];
        }
    }
    
    @Override
    public double[] compute(double[] features){
        return networkOutput = super.compute(features);
    }
    
    public void propagate(){
        for (int i=0; i<TRAINING_SET.length; i++){
            //compute the output
            compute(TRAINING_SET[i]);
            
            
        }
    }
    
    private void computeSignalError(){
        final int OUTPUT_LAYER_POSITION = layers.length -1;
        
        for (int i=OUTPUT_LAYER_POSITION; i>=0; i--){
            
        }
    }
    
    private static NetworkLayer[] getNetworkLayers(NeuralNetwork neuralNetwork){
        NetworkLayer tempLayer;
        Function layerFunction;
        
        NetworkLayer[] networkLayers = new NetworkLayer[neuralNetwork.getLayerCount()];
        for (int i=0; i<networkLayers.length; i++){
            tempLayer = neuralNetwork.getLayerAt(i);
            layerFunction = tempLayer.getFunction();
            
            if (layerFunction != null){
                networkLayers[i] = new NetworkLayer(
                        tempLayer.getNeuronsCount(), 
                        tempLayer.getNeuronAt(0).getInputsCount(),
                        tempLayer.getFunction()
                );
            }
            else{
                networkLayers[i] = new NetworkLayer(
                        tempLayer.getNeuronsCount(), 
                        tempLayer.getNeuronAt(0).getInputsCount()
                );
            }
        }
        
        return networkLayers;
    }
}
