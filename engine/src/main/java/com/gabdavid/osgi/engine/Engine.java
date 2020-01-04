package com.gabdavid.osgi.engine;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.Attributes;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

public class Engine {

  public static void main(String[] args) throws Exception {

    if (args.length != 1) {
      System.out.println("Path must be passed in");
      return;
    }

    String path = args[0];
    File jarFile = new File(path);

    String className;
    // Try with resources
    try (JarInputStream jarStream = new JarInputStream(new FileInputStream(jarFile))) {
      Manifest manifest = jarStream.getManifest();

      // Get Main-Class attribute from MANIFEST.MF
      className = manifest.getMainAttributes().getValue(Attributes.Name.MAIN_CLASS);
    }

    System.out.println("Class name found in manifest is: " + className);

    // Here, we initialize the class loader with the path to the jar file.
    //
    // We use the jar protocol here. The !/ is used to separate between the URL to the actual
    // jar resource and the path within the jar. For example, I could download a whole jar by
    // calling jar:http://something.com/myjar!/ or I could download only 1 class file by calling
    // jar:http://something.com/myjar!/com/gabdavid/myjar/File.class
    // See https://docs.oracle.com/javase/7/docs/api/java/net/JarURLConnection.html for more details
    URLClassLoader classLoader = new URLClassLoader(
        new URL[]{new URL("jar:" + jarFile.toURI().toURL() + "!/")}
    );

    Class<?> pluginClass = classLoader.loadClass(className); // Load the class

    System.out.println("Package of plugin class: " + pluginClass.getPackage());
    System.out.println("Interface of plugin class is: " + pluginClass.getInterfaces()[0].getName());
    System.out.println("Creating a new instance now....!");

    // Create an instance of the loaded class
    Runnable plugin = (Runnable) pluginClass.newInstance();
    System.out.println("Running instance now....!");
    plugin.run();

    System.out.println("Exiting successfully!");
  }
}
