package statistics;


import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author arx-dev-3a-19
 */
public class Statistics {
    private ArrayList<Double> data = new ArrayList<>();
    private double sum = 0;
    
    public void put(Double data){
        this.data.add(data);
        
        sum += data;
    }
    
    public void put(Double[] data){
        for (Double d : data) put(d);
    }
    
    public Double get(int position){
        return data.get(position);
    }
    
    public void clearAll(){
        data.clear();
        sum = 0;
    }
    
    public double avg(){
        return sum/data.size();
    }
    
    public double sum(){
        return sum;
    }
    
    public Data max(){
        int maxPosition = 0;
        double max = (double) data.get(0), tempValue;
        
        int size = data.size();
        for (int i=1; i<size; i++){
            tempValue = (double) data.get(i);
            
            if (tempValue > max){
                max = tempValue;
                maxPosition = i;
            }
        }
        
        return new Data(data.get(maxPosition), maxPosition);
    }
    
    public Data min(){
        int minPosition = 0;
        double min = (double) data.get(0), tempValue;
        
        int size = data.size();
        for (int i=1; i<size; i++){
            tempValue = (double) data.get(i);
            
            if (tempValue < min){
                min = tempValue;
                minPosition = i;
            }
        }
        
        return new Data(data.get(minPosition), minPosition);
    }
    
    public void runFunction(Function function){
        int size = data.size();
        for (int i=0; i<size; i++){
            function.f(i, data.get(i));
        }
    }
    
    public class Data {
        public Double value;
        public int position;

        public Data(Double value, int position) {
            this.value = value;
            this.position = position;
        }
    }
    
    public interface Function<T> {
        void f(int position, T data);
    }
}
