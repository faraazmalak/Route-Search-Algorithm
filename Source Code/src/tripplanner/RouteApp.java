/**
 *RouteApp.java
 *Purpose: Controls the overall program logic and interaction with the user
 *@Author: Faraaz Malak
 *@version 1.0 22/05/2010
 */
package tripplanner;

import java.io.*;
import java.util.*;

public class RouteApp {

  
  
    private GUI appGUI=null;
    

    /**
     *Constructor for RouteApp class, which gets user input and controls overall program logic
     */
    public RouteApp() {
       appGUI=new GUI();
    }

  
    /**
     *Creates RouteApp object
     *
     *@args List of arguments passed into the program
     */
    public static void main(String[] args) {
        RouteApp app = new RouteApp();
    }
}
