import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
	
public class Rain {
    //used https://stackoverflow.com/questions/38966000/animation-sequence-in-jframe for setup of filling an array with images

	BufferedImage rain[] = new BufferedImage[11];
	public String rainString[];
	int plotSize = 200;
	static int i = 0;


	public Rain() throws IOException {
        //takes string of image file names, uses them to fill rain[] array with buffered images
		rainString = new String[] { "rain0.png", "rain1.png", "rain2.png", "rain3.png", "rain4.png", "rain5.png", "rain6.png",
      	"rain7.png", "rain8.png", "rain9.png", "rain10.png"};

      	for (int i=0; i<rain.length; i++) {

 			rain[i] = ImageIO.read(new FileInputStream("graphics/" + rainString[i]));

     	}


     }

     //sets i to last image (which is blank) to avoid animation loop being off track if animation is interrupted
     public void reset() {
     	i = 10;
     }
     
     //Draws rain animation on given plot coordinates, increments which image in the array is used each time draw is called, then loops back to the beginning
     public void draw(Graphics g, int x, int y) {
     	g.drawImage(rain[i], x*plotSize, y*plotSize, null);
     	if(i<11) {
     		i++;
     	}
     	if(i==11) {

     		i = 0;
     	}
     }

     
	
}