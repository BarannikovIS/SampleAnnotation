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
public class Class2 {
    private static final Logger Log = Logger.getLogger(Class2.class);
    
    @Initialize(lazy=false)
    public void doAction(){
        Log.info("Class2, doAction with lazy=false");
    }
    @Initialize(lazy=true)
    public void doAction1(){
        Log.info("Class2, doAction1 with lazy=true");
    }
    public void doAction2(){
        Log.info("Class2, doAction2 without annotation");
    }
}
