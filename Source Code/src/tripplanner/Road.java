/**
 *Road.java
 *Purpose: Represents a one-way road, with start location, end location, distance between start location & end location etc
 *@Author: Faraaz Malak
 *@version 1.0 22/05/2010
 */
package tripplanner;

public class Road {

    private Town origin;
    private Town destination;
    private double distance;
    //road id
    private int id;

    private Point start=null;
    private Point end=null;

    /**
     *Constructor for Road class, which initializes a Road object with origin, destionation and a ID
     *
     *@param  theOrigin Town object, from where the road starts
     *@param theDestination Town object, wheer the road ends
     * @param ID Road object's ID
     */
    public Road(Town theOrigin, Town theDestination, int theID,Point newStart,Point newEnd) {
        origin = theOrigin;
        destination = theDestination;
        id = theID;
        start=newStart;
        end=newEnd;
        calculateDistance();

    }

     public Road(Town theOrigin, Town theDestination, int theID) {
        origin = theOrigin;
        destination = theDestination;
        id = theID;

        calculateDistance();

    }
    public Point getStartPt()
    {
        return start;
    }

    public Point getEndPt()
    {
        return end;
    }
    public Road(int theID)
    {
        id=theID;
    }
    /**
     *Returns ID of the road
     *
     *@return  ID of the road
     */
    public int getID() {
        return id;
    }


    public void setOrigin(Town theOrigin)
    {
        origin=theOrigin;
    }
    /**
     *Returns origin town of the road
     *
     *@return   origin town of the road
     */
    public Town getOrigin() {

        return origin;

    }

    public void setDestination(Town theDest)
    {
        destination=theDest;

    }
    /**
     *Returns destination town of the road
     *
     *@return   destination town of the road
     */
    public Town getDestination() {
        return destination;
    }

    /**
     *Calculates the distance between Origin town and destination town. Distance is calculated in km, using Haversine formula
     */
    public void calculateDistance() {
//        double aa=(111) + (52/60) + (50.88/3600);
//        double b=(2) + (51/60) + (35.10/3600);
//        double cc=(111) + (56/60) + (20.12/3600);
//        double d=(2) + (44/60) + (35.79/3600);

        double long1 = Math.toRadians(origin.getLongitude());
        double lat1 = Math.toRadians(origin.getLatitude());

        double long2 = Math.toRadians(destination.getLongitude());
        double lat2 = Math.toRadians(destination.getLatitude());



        double dlong = long2 - long1;


        double dlat = lat2 - lat1;


        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlong / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = (6378.7 * c);


        distance = dist;
      

    }

    public double getLongMetre(Town targetTown) {
        double long1 = Math.toRadians(targetTown.getLongitude());
        double lat1 = Math.toRadians(targetTown.getLatitude());





        double dlong = long1;


        double dlat =lat1;


        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.pow(Math.sin(dlong / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = (6378.7 * c);


     return dist;
    }

    /**
     *Returns distance between origin town and destination town
     *
     *@return  distance between origin town and destination town
     */
    public double getDistance() {
        return distance;

    }
}
