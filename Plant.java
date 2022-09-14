import java.util.Random;
import java.awt.Graphics;

//parent class to vegetable and flower
public abstract class Plant {

	private int stage;
	public int waterLevel; 
	private int yield;
	public boolean canHarvest;
	public boolean isFertilized;
	public int cashValue;
	public int harvestCounter;
	public int price;
	public int plotSize;

//constructor sets fields to initial values
	public Plant() {

		stage = 0; 
		waterLevel = 0;
		yield = 1;
		canHarvest = false;
		isFertilized = false;
		plotSize = 200;

	}
//increase waterLevel by 1
	public void water() {

		waterLevel++;

	}
	//Half the time, fertilize doubles the yield
	public void fertilize() {

		isFertilized = true;
		yield = (int)(Math.random()*2) + 1;

	}

// Grows the plant
	public void grow() {

		stage++;

	}
//returns stage
	public int getStage() {

		return stage;

	}
//sets the stage
	public void setStage(int x){
		stage = x;
	}

//decrements harvestCounter and returns the selling value
	public int harvest() {

		harvestCounter--;
		canHarvest = false;
		return cashValue * yield;

	}
	// returns cashValue
	public int getCashValue() {
		return cashValue;
	}
	//returns price
	public int getPrice(){
		return price;
	}
	//returns waterLevel
	public int getWaterLevel(){
		return waterLevel;
	}

	//sets waterLevel
	public void setWaterLevel(int x){
		waterLevel= x;
	}

	//returns true if the plant cannot be harvested again
	public boolean lastHarvest() {

		if(harvestCounter < 1)
			return true;

		else
			return false;

	}

	//sets the harvestCounter
	public void setHarvestCounter(int x){
		harvestCounter=x;
	}

	//draw method
	public void draw(Graphics g, int x, int y){}

}




