package us.scoreme.locationpicker;

import java.util.Random;

/**
 * Created by markrose on 12/28/14.
 */
public class myClass {
    public int doMath(){
        int a=5;
        int b=10;
        int c=a+b;
        return c;
    }
    public int randomMath(){
        Random rand = new Random();
        int c = rand.nextInt(20);
        return c;
    }
}