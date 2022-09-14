import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.io.*;

import java.util.*;

public class RainAll{

	private BufferedImage rainAll[] = new BufferedImage[11];
    private String rainStringAll[];
    private static int j= 0;

    public RainAll() throws IOException{

    	//Sets up rain animation for the Water All button (structure is same as Rain.java but not specific to a plot, see Rain.java for more detail)
        
        rainStringAll = new String[] { "rainAll0.png", "rainAll1.png", "rainAll2.png", "rainAll3.png", "rainAll4.png", "rainAll5.png", "rainAll6.png",
        "rainAll7.png", "rainAll8.png", "rainAll9.png", "rainAll10.png"};

        for (int j=0; j<rainAll.length; j++) {

            rainAll[j] = ImageIO.read(new FileInputStream("graphics/" + rainStringAll[j]));

        }

    }

    public void reset(){
    	j = 10;
    }

    //Draws rain animation over whole garden
     
     public void draw(Graphics g) {
        g.drawImage(rainAll[j], 0, 0, null);
        if(j<11) {
            j++;
        }
        if(j==11) {

            j = 0;
        }
     }


}