/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machine_learning;

/**
 *
 * @author main
 */
public class StackLearning {
    private double[][] stack;
    
    public StackLearning(int numberOfLevels, int numberOfTrainingData){
        if (numberOfTrainingData < 1){
            throw new IllegalArgumentException("Number of training data must be greater than zero.");
        }
        
        if (numberOfLevels < 1){
            throw new IllegalArgumentException("Number of levels must be greater than zero.");
        }
        
        stack = new double[numberOfLevels][numberOfTrainingData];
        
        for (int i=0; i<numberOfLevels; i++){
            for (int j=0; j<numberOfTrainingData; j++){
                stack[i][j] = Math.random();
            }
        }
    }
    
    public double getValueAt(int position){
        return stack[position%stack.length][position%stack[0].length];
    }
    
    public void setValueAt(int position, double value){
        stack[position%stack.length][position%stack[0].length] = value;
    }
    
    public int getValuesCount(){
        return stack.length*stack[0].length;
    }
    
    public double compute(double[] features){
        if (features.length != stack[0].length){
            throw new IllegalArgumentException("Features length must be same of number of training data.");
        }
        
        double sum = 0;
        for (int i=0; i<stack.length; i++){
            sum += sumOfProduct(features, stack[i]);
        }
        
        return sum;
    }
    
    public static double sumOfProduct(double[] table1, double[] table2){
        if (table1.length != table2.length){
            throw new IllegalArgumentException("The two tables must have the same length.");
        }
        
        double sum = 0;
        for (int i=0; i<table1.length; i++){
            sum += table1[i] * table2[i];
        }
        
        return sum;
    }
}
