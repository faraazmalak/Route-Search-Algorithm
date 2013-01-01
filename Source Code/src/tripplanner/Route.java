/**
 *Route.java
 *Purpose: Represents a route between start location (town) and end location (town)
 *@Author: Faraaz Malak
 *@version 1.0 22/05/2010
 */
package tripplanner;

import java.util.*;

public class Route {

    private Town legOrigin;
    private Town finalDest;
    private ArrayList<Road> routeRoads = new ArrayList<Road>();
    private ArrayList<Route> routeList = null;
    private String status = "open";
    private double routeDist = 0;
    private Town startTown = null;

    /**
     *Constructor for Route class, which initializes the route object with starting town (of the route), starting town(of the leg), final destination (of the route) and a list of existing routes
     *
     *@param theStartTown  Origin town (of the entire route)
     * @param theLegOrigin Origin town (of the current leg only)
     * @param newRteList A list of all existing routes
     */
    public Route(Town theStartTown, Town theLegOrigin, Town theDest, ArrayList<Route> newRteList) {
        finalDest = theDest;
        routeList = newRteList;
        startTown = theStartTown;

        if (legOrigin == null) {
            legOrigin = theLegOrigin;
        }

        //System.out.println("Final Dest: "+finalDest.getName());
    }

    /**
     *Checks to see if current route has reached a dead end
     *
     *@return  true = dead end reached; false = dead end not reached
     */
    private boolean checkDeadEnd() {
        boolean result = verifyRoad(legOrigin.getRoads());

        if (result == true) {
            return false;
        } else {
            return true;
        }
    }

    /**
     *Checks to see if a list of roads have already been covered or not.
     * @param roadList The road list to be verified
     *@return true = All roads in the list have not been covered yet; false = Even if a single road in the list has already been covered
     */
    private boolean verifyRoad(ArrayList<Road> roadList) {
        boolean result = true;
        for (int i = 0; i < roadList.size(); i++) {
            if (verifyRoad(roadList.get(i)) == false) {
                result = false;

                //If  a road exists in routeRoads, then remove it from roads list
                roadList.remove(i);


            }
        }
        return result;
    }

    /**
     *Checks to see if the road being added (to the current route or duplicated route), has already been covered or not.
     * @param targetRoad The road to be verified
     *@return true = road can be added to current or duplicated route; false = road cannot be added to current or duplicated route
     */
    private boolean verifyRoad(Road targetRoad) {
        for (int i = 0; i < routeRoads.size(); i++) {
            //System.out.println("Route Road Origin: "+ routeRoads.get(i).getOrigin().getName()+" Route Road Dest: "+ routeRoads.get(i).getDestination().getName());
            
            if (routeRoads.get(i).getID() == targetRoad.getID() || routeRoads.get(i).getDestination().getName().equals(startTown.getName())) {
                return false;
            }
        }
        return true;
    }

    /**
     *Checsk to see if current route is finished or not (Destination has been reached or not)
     *
     *@param true = destination reached; false = destination not reached
     */
    private boolean isDestReached() {
        boolean result = false;

        if (legOrigin.getName().equals(finalDest.getName()) == false && routeRoads.size() > 0 && routeRoads.get(routeRoads.size() - 1).getDestination().getName().equals(finalDest.getName()) == false) {
            result = false;

        } else if (legOrigin.getName().equals(finalDest.getName()) == true && routeRoads.size() > 0 && routeRoads.get(routeRoads.size() - 1).getDestination().getName().equals(finalDest.getName()) == true) {
            result = true;
        }
        return result;
    }

    /**
     *Updates the status of the current route to either close (meanining route has reached the destination); open (route has not yet reached destination) or dead (route has reached a dead end)
     */
    private void updateStatus() {
        if (status.equals("open")) {
            if (isDestReached() == true) {
                status = "close";
                calculateRteDist();

            } else if (status.equals("close") == false && status.equals("open") == false) {
                status = "dead";

            } else if (checkDeadEnd() == true && isDestReached() == false) {
                status = "dead";
            } else if (legOrigin.getTotalRoads() == 0) {
                status = "dead";
            }




        }
    }

