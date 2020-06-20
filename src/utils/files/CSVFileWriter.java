/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils.files;

import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Zachs
 */
public class CSVFileWriter {
    private final String DELIMITER, NEXT_LINE = "\n";
    
    private FileWriter fileWriter;
    private String stringBuffer = "";
    
//    public CSVFileWriter(){
//        DELIMITER = ",";
//    }
    public CSVFileWriter(String filePath){
        open(filePath);
        
        DELIMITER = ",";
    }
    public CSVFileWriter(String filePath, String delimiter){
        open(filePath);
        
        DELIMITER = delimiter;
    }
    
    public String getBuffer(){
        return stringBuffer;
    }
    
    public void clearBuffer(){
        stringBuffer = "";
    }
    
    public final void open(String filePath){
        if (fileWriter == null){
            try {
                fileWriter = new FileWriter(filePath);
            } 
            catch (IOException ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
    }
    
    public void addToBuffer(Object... object){
        if (stringBuffer.equals("")){
            stringBuffer += join(object);        
        }
        else{
            stringBuffer += DELIMITER+join(object);
        }
    }
    
    public void writeLine(){
        try {
            fileWriter.write(stringBuffer+NEXT_LINE);
            fileWriter.flush();
        } 
        catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        
        stringBuffer = "";
    }
    
    public void writeLine(Object... objects){
        try {
            if (stringBuffer.equals("")){
                fileWriter.write(join(objects)+NEXT_LINE);
            }
            else{
                fileWriter.write(stringBuffer+DELIMITER+join(objects)+NEXT_LINE);
            }
            
            fileWriter.flush();
        } 
        catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        
        stringBuffer = "";
    }
    
    public void close(){
        try {
            fileWriter.flush();
            fileWriter.close();
        } 
        catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
    
    private String join(Object[] objects){
        String joinedString = "";
        
        int i=0;
        for (; i<objects.length -1; i++){
            joinedString += objects[i].toString().replace("\n", "")+DELIMITER;
        }
        joinedString += objects[i].toString().replace("\n", "");
        
        return joinedString;
    }
}
