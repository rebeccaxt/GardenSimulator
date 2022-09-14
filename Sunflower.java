
import java.util.Random;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


//sunflower class
public class Sunflower extends Flower {

	//constructor for creating sunflower objects
	public Sunflower() {

		super();
		cashValue = 5;
		price = 2;

	} 
	//draw method for the graphics of sunflower depending on the stage
	//sources: https://docs.oracle.com/javase/tutorial/2d/images/loadimage.html 
			// https://stackoverflow.com/questions/17865465/how-do-i-draw-an-image-to-a-jpanel-or-jframe
			 
	public void draw(Graphics g,int x, int y){

		BufferedImage seed= null; 
		try{
			seed = ImageIO.read(new File("graphics/seed2.png"));
		} catch(IOException e){}

		BufferedImage sprout= null;
		try{
		sprout = ImageIO.read(new File("graphics/sprout.png"));
		} catch(IOException e){}

		BufferedImage smallbud= null;
		try{
		smallbud = ImageIO.read(new File("graphics/small bud.png"));
		} catch(IOException e){}

		BufferedImage midbud= null;
		try{
		midbud = ImageIO.read(new File("graphics/mid bud.png"));
		} catch(IOException e){}

		BufferedImage bud= null;
		try{
		bud = ImageIO.read(new File("graphics/flowerbud.png"));
		} catch(IOException e){}

		BufferedImage flower= null;
		try{
		flower = ImageIO.read(new File("graphics/sunflower.png"));
		} catch(IOException e){}


		if (this.getStage()==0) {
			g.drawImage(seed, x*200, y*200, null);
		}
		if (this.getStage()==1) {
			g.drawImage(sprout, x*200, y*200, null);
		}
		if (this.getStage()==2) {
			g.drawImage(smallbud, x*200, y*200, null);
		}
		if (this.getStage()==3) {
			g.drawImage(midbud, x*200, y*200, null);
		}
		if (this.getStage()==4) {
			g.drawImage(bud, x*200, y*200, null);
		}
		if (this.getStage()>=5) {
			g.drawImage(flower, x*200, y*200, null);
		}

	}
	//grows sunflower
	public void grow() {

		super.grow();
		if(this.getStage() == 5)
			canHarvest = true;

	}

}