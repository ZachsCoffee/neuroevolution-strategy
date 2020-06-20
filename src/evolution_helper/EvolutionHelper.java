/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolution_helper;

import evolutionbuilder.Evolution;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author main
 */
public abstract class EvolutionHelper {
    
    private static double[][] readDataFromFile(String filePath){
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
            ArrayList<double[]> data = new ArrayList();
            
            double[] temp;
            String line, lineData[];
            int lineCounter = 0;
            while ((line = reader.readLine()) != null){
                if (lineCounter++ < 7) continue;
                lineData = line.split(" ");
                if (lineData[0].charAt(0) != '#'){
                    temp = new double[lineData.length];
                    for (int i=0; i<lineData.length; i++){
                        temp[i] = Double.parseDouble(lineData[i]);
                    }
                
                    data.add(temp);
                }
                
            }
            
            return data.toArray(new double[][]{});
        } 
        catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        } 
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static double[][] readDataFromFile(String filePath, String separator){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"))){
            
            ArrayList<double[]> data = new ArrayList();
            
            String[] splitedLine;
            double[] doubleArray;
            String line;
            while ((line = reader.readLine()) != null){
                splitedLine = line.split(separator);
                doubleArray = new double[splitedLine.length];
                
                for (int i=0; i<splitedLine.length; i++){
                    doubleArray[i] = Double.parseDouble(splitedLine[i]);
                }
                
                data.add(doubleArray);
            }
            
            return data.toArray(new double[][]{});
        } 
        catch (UnsupportedEncodingException ex) {
            Logger.getLogger(EvolutionHelper.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (FileNotFoundException ex) {
            Logger.getLogger(EvolutionHelper.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex) {
            Logger.getLogger(EvolutionHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
}
