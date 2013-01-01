/**
 *Town.java
 *Purpose: Represnts a town, including name of the town, its latitude, longitude etc
 *@Author: Faraaz Malak
 *@version 1.0 22/05/2010
 */
package tripplanner;

import java.util.*;
import java.awt.geom.*;

public class Town {

    private String name;
    private double latDeg = 0;
    private double latMin = 0;
    private double latSec = 0;
    private double longDeg = 0;
    private double longMin = 0;
    private double longSec = 0;
    private Point pixelCoords = null;
    //A list of roads exiting the town
    private ArrayList<Road> roads = new ArrayList<Road>();

    /**
     *Constructor for town object
     *
     *@param theName Name of the town
     * @param theLatDeg latitude degrees
     * @param theLatMin latitude minutes
     * @param theLatSec the latitude seconds
     * @param theLongDeg the longitude degree
     * @param theLongMin longitude minutes
     * @param theLongSec Longitude seconds
     * @param theLongitude longitude
     */
    public Town(String theName, double theLatDeg, double theLatMin, double theLatSec, double theLongDeg, double theLongMin, double theLongSec) {
        name = theName;

        latDeg = theLatDeg;
        latMin = theLatMin;
        latSec = theLatSec;

        longDeg = theLongDeg;
        longMin = theLongMin;
        longSec = theLongSec;


    }

    /**
     *Sets town's coords in pixels
     *
     *@param newCoords Pair of x and y valuess
     */
    public void setPixelCoords(Point newCoords) {
        pixelCoords = newCoords;
    }

    /**
     *Returns town's coords in pixels
     *
     *@return  Pair of x and y valuess
     */
    public Point getPixelCoords() {
        return pixelCoords;
    }

    /**
     *Returns a list of roads exiting the town
     *
     *@return A list of roads exiting the town
     */
    public ArrayList<Road> getRoads() {
        return (ArrayList<Road>) roads.clone();
    }

    /**
     *Returns total number of roads exiting the town
     *
     *@return total number of roads exiting the town
     */
    public int getTotalRoads() {
        return roads.size();
    }

    /**
     *Adds a road (which exits the current town), to the list
     *
     *@param theRoad Road to be added to the list (of roads, which exit the current town)
     */
    public void addRoad(Road theRoad) {
        roads.add(theRoad);
    }

    /**
     *Returns the road, at specified index position within the road list
     *
     *@param Road object
     */
    public Road getRoadAtIndex(int index) {
        return roads.get(index);
    }

    /**
     *Returns the road, with specified ID (which is passed in as parameter)
     *
     *@param ID Road object's ID
     * @return Road object
     */
    public Road getRoadByID(int id) {

        for (int i = 0; i < getTotalRoads(); i++) {
            if (roads.get(i).getID() == id) {
                return roads.get(i);
            }
        }
        return null;
    }

    /**
     *Returns the road, starting at the current town and going the dest (passed in as parameter)
     *
     *@param dest Destination town, where the road ends
     * @return Road, connecting current town and dest
     */
    public Road getRoadToDest(Town dest) {
        Road result = null;
        for (int i = 0; i < roads.size(); i++) {
            if (roads.get(i).getDestination().getName().equals(dest.getName())) {
                result = roads.get(i);
            }
        }

        return result;
    }

    /**
     *Returns name of the town
     *
     *@return name of the town
     */
    public String getName() {

        return name;
    }

    /**
     *Returns longitude of the town in decimal degrees
     *
     *@return Town's longitude
     */
    public double getLongitude() {

        return toDegree(longDeg, longMin, longSec);
    }

    /**
     *Returns latitude of the town in decimal degrees
     *
     *@return Town's latitude
     */
    public double getLatitude() {
        return toDegree(latDeg, latMin, latSec);
    }

    /**
     *Converts degrees, minutes, second format of latitude / longitude to decimald degrees
     *
     * @param theDeg Degree of latitude / longitude
     * @param theMin Minutes of latitude / longitude
     * @param theSec Seconds of latitude / longitude
     *@return Town's latitude / longitude in decimal degrees
     */
    private double toDegree(double theDeg, double theMin, double theSec) {
        double result = (theDeg) + (theMin / 60) + (theSec / 3600);
        return result;
    }
}
