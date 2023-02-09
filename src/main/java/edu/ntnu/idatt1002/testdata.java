package edu.ntnu.idatt1002;
import java.util.HashMap;

public class testdata {
    public HashMap<String, Double> testHMap;
    public testdata(){
        testHMap = new HashMap<String, Double>();
        testHMap.put("Hello", 10.5);
        testHMap.put("Hewdawdlo", 12.5);
        testHMap.put("Hwadawdlo", 103.5);
    }

    public static String getItem(){
        return "Item: " + prices.getName() + prices.getPrice();
    }

}
