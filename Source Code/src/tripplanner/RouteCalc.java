/**
 *RouteCalc.java
 *Purpose: Calculates all possible routes between origin and destination. Also, calculates shortest possible route between origin and destination
 *@Author: Faraaz Malak
 *@version 1.0 22/05/2010
 */
package tripplanner;

import java.util.*;

public class RouteCalc {

    private Town origin;
    private Town destination;
    private ArrayList<Route> routeList = new ArrayList<Route>();
    private Route shortestRte = null;

    public RouteCalc(Town theOrigin, Town theDest) {
        origin = theOrigin;
        destination = theDest;
    }

    /**
     *Checks each and every route's status (which is present in rteList)
     *
     *@return true If no route in rteList has a status of "open". Else returns false
     */
    private boolean checkRteStatus() {

        for (int i = 0; i < routeList.size(); i++) {
            if (routeList.get(i).getStatus().equals("open")) {
                return false;
            }
        }
        return true;
    }

    /**
     *Updates each and every route in rteList, until the route's status is either "dead" or "close"
     *
     *@param index Index of the route (in rteList), which is to be updated
     */
    private void updateRoute(int index) {
        Route targetRte = routeList.get(index);
        while (targetRte.getStatus().equals("open")) {
            targetRte.updateRoute();

        }

    }

    /**
     *Only returns the routes, whose status="close"
     *
     *@return A list of routes
     */
    public ArrayList<Route> getRteList(boolean finRoutesOnly) {
        ArrayList<Route> result = new ArrayList<Route>();
      
        for (int i = 0; i < routeList.size(); i++) {
             
            if (finRoutesOnly == true) {
                if (routeList.get(i).getStatus().equals("close")) {
                    result.add(routeList.get(i));
                }
            } else {
                result.add(routeList.get(i));
            }

        }

        return result;

    }

    /**
     *Co-ordinates all functions involved in route creation. This includes checkRteStatus(), updateRoute(), calcShortestRte();
     */
    public void calculateRoutes() {
        Route rte = new Route(origin, origin, destination, routeList);
        routeList.add(rte);
        int index = 0;
        while (checkRteStatus() == false) {
            updateRoute(index);

            if (index < routeList.size()) {
                index++;
            }
        }
        calcShortestRte();
    }

    /**
     *Returns the shortest route between origin and destination
     *
     *@return Shortest route
     */
    public Route getShortestRte() {
        return shortestRte;
    }

    /**
     *Determines the shortest route between origin and destination
     */
    private void calcShortestRte() {
        ArrayList<Route> validRoutes = getRteList(true);
        if (validRoutes.size() > 0) {
            shortestRte = validRoutes.get(0);

            for (int i = 0; i < validRoutes.size(); i++) {
                if (validRoutes.get(i).getRteDist() < shortestRte.getRteDist()) {
                    shortestRte = validRoutes.get(i);

                }
            }
        }
    }
}




