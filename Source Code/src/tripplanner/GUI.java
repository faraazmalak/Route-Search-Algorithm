/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tripplanner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author Faraaz Malak
 */
public class GUI extends JPanel implements MouseMotionListener, ActionListener, MouseListener {
    //Declaration and initialization of UI components

    //Main frame, which holds the entrire UI
    private JFrame mainFrame = new JFrame();
    //Label to display text in status bar
    private JLabel statusLabel = new JLabel("Status: Ready !");
    //Panel, on which routes will be drawn
    private DrawBoard drawPanel = null;
    //Holds user- input controls like text boxes,buttons etc
    private JPanel inputPanel = new JPanel();
    //Menu Bar
    private JMenuBar menuBar = new JMenuBar();
    //Help menu
    private JMenu helpMenu = new javax.swing.JMenu("Help");
    //Instructions menu item
    private JMenuItem instMenuItem = new JMenuItem("Instructions");
    //File menu
    private JMenu fileMenu = new javax.swing.JMenu("File");
    //Open menu item to be placed under File menu
    private JMenuItem openMenuItem = new JMenuItem("Read Towns");
    //Exit menu item to be placed under File menu
    private JMenuItem exitMenuItem = new JMenuItem("Exit");
    //Label displaying "From" text. Ment to capture start town index
    private JLabel startTownLabel = new JLabel("From:");
    //Text box, where user is expected to enter start town index
    private JTextField startTownTxt = new JTextField();
    //Label displaying "To" text. Ment to capture end town index
    private JLabel endTownLabel = new JLabel("To:");
    //Text box, where user is expected to enter end town index
    private JTextField endTownTxt = new JTextField();
    //Button, which when clicked, will display all possible routes
    private JButton routeBtn = new JButton("Get Route(s)");
    //Label
    private JLabel indexLabel = new JLabel("Town Indices:");
    //Text area to display all town indices
    private JTextArea indexTxtArea = new JTextArea();
    private JTextArea rteOutput = new JTextArea();
    //File chooser dialog box
    private JFileChooser fileChooser = new JFileChooser(".");
    //Help UI to display instructions on how to use this application
    private helpUI help = null;
    private JScrollPane outputScrollPane = new JScrollPane(rteOutput);
    private static ArrayList<Town> townList = null;
    private RouteCalc rteCalc = null;
    private Map townMap = new Map();
    private Town rdOrigin = null;
    private Town rdDest = null;

    
    
