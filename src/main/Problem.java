/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import utils.maths.LinearValues;
import evolution_helper.EvolutionHelper;
import evolutionbuilder.population.PersonManager;
import machine_learning.functions.Function;
import machine_learning.abstraction.MLProblem;
import machine_learning.utlis.DatasetsUtils;
import utils.maths.MinMax;

/**
 *
 * @author Zachs
 */
public abstract class Problem implements MLProblem{
    protected final double[][] TRAINING_SET, VALIDATION_SET, TEST_SET, TRAINING_DATA;
    
    protected Function hiddenLayerFunction, outputLayerFunction;
    
    private final String SEPARATOR = " ";
    private LinearValues dynamicMutation = null, dynamicBreakPointLength = null;
    private int fixedMutation, breakPointLength = 2;
    
    protected final Person PERSON;
    
    protected Problem(String trainingFile, String validationFile, String testFile){
        //diabazw ta dedomena apo ta arxeia
        TRAINING_SET = EvolutionHelper.readDataFromFile(trainingFile, SEPARATOR);
        VALIDATION_SET = EvolutionHelper.readDataFromFile(validationFile, SEPARATOR);
        TEST_SET = EvolutionHelper.readDataFromFile(testFile, SEPARATOR);
        
        TRAINING_DATA = DatasetsUtils.sliceColumns(TRAINING_SET, 0, TRAINING_SET[0].length-1);
        
        System.out.println(
                "Training set: "+TRAINING_SET.length+"\n"+
                "Validation set: "+VALIDATION_SET.length+"\n"+
                "Test set: "+TEST_SET.length+"\n"
        );
        
        PERSON = new Person(this);
    }
    
    //MLProblem interface
    @Override
    public double[][] getTrainingSet() {
        return TRAINING_SET;
    }

    @Override
    public double[][] getValidationSet() {
        return VALIDATION_SET;
    }

    @Override
    public double[][] getTestSet() {
        return TEST_SET;
    }
    
    @Override
    public double[][] getTrainingData(){
        return TRAINING_DATA;
    }
    //END MLProblem
    
    public void setFixedMutation(int mutation){
        if (mutation <= 0) throw new IllegalArgumentException("Muation must be grater than zero");
        
        fixedMutation = mutation;
    }
    public final void setDynamicMutation(MinMax mutationValues, int epochs){
        dynamicMutation = new LinearValues(mutationValues, epochs, LinearValues.Order.DESC);
    }
    public final void setDynamicBreakPointLength(MinMax mutationValues, int epochs){
        dynamicBreakPointLength = new LinearValues(mutationValues, epochs, LinearValues.Order.ACS);
    }
    
    public PersonManager getPersonManager(){
        return PERSON;
    }
    
    public int getMutationChange(int currentEpoch){
        if (dynamicMutation != null){
            return dynamicMutation.compute(currentEpoch) +2;
        }
        else{
            return fixedMutation;
        }
    }
    public int getBreakPointLength(int currentEpoch){
        if (dynamicBreakPointLength != null){
            return dynamicBreakPointLength.compute(currentEpoch) +2;
        }
        else{
            return breakPointLength;
        }
    }
}
