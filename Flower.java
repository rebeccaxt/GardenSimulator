
import java.awt.Graphics;
import java.awt.Color;

//Flower class that is parent to all flower subclasses 
public abstract class Flower extends Plant {
	
	private int harvestCounter;
	//contstructor for creating flowers
	public Flower() {

		super();
		harvestCounter = 1;
		
	}

} 

