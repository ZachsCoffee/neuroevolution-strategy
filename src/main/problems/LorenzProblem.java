/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.problems;

import evolutionbuilder.components.EvolutionComponents;
import evolutionbuilder.components.Mutation;
import evolutionbuilder.components.PercentOfFitness;
import evolutionbuilder.components.Recombination;
import evolutionbuilder.components.Selection;
import evolutionbuilder.population.Population;
import machine_learning.mlp.BackpropagationMLP;
import machine_learning.functions.Function;
import machine_learning.functions.Functions;
import machine_learning.abstraction.MLProblem;
import utils.maths.MinMax;
import machine_learning.abstraction.Network;
import machine_learning.mlp.NetworkLayer;
import machine_learning.mlp.NeuralNetwork;
import main.Problem;
import main.TimeSeriesProblems;

/**
 *
 * @author Zachs
 */
public class LorenzProblem extends Problem implements EvolutionComponents, MLProblem {
    
    public LorenzProblem(int epochs){
        super(
            "./src/dataset/Lorenz/train.txt", 
            "./src/dataset/Lorenz/validation.txt", 
            "./src/dataset/Lorenz/test.txt"
        );
        
        setDynamicMutation(new MinMax(80, 200), epochs);
    }
    
    @Override
    public void computePercentOfFitness(Population population) {
//        PercentOfFitness.percentFromTotalBest(population, 2);
        PercentOfFitness.percentFromCurrentBestRanked(population);

    }

    @Override
    public Population recombinationOperator(Population population, int epoch) {
//        return Recombination.random(population, 5, PERSON);
        return Recombination.variableLength(population, 1, PERSON);
//        return Recombination.randomWithFilter(population, 5, PERSON);

    }

    @Override
    public Population selectionMethod(Population population) {
        return Selection.tournament(population, 3, TimeSeriesProblems.WITH_PERCENT_OF_FITNESS);
    }

    @Override
    public void mutationMethod(Population population, int epoch, int maxEpoch) {
//        Mutation.mutation(population, getMutationChange(epoch), 1, true, PERSON);
        Mutation.variableLength(population, getMutationChange(epoch), 5, true, PERSON);
    }

    @Override
    public NeuralNetwork buildNetwork(int maxStartValue) {
        hiddenLayerFunction = Functions.sigmoid();
        final Function gauss = Functions.gauss();
        outputLayerFunction = null;
//        outputLayerFunction = Functions.leakyRelu();
        
        return new BackpropagationMLP(
            (NeuralNetwork) NeuralNetwork.buildRandomSizeNetwork(3, 1, new MinMax(5, 9), new MinMax(2, 2), hiddenLayerFunction, null),
            TimeSeriesProblems.LEARNING_RATE, 
            TRAINING_SET,
            getTrainingData()
        );
//        
//        NetworkLayer[] layers = new NetworkLayer[2];
//        layers[0] = new NetworkLayer(9, 3, hiddenLayerFunction);
//        layers[1] = new NetworkLayer(1, 9);
//
//        return new BackpropagationMLP(layers, TimeSeriesProblems.LEARNING_RATE, TRAINING_SET, TRAINING_DATA);
    }

    @Override
    public Network buildRandomNetwork(int maxStartValue) {
//        return buildNetwork(maxStartValue);
        hiddenLayerFunction = Functions.sigmoid();
        final Function gauss = Functions.gauss();
        outputLayerFunction = null;
//
////        outputLayerFunction = Functions.leakyRelu();
//        
        return new BackpropagationMLP(
            (NeuralNetwork) NeuralNetwork.buildRandomSizeNetwork(3, 1, new MinMax(5, 9), new MinMax(2, 2), hiddenLayerFunction, null),
            TimeSeriesProblems.LEARNING_RATE, 
            TRAINING_SET,
            getTrainingData()
        );
    }
    
}
