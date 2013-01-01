/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tripplanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author Faraaz Malak
 */
public class helpUI  extends JDialog implements MouseListener {
 //Declaration and initialization of UI components

    // Button to close the dialog box
     private JButton closeBtn=new JButton("Close");

     //Text area to display the instructions
     private JTextArea instructTxtArea=new JTextArea();

     //Used for laying out text area component
     private JPanel mainPanel=new JPanel();

     //Holds the close button
     private JPanel btnPanel=new JPanel();

 /**
  * Creates UI of dialog box
  * @parent: parent of dialog box
  * @title: Title of dialog box
  * @modal: Boolean indicating if dialog box is modal or not
  * @helpText: Help text to be displayed in text area
 */
    public helpUI(JFrame parent, String title, boolean modal,String helpText)
    {

        //Calls super class
            super(parent,modal);

        //Centers the dialog on the screen
            Rectangle bounds = getParent().getBounds();
            Rectangle abounds = getBounds();
            setLocation( (bounds.width - abounds.width)/ 2, (bounds.height - abounds.height)/2);


        //Sets properties of dialog box
            setResizable(false);
            setTitle(title);
            setSize(450,277);



        //Sets layout manager of mainPanel
            mainPanel.setLayout(new GridBagLayout());

       //Sets properties of instructTxtArea (Text Area)
            instructTxtArea.setColumns(20);
            instructTxtArea.setRows(10);
            instructTxtArea.setEditable(false);
            instructTxtArea.setLineWrap(true);
            instructTxtArea.setText(helpText);

            getContentPane().add(instructTxtArea,BorderLayout.CENTER);



       //Adds btnPanel to mainFrame (JFrame)
            getContentPane().add(btnPanel,BorderLayout.SOUTH);

       //Adds close button to btnPanel
            btnPanel.add(closeBtn,Component.CENTER_ALIGNMENT);

        //Adds mouse actionlistener for close button
            closeBtn.addMouseListener(this);





    }

/*Interface Implementation of MouseListner*/

    public void mouseExited(MouseEvent evt){}
    public void mouseEntered(MouseEvent evt){}
    public void mousePressed(MouseEvent evt){}
    public void mouseClicked(MouseEvent evt){}

    /**
     * Closes the dialog. Executed when close button is clicked
     */
    public void mouseReleased(MouseEvent evt){
       if(evt.getSource()==closeBtn)
        {

            dispose();

        }

    }


/*End of Interface Implementation*/
}