    /**
     *Adds a new leg (road) to the current route, if certain conditions are met
     */
    public void updateRoute() {

        if (status.equals("open") && isDestReached() == false) {

            //Gets all the roads starting from legOrigin
            ArrayList<Road> roads = legOrigin.getRoads();
          //  System.out.println("Toatl outgoing roads from "+legOrigin.getRoads().get(0).getOrigin().getName()+" "+legOrigin.getRoads().get(0).getDestination().getName());

            if (roads.size() > 0) {
                //If there are out-going one way roads from legOrigin, then below code is processed

                //Verifies if out-going roads have already been covered or not. If any roads are found, which have already been covered, verifyRoad() removes them
                verifyRoad(roads);
              // System.out.println("Road size after verififcation: "+roads.size());
                if (roads.size() > 0) {
                    //If there are some roads that have not yet been covered, then below code is processed

                    //Takes the first road from the list, so that it is included in current route
                    Road theRoad = roads.get(0);
                    roads.remove(0);

                    //Duplicates routes to create new route from this branching point (legOrigin)
                    duplicateRoute(roads);

                    if (verifyRoad(theRoad) == true) {
                        //Verifies the road and adds it to current route
                        routeRoads.add(theRoad);
                    } else {
                        //If verification fails, the current route is marked as "dead"
                        status = "dead";
                    }
                    //Updates legOrigin
                    legOrigin = routeRoads.get(routeRoads.size() - 1).getDestination();
                } else {
                    updateStatus();
                }
            } else {
                updateStatus();
            }
        } else {
            updateStatus();
        }




    }

    /**
     *Adds a list of roads (already covered), to a newly duplicated route
     *
     *@param newRoadList  A list of roads, which have already been covered
     */
    public void addRoads(ArrayList<Road> newRoadList) {

        for (int i = 0; i < newRoadList.size(); i++) {
            if (verifyRoad(newRoadList.get(i)) == true) {
                routeRoads.add(newRoadList.get(i));
            }
        }
    }

    /**
     *Adds a single road, to a newly duplicated route
     *
     *@param theRoad  A Road object to be added to the newly created route
     */
    public void addRoad(Road theRoad) {
        if (verifyRoad(theRoad) == true) {
            routeRoads.add(theRoad);
        }

    }

    /**
     *Duplicates the current route object (if there are more than 1 possible exit locations from a town).
     * Also, adds to the newly duplicated route object, a list of roads already covered by this route object
     *
     *@param A list of roads exiting the current town
     */
    private void duplicateRoute(ArrayList<Road> targetRoads) {

        for (int i = 0; i < targetRoads.size(); i++) {

            Route newRoute = new Route(startTown, targetRoads.get(i).getDestination(), finalDest, routeList);
            if (routeRoads.size() > 0) {
                newRoute.addRoads(routeRoads);
            }
            newRoute.addRoad(targetRoads.get(i));
            routeList.add(newRoute);


        }

    }

    /**
     *Returns the status of current route
     *
     *@return status of current route
     */
    public String getStatus() {
        return status;
    }

    /**
     *Returns total distance (in km) of the current route
     *
     *@return  Total distance (in km) of the current route
     */
    public double getRteDist() {
        return routeDist;

    }

    /**
     *Calculates the total distance of the entire route (in km)
     */
    private void calculateRteDist() {
        if (status.equals("close")) {
            for (int i = 0; i < routeRoads.size(); i++) {
                routeDist += routeRoads.get(i).getDistance();
            }
        }
    }

    /**
     *Returns a list of legs / roads, that make the current route
     *
     *@return A list of roads
     */
    public ArrayList<Road> getRteRoads() {
        return (ArrayList<Road>) routeRoads.clone();
    }

    /**
     *Returns total number of legs, which form the current route
     *
     *@param Total number of roads / legs, which form the current route
     */
    public int getTotalLegs() {
        return routeRoads.size();
    }
}
