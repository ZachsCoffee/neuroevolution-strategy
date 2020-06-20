/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolutionbuilder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author main
 */
public class EvolutionBuilder<T> {//htan mia palia class pou eixa kanei
    
    
    /*public static final int 
            //epiloghs
            TOURNAMENT = 1, ROULETTE = 2,
            //einai gia tous tele epiloghs
            CURRENT_BEST = 3, CURRENT_BEST_RANKED = 4, TOTAL_BEST = 5,
            //anasundiasmou
            STATIC = 6, RANDOM = 7, STATIC_WITH_FILTER = 8;*/
    
    /*private int populationCount;
    private ArrayList<T> population;
    private double[] fitness;
    private double totalBestValue = -1;
    private int mutationChance, geneCount, runnedGens, targetGens;
    private int[] fitnessPercent;
    private EvolutionStage stage;
    private int maxGens;
    private Recombination recombination;
    private PercentOfFitness percentOfFitness;
    private Mutation mutation;
    private Selection selection;
    private int posOfBestPerson = -1;
    private EvolutionStage.ShowProgress progress;
    
    public EvolutionBuilder(ArrayList<T> population, EvolutionStage stage){//ama mutation chance einai mhden tote den 8elei na uparxei mutation cahnce
        
        
        
        this.stage = stage;
        if (population == null){
            throw new IllegalArgumentException("Population can't be null.");
        }
        int size = population.size();
        if (size<2){
            throw new RuntimeException("Population size must be at least 2.");
        }
        this.population = population;
        
        this.populationCount = size;
    }
    
    public void startEvolution(int maxGens){
        //0. elenxos gia to maxGens ama einai swstos ari8mos
        if (maxGens < 1){
            throw new IllegalArgumentException("Max gens must be, >= 1 .");
        }
        
        if (selection == null || recombination == null || mutation == null){
            throw new RuntimeException("Please set selection method, recombination operator and mutation operator.");
        }
        
        int genCount = 0;
        
        computeFitness();//2. upoligsmos fitness
        progress.getProgress(0);
        if (percentOfFitness != null){
            percentOfFitness.percentOfFitnessMethod();
        }
        while (genCount < maxGens){//1. while gia to maxGens
            
            if (percentOfFitness != null){
                percentOfFitness.percentOfFitnessMethod();
            }
            System.out.println("Selection");
            selection.selectionMethod();//3. methodos epiloghs
            System.out.println("Recombination");
            recombination.recombinationOperator();//4. anasundiasmos
            System.out.println("Mutation");
            mutation.mutationOperator();//5. mutation
            System.out.println("end");
            computeFitness();//upoligsmos fitness
            if (percentOfFitness != null){
                percentOfFitness.percentOfFitnessMethod();
            }
            genCount++;
            if (progress != null){
                progress.getProgress(100*genCount/maxGens);
            }
        }
        targetGens = maxGens;
        runnedGens = genCount;
        
        posOfBestPerson = findBestPerson();
        //6. elenxos gia termatismo ama ftasoume sthn lush
        //na mporw na psaxnw gia polles kales lushs
    }
    
    public void startEvolutionWithStatement(int maxGens, StopEvolution stopEvolution){
        //0. elenxos gia to maxGens ama einai swstos ari8mos
        if (maxGens < 1){
            throw new IllegalArgumentException("Max gens must be, >= 1 .");
        }
        
        if (selection == null || recombination == null || mutation == null){
            throw new RuntimeException("Please set selection method, recombination operator and mutation operator.");
        }
        
        int genCount = 0;
        
        computeFitness();//2. upoligsmos fitness
        if (percentOfFitness != null){
            percentOfFitness.percentOfFitnessMethod();
        }
        progress.getProgress(0);
        while (genCount < maxGens){//1. while gia to maxGens
            
            if (percentOfFitness != null){
                percentOfFitness.percentOfFitnessMethod();
            }
            
            selection.selectionMethod();//3. methodos epiloghs
            
            recombination.recombinationOperator();//4. anasundiasmos
            
            mutation.mutationOperator();//5. mutation
            
            computeFitness();//upoligsmos fitness
            if (percentOfFitness != null){
                percentOfFitness.percentOfFitnessMethod();
            }
            genCount++;
            
            if (progress != null){
                progress.getProgress(100*genCount/maxGens);
            }
            
            if (stopEvolution.stopEvolution(population, fitness)){
                break;
            }
        }
        targetGens = maxGens;
        runnedGens = genCount;
        
        posOfBestPerson = findBestPerson();
        //6. elenxos gia termatismo ama ftasoume sthn lush
        //na mporw na psaxnw gia polles kales lushs
    }
    
    public void setSelectionMethod(int choice, int tournamentNumber){
        switch (choice){
            case (SelectionMethod.ROULETTE):
                selection = new Selection(){
                    @Override
                    public void selectionMethod() {
                        roulette();
                    }
                };
                break;
            case (SelectionMethod.TOURNAMENT):
                selection = new Selection(){
                    @Override
                    public void selectionMethod() {
                        tournament(tournamentNumber);
                    }
                };
                break;
            default:
                throw new RuntimeException("Choice number is not valid.");
        }
    }
    
    public void setRecombinationOperator(int choice, int geneCount, Genes genes){
        int part;
        if (geneCount < 2){//den mporw na kanw recombination ama den exw toulaxiston 2 gene
            throw new IllegalArgumentException("Need at least 2 genes, in order to make recombination.");
        }
        else if (geneCount < 4){//ama einai mikrotero tou 4 tote na to kanw na phgenei ana 1
            part = 1;
        }
        else{
            part = geneCount/(geneCount/2);
        }
        
        this.geneCount = geneCount;
        
        switch (choice){
            case (RecombinationOperator.STATIC):
                recombination = new Recombination(){
                    @Override
                    public void recombinationOperator() {
                        staticRecombination(part, geneCount, genes);
                    }
                };
                break;
            case (RecombinationOperator.RANDOM):
                recombination = new Recombination(){
                    @Override
                    public void recombinationOperator() {
                        randomRecombination(part, geneCount, genes);
                    }
                };
                break;
            case (RecombinationOperator.STATIC_WITH_FILTER):
                if (percentOfFitness == null){
                    throw new RuntimeException("You must set a percent of fitness method, in order to use STATIC_WITH_FILTER .");
                }
                recombination = new Recombination(){
                    @Override
                    public void recombinationOperator() {
                        staticRecombinationWithFilter(part, geneCount, genes);
                    }
                };
                break;
            default:
                throw new RuntimeException("Choice number is not valid.");
        }
    }
    
    public void setPercentOfFitnessMethod(int choice, boolean biggerIsBetter){
        switch (choice){
            case (PercentFitnessMethod.CURRENT_BEST):
                percentOfFitness = new PercentOfFitness(){
                    @Override
                    public void percentOfFitnessMethod() {
                        currentBest(biggerIsBetter);
                    }
                };
                break;
            case (PercentFitnessMethod.CURRENT_BEST_RANKED):
                percentOfFitness = new PercentOfFitness(){
                    @Override
                    public void percentOfFitnessMethod() {
                        currentBestRanked(biggerIsBetter);
                    }
                };
                break;
            case (PercentFitnessMethod.TOTAL_BEST):
                percentOfFitness = new PercentOfFitness(){
                    @Override
                    public void percentOfFitnessMethod() {
                        totalBest(biggerIsBetter);
                    }
                };
                break;
            default:
                throw new RuntimeException("Choice number is not valid.");
        }
    }
    
    public void setMutationOperator(int choice, int chance){
        switch (choice){
            case (MutationOperator.STATIC_MUTATION):
                mutation = new Mutation(){
                    @Override
                    public void mutationOperator() {
                        staticMutation(chance);
                    }
                    
                };
                break;
            default:
                throw new RuntimeException("Choice number is not valid.");
        }
    }
    public int getPopulationCount(){
        return populationCount;
    }
    
    public void setTotalBestValue(double value){
        if (value < 0){
            throw new IllegalArgumentException("Value must be, >= 0 .");
        }
        totalBestValue = value;
    }
    
    public double getTotalBestValue(){
        return totalBestValue;
    }
    
    public double getBestFitness(){
        if (posOfBestPerson != -1){//ama exei treksei mia fora estw to evolution
            return fitness[posOfBestPerson];
        }
        else{
            return -1;
        }
    }
    
    public T getPerson(int pos){
        return population.get(pos);
    }
    
    public T getBestPerson(){
        return population.get(posOfBestPerson);   
    }
    
    public String toString(){
        return "Evolution results:\n\t"
                + "Runned for "+runnedGens+" of "+targetGens+" gens.\n\t"
                + "Population count: "+populationCount+"\n\t"
                + "Best fitness : "+fitness[posOfBestPerson];
    }
    
    public void setShowProgress(EvolutionStage.ShowProgress progress){
        if (progress == null){
            throw new IllegalArgumentException("Progress can't be null.");
        }
        this.progress = progress;
    }
    
    public void clearShowProgress(){
        progress = null;
    }
    
    private int findBestPerson(){
        double max = fitness[0];
        int pos = 0;
        for (int i=1 ; i<fitness.length ; i++){
            if (fitness[i]>max){
                max = fitness[i];
                pos = 0;
            }
        }
        return pos;
    }
    //gia to recombination
    private void staticRecombination(int part, int geneCount, Genes genes){
        ArrayList<T> newPopulation = new ArrayList();
        T parent1, parent2, chield1, chield2;
        int pointer, count = 0;
        //prepei na mhn peirazw ton trexon plh8usmo. sunexeia 8a eprnw duo random goneis kai 8a tpus anasundiazw mexri na ftiaksw enan neo plh8usmo

        for (int i=0 ; i<populationCount ; i++){
            //pernw tous duo goneis
            chield1 = parent1 = population.get((int)(Math.random()*populationCount));
            chield2 = parent2 = population.get((int)(Math.random()*populationCount));
            pointer = part;
            
            while (pointer < geneCount){
                for (int j=pointer ; j<pointer+part && j<geneCount ; j++){
                    genes.setGen(chield1, genes.getGen(parent2, j), j);//pernw apo ton enan gonea kai to bazw ston allon
                }
                
                pointer+= part*2;
            }
            
            pointer = part;
            while (pointer < geneCount){
                for (int j=pointer ; j<pointer+part && j<geneCount ; j++){
                    genes.setGen(chield2, genes.getGen(parent1, j), j);//pernw apo ton enan gonea kai to bazw ston allon
                }
                
                pointer+= part*2;
            }
            
            newPopulation.add(chield1);
            newPopulation.add(chield2);
            
            count += 2;

            if (count >= populationCount) {
                population = newPopulation;
                return;
            }
        }
        
    }
    
    private void randomRecombination(int part, int geneCount, Genes genes){
        
        //gia to random array
        int arraySize = (int)Math.ceil(geneCount/(double)part);
        int[] randomArray = new int[arraySize];
        
        for (int i=0 ; i<randomArray.length ; i++){
            randomArray[i] = i;
        }
        
        int temp, rand;
        for (int i=0 ; i<randomArray.length ; i++){
            //kanw random swap gia na ta mperdepsw
            rand = (int)(Math.random()*randomArray.length);
            temp = randomArray[rand];
            randomArray[rand] = randomArray[rand = (int)(Math.random()*randomArray.length)];
            randomArray[rand] = temp;
            //end random swap
        }
        //end random array
        
        ArrayList<T> newPopulation = new ArrayList();
        
        T parent1, parent2, chield1, chield2;
        int pointer, count = 0;
        
        for (int i=0 ; i<populationCount ; i++){
            //pernw tous duo goneis
            chield1 = parent1 = population.get((int)(Math.random()*populationCount));
            chield2 = parent2 = population.get((int)(Math.random()*populationCount));
            
            pointer = part*randomArray[1];
            
            for (int p=1 ; p<randomArray.length ; i++){
                
                if (randomArray[p]%2 != 0){//ama einai monos
                    for (int j=pointer ; j<pointer+part && j<geneCount ; j++){
                        genes.setGen(chield1, genes.getGen(parent2, j), j);//pernw apo ton enan gonea kai to bazw ston allon
                    }
                }
 
                pointer = part*randomArray[p];
            }
            
            pointer = part*randomArray[1];
            for (int p=1 ; p<randomArray.length ; i++){

                if (randomArray[p]%2 != 0){//ama einai monos
                    for (int j=pointer ; j<pointer+part && j<geneCount ; j++){
                        genes.setGen(chield2, genes.getGen(parent1, j), j);//pernw apo ton enan gonea kai to bazw ston allon
                    }
                }

                pointer = part*randomArray[p];
            }
            
            newPopulation.add(chield1);
            newPopulation.add(chield2);
            
            count += 2;

            if (count >= populationCount) {
                population = newPopulation;
                return;
            }
        }
    }
    
    private void staticRecombinationWithFilter(int part, int geneCount, Genes genes){
        ArrayList<T> newPopulation = new ArrayList();
        T parent1, parent2, chield1, chield2;
        int count = 0, pointer;
        while (true){
            for (int i=0 ; i<populationCount ; i++){
                if ((int)(Math.random()*100)+1 <= fitnessPercent[i]){//ama einai tuxairos
                    chield1 = parent1 = population.get((int)(Math.random()*populationCount));
                    chield2 = parent2 = population.get((int)(Math.random()*populationCount));
                    pointer = part;
                    
                    while (pointer < geneCount){
                        for (int j=pointer ; j<pointer+part && j<geneCount ; j++){
                            genes.setGen(chield1, genes.getGen(parent2, j), j);//pernw apo ton enan gonea kai to bazw ston allon
                        }

                        pointer+= part*2;
                    }

                    pointer = part;
                    while (pointer < geneCount){
                        for (int j=pointer ; j<pointer+part && j<geneCount ; j++){
                            genes.setGen(chield2, genes.getGen(parent1, j), j);//pernw apo ton enan gonea kai to bazw ston allon
                        }

                        pointer+= part*2;
                    }
                    
                    newPopulation.add(chield1);
                    newPopulation.add(chield2);
                    
                    count+=2;
                    
                    if (count >= populationCount){
                        population = newPopulation;
                        return;
                    }
                }
            }
        }
    }
    //end recombination
    
    //gia ta selection
    private void tournament(int n){
        computeFitness();
        
        if (n<2){//ama exei balei sunolo mikro tero tou 2, toulaxiston 2 gia na mporw na sungkrino
            throw new IllegalArgumentException("\"N\" must be >=2 .");
        }
        
        if (populationCount<2){//ama  den exei arketo plh8usmo tot error
            throw new RuntimeException("Can't use turnament with population number <2 .");
        }
        
        blendPopulation();
        
        ArrayList<T> newPopulation = new ArrayList(populationCount);
        double[] newFitness = new double[populationCount];//krata to fitness me bash tous neous goneis
        
        int counter = 0, pos = 0;
        double max;
        T bestPerson;
        //ksekinaei h tournament
        while (counter<populationCount){
            
            bestPerson = population.get(pos);
            max = fitness[pos++];
            
            for (int j=1 ; j<n ; j++){
                if (max < fitness[pos]){
                    max = fitness[pos];
                    bestPerson = population.get(pos);
                }
                pos++;
            }
            
            if (pos < populationCount){
                newPopulation.add(bestPerson);
                newFitness[pos] = fitness[pos];
            }
            else{
                pos = 0;
                blendPopulation();
            }
            
            counter++;
        }
        //end tournament
        
        population = newPopulation;
        fitness = newFitness;
    }
    
    private void roulette(){
        int count = 0;
        ArrayList<T> selectedPopulation = new ArrayList<T>(populationCount);
        double[] newFitness = new double[populationCount];//krata to fitness me bash tous neous goneis
        
        while (true){
            for (int i=0 ; i<fitness.length ; i++){
                
                if ((int)(Math.random()*100)+1 <= fitness[i]){//ama einai tuxairos epilegete
                    selectedPopulation.add(population.get(i));
                    newFitness[count] = fitness[i];
                    count++;
                }
                if (count == populationCount){
                    population = selectedPopulation;
                    fitness = newFitness;
                    return;
                }
            }
        }
    }
    //end selection
    
    //mutation
    private void staticMutation(int chance){
        if (chance < 0){
            throw new IllegalArgumentException("Chance must be, >=0 .");
        }
        int size = population.size();//giati mporei na einai ena parapanw
        for (int i=0 ; i<size ; i++){
            for (int j=0 ; j<geneCount ; j++){
                if ((int)(Math.random()*chance) == 0){
                    stage.makeMutation(population.get(i), j);
                }
            }
        }
        
    }
    //end mutation
    
    //gia ta PercentOfFitness
    private void currentBest(boolean biggerIsBetter){
        //biggerIsBetter einai gia na kserw ama kaluteros einai autos me megalo fitness h me mikro fitness
        fitnessPercent = new int[fitness.length];
        double max = findMaxValue();//ston pinaka fitness
        if (biggerIsBetter){
            //upologismos tou posostou
            for (int i=0 ; i<fitness.length ; i++){
                fitnessPercent[i] = (int)Math.round(fitness[i] / max * 100);
            }
        }
        else{
            //find min
            double min = findMinValue();//ston pinaka fitness
            
            //upologismos tou posostou
            for (int i=0 ; i<fitness.length ; i++){
                fitnessPercent[i] = (int)Math.round((max - fitness[i]) * 100 / min);
            }
        }
    }
    
    private void totalBest(boolean biggerIsBetter) {
        fitnessPercent = new int[fitness.length];
        if (totalBestValue != -1) {//ama exei balei total best value

            //biggerIsBetter einai gia na kserw ama kaluteros einai autos me megalo fitness h me mikro fitness
            if (biggerIsBetter) {
                //upologismos tou posostou
                for (int i = 0; i < fitness.length; i++) {
                    fitnessPercent[i] = (int)Math.round(fitness[i] / totalBestValue * 100);

                }
            } 
            else {
                //find min
                double min = findMinValue();//ston pinaka fitness

                //upologismos tou posostou
                for (int i = 0; i < fitness.length; i++) {
                    fitnessPercent[i] = (int)Math.round((totalBestValue - fitness[i]) * 100 / min);
                }
            }
        } 
        else {
            throw new RuntimeException("Can't use TOTAL_BEST method with out, total best value. Please set total best value.");
        }
    }
    
    private void currentBestRanked(boolean biggerIsBetter){
        fitnessPercent = new int[fitness.length];
        double max = findMaxValue(),//ston pinaka fitness
               min = findMinValue(),
               rankedMax = max-min;//ston pinaka fitness
        
        if (biggerIsBetter){
            //upologismos tou posostou
            for (int i=0 ; i<fitness.length ; i++){
                fitnessPercent[i] = (int)Math.round((fitness[i] - min) / rankedMax * 100);
            }
        }
        else{
            //find min
            
            //upologismos tou posostou
            for (int i=0 ; i<fitness.length ; i++){
                fitnessPercent[i] = (int)Math.round(100 - ((fitness[i] - min) / rankedMax * 100));
            }
        }
    }
    //end PercentOfFitness
    
    //find max
    private double findMaxValue(){
        double max = fitness[0];
        for (int i = 1; i < fitness.length; i++) {
            if (fitness[i] > max) {
                max = fitness[i];
            }
        }
        
        return max;
    }
    
    //find min
    private double findMinValue(){
        double min = fitness[0];
        for (int i = 1; i < fitness.length; i++) {
            if (fitness[i] < min) {
                min = fitness[i];
            }
        }
        
        return min;
    }
    
    private void computeFitness(){//upologizw to fitness gia ka8e atomo tou plh8usmou
        fitness = new double[populationCount];
        
        for (int i=0 ; i<populationCount ; i++){
            fitness[i] = stage.computeFitness(population.get(i));
        }
    }
    
    private void blendPopulation(){//mperdeuw to population wste na einai tuxaio
        int pos1, pos2;//kratane ta tuxaia positons
        double tempFitness;
        for (int i=0 ; i<populationCount ; i++){
            pos1 = (int)(Math.random()*populationCount);
            pos2 = (int)(Math.random()*populationCount);
            
            //kanw swap
            T temp = population.get(pos1);
            population.set(pos1, population.get(pos2));
            population.set(pos2, temp);

            //kanw parallhla swap kai ton pinaka me ta fitness
            tempFitness = fitness[pos1];
            fitness[pos1] = fitness[pos2];
            fitness[pos2] = tempFitness;
            //end swap
        }
    }
    
    private interface Recombination {
        //8a krata thn me8odo anasundiasmou
        public void recombinationOperator();
    }
    
    private interface PercentOfFitness {
        //8a krata thn me8odo ana8eshs pi8anothtwn
        public void percentOfFitnessMethod();
    }
    
    private interface Mutation {
        //8a krata thn me8odo mutation
        public void mutationOperator();
    }
    
    private interface Selection {
        //8a krata thn me8odo epiloghs
        public void selectionMethod();
    }
    

    public static void main(String[] args) {

    }
    
    public final class SelectionMethod {
        public static final int 
            //epiloghs
            TOURNAMENT = 1, ROULETTE = 2;
        
        private SelectionMethod(){};
    }
    
    public final class RecombinationOperator {
        public static final int
                //anasundiasmou
                STATIC = 6, RANDOM = 7, STATIC_WITH_FILTER = 8;
        
        private RecombinationOperator(){};
    }
    
    public final class PercentFitnessMethod {
        public static final int
                //einai gia tous tele epiloghs
                CURRENT_BEST = 3, CURRENT_BEST_RANKED = 4, TOTAL_BEST = 5;
                
        
        private PercentFitnessMethod(){};
    }
    
    public final class MutationOperator {
        public static final int
                STATIC_MUTATION = 1;
        
        private MutationOperator(){};
    }*/
}