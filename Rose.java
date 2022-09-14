import java.util.Random;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

//rose class
public class Rose extends Flower {

	public Rose() {

		super();
		cashValue = 7;
		price = 4;

	}
	//draw method for the graphics of rose depending on the stage
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
		flower = ImageIO.read(new File("graphics/rose.png"));
		} catch(IOException e){}


		if (this.getStage()==0) {
			g.drawImage(seed, x*plotSize, y*plotSize, null);
		}
		if (this.getStage()==1) {
			g.drawImage(sprout, x*plotSize, y*plotSize, null);
		}
		if (this.getStage()==2) {
			g.drawImage(smallbud, x*plotSize, y*plotSize, null);
		}
		if (this.getStage()==3) {
			g.drawImage(midbud, x*plotSize, y*plotSize, null);
		}
		if (this.getStage()==4) {
			g.drawImage(bud, x*plotSize, y*plotSize, null);
		}
		if (this.getStage()>=5) {
			g.drawImage(flower, x*plotSize, y*plotSize, null);
		}

	}

	//grows rose
	public void grow() {

		super.grow();
		if(this.getStage() == 5)
			canHarvest = true;

	}

}