    /**
     * Constructor that creates GUI for the application
     */
    public GUI() {


        drawPanel = new DrawBoard();
        drawPanel.addMouseListener(this);

        //GridBagLayout Manager constraint
        GridBagConstraints layoutConstraint;

        //Text Box dimensions
        Dimension txtSize = new Dimension(150, 22);


        //Code to initialize JFame
        mainFrame.setSize(800, 600);
        mainFrame.setTitle("Route Planner");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension d = new Dimension();
        d.height = 800;
        d.width = 1280;

        mainFrame.setSize(d);


        //Menu Bar
        mainFrame.setJMenuBar(menuBar);
        menuBar.addMouseMotionListener(this);



        //Open menu item. Accessed by going to File->Open
        openMenuItem.addActionListener(this);

        fileMenu.add(openMenuItem);

        menuBar.add(fileMenu);

        //Exit menu item. Accessed by going to File->Exit
        exitMenuItem.addActionListener(this);

        fileMenu.add(exitMenuItem);

        //Instructions menu item. Accessed by going to Help->Instructions
        instMenuItem.addActionListener(this);
        helpMenu.add(instMenuItem);
        menuBar.add(helpMenu);


        //Input Panel. Contains, user-input controls like text boxes, button etc
        inputPanel.setBackground(Color.lightGray);
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.addMouseMotionListener(this);

        mainFrame.getContentPane().add(inputPanel, BorderLayout.WEST);




        //Layout for From Label:
        layoutConstraint = new GridBagConstraints();
        layoutConstraint.gridx = 0;
        layoutConstraint.gridy = 0;
        layoutConstraint.fill = GridBagConstraints.HORIZONTAL;

        inputPanel.add(startTownLabel, layoutConstraint);

        //Layout for startTown Text Fields. User need to enter starting town in this text field
        layoutConstraint = new GridBagConstraints();
        layoutConstraint.gridx = 1;
        layoutConstraint.gridy = 0;
        layoutConstraint.fill = GridBagConstraints.HORIZONTAL;

        startTownTxt.setPreferredSize(txtSize);

        inputPanel.add(startTownTxt, layoutConstraint);


        //Layout for Text Label:
        layoutConstraint = new GridBagConstraints();
        layoutConstraint.gridx = 0;
        layoutConstraint.gridy = 1;
        layoutConstraint.fill = GridBagConstraints.HORIZONTAL;

        inputPanel.add(endTownLabel, layoutConstraint);

        //Layout for To Text Fields. Usre needs to enter destination town in this text box
        layoutConstraint = new GridBagConstraints();
        layoutConstraint.gridx = 1;
        layoutConstraint.gridy = 1;
        layoutConstraint.fill = GridBagConstraints.HORIZONTAL;
        layoutConstraint.insets = new java.awt.Insets(10, 0, 0, 0);

        endTownTxt.setPreferredSize(txtSize);

        inputPanel.add(endTownTxt, layoutConstraint);

        //Layout for Get Route button. User clicks this button to display all routes between start and end town
        layoutConstraint = new GridBagConstraints();
        layoutConstraint.gridx = 0;
        layoutConstraint.gridy = 2;
        layoutConstraint.gridwidth = 2;
        layoutConstraint.insets = new java.awt.Insets(10, 0, 0, 0);

        inputPanel.add(routeBtn, layoutConstraint);

        routeBtn.addActionListener(this);

        //Layout for Text Label:
        layoutConstraint = new GridBagConstraints();
        layoutConstraint.gridx = 0;
        layoutConstraint.gridy = 4;
        layoutConstraint.gridwidth = 2;
        layoutConstraint.fill = GridBagConstraints.HORIZONTAL;

        layoutConstraint.insets = new java.awt.Insets(5, 0, 0, 0);

        inputPanel.add(indexLabel, layoutConstraint);


        //Layout & initialization for Text Area: Displays all the indices of different towns
        indexTxtArea.setColumns(20);
        indexTxtArea.setRows(10);
        indexTxtArea.setEditable(false);
        indexTxtArea.setText("0=Sibu\n1=Selangau\n2=Mukah\n3=Oya\n4=Dalat\n5=Igan\n6=Matu\n7=Daro");

        layoutConstraint = new GridBagConstraints();
        layoutConstraint.gridx = 0;
        layoutConstraint.gridy = 5;
        layoutConstraint.gridwidth = 2;
        layoutConstraint.gridheight = 1;
        layoutConstraint.fill = GridBagConstraints.HORIZONTAL;


        inputPanel.add(indexTxtArea, layoutConstraint);

        //Draw Panel. This is where all the routes will be drawn
        drawPanel.setBackground(Color.darkGray);
        drawPanel.addMouseMotionListener(this);
        drawPanel.setLayout(new BorderLayout());
        mainFrame.getContentPane().add(drawPanel, BorderLayout.CENTER);
        //splitContainer.setTopComponent(drawPanel);

       // rteOutput.setEditable(false);

        //Makes the main JFrame visible
        mainFrame.setVisible(true);
      
        Dimension d2 = new Dimension(100, 100);

        outputScrollPane.setPreferredSize(d2);
        mainFrame.getContentPane().add(outputScrollPane, BorderLayout.SOUTH);

        

        mainFrame.setSize(800, 600);


    }

     
    /**
     *Returns minimum latitude from townList
     *
     *@return Minimum latitude
     */
    private double getMinLat() {
        double result = townList.get(0).getLatitude();

        for (int i = 1; i < townList.size(); i++) {

            if (townList.get(i).getLatitude() < result) {

                result = townList.get(i).getLatitude();
            }
        }


        return result;


    }

