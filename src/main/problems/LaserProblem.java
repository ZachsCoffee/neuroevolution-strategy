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
import machine_learning.functions.Functions;
import machine_learning.abstraction.MLProblem;
import utils.maths.MinMax;
import machine_learning.abstraction.Network;
import machine_learning.mlp.NeuralNetwork;
import main.Problem;
import main.TimeSeriesProblems;

/**
 *
 * @author Zachs
 */
public class LaserProblem extends Problem implements EvolutionComponents, MLProblem {

    public LaserProblem(int epochs) {
        super(
            "./src/dataset/Laser/train.txt",
            "./src/dataset/Laser/validation.txt",
            "./src/dataset/Laser/test.txt"
        );
        
        setDynamicMutation(new MinMax(80, 200), epochs);
    }

    @Override
    public void computePercentOfFitness(Population population) {
//        PercentOfFitness.percentFromTotalBest(population, 1);
        PercentOfFitness.percentFromCurrentBestRanked(population);

    }

    @Override
    public Population recombinationOperator(Population population, int epoch) {
//        return Recombination.randomWithFilter(population, 5, PERSON);
//        return Recombination.random(population, 5, PERSON);
        return Recombination.variableLength(population, 1, PERSON);

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
    public Network buildNetwork(int maxStartValue) {
        hiddenLayerFunction = Functions.sigmoid();
        outputLayerFunction = Functions.groundRelu();
////        
//        NetworkLayer[] layers = new NetworkLayer[2];
//        layers[0] = new NetworkLayer(9, 7, hiddenLayerFunction);
//        layers[1] = new NetworkLayer(1, 9, outputLayerFunction);
//
//        return new BackpropagationMLP(layers, TimeSeriesProblems.LEARNING_RATE, TRAINING_SET, TRAINING_DATA);
        return new BackpropagationMLP(
                (NeuralNetwork) NeuralNetwork.buildRandomSizeNetwork(7, 1, new MinMax(5, 9), new MinMax(2, 2), hiddenLayerFunction, outputLayerFunction),
                TimeSeriesProblems.LEARNING_RATE, 
                TRAINING_SET,
                getTrainingData()
        );
    }

    @Override
    public Network buildRandomNetwork(int maxStartValue) {
//        return buildNetwork(maxStartValue);
        
        hiddenLayerFunction = Functions.sigmoid();
        outputLayerFunction = Functions.groundRelu();
        return new BackpropagationMLP(
                (NeuralNetwork) NeuralNetwork.buildRandomSizeNetwork(7, 1, new MinMax(5, 9), new MinMax(2, 2), hiddenLayerFunction, outputLayerFunction),
                TimeSeriesProblems.LEARNING_RATE, 
                TRAINING_SET,
                getTrainingData()
        );
    }
}
