/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import evolutionbuilder.population.PersonMigration;
import evolutionbuilder.EvolutionStage;
import evolutionbuilder.population.Population;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import org.jfree.data.xy.XYSeries;

/**
 *
 * @author Zachs
 */
public class Stage implements EvolutionStage{
    private final XYSeries EVOLUTION_TIME_SERIES, VALIDATION_ERROR_TIME_SERIES;
    private javax.swing.JProgressBar progressBar;
    
    private evolutionbuilder.population.Person validationBestPerson;
    
    private PersonMigration migration;
    
    private Runnable guiRefresh = new Runnable() {
        @Override
        public void run() {
            if (progressBar != null){
                progressBar.setValue(TimeSeriesProblems.totalProgress);
                progressBar.validate();
            }
        }
    };
    
//    private double[] epochBest;
    private double[][] validationSet;
    private double validationMaxFitness = 0;
    private int validationBestAtEpoch = 0;
//    private int currentEpoch;
    
    
    public Stage(int epochs, XYSeries timeSeries, XYSeries validationErrorTimeSeries, double[][] validationSet){
//        epochBest = new double[epochs];
        this.EVOLUTION_TIME_SERIES = timeSeries;
        this.VALIDATION_ERROR_TIME_SERIES = validationErrorTimeSeries;
        this.validationSet = validationSet;
    }
    
    @Override
    public void beforeEndEpoch(Population population, int epoch) {
        try {
            if (migration != null){
                migration.migrate(population, epoch);
            }
        } 
        catch (CloneNotSupportedException ex) {
            Logger.getLogger(Stage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    @SuppressWarnings("SynchronizeOnNonFinalField")
    public void onEndEpoch(Population population, evolutionbuilder.population.Person totalBestPerson, int epoch) {
        //for the progress bar
        synchronized (TimeSeriesProblems.totalProgress){
            TimeSeriesProblems.totalProgress++;
        }
        
        SwingUtilities.invokeLater(guiRefresh);
        //end progress bar
        
        EVOLUTION_TIME_SERIES.add(epoch, population.findBestPerson().getFitness());
        
        synchronized(TimeSeriesProblems.costCounter){
//            TimeSeriesProblems.costCounter += 3 * 2;
            TimeSeriesProblems.costCounter += population.getSize();
        }
    }

    @Override
    public boolean stopEvolution(Population population, evolutionbuilder.population.Person totalBestPerson, int epoch) {
        double currentFitness, epochBestFitness = 0;

        int populationSize = population.getSize();
        evolutionbuilder.population.Person currentPerson;
        for (int i=0; i<populationSize; i++){
            
            currentPerson = population.getPersonAt(i);
            currentFitness = Person.computeFitness(currentPerson, validationSet);
            
            if (currentFitness > validationMaxFitness){
                validationMaxFitness = currentFitness;
                validationBestPerson = currentPerson;
                validationBestAtEpoch = epoch;
            }
            if (currentFitness > epochBestFitness){
                epochBestFitness = currentFitness;
            }
            
        }
        VALIDATION_ERROR_TIME_SERIES.add(epoch, 1 - epochBestFitness);
        
        return false;
    }

    public void setProgressBar(JProgressBar progressBar) {
        this.progressBar = progressBar;
    }
    public void setMigration(PersonMigration personMigration){
        migration = personMigration;
    }
    
    public XYSeries getEvolutionTimeSeries(){
        return EVOLUTION_TIME_SERIES;
    }
    public XYSeries getValidationErrorTimeSeries(){
        return VALIDATION_ERROR_TIME_SERIES;
    }
    public double getValidationBestFitness(){
        return validationMaxFitness;
    }
    public evolutionbuilder.population.Person getValidationBestPreson(){
        return validationBestPerson;
    }
    public int getValidationBestAtEpoch(){
        return validationBestAtEpoch;
    }
}
