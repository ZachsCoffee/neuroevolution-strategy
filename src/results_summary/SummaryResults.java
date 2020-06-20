/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package results_summary;

import java.util.ArrayList;

/**
 *
 * @author main
 */
public class SummaryResults{
    public static final int 
            FITNESS_MAX = 0, 
            FITNESS_MIN = 1, 
            FITNESS_MEAN = 2, 
            HIT_MAX = 3, 
            HIT_MIN = 4, 
            HIT_MEAN = 5,
            MISS_MAX = 6,
            MISS_MIN = 7,
            MISS_MEAN = 8,
            VALIDATION_ERROR_MAX = 9,
            VALIDATION_ERROR_MIN = 10,
            VALIDATION_ERROR_MEAN = 11,
            VALIDATION_ERROR_FITNESS_MAX = 12,
            VALIDATION_ERROR_FITNESS_MIN = 13,
            VALIDATION_ERROR_FITNESS_MEAN = 14;
    
    private double[] offsets = new double[5];
    
    private double[] summary = new double[15];
    private ArrayList<Result> list = new ArrayList();
    
    public void addResult(Result result){
        list.add(result);
    }
    
    public Result getResultAt(int position){
        return list.get(position);
    }
    
    public void clear(){
        list.clear();
        //offsets = new double[5];
        //summary = new double[15];
    }
    
    public boolean compute(){
        int size = list.size();
        if (size <= 0){
            return false;
        }
        
        double[][] results = new double[size][];
        
        for (int i=0; i<size; i++){
            results[i] = list.get(i).results;
        }
        
        double[] computedMMM;
        for (int i=0; i<summary.length/3; i++){
            computedMMM = findMaxMinMean(results, i);
            
            for (int j=0; j<3; j++){
                summary[i*3+j] = computedMMM[j];
            }
        }
        return true;
    }
    
    private double[] findMaxMinMean(double[][] table, int column){
        double[] result = {0, 0, 0};// {max, min, mean}
        
        result[1] = table[0][column];//gia na einai to arxiko min
        
        for (int i=0; i<table.length; i++){
            if (table[i][column] > result[0])//max
                result[0] = table[i][column];
            else if (table[i][column] < result[1])//min
                result[1] = table[i][column];
            
            result[2] += table[i][column];
        }
        result[2] /= table.length;
        
        offsets[column] = 100 * (result[2]-result[1])/(result[0]-result[1]);
        offsets[column] -= 50;
        return result;
    }
    
    public String toString(){
        String sum = "";
        for (int i=0; i<15; i++){
            sum += String.format("%.2f | ", summary[i]);
            if ((i+1)%3 == 0){
                sum += String.format("%.2f%% | ", offsets[i/4]);
            }
        }
        return sum;
    }
}
