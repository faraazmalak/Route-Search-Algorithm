/**
 *GUI.java
 *Purpose: Creates GUI
 *@Author: Faraaz Malak
 *@version 1.0 22/05/2010
 */
package tripplanner;

import java.io.*;
import java.util.*;

public class Map {

    private ArrayList<Town> townList = new ArrayList<Town>();

    /**
     *Returns a list of towns
     *
     *@return  List of towns
     */
    public ArrayList<Town> getTownList() {
        return (ArrayList<Town>) townList.clone();
    }

    /**
     *Returns a town object, based on name of the town (a string value), passed in as parameter
     *
     *@param theTown String value representing name of the town
     *@return  Town object returned, based on name of the town passed in as parameter. If no town object is found, NULL is returned
     */
    private Town getTown(String theTown) {


        Iterator it = townList.iterator();
        Town newTown = null;
        while (it.hasNext()) {
            newTown = (Town) it.next();
            if (newTown.getName().equals(theTown) == true) {


                return newTown;

            }
        }

        return null;
    }

    /**
     *Returns a town object, based on index of the town (a int value), passed in as parameter
     *
     *@param index Int value representing index of the town in townList
     *@return  Town object returned, based on index of the town passed in as parameter. If no town object is found, NULL is returend
     */
    public Town getTown(int index) {
        if (index < townList.size() && townList.get(index) != null) {
            return townList.get(index);
        } else {
            return null;
        }

    }

    /**
     *Reads town data such as name, latitude, latitude etc from text file. And creates Town objects and adds thenm to townList
     *
     *@param fileName Path of text file, from which the data has to be read
     */
    public void readTowns(File file) throws FileNotFoundException, Exception {

        
       
        try {

            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            String townName = null;
            int i = 0;

            while ((townName = reader.readLine()) != null) {

                double latDeg = 0;
                double latMin=0;
                double latSec=0;

                double longDeg = 0;
                double longMin=0;
                double longSec=0;

                String longitude=reader.readLine();
                String latitude=reader.readLine();
                
                
                //Reads data fromf ile
                longDeg = Double.parseDouble(longitude.split(" ")[0]);
                longMin = Double.parseDouble(longitude.split(" ")[1]);
                 longSec = Double.parseDouble(longitude.split(" ")[2]);

                 latDeg = Double.parseDouble(latitude.split(" ")[0]);
                 latMin = Double.parseDouble(latitude.split(" ")[1]);
                 latSec = Double.parseDouble(latitude.split(" ")[2]);

                //Creates town object based on data read from file
                townList.add(new Town(townName, latDeg, latMin,latSec,longDeg,longMin,longSec));

            //    System.out.println("Town Name: "+townName);
                i++;
            }
            reader.close();
        } catch (FileNotFoundException ex) {
            throw new FileNotFoundException("IO Error: " + file.getName() + " cannot be found. Please try entering locations.txt");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }




    }

    public void run()
    {
//        for(int i=0;i<townList.size();i++)
//        {
//            Town t=townList.get(i);
//            System.out.println("X: "+t.getPixelCoords().getX()+" Y: "+t.getPixelCoords().getY());
//        }
//           for(int i=0;i<townList.size();i++)
//     {
//
//            Town targetTown=townList.get(i);
//            System.out.println("Main Town: "+targetTown.getName());
//            for(int i2=0;i2<targetTown.getTotalRoads();i2++)
//            {
//                Town rdOrigin=targetTown.getRoads().get(i2).getOrigin();
//                Town rdDest=targetTown.getRoads().get(i2).getDestination();
//
//                System.out.println("Road from :"+ rdOrigin.getName()+" to: "+rdDest.getName());
//            }
//
//     }
    }
    public void updateTown(Town newTown)
    {
        for(int i=0;i<townList.size()-1;i++)
        {

            Town targetTown=townList.get(i);
            if(newTown.getName().equals(targetTown.getName()))
            {
                targetTown=newTown;
            }
        }

          for(int i=0;i<townList.size()-1;i++)
        {

            Town targetTown=townList.get(i);

            for(int i2=0;i2<targetTown.getTotalRoads();i2++)
            {
                Town rdOrigin=targetTown.getRoads().get(i2).getOrigin();
                Town rdDest=targetTown.getRoads().get(i2).getDestination();

                if(rdOrigin.getName().equals(newTown.getName()))
                {
                    rdOrigin=newTown;
                }
                else if(rdDest.getName().equals(newTown.getName()))
                {
                    rdDest=newTown;
                }
            }

        }

    }
    /**
     *Reads road data from text file. And creates Road objects and adds thenm to the Town objects (which are coonected by the road)
     *
     *@param fileName Path of text file, from where the data has to be read
     */
    public void readRoads(String fileName) throws FileNotFoundException, Exception {

        System.out.println("Reading connection data...");
        try {
            File file = new File(fileName);
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            int i = 0;
            while (reader.readLine() != null) {


                Town originTown = getTown(reader.readLine().split(":")[1]);
                Town destTown = getTown(reader.readLine().split(":")[1]);
                String roadType = reader.readLine();

                originTown.addRoad(new Road(originTown, destTown, i));

                if (roadType.equals("2-way")) {
                    destTown.addRoad(new Road(destTown, originTown, i));
                }
                i++;
            }

            reader.close();

        } catch (FileNotFoundException ex) {
            throw new FileNotFoundException("IO Error: " + fileName + " cannot be found. Please try entering roads.txt");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }



    }
}
