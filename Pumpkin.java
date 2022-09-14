
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

//Pumpkin class
public class Pumpkin extends Vegetable {

	//constructor for creating pumpkins
	public Pumpkin() {

		super();
		cashValue = 14;
		price = 10;

	}

	//draw method for the graphics of pumpkin depending on the stage
	//sources: https://docs.oracle.com/javase/tutorial/2d/images/loadimage.html 
			// https://stackoverflow.com/questions/17865465/how-do-i-draw-an-image-to-a-jpanel-or-jframe

	public void draw(Graphics g,int x, int y){

		BufferedImage seed= null;
		try{
			seed = ImageIO.read(new File("graphics/seed2.png"));
		} catch(IOException e){}

		BufferedImage sprout= null;
		try{
		sprout = ImageIO.read(new File("graphics/pumpkin sprout.png"));
		} catch(IOException e){}

		BufferedImage mid= null;
		try{
		mid = ImageIO.read(new File("graphics/pumpkin mid1.png"));
		} catch(IOException e){}

		BufferedImage mid2= null;
		try{
		mid2 = ImageIO.read(new File("graphics/pumpkin mid2.png"));
		} catch(IOException e){}

		BufferedImage unripe= null;
		try{
		unripe = ImageIO.read(new File("graphics/unripe pumpkin.png"));
		} catch(IOException e){}

		BufferedImage ripe= null;
		try{
		ripe = ImageIO.read(new File("graphics/ripe pumpkin2.png"));
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
		if (this.getStage()<6 && this.getStage()>2) {
			g.drawImage(mid2, x*plotSize, y*plotSize, null);				
		}
		if (this.getStage()<8 && this.getStage()>5) {
			g.drawImage(unripe, x*plotSize, y*plotSize, null);
		}
		if (this.getStage()>=8){
			g.drawImage(ripe, x*plotSize, y*plotSize, null);
		}
	}
	//grows pumpkin
	public void grow() {

		super.grow();
		if (this.getStage() >=8)
			canHarvest= true; 

	}
}





