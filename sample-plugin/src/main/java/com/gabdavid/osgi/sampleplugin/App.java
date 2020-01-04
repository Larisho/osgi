package com.gabdavid.osgi.sampleplugin;

public class App implements Runnable {

  public static void main(String[] args) {
    new App().run();
  }

  public void run() {
    System.out.println("Hello from the Sample Plugin!");
  }
}
