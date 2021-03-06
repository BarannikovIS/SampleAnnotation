/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package university.annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.apache.log4j.Logger;

/**
 *
 * @author Иван
 */
public class Main {

    private static final Logger Log = Logger.getLogger(Main.class);

    public static HashMap map;
    public static ArrayList<String> initializeService=new ArrayList<String>();

    public static void main(String[] args) {
        try {
            Main m = new Main();
            m.check();
            
            m.checkLazy(map.get("university.annotation.ClassesToTest.Class2"));
            m.checkLazy(map.get("university.annotation.ClassesToTest.Class2"));
            
        } catch (InstantiationException ex) {
            Log.error(ex.getMessage(), ex);
        } catch (IllegalAccessException ex) {
            Log.error(ex.getMessage(), ex);
        } catch (IllegalArgumentException ex) {
            Log.error(ex.getMessage(), ex);
        } catch (InvocationTargetException ex) {
            Log.error(ex.getMessage(), ex);
        }
    }

    private void check() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        map = new HashMap<String, Object>();
        Set<Class<?>> classes = getAllClasses();

        for (Class c : classes) {
            if (c.isAnnotationPresent(Component.class)) {
                Object instance = toInstantiate(c);
                Method[] methods = c.getDeclaredMethods();

                for (Method m : methods) {
                    if (m.isAnnotationPresent(Initialize.class)) {
                        if (m.getAnnotation(Initialize.class).lazy() == false) {
                            m.setAccessible(true);
                            m.invoke(instance);
                            initializeService.add(instance.getClass().getName() + m.getName());
                        }
                    }
                }
                map.put(c.getName(), instance);
                System.out.println(c.getName());
            }
        }
    }

    public void checkLazy(Object instance) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method[] methods = instance.getClass().getDeclaredMethods();
        for (Method m : methods) {
            if (m.isAnnotationPresent(Initialize.class)) {
                if (m.getAnnotation(Initialize.class).lazy() == true) {
                    if (!initializeService.contains(instance.getClass().getName() + m.getName())) {
                        m.setAccessible(true);
                        m.invoke(instance);
                        initializeService.add(instance.getClass().getName() + m.getName());
                    }
                }
            }
        }
    }

    private Object toInstantiate(Class c) throws InstantiationException, IllegalAccessException {
        return c.newInstance();
    }

    private Set<Class<?>> getAllClasses() {
        Set<Class<?>> classes;

        List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
        classLoadersList.add(ClasspathHelper.contextClassLoader());
        classLoadersList.add(ClasspathHelper.staticClassLoader());

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false), new ResourcesScanner())
                .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
                .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix("university.annotation.ClassesToTest"))));

        classes = reflections.getSubTypesOf(Object.class);
        return classes;
    }

}
