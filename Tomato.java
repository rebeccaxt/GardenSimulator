
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

//Tomato class
public class Tomato extends Vegetable {

	//Tomato constructor
	public Tomato() {

		super();
		cashValue = 10;
		price = 6;

	}

	//draw method for the graphics of tomato depending on the stage
	//sources: https://docs.oracle.com/javase/tutorial/2d/images/loadimage.html 
			// https://stackoverflow.com/questions/17865465/how-do-i-draw-an-image-to-a-jpanel-or-jframe

	public void draw(Graphics g, int x, int y){

		BufferedImage seed= null;
		try{
			seed = ImageIO.read(new File("graphics/seed2.png"));
		} catch(IOException e){}

		BufferedImage sprout= null;
		try{
		sprout = ImageIO.read(new File("graphics/sprout.png"));
		} catch(IOException e){}

		BufferedImage mid= null;
		try{
		mid = ImageIO.read(new File("graphics/tomato mid.png"));
		} catch(IOException e){}

		BufferedImage mid2= null;
		try{
		mid2 = ImageIO.read(new File("graphics/tomato mid2.png"));
		} catch(IOException e){}

		BufferedImage unripe= null;
		try{
		unripe = ImageIO.read(new File("graphics/unripe tomato.png"));
		} catch(IOException e){}

		BufferedImage ripe= null;
		try{
		ripe = ImageIO.read(new File("graphics/ripe tomato.png"));
		} catch(IOException e){}


		if (this.getStage()==0) {
			g.drawImage(seed, x*plotSize, y*plotSize, null);
		}
		if (this.getStage()==1) {
			g.drawImage(sprout, x*plotSize, y*plotSize, null);
		}
		if (this.getStage()==2) {
			g.drawImage(mid, x*plotSize, y*plotSize, null);
		}
		if (this.getStage()>2 && this.getStage()<5) {
			g.drawImage(mid2, x*plotSize, y*plotSize, null);				
		}
		if (this.getStage()<7 && this.getStage()>4) {
			g.drawImage(unripe, x*plotSize, y*plotSize, null);
		}
		if (this.getStage()>=7){
			g.drawImage(ripe, x*plotSize, y*plotSize, null);
		}

	}

	//grows tomato
	public void grow() {

			super.grow();
			if (this.getStage() >=7)
				canHarvest= true; 

	}

}




