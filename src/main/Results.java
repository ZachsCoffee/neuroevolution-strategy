/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.HashMap;
import statistics.Statistics;

/**
 *
 * @author arx-dev-3a-19
 */
public class Results {
    public static final String 
            EVALUATION_ERROR = "evaluation_error", 
            TRAINING_ERROR = "training_error",
            VALIDATION_ERROR = "validation_error",
            VALIDATION_BEST_EPOCH = "validation_best_epoch";
    
    private HashMap<String, Statistics> results = new HashMap<>();
    
    public Results(){
        results.put(EVALUATION_ERROR, new Statistics());
        results.put(TRAINING_ERROR, new Statistics());
        results.put(VALIDATION_ERROR, new Statistics());
        results.put(VALIDATION_BEST_EPOCH, new Statistics());
    }
    
    public Statistics get(String name){
        return results.get(name);
    }
    
    public Object[] getBest(){
        int positionOfBest = results.get(EVALUATION_ERROR).min().position;
        
        return new Object[]{
            results.get(EVALUATION_ERROR).get(positionOfBest),
            results.get(TRAINING_ERROR).get(positionOfBest),
            results.get(VALIDATION_ERROR).get(positionOfBest),
            results.get(VALIDATION_BEST_EPOCH).get(positionOfBest)
        };
    }
    
    public void addResults(Object[] results){
        this.results.get(EVALUATION_ERROR).put((Double)results[0]);
        this.results.get(TRAINING_ERROR).put((Double)results[1]);
        this.results.get(VALIDATION_ERROR).put((Double)results[2]);
        this.results.get(VALIDATION_BEST_EPOCH).put((Double)results[3]);
    }
    
    public void clear(){
        results.get(EVALUATION_ERROR).clearAll();
        results.get(TRAINING_ERROR).clearAll();
        results.get(VALIDATION_ERROR).clearAll();
        results.get(VALIDATION_BEST_EPOCH).clearAll();
    }
}
