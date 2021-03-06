/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import evolution_helper.EvolutionHelper;
import evolutionbuilder.population.PersonMigration;
import evolutionbuilder.Evolution;
import evolutionbuilder.population.Population;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.util.Arrays;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import machine_learning.abstraction.MLProblem;
import machine_learning.abstraction.Network;
import machine_learning.abstraction.TimeNetwork;
import machine_learning.functions.Functions;
import machine_learning.mlp.BackpropagationMLP;
import machine_learning.mlp.MLPBackpropagation;
import machine_learning.mlp.NetworkLayer;
import machine_learning.utlis.DatasetsUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import main.problems.LaserProblem;
import main.problems.LorenzProblem;
import main.problems.MackeyProblem;
import main.problems.SunspotProblem;
import main.problems.TWIExchangeProblem;
import utils.files.CSVFileWriter;
import utils.maths.LinearValues;
import utils.maths.MinMax;

/**
 *
 * @author Zachs
 */
public class TimeSeriesProblems extends javax.swing.JFrame {
    public static final double LEARNING_RATE = 0.01;
    public static final boolean WITH_PERCENT_OF_FITNESS = false;
    public static Integer totalProgress, costCounter = 0;
    public static int threadCount;

    private final int RUNS = 30;
    private final boolean SHOW_CHARTS = true;
    
    private int runsCounter = 0, threadsCounter = 0;
    
    private int selectedResult = 0;
    private boolean isFirstTheadEnd = false, alreadyAnswered = false;
    private CSVFileWriter csvFileWriter;
    private Results runResults = new Results(), totalResults = new Results();
    
    private CardLayout predictionCardLayout, evolutionCardLayout, errorCardLayout;
    
    private static int getPositionOfA(int[] a, int[] b, long positionB){
        return (int)(positionB*(a.length) / (b.length));
    }
    
    private static void test(){
        int[] array1 = new int[32], array2 = new int[64];
        int[] new1 = new int[32], new2 = new int[64];
        
        Arrays.fill(array2, 1);
        
        int breakSize = 1, translatedPosition;
        boolean changeGenesSide = true;
        for (int i=0; i<32; i++){
            if (i%breakSize == 0){
                changeGenesSide = !changeGenesSide;
            }

            translatedPosition = getPositionOfA(array2, array1, i);
            if (changeGenesSide){
                new1[i] = array1[i];
                new2[translatedPosition] = array2[translatedPosition];
            }
            else{
                try {
                    new1[i] = array2[translatedPosition];
                    new2[translatedPosition] = array1[i];
                }
                catch (Exception ex){
                    System.err.println(translatedPosition+" "+i);
                }
            }
        }
        
        System.out.println(Arrays.toString(new1)+"\n"+Arrays.toString(new2));
    }
    
