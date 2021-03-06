/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolutionbuilder.components;

import evolutionbuilder.population.Person;
import evolutionbuilder.population.Population;

/**
 *
 * @author main
 */
public interface EvolutionComponents{
    public void computePercentOfFitness(Population population);
    
    public Population recombinationOperator(Population population, int epoch);
    
    public Population selectionMethod(Population population);
    
    public void mutationMethod(Population population, int epoch, int maxEpoch);
}