    /**
     *Returns maximum latitude from townList
     *
     *@return Maximum latitude
     */
    private double getMaxLat() {

        double result = 0;

        for (int i = 0; i < townList.size(); i++) {

            if (townList.get(i).getLatitude() > result) {
                result = townList.get(i).getLatitude();
            }
        }

        return result;

    }

    /**
     *Returns maximum longitude from townList
     *
     *@return Maximum longitude
     */
    private double getMaxLong() {

        double result = 0;

        for (int i = 0; i < townList.size(); i++) {

            if (townList.get(i).getLongitude() > result) {
                result = townList.get(i).getLongitude();
            }
        }

        return result;

    }

    /**
     *Returns minimum longitude from townList
     *
     *@return Minimum longitude
     */
    private double getMinLong() {
        double result = townList.get(0).getLongitude();

        for (int i = 1; i < townList.size(); i++) {

            if (townList.get(i).getLongitude() < result) {
                result = townList.get(i).getLongitude();
            }
        }

        return result;


    }

    /**
     *Scales down latitudes and longitude of each town from decimal  degrees to pixels
     */
    private void setTownCoords() {

        for (int i = 0; i < townList.size(); i++) {
            Town newTown = townList.get(i);

            Point townCoords = new Point();



            int xVal = (int) Math.abs(((newTown.getLongitude() - getMinLong()) * getScale()));
            if (xVal > drawPanel.getWidth() - drawPanel.getPanelBorder()) {

                xVal = drawPanel.getWidth() - drawPanel.getPanelBorder();
            } else if (xVal < drawPanel.getPanelBorder()) {
                xVal = drawPanel.getPanelBorder();

            }
            townCoords.setX(xVal);


            townCoords.setY((int) Math.abs((drawPanel.getHeight() - drawPanel.getPanelBorder()) - ((newTown.getLatitude() - getMinLat()) * getScale())));


            newTown.setPixelCoords(townCoords);
        }


    }

    /**
     *Returns the scaling factor to scale fro, decimal degrees to pixels
     *
     *@return Scaling Factor
     */
    private double getScale() {
        double lat = Math.abs((drawPanel.getHeight() - drawPanel.getPanelBorder()) / (getMaxLat() - getMinLat()));
        double longi = Math.abs((drawPanel.getWidth() - drawPanel.getPanelBorder()) / (getMaxLong() - getMinLong()));

        if (lat < longi) {

            return lat;
        } else {

            return longi;

        }

    }

    /**
     *Returns Town object, if mouse is currently over a town's pixel coordinate.Also, highlights the town, if mouse is being dragged over it. Else, returns a null
     *
     *@return Town object / null
     */
    private Town mouseOverTown(int x, int y) {


        for (int i = 0; i < townList.size(); i++) {
            Town targetTown = townList.get(i);

            if (x >= targetTown.getPixelCoords().getX() && x <= targetTown.getPixelCoords().getX() + drawPanel.getTownRadius()) {

                if (y >= targetTown.getPixelCoords().getY() && y <= targetTown.getPixelCoords().getY() + drawPanel.getTownRadius()) {

                    drawPanel.drawTown(targetTown, "highlightTown");
                    return targetTown;

                }




            }

        }


        return null;
    }

    /*Interface Implementations*/

    /*MouseMotionListner Interface implementation*/
    /**
     * Updates text in status panel, based on position of the mouse on the UI
     */
    public void mouseMoved(MouseEvent event) {
    }

    /**
     *Generates road id for a new road
     *
     *@return Road ID
     */
    private int generateRdID() {
        int roadID = 0;

        for (int i = 0; i < townList.size(); i++) {
            Town newTown = townList.get(i);

            //If a two way road is being created, then returns ID of existing one way road between the same origin and destination
            for (int i2 = 0; i2 < newTown.getTotalRoads() - 1; i2++) {
                Road newRoad = newTown.getRoadAtIndex(i2);

                if (newRoad.getOrigin().getName().equals(rdDest.getName()) && newRoad.getDestination().getName().equals(rdOrigin.getName())) {
                    return newRoad.getID();
                }
            }
        }


        //If 2-way road is not being created, then returns highest road id + 1
        for (int i = 0; i < townList.size(); i++) {
            Town newTown = townList.get(i);

            for (int i2 = 0; i2 < newTown.getTotalRoads(); i2++) {

                Road newRoad = newTown.getRoads().get(i2);

                if (newRoad.getID() >= roadID) {
                    roadID = newRoad.getID() + 1;
                }
            }
        }


        return roadID;
    }

