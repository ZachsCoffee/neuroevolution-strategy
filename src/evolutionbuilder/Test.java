/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolutionbuilder;



import evolutionbuilder.population.Person;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author main
 */
public class Test{
    public static Evolution evolution;
    public static final double MAX_FITNESS = 1000000000;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
            /*double[][] irisData = readData("C:\\Users\\main\\Desktop\\iris-data.txt");//diabazw ta data apo to iris file
            Population population = new Population(new PersonManager() {
            @Override
            public Person newPerson() {
            double[] personStats = {Math.random()*2, Math.random()*2, Math.random()*2, Math.random()*2};
            
            return new Person(personStats);
            }

            @Override
            public double computeFitness(Person person) {
            return Test.computeFitness(person, irisData);
            }
            }, 300);
            population.createPopulation();
            Evolution evolution = new Evolution(population, new EvoComponents());
            Population finalPopulation = evolution.startEvolution(200, new EvolutionStage() {
            @Override
            public void onEndEpoch(Population population, int epoch) {
            System.out.println(epoch+" "+population.findBestPerson().getFitness());
            }

            @Override
            public boolean stopEvolution(Population population, int epoch) {
            return false;
            }
            });
            Test.evolution = evolution;
            System.out.println(evolution.getTotalBestPerson().getFitness()+"");
            System.out.println(Arrays.toString((double[])evolution.getTotalBestPerson().getGeneCode()));*/
//        long sum = 0;
//        long first;
//        File temp = new File("C:\\Program Files (x86)");
//        for (int i=1; i<=100; i++){
//            first = System.nanoTime();
//            scanFolder(temp);
//            sum += System.nanoTime() - first;
//        }
            
