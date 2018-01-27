package org.jarexplorer;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Brute force index of jar file entries.
 */
public class EntryIndex
{


    public ArrayList<?> getClassesInJar ( String jarName ) throws Exception {

        System.out.println(jarName);
        String className = "";
        File jarIoFile = new File(jarName);
        ArrayList<Class<?>> classes = new ArrayList();

        if (!jarIoFile.exists())
            return null;

        JarFile jarFile = null;
        try {
            jarFile = new JarFile(jarName);
            System.out.println(jarName);
            Enumeration<JarEntry> entries = jarFile.entries();
            System.out.println(jarName);
            URL[] urls = {new URL("jar:file:" + jarName + "!/")};
            URLClassLoader cl = URLClassLoader.newInstance(urls);


            while (entries.hasMoreElements()) {
                JarEntry je = entries.nextElement();
                if (je.isDirectory() || !je.getName().endsWith(".class")) {
                    continue;
                }

                className = je.getName().substring(0, je.getName().length() - 6);
                className = className.replace('/', '.');
                try {
                    Class<?> c = cl.loadClass(className);
                    if (!c.isAnnotationPresent(jfk.prism.Description.class))
                        continue;
                    if (!jfk.prism.ICallable.class.isAssignableFrom(c))
                        continue;

                    classes.add(c);

                } catch (ClassNotFoundException exp) {
                    continue;
                }

            }
        } catch (IOException exp) {
            System.out.println("Blad");
        } finally {
            if (null != jarFile)
                jarFile.close();
        }
        return classes;
    }
}
