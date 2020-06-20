/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package results_summary;


/**
 *
 * @author main
 */
public class Result {
    public static final int 
            BEST_FITNESS = 0,
            HIT = 1, 
            MISS = 2,
            MIN_VALIDATION_ERROR = 3, 
            MIN_VALIDATION_FITNESS = 4;
    
    double[] results = new double[5];
    private int epochs, total;
    
    public void setForValidation(double minValidationError, double minValidationFitness){
        results[MIN_VALIDATION_ERROR] = minValidationError;
        results[MIN_VALIDATION_FITNESS] = minValidationFitness;
    }
    
    public void setForFitness(double fitness, int epochs){
        if (epochs < 0){
            throw new IllegalArgumentException("Argument, epoch must be greater than zero.");
        }
        
        this.epochs = epochs;
        results[BEST_FITNESS] = fitness;
    }
    
    public void setForResults(int hit, int total){
        results[MISS] = total - hit;
        results[HIT] = hit;
        total = total;
    }

    public double getBestFitness() {
        return results[BEST_FITNESS];
    }

    public double getMinValidationError() {
        return results[MIN_VALIDATION_ERROR];
    }

    public double getMinValidationFitness() {
        return results[MIN_VALIDATION_FITNESS];
    }

    public int getEpochs() {
        return epochs;
    }

    public int getHit() {
        return (int)results[HIT];
    }

    public int getMiss() {
        return (int)results[MISS];
    }

    public int getTotal() {
        return total;
    }
    
    public String toString(){
        return 
                "Epochs: "+epochs+
                " Best fitness: "+results[BEST_FITNESS]+
                " | Validation fitness: "+results[MIN_VALIDATION_FITNESS]+
                " Validation error"+results[MIN_VALIDATION_ERROR]+
                " ("+results[HIT]+"/"+total+", "+results[MISS];
    }
    //min, max, mo, apostash min max, apostash se % apo thn mesh tou min max
}
