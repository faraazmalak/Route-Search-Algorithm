/**
 *Point.java
 *Purpose: Represents a 2D point, with x and y coordinates
 *@Author: Faraaz Malak
 *@version 1.0 22/05/2010
 */
package tripplanner;

public class Point {

    private int x = 0;
    private int y = 0;

    /**
     *Constructor
     */
    public Point() {
    }

    /**
     *Constructor
     *
     *@param newX  New x coordinate
     * @param newY new Y coordinate
     */
    public Point(int newX, int newY) {
        x = newX;
        y = newY;
    }

    /**
     *Sets x coordinate
     *
     *@param  New x coordinate
     */
    public void setX(int newX) {
        x = newX;
    }

    /**
     *Returns x coordinate
     *
     *@return   x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     *Sets y coordinate
     *
     *@param  New y coordinate
     */
    public void setY(int newY) {
        y = newY;
    }

    /**
     *Returns y coordinate
     *
     *@return   y coordinate
     */
    public int getY() {
        return y;
    }
}
