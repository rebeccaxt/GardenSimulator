import java.util.Random;
import java.awt.Graphics;
import java.awt.Color;

//parent class to all vegetable type classes
public abstract class Vegetable extends Plant {

	//constructor for creating vegetable objects. Vegetables can be harvested between 2-5 times (randomly determined when object is created)
	public Vegetable() {

		super();
		harvestCounter = (int)(Math.random() * 4) + 2;

	}
//returns the selling value of the vegetable and returns to stage 3
	public int harvest() {

		this.setStage(3);
		isFertilized = false;
		canHarvest = false;
		return(super.harvest());
		
	}

} 