    /**
     *Displays routes in text area component
     *
     *@param origin Origin town of the journey
     * @param  dest Destination town of the journey
     */
    private void displayRoutes(Town origin, Town dest) {


        String output = "";
        ArrayList<Route> rteOutputList = null;
        int counter = 0;

        while (counter < 2) {
            if (counter == 1) {
                rteOutputList = new ArrayList<Route>();
                rteOutputList.add(rteCalc.getShortestRte());
            } else {
                rteOutput.setText("");
                rteOutputList = rteCalc.getRteList(true);
            }

            for (int i = 0; i < rteOutputList.size(); i++) {
                Route currentRte = rteOutputList.get(i);


                ArrayList<Road> rteRoads = currentRte.getRteRoads();

                if (counter == 1) {
                    output += "\n====Displaying Shortest Route ==========================================================================================\n";
                } else {
                    output += "\n====Showing Route " + (i + 1) + " of " + rteOutputList.size() + " ==========================================================================================\n";
                }

                output += "\n------Route Summary----------\n";
                if (currentRte.getStatus().equals("close")) {
                    output += "\nTotal Route Distance: " + currentRte.getRteDist() + " km\n";
                    output += "\nNumber of Legs: " + currentRte.getTotalLegs() + "\n";
                }

                output += "\nOrigin: " + origin.getName() + "\n";
                output += "\nDestination: " + dest.getName() + "\n";
                output += "\nRoute Status: " + currentRte.getStatus() + "\n";
                output += "\n--------------------------------\n";

                output += "\n------Route Legs----------\n";

                for (int i2 = 0; i2 < rteRoads.size(); i2++) {

                    Road firstRoad = rteRoads.get(i2);
                    Road secRoad = firstRoad.getDestination().getRoadToDest(firstRoad.getOrigin());


                    output += "\nRoad From: " + firstRoad.getOrigin().getName() + " To: " + firstRoad.getDestination().getName() + " Distance:  " + firstRoad.getDistance() + " km\n";

                    if (secRoad != null) {

                        output += "\n(Another Road runs From: " + secRoad.getOrigin().getName() + " To: " + secRoad.getDestination().getName() + " Distance:  " + secRoad.getDistance() + " km)\n";
                    }

                    output += "\n";




                }
                output += "\n--------------------------------\n";
                output += "\n===================================================================================================================\n";

            }

            rteOutput.setText(output);
            counter++;
        }

    }

    /**
     *If mouse is currently over a town's pixel coordinates, then sets RdOrigin variable to that town
     *
     *@param event Containing info about the event
     */
    public void mousePressed(MouseEvent event) {
        rdOrigin = mouseOverTown(event.getX(), event.getY());
    }

    public void mouseClicked(MouseEvent event) {
    }

    /**
     *If mouse is currently over a town's pixel coordinates, then sets RdOrigin variable to that town. And creates a new road object, and adds it
     * to RdOrigin variable
     *
     *@param event Containing info about the event
     */
    public void mouseReleased(MouseEvent event) {

        Town newTown = mouseOverTown(event.getX(), event.getY());
        if (newTown != null && newTown.getName().equals(rdOrigin.getName()) == false) {
            rdDest = newTown;
            int rdID = generateRdID();


            Point rdEndPt = new Point(event.getX(), event.getY());
            Road newRoad = new Road(rdOrigin, rdDest, rdID, rdOrigin.getPixelCoords(), rdEndPt);
            rdOrigin.addRoad(newRoad);


           
            drawPanel.setTownList(townList);
            drawPanel.drawLine(rdOrigin.getPixelCoords().getX() + drawPanel.getTownRadius() / 2, rdOrigin.getPixelCoords().getY() + drawPanel.getTownRadius() / 2, event.getX(), event.getY(), "erase");

            townMap.updateTown(rdOrigin);


            rdOrigin = null;
            rdDest = null;

        } else if (rdOrigin != null) {
            drawPanel.drawLine(rdOrigin.getPixelCoords().getX(), rdOrigin.getPixelCoords().getY(), event.getX(), event.getY(), "erase");
            rdOrigin = null;
            rdDest = null;
        }
    }

