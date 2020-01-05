package com.gabdavid.osgi.sampleplugin;

public class App implements Runnable {

  // This is here for the sake of completeness, really. Wouldn't want to tell
  // Maven that this class has a main method when it doesn't actually have one!
  public static void main(String[] args) {
    new App().run();
  }

  public void run() {
    System.out.println("Hello from the Sample Plugin!");
  }
}