    /**
     * Creates new form TimeSeriesProblems
     */
    public TimeSeriesProblems() {
        initComponents();
        resultsScrollPane.getVerticalScrollBar().setUnitIncrement(15);
        resultsScrollPane.getVerticalScrollBar().setUnitIncrement(15);
        
        predictionCardLayout = (CardLayout) predictionPanel.getLayout();
        evolutionCardLayout = (CardLayout) evolutionPanel.getLayout();
        errorCardLayout = (CardLayout) predictionErrorPanel.getLayout();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        evolutionPanel = new javax.swing.JPanel();
        predictionPanel = new javax.swing.JPanel();
        evolutionSettingsPanel = new javax.swing.JPanel();
        runButton = new javax.swing.JButton();
        evolutionProgress = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        runsCountText = new javax.swing.JLabel();
        predictionErrorPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        resultsScrollPane = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        resultsPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        evolutionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Evolution"));
        evolutionPanel.setPreferredSize(new java.awt.Dimension(484, 279));
        evolutionPanel.setLayout(new java.awt.CardLayout());

        predictionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Prediction"));
        predictionPanel.setMaximumSize(new java.awt.Dimension(350, 2147483647));
        predictionPanel.setPreferredSize(new java.awt.Dimension(500, 223));
        predictionPanel.setLayout(new java.awt.CardLayout());

        evolutionSettingsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Evolution Settings"));

        runButton.setText("Run");
        runButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runButtonActionPerformed(evt);
            }
        });

        evolutionProgress.setStringPainted(true);

        jLabel1.setText("Runs:");

        runsCountText.setText("0");

        javax.swing.GroupLayout evolutionSettingsPanelLayout = new javax.swing.GroupLayout(evolutionSettingsPanel);
        evolutionSettingsPanel.setLayout(evolutionSettingsPanelLayout);
        evolutionSettingsPanelLayout.setHorizontalGroup(
            evolutionSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(evolutionProgress, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, evolutionSettingsPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(runButton)
                .addContainerGap())
            .addGroup(evolutionSettingsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(runsCountText)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        evolutionSettingsPanelLayout.setVerticalGroup(
            evolutionSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, evolutionSettingsPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(evolutionSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(runsCountText))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(evolutionProgress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(runButton)
                .addContainerGap())
        );

        predictionErrorPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Error"));
        predictionErrorPanel.setLayout(new java.awt.CardLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Results"));

        resultsScrollPane.setBorder(null);
        resultsScrollPane.setMaximumSize(new java.awt.Dimension(509, 350));
        resultsScrollPane.setPreferredSize(new java.awt.Dimension(509, 100));

        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT);
        flowLayout1.setAlignOnBaseline(true);
        jPanel1.setLayout(flowLayout1);

        resultsPanel.setMaximumSize(new java.awt.Dimension(509, 32767));
        resultsPanel.setLayout(new java.awt.GridLayout(0, 1));
        jPanel1.add(resultsPanel);

        resultsScrollPane.setViewportView(jPanel1);

        jButton1.setText("Previous");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previousButton(evt);
            }
        });

        jButton2.setText("Next");
        jButton2.setMaximumSize(new java.awt.Dimension(80, 32));
        jButton2.setMinimumSize(new java.awt.Dimension(80, 32));
        jButton2.setPreferredSize(new java.awt.Dimension(80, 32));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButton(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(resultsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(resultsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(evolutionSettingsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(evolutionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 761, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(predictionErrorPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
                    .addComponent(predictionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(predictionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                    .addComponent(evolutionSettingsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(evolutionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(predictionErrorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void runButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runButtonActionPerformed
        
        costCounter = 0;
        //MACKEY
//        final double PERCENT = 0.2;
//        final int EPOCHS = 2000, POPULATION_SIZE = 15;
        //PERCENT false
        
        //SUNSPOT AND TWI
//        final double PERCENT = 0.2;
//        final int EPOCHS = 2000, POPULATION_SIZE = 15;
        //PERCENT false
        
        //final String TEST_TYPE = "VF";// varibale length without percent of fitness
        final String TEST_TYPE = "Test";// varibale length without percent of fitness
        final double PERCENT = 0.1;
        final int 
                EPOCHS = 2000, 
                POPULATION_SIZE = 15,
                THREADS = 10;

//       final SunspotProblem problem = new SunspotProblem(EPOCHS);
       final MackeyProblem problem = new MackeyProblem(EPOCHS);

//       final TWIExchangeProblem problem = new TWIExchangeProblem(EPOCHS);//ok
//       final LaserProblem problem = new LaserProblem(EPOCHS);//ok
//        final LorenzProblem problem = new LorenzProblem(EPOCHS);
        PersonMigration personMigration = new PersonMigration(PERCENT, EPOCHS, THREADS);
        
        totalProgress = 0;
        evolutionProgress.setMaximum(EPOCHS*THREADS);
        
        TimeSeriesProblems.costCounter += THREADS * POPULATION_SIZE;
        
        final String PROBLEM_NAME = problem.getClass().getSimpleName();
        //for file ouput, csv format
        new File("./output/"+PROBLEM_NAME).mkdirs();
        File outputFile = new File("./output/"+PROBLEM_NAME+"/"+PROBLEM_NAME+"_"+TEST_TYPE+".csv");
        if (outputFile.exists() && !alreadyAnswered){
            if (!showAlertFileExists()){//if answer no
                return;
            }
            
            alreadyAnswered = true;
        }
        else{
            alreadyAnswered = true;
        }
        
        if (csvFileWriter == null) {
            csvFileWriter = new CSVFileWriter(outputFile.getPath());
            
            csvFileWriter.writeLine("Epochs", "Population size", "Threads", "Percent", "Neural network");
            csvFileWriter.writeLine(EPOCHS, POPULATION_SIZE, THREADS, PERCENT, problem.buildNetwork(0));
            csvFileWriter.writeLine("Cost", "Evaluation", "Training_error", "Validation_error", "Best_validation_epoch");
        }
        
        for (int i=0; i<THREADS; i++){
            
            new Thread(() -> {
                XYSeries
                        evolutionTimeSeries = new XYSeries("Fitness"),
                        validationErrorTimeSeries = new XYSeries("Validation fitness");
                
                Population population = new Population(problem.getPersonManager(), POPULATION_SIZE);
                
                System.out.println("Start create population");
                population.createPopulation();
                System.out.println("End");
                
                Evolution evolution = new Evolution(population, WITH_PERCENT_OF_FITNESS, problem);
                personMigration.addPopulation(evolution);
                
                Stage stage = new Stage(EPOCHS, evolutionTimeSeries, validationErrorTimeSeries, problem.getValidationSet());
                stage.setProgressBar(evolutionProgress);
                stage.setMigration(personMigration);
                evolution.startEvolution(EPOCHS, stage);
                
//                Network neuralNetwork = (Network) evolution.getTotalBestPerson().getGeneCode();
                Network neuralNetwork = (Network) stage.getValidationBestPreson().getGeneCode();
                
                JLabel resultsLabel = new JLabel();
                
                buildPredictionChart(problem, neuralNetwork, resultsLabel);
                buildEvolutionChart(stage.getEvolutionTimeSeries(), stage.getValidationErrorTimeSeries());
                
                double trainingError, validationError;
                int validationBestEpoch;
                
                trainingError = 1 - evolution.getTotalBestPerson().getFitness();
                validationError = 1 - stage.getValidationBestFitness();
                validationBestEpoch = stage.getValidationBestAtEpoch();
                
                append(resultsLabel,
                        "[Training: "+formatDouble(trainingError)+"] "+
                                "[Validation: "+formatDouble(validationError)+" epoch: "+validationBestEpoch+"] "
                );

                
                append(resultsLabel, "[Neural network: "+((Network)evolution.getTotalBestPerson().getGeneCode())+"] ");
                
                //for the results
                runResults.get(Results.TRAINING_ERROR).put(trainingError);
                runResults.get(Results.VALIDATION_ERROR).put(validationError);
                runResults.get(Results.VALIDATION_BEST_EPOCH).put(Double.valueOf(validationBestEpoch));
                //end results
                
                SwingUtilities.invokeLater(() -> {
//                    csvFileWriter.addToBuffer(costCounter);
//                    csvFileWriter.writeLine(
//                            (Object[]) prepareStringForCSV(resultsLabel.getText())
//                    );

                    resultsPanel.add(resultsLabel);
                    resultsPanel.revalidate();
                    
                    if (!isFirstTheadEnd){// for the first thread
                        resultsPanel.getComponent(selectedResult).setForeground(Color.RED);
                        isFirstTheadEnd = true;
                    }
                    
                    if (++threadsCounter == THREADS){//when a run complete
                        
                        synchronized (runResults){
                            synchronized (totalResults){
                                Object[] bestSubpopulationData = runResults.getBest();//get the best from subpopulations
                            
                                totalResults.addResults(bestSubpopulationData);//put this run results into total results

                                //write only the best into the file
                                csvFileWriter.addToBuffer(costCounter);
                                csvFileWriter.writeLine(
                                        (Object[]) bestSubpopulationData
                                );
                            }
                        }
                        
                        runResults.clear();
                        
                        if (++runsCounter < RUNS) {//if have more runs to complete, run the method again
                            runButtonActionPerformed(null);
                        }
                        else{//the end
                            double 
                                    bestEvaluation = totalResults.get(Results.EVALUATION_ERROR).min().value,
                                    averageEvaluation = totalResults.get(Results.EVALUATION_ERROR).avg(),
                                    worstEvaluation = totalResults.get(Results.EVALUATION_ERROR).max().value;
                            
                            csvFileWriter.writeLine("Best evaluation", "Average evaluation", "Worst evaluation");
                            csvFileWriter.writeLine(bestEvaluation, averageEvaluation, worstEvaluation);
                            
                            System.err.println(
                                    "Best evaluation: "+bestEvaluation+"\n"+
                                    "Average: "+averageEvaluation+"\n"+
                                    "Worst: "+worstEvaluation
                            );
                        }
                        
                        runsCountText.setText(runsCounter+"");//set the label the run count
                        threadsCounter = 0;//reinit
                    }
                    
                });
            }).start();
        }
        //System.out.println("Fitness Evaluations: "+LaserProblem.fitnessEvaluations);
    }//GEN-LAST:event_runButtonActionPerformed

    private void previousButton(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousButton
        resultsPanel.getComponent(selectedResult).setForeground(null);
        if (selectedResult == 0){
            selectedResult = resultsPanel.getComponentCount() -1;
        }
        else{
            selectedResult--;
        }
        resultsPanel.getComponent(selectedResult).setForeground(Color.RED);
        
        predictionCardLayout.previous(predictionPanel);
        evolutionCardLayout.previous(evolutionPanel);
        errorCardLayout.previous(predictionErrorPanel);
    }//GEN-LAST:event_previousButton

    private void nextButton(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButton
        resultsPanel.getComponent(selectedResult).setForeground(null);
        if (selectedResult == resultsPanel.getComponentCount() -1){
            selectedResult = 0;
        }
        else{
            selectedResult++;
        }
        resultsPanel.getComponent(selectedResult).setForeground(Color.RED);
        
        predictionCardLayout.next(predictionPanel);
        evolutionCardLayout.next(evolutionPanel);
        errorCardLayout.next(predictionErrorPanel);
    }//GEN-LAST:event_nextButton

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if (csvFileWriter != null) csvFileWriter.close();
    }//GEN-LAST:event_formWindowClosing
    
    private void buildEvolutionChart(XYSeries evolutionTimeSeries, XYSeries validationErrorTimeSeries){
        XYSeriesCollection  evolutionChart = new XYSeriesCollection(); 
        evolutionChart.addSeries(evolutionTimeSeries);
        evolutionChart.addSeries(validationErrorTimeSeries);
        
        JFreeChart scatterPlot = ChartFactory.createScatterPlot(
            "Evolution",
            "Epochs", "Value",
            evolutionChart,
            PlotOrientation.VERTICAL,
        true,true,false);
        
        Shape cross = new Ellipse2D.Double(0, 0, 2, 2);
        ((XYPlot)scatterPlot.getPlot()).getRenderer().setSeriesShape(0, cross);
        ((XYPlot)scatterPlot.getPlot()).getRenderer().setSeriesShape(1, cross);
        
        if (SHOW_CHARTS){
            evolutionPanel.add(
                new JPanel().add(new ChartPanel(scatterPlot))
            );
            evolutionPanel.revalidate();
        }
    }
    
    private void buildPredictionChart(MLProblem mlProblem, Network neuralNetwork, JLabel resultsLabel){
        XYSeries errorSeries = new XYSeries("Prediction error");
        
        XYSeriesCollection  predictionChartData = new XYSeriesCollection();
        predictionChartData.addSeries(buildTestSeries(mlProblem));
        predictionChartData.addSeries(evaluation(mlProblem, neuralNetwork, errorSeries, resultsLabel));
        
        JFreeChart predictionChart = ChartFactory.createXYLineChart(
            "Prediction",
            "Count", "Value",
            predictionChartData, PlotOrientation.VERTICAL, 
        true, true, false);
        
        if (SHOW_CHARTS){
            predictionPanel.add(
                new JPanel().add(new ChartPanel(predictionChart))
            );
            predictionPanel.revalidate();
        }
        
        XYSeriesCollection errorCollection = new XYSeriesCollection(errorSeries);
        JFreeChart errorChart = ChartFactory.createXYLineChart(
                "Error", 
                "Count", "Error(RMSE)", 
                errorCollection, 
                PlotOrientation.VERTICAL, 
                true, true, false);
        
        if (SHOW_CHARTS){
            predictionErrorPanel.add(
                new JPanel().add(new ChartPanel(errorChart))
            );
            predictionErrorPanel.revalidate();
        }
    }
    
    private XYSeries buildTestSeries(MLProblem mlProblem){
        double[][] testSet = mlProblem.getTestSet();
        
        XYSeries  testData = new XYSeries("real data");
        
        int count = 1;
        int target = testSet[0].length -1;
        for (double[] feature : testSet) {
            testData.add(count++, feature[target]);
        }
        
        return testData;
    }
    
    private double computeCost(int threadsCount, int totalEpochs, int populationSize, double percent){
        double cost = threadsCount * totalEpochs * populationSize;
        
        double propagationEpochs = totalEpochs * percent;
        
        cost += totalEpochs / propagationEpochs * propagationEpochs * threadsCount;
        
        return cost;
    }
    
    private boolean showAlertFileExists(){
        int answer = JOptionPane.showConfirmDialog(
            this,
            "All results will be removed. Do you want to continue?",
            "Output file already exists",
            JOptionPane.YES_NO_OPTION
        );
        
        return answer == JOptionPane.YES_OPTION;
    }
    
    public XYSeries evaluation(MLProblem mlProblem, Network neuralNetwork, XYSeries errorSeries, JLabel resultsLabel){
        double[][] testSet = mlProblem.getTestSet();
        
        XYSeries  predictionData = new XYSeries("predicted data");
        
        if (neuralNetwork instanceof TimeNetwork){
            ((TimeNetwork) neuralNetwork).startCompute();
        }
        
        int target = testSet[0].length -1;
        double sum = 0, predicted, tempError;
        for (int i=0; i<testSet.length; i++) {
            predicted = neuralNetwork.compute(Arrays.copyOf(testSet[i], testSet[i].length -1))[0];
            
            tempError = Math.pow(testSet[i][target] - predicted, 2);
            sum += tempError;
            
            predictionData.add(i+1, predicted);
            errorSeries.add(i+1, Math.sqrt(tempError));
        }
        
        if (neuralNetwork instanceof TimeNetwork){
            ((TimeNetwork) neuralNetwork).endCompute();
        }
        
        double evaluation = Math.sqrt(sum/testSet.length);//temp
        runResults.get(Results.EVALUATION_ERROR).put(evaluation);//hold the results to find the best, at the end
        
        append(resultsLabel, "[Evaluation: "+formatDouble(evaluation)+"] ");//to show the results at label
//        System.err.println("Evaluation: "+Math.sqrt(sum/testSet.length)+" Test set "+testSet.length);
        System.err.println(costCounter);
        return predictionData;
    }
    
    
    private void append(JLabel jLabel, String str){
        jLabel.setText(jLabel.getText()+str);
    }
    private String formatDouble(double number){
        return String.format("%.6f", number);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TimeSeriesProblems.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TimeSeriesProblems.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TimeSeriesProblems.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TimeSeriesProblems.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Set cross-platform Java L&F (also called "Metal")
                    UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
                } 
                catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                   // handle exception
                }
                // handle exception
                // handle exception
                // handle exception

                new TimeSeriesProblems().setVisible(true);
            }
        });
    }
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel evolutionPanel;
    private javax.swing.JProgressBar evolutionProgress;
    private javax.swing.JPanel evolutionSettingsPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel predictionErrorPanel;
    private javax.swing.JPanel predictionPanel;
    private javax.swing.JPanel resultsPanel;
    private javax.swing.JScrollPane resultsScrollPane;
    private javax.swing.JButton runButton;
    private javax.swing.JLabel runsCountText;
    // End of variables declaration//GEN-END:variables
}