    public void mouseEntered(MouseEvent event) {
    }

    public void mouseExited(MouseEvent event) {
    }

    /**
     *Draws a line to follow the cursor, as mouse is being dragged. Also calls mouseOverTown(), to highlight the town in yellow, if mouse
     * is currently over the town
     *
     *@param event Containing info about the event
     */
    public void mouseDragged(MouseEvent event) {

        if (rdOrigin != null) {
            drawPanel.drawLine(rdOrigin.getPixelCoords().getX() + drawPanel.getTownRadius() / 2, rdOrigin.getPixelCoords().getY() + drawPanel.getTownRadius() / 2, event.getX(), event.getY(), "drawLine");

        }
        mouseOverTown(event.getX(), event.getY());
    }
    /*End of MouseMotionListner Interface implementation*/

    /*ActionListner Interface Implementation*/
    /**
     * Executed when menu items under File and Help menus are clicked
     */
    public void actionPerformed(ActionEvent evt) {

        if (evt.getSource() == instMenuItem) {

            help = new helpUI(mainFrame, "Instructions", true, "Instructions: To get all possible routes between 2 towns, please do the following:\n\n1. Click on File->Read Towns to load the file with locations of the towns\n\n2. Create roads between towns. Stop dragging when the white circle turns yellow.\n\n3. Enter indices of start and end locations and click on Get Routes button\n\n4. Shortest Route will be highlighted in blue color");
            help.setVisible(true);

        } else if (evt.getSource() == exitMenuItem) {

            System.exit(0);

        } else if (evt.getSource() == openMenuItem) {

            fileChooser.showOpenDialog(null);
            File townFile = fileChooser.getSelectedFile();

            try {
                townMap.readTowns(townFile);
                townList = townMap.getTownList();

                setTownCoords();


                drawPanel.setTownList(townList);
                drawPanel.repaint();
            } catch (FileNotFoundException e) {
                System.out.println(e);
            } catch (Exception e) {
                System.err.println(e);
            }


        } else if (evt.getSource() == routeBtn) {

            townMap.run();
            Town origin = null;
            Town dest = null;

            try {
                if (townList != null) {
                    origin = townList.get(Integer.parseInt(startTownTxt.getText()));
                    dest = townList.get(Integer.parseInt(endTownTxt.getText()));
                    if (townList != null && (origin != null && dest != null) && (origin.getName().equals(dest.getName()) == false)) {

                        rteCalc = new RouteCalc(origin, dest);
                        rteCalc.calculateRoutes();

                        if (rteCalc.getRteList(true).size() > 0) {
                            ArrayList<Route> shortestRte = new ArrayList<Route>();
                            shortestRte.add(rteCalc.getShortestRte());

                            //Displays the routes to the user
                            displayRoutes(origin, dest);
                            drawPanel.drawRoute(rteCalc.getShortestRte(), "highlightRoute");
                        } else {
                            rteOutput.setText("No routes found between " + origin.getName() + " and " + dest.getName());
                        }
                    } else if (origin == null) {
                        help = new helpUI(mainFrame, "Invalid Origin", true, "Please select a valid origin");
                        help.setVisible(true);
                    } else if (dest == null) {
                        help = new helpUI(mainFrame, "Invalid Destination", true, "Please select a valid destination");
                        help.setVisible(true);
                    }

                } else if (townList == null) {
                    help = new helpUI(mainFrame, "No town data found", true, "Please select file containing town data");
                    help.setVisible(true);
                }
            } catch (NumberFormatException e) {
                help = new helpUI(mainFrame, "Invalid Input", true, "Only numbers a valid for origin and destination");
                help.setVisible(true);
            }



        }
    }
    /*End of ActionListner Interface implementation*/

    /*End of Interface Implementations*/
}
