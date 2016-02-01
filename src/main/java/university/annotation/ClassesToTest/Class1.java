/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package university.annotation.ClassesToTest;
import org.apache.log4j.Logger;
import university.annotation.Component;
import university.annotation.Initialize;
/**
 *
 * @author Иван
 */
@Component
public class Class1 {
    private static final Logger Log = Logger.getLogger(Class1.class);
    
    @Initialize(lazy=false)
    public void doAction(){
        Log.info("Class1, doAction with lazy=false");
    }
}
