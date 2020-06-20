/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maths;

/**
 *
 * @author main
 */
public class LinearFunction {
    private final double m, x1, y1, x2, y2;
    
    public LinearFunction(double x1, double y1, double x2, double y2){
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        
        m = (y2 - y1)/(x2 - x1);
    }
    
    public double f(int x){
        return m*(x-x1)+y1;
    }
}
