/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machine_learning.utlis;

import java.util.Arrays;

/**
 *
 * @author arx-dev-3a-19
 */
public class DatasetsUtils {
    private DatasetsUtils(){}
    
    /**
     * Returns a new array from the last value of each array from the given data-set. 
     * @param dataset The data set
     * @return The new array
     * @throws RuntimeException If not all the arrays length isn't the same
     */
    public static double[] sliceLast(double[][] dataset){
        
        double[] slice = new double[dataset.length];
        int lastPosition = dataset[0].length -1;
        for (int i=0; i<slice.length; i++){
            
            if (dataset[i].length -1 != lastPosition) throw new RuntimeException(
                    "All arrays must have the same length. Error: row="+i+", length="+dataset[i].length
            );
            
            slice[i] = dataset[i][lastPosition];
        }
        
        return slice;
    }
    
    /**
     * Returns a new array based the data-set, with the sliced columns, from the startPosition until the end. No checks if the arrays lengths isn't the same
     * @param dataset The data-set
     * @param startPosition The position to start the slice (Include this position). Starts from 0 (zero)
     * @return The new sliced array
     */
    public static double[][] sliceColumns(double[][] dataset, int startPosition) {
        return sliceColumns(dataset, startPosition, dataset[0].length);
    }
    
    /**
     * Returns a new array based the data-set, with the sliced columns, from the startPosition until the endPosition (exclude the endPosition). No checks if the arrays lengths isn't the same
     * @param dataset The data-set
     * @param startPosition The position to start the slice (Include this position). Starts from 0 (zero)
     * @param endPosition The position to stop the slice. (Exclude this position)
     * @return The new sliced array
     */
    public static double[][] sliceColumns(double[][] dataset, int startPosition, int endPosition) {
        if (startPosition > endPosition) throw new RuntimeException(
                "Start position must be lower than endPosition. Error: startPosition="+startPosition+", endPosition="+endPosition
        );
        
        double[][] slice = new double[dataset.length][];
        
        for (int i=0; i<dataset.length; i++){
            slice[i] = Arrays.copyOfRange(dataset[i], startPosition, endPosition);
        }
        
        return slice;
    }
}
