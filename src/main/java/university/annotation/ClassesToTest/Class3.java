/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package university.annotation.ClassesToTest;
import org.apache.log4j.Logger;
/**
 *
 * @author Иван
 */
public class Class3 {
    private static final Logger Log = Logger.getLogger(Class3.class);
    
    public void doAction(){
        Log.info("Class3, without annotation Component");
    }
}