            //System.out.println((sum/100f)/1000000);
        
//        System.out.println(scanFolder(temp));
//        Word[] words = new Word[3];
//        words[0] = new Word("neraki", 0, 1, true);
//        words[1] = new Word("aeraki", 2, 2, false);
//        words[2] = new Word("papaki", 3, 4, true);
//        CrossWordProblem crossWordProblem = new CrossWordProblem(10, 10, words);
//        Population population = new Population(crossWordProblem.getPersonManager(), 100);
//        population.createPopulation();
//        
//        // AMA TO ROW TOU ENOS EINAI POIO PANW H KAI ISO ME TO ROW TOU ALLOU KAI KATI TO ANTISTIXO GIA TO COL
//        //ftiaxnw to evolution
//        //Evolution evolution = new Evolution(population, withPercent, cancerProblem);
//        Evolution evolution = new Evolution(population, false, crossWordProblem);
//        //evolution.startEvolution(epochs, new CancerProblem.Stage(evolutionSeries));
//        
//        //CancerProblem.IStage stage = new CancerProblem.IStage(evolutionSeries, validationSeries, validationSet);
//        Population lastPopulation = evolution.startEvolution(1000);
//        CrossWord crossWord = (CrossWord) lastPopulation.findBestPerson().getGeneCode();
//        System.out.println(crossWord);
//        System.out.println(lastPopulation.findBestPerson().getFitness());

    }
    
    
    static ScanResult scanFolder(File folder){
        ScanResult result = new ScanResult();
        scanFolderRegresion(folder, result);
        return result;
    }
    
    static void scanFolderRegresion(File startFolder, ScanResult result){
        File[] temp = startFolder.listFiles();
        if (temp == null || temp.length == 0){
            return;
        }
        result.filesCount += temp.length;
        for (int i=0; i<temp.length; i++){
            if (temp[i].isDirectory()){
                //result.filesCount++;
                scanFolderRegresion(temp[i], result);
            }   
            else{
                //result.filesCount++;
                result.bytesCount += temp[i].length();
            }
        }
    }
    static class ScanResult{
        long bytesCount = 0;
        int filesCount = 0;
        
        public String toString(){
            return "Files count: "+filesCount+"\nBytes count: "+bytesCount;
        }
    }
    
    public static double computeFitness(Person person, double[][] data){
        double[] personStats = (double[])person.getGeneCode();
        double rowSum, fitnessSum = 0, fitness;
        
        for (int j=0; j<data.length; j++){
            rowSum = 0;
            for (int i=0; i<personStats.length; i++){
                rowSum += data[j][i] * personStats[i];
            }
            //fitness = Math.abs(rowSum - data[j][EvoComponents.CHROMOSOME_COUNT]);
            fitness = Math.pow(rowSum - data[j][personStats.length-1], 2);
            fitnessSum += fitness;

            
        }
        
        return MAX_FITNESS - fitnessSum;
    }
    
    public static double[][] readData(String filePath){
        double[][] irisData = new double[150][];
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            String line;
            String[] splitedLine;
            double[] splitedPersonStats;
            int count = 0;
            while((line = bufferedReader.readLine()) != null){
                splitedLine = line.split(",");
                splitedPersonStats = new double[5];
                for (int i=0; i<4; i++){
                    splitedPersonStats[i] = Double.parseDouble(splitedLine[i]);
                }
                irisData[count++] = splitedPersonStats;
                //irisData[count++] = line.split(",");
                if (count <= 50){
                    irisData[count - 1][4] = 1;
                }
                else if (count <= 100){
                    irisData[count - 1][4] = 2;
                }
                else{
                    irisData[count - 1][4] = 3;
                }
            }
            return irisData;
            
        } 
        catch (FileNotFoundException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    /*public static void main(String[] args) {
        ArrayList<double[]> trainingData = new ArrayList();
        try (BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream("irisTrain.dat")))){
            String data;
            String[] comma;
            double[] clearData;
            while ((data = input.readLine()) != null){
                comma = data.split(" ");
                clearData = new double[comma.length];
                
                for (int j=0 ; j<comma.length ; j++){
                    clearData[j] = Double.parseDouble(comma[j]);
                }
                trainingData.add(clearData);
            }
        } 
        catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        double[] genes = new double[4];
        ArrayList<double[]> population = new ArrayList();
        for (int i=0 ; i<100 ; i++){
            for (int j=0 ; j<4 ; j++){
                genes[j] = Math.random();
            }
            population.add(genes);
        }
        
        EvolutionBuilder<double[]> evo = new EvolutionBuilder(population, new EvolutionStage(){
            @Override
            public double computeFitness(Object person) {
                double fitness = 0, sum = 0;
                
                double[] p = (double[])person, training;
                int size = trainingData.size();
                
                for (int i=0 ; i<size ; i++){
                    training = trainingData.get(i);
                    for (int j=0 ; j<4 ; j++){
                        sum+= p[j] * training[j];
                    }
                    fitness+= Math.abs(training[4]-Math.round(sum));
                }
                
                return fitness;
            }

            @Override
            public Object makeMutation(Object person, int gene) {
                double[] p = (double[])person;
                p[gene] = Math.random();
                return p;
            }
        });
        
        //set up
        evo.setTotalBestValue(trainingData.size());//auto einai to kalutero fitness
        evo.setPercentOfFitnessMethod(PercentFitnessMethod.TOTAL_BEST, true);
        
        evo.setSelectionMethod(SelectionMethod.TOURNAMENT, 5);
        
        evo.setRecombinationOperator(RecombinationOperator.STATIC_WITH_FILTER, 4, new Genes(){
            @Override
            public void setGen(Object person, Object gene, int position) {
                double[] p = (double[])person;
                double g = (double)gene;
                p[position] = g;
            }

            @Override
            public Object getGen(Object person, int position) {
                double[] p = (double[])person;
                return p[position];
            }
        });
        
        evo.setMutationOperator(MutationOperator.STATIC_MUTATION, 100);
        evo.setShowProgress(new EvolutionStage.ShowProgress(){
            @Override
            public void getProgress(double progress) {
                System.out.printf("%3.2f%%", progress);
            }
        });
        evo.startEvolution(1000);
        //end set up
        
        System.out.println(evo);
    }*/
    
}
