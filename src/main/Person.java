/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import evolutionbuilder.population.PersonManager;
import java.util.Arrays;
import machine_learning.functions.Function;
import machine_learning.abstraction.MLProblem;
import machine_learning.abstraction.Network;
import machine_learning.mlp.NetworkLayer;
import machine_learning.mlp.NeuralNetwork;
import machine_learning.abstraction.TimeNetwork;
import machine_learning.mlp.BackpropagationMLP;
import machine_learning.mlp.MLPBackpropagation;

/**
 *
 * @author Zachs
 */
class Person extends Genes implements PersonManager {
    private MLProblem mlProblem;
    
    protected Person(MLProblem mlProblem){
        if (mlProblem == null) throw new IllegalArgumentException("Argument mlProblem not null!");
        
        this.mlProblem = mlProblem;
    }
    
    @Override
    public evolutionbuilder.population.Person newPerson() {
        return new evolutionbuilder.population.Person(
            mlProblem.buildNetwork(Genes.maxStartValue)
        );
    }

    @Override
    public evolutionbuilder.population.Person newRandomPerson() {
        return new evolutionbuilder.population.Person(
            mlProblem.buildRandomNetwork(Genes.maxStartValue)
        );
    }
    
    @Override
    public evolutionbuilder.population.Person newSameLengthAs(evolutionbuilder.population.Person person) {
        NeuralNetwork givenNetwork = (NeuralNetwork) person.getGeneCode();
        
        NetworkLayer[] networkLayers = new NetworkLayer[givenNetwork.getLayerCount()];
        
        NetworkLayer firstLayer = givenNetwork.getLayerAt(0);
        Function firstLayerFunction = firstLayer.getFunction();
        int pastLayerNeurons = firstLayer.getNeuronsCount();
        
        if (firstLayerFunction != null){
//            if (givenNetwork.getLayerAt(0).getNeuronAt(0).getInputsCount()-1 == 6) throw new RuntimeException("AAAA");
            networkLayers[0] = new NetworkLayer(pastLayerNeurons, givenNetwork.getLayerAt(0).getNeuronAt(0).getInputsCount(), firstLayerFunction);
        }
        else{
            networkLayers[0] = new NetworkLayer(pastLayerNeurons, givenNetwork.getLayerAt(0).getNeuronAt(0).getInputsCount());
        }
        
        NetworkLayer tempLayer;
        Function tempLayerFunction;
        for (int i=1; i<networkLayers.length; i++){
            tempLayer = givenNetwork.getLayerAt(i);
            tempLayerFunction = tempLayer.getFunction();
            
            if (tempLayerFunction != null){
                networkLayers[i] = new NetworkLayer(tempLayer.getNeuronsCount(), pastLayerNeurons, tempLayer.getFunction());
            }
            else{
                networkLayers[i] = new NetworkLayer(tempLayer.getNeuronsCount(), pastLayerNeurons);
            }
            
            pastLayerNeurons = tempLayer.getNeuronsCount();
        }
        
//        return new evolutionbuilder.population.Person(new NeuralNetwork(networkLayers, 1));
        return new evolutionbuilder.population.Person(
                new BackpropagationMLP(
                        networkLayers, 
                        TimeSeriesProblems.LEARNING_RATE, 
                        mlProblem.getTrainingSet(), 
                        mlProblem.getTrainingData()
                )
        );

    }
    
    @Override
    public double computeFitness(evolutionbuilder.population.Person person) {
        //LaserProblem.fitnessEvaluations++;
        //System.out.println("Training "+ mlProblem.getTrainingSet().length);
        return computeFitness(person, mlProblem.getTrainingSet());
    }
  
    
//    static double computeFitness(evolutionbuilder.population.Person person, double[][] dataSet){
//        Network network = (Network) person.getGeneCode();
//        
//        //krataw thn 8esh tou target apo to training set
//        int target = dataSet[0].length -1;
//        
//        double sum = 0, predictedValue;
//        for (int i=0; i<dataSet.length; i++) {
//            predictedValue = network.compute(Arrays.copyOf(dataSet[i], dataSet[i].length-1))[0];
//            sum += Math.sqrt(Math.pow(dataSet[i][target] - predictedValue, 2));
////            sum += Math.abs(dataSet[i][target] - predictedValue);
//            
//        }
//        
//        return 1 - sum/dataSet.length;
////        return 1 - sum/dataSet.length;
//    }
    static double computeFitness(evolutionbuilder.population.Person person, double[][] dataSet){
        double fitness;
        
        if (person.getGeneCode() instanceof TimeNetwork){
            TimeNetwork timeNetwork = (TimeNetwork) person.getGeneCode();
            timeNetwork.startCompute();
            
            fitness = compute(timeNetwork, dataSet);
            
            timeNetwork.endCompute();
        }
        else{
            Network network = (Network) person.getGeneCode();
            //krataw thn 8esh tou target apo to training set
            
            fitness = compute(network, dataSet);
        }
        
        return fitness;
    }
    
    private static double compute(Network network, double[][] dataSet){
        //krataw thn 8esh tou target apo to training set
        int target = dataSet[0].length -1;

        double sum = 0, predictedValue;
        for (int i=0; i<dataSet.length; i++) {
            predictedValue = network.compute(Arrays.copyOf(dataSet[i], dataSet[i].length-1))[0];
            sum += Math.pow(dataSet[i][target] - predictedValue, 2);
        }

        return 1 - Math.sqrt(sum/dataSet.length);
    }
}