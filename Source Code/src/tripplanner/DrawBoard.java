/**
 *DrawBoard.java
 *Purpose: A panel, on which all routes, towns, roads etc are drawn
 *@Author: Faraaz Malak
 *@version 1.0 22/05/2010
 */
package tripplanner;

import javax.swing.*;
import java.awt.*;
import java.util.*;


public class DrawBoard extends JPanel {

    private ArrayList<Town> townList = null;
    private static final int BORDER = 60;
    private static final int TOWNRADIUS = 20;
    private static final int CONNECTORRADIUS = 10;
    private Point p1 = null;
    private Point p2 = null;
    private Route rte = null;
    //Contains drawing instruction
    private String instruction = "";
    private Town town = null;

    /**
     *Returns a border, which is applied as padding to all 4 sides of the panel
     *
     *@return border
     */
    public int getPanelBorder() {
        return BORDER;
    }

    /**
     *Gets radius of the circle, representing a town on drawPanel
     *
     *@return Circle radius
     */
    public int getTownRadius() {
        return TOWNRADIUS;
    }

    /**
     *Gets radius of the red connector, which connects the road and the town
     *
     *@return Circle radius
     */
    public int getConnectorRadius() {
        return CONNECTORRADIUS;

    }

    /**
    Updates townList property of this class
     *
     *@param newTownList ArrayList<Town> of towns radius
     */
    public void setTownList(ArrayList<Town> newTownList) {
        townList = newTownList;
    }

    /**
     *Draws a line on this panel
     *
     *@param x1 x coordinate pf first point
     * @param y1 y coordinate of first point
     * @param  x2 x coordinate of second point
     * @param y2 y coordinate of second point
     * @param  newInstruction Drawing instruction
     */
    public void drawLine(int x1, int y1, int x2, int y2, String newInstruction) {
        p1 = new Point(x1, y1);
        p2 = new Point(x2, y2);
        instruction = newInstruction;
        this.repaint();
    }

    /**
     *Draw a route on this panel
     *
     *@param targetRte Route to be drawn
     * @param  newInstruction Drawing instruction
     */
    public void drawRoute(Route targetRte, String newInstruct) {

        instruction = newInstruct;
        rte = targetRte;
        this.repaint();
    }

    /**
     *Draw a town on this panel
     *
     *@param newTown Town to be drawn
     * @param  newInstruction Drawing instruction
     */
    public void drawTown(Town newTown, String newInstruct) {
        instruction = newInstruct;
        town = newTown;
        this.repaint();
    }

    /**
     *Road, along with the coonector is drawn
     *
     *@param g Graphics class
     * @param p1 First point
     * @param p2 Second point
     * @rdColor Color of the road
     * @@param connectorColor color of the connector
     */
    private void repaintRoad(Graphics g, Point p1, Point p2, Color rdColor, Color connectorColor) {
        g.setColor(rdColor);
        g.drawLine(p1.getX() + (TOWNRADIUS / 2), p1.getY() + (TOWNRADIUS / 2), p2.getX(), p2.getY());

        int d = (int) Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));

        int x = 0;
        int y = 0;

        x = p1.getX() + (p2.getX() - p1.getX());
        y = p1.getY() + (p2.getY() - p1.getY());

        g.setColor(Color.red);
        g.drawOval(x - (CONNECTORRADIUS / 2), y - (CONNECTORRADIUS / 2), CONNECTORRADIUS, CONNECTORRADIUS);
        g.fillOval(x - (CONNECTORRADIUS / 2), y - (CONNECTORRADIUS / 2), CONNECTORRADIUS, CONNECTORRADIUS);
    }

    /**
     *Draw a town on this panel
     *
     *@param g Graphics class
     * @param  newTown Town to be drawn
     * @param townColor Color of the town
     */
    private void repaintTown(Graphics g, Town newTown, Color townColor) {
        g.setColor(townColor);

        g.drawOval(newTown.getPixelCoords().getX(), newTown.getPixelCoords().getY(), TOWNRADIUS, TOWNRADIUS);
        g.fillOval(newTown.getPixelCoords().getX(), newTown.getPixelCoords().getY(), TOWNRADIUS, TOWNRADIUS);
        g.drawString(newTown.getName(), newTown.getPixelCoords().getX(), (int) newTown.getPixelCoords().getY() + TOWNRADIUS + 10);
    }

    /**
     *Road, along with its connector is drawn
     *
     *@param g Graphics class
     * @param  lineColor color of the road
     * @param connectorColor Color of connector
     */
    private void repaintRoad(Graphics g, Color lineColor, Color connectorColor) {
        if (p1 != null && p2 != null) {
            g.setColor(lineColor);
            g.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());

            int x = 0;
            int y = 0;

            x = p1.getX() + (p2.getX() - p1.getX());
            y = p1.getY() + (p2.getY() - p1.getY());

            g.setColor(connectorColor);
            g.drawOval(x - (CONNECTORRADIUS / 2), y - (CONNECTORRADIUS / 2), CONNECTORRADIUS, CONNECTORRADIUS);
            g.fillOval(x - (CONNECTORRADIUS / 2), y - (CONNECTORRADIUS / 2), CONNECTORRADIUS, CONNECTORRADIUS);
        }

    }

    /**
     *Repains this component and all existing roads and towns. Also, draws additional towns, routes, roads etc, based on drawing instructions stored in instructions variable
     *
     *@param g Graphics class
     */
    public void paintComponent(Graphics g) {
        //Graphics g=new Graphics();


        super.paintComponent(g);
        if (townList != null && townList.size() > 0) {

            if (instruction.equals("drawLine") && p1 != null && p2 != null) {

                //Road drawn to follow mouse, when it is being dragged
                repaintRoad(g, Color.GREEN, Color.red);

            } else if (instruction.equals("erase") && p1 != null && p2 != null) {

                //Erases the drawn road
                g.setColor(this.getBackground());
                g.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
                p1=null;
                p2=null;

            }
            //Re-draws all existing towns
            for (int i = 0; i < townList.size(); i++) {

                Town newTown = townList.get(i);
                g.setColor(Color.white);

                repaintTown(g, newTown, Color.white);
            }

            //Hihlishts town in yellow
            if (town != null && instruction.equals("highlightTown")) {

                repaintTown(g, town, Color.yellow);
                repaintRoad(g, Color.GREEN, Color.red);

            }

            //Re-draws all existing roads
            for (int i = 0; i < townList.size(); i++) {

                Town newTown = townList.get(i);

                for (int i2 = 0; i2 < newTown.getTotalRoads(); i2++) {



                    Road newRoad = newTown.getRoadAtIndex(i2);
                    repaintRoad(g, newRoad.getStartPt(), newRoad.getEndPt(), Color.GREEN, Color.red);

                }
            }

            //Highlights shortest route
            if (instruction.equals("highlightRoute")) {

                g.setColor(Color.yellow);
                for (int i = 0; i < rte.getTotalLegs(); i++) {
                    Road newRoad = rte.getRteRoads().get(i);
                    repaintRoad(g, newRoad.getStartPt(), newRoad.getEndPt(), Color.BLUE, Color.red);
                }
            }
        } else {
            g.setColor(Color.white);
            g.drawString("Please select a file to read town data. Go to File -> Read Towns", (this.getWidth() / 2) - 100, this.getHeight() / 2);
        }
    }
}


