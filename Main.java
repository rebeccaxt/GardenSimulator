import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.io.File;
import java.lang.System;

//Main class for handling the game play and setup.
public class Main extends JPanel implements ActionListener, MouseListener, KeyListener {

	private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private static final int plotSize = WIDTH/3;

    private static Plant[][] garden;
	private static int day = 0;
	private static int moneyAmount = 5;

    private int numPlanted;

    private static int xpos;
    private static int ypos;
   
    private static int xWater;
    private static int yWater;

    Rain watering;
    RainAll wateringAll;
    private long rainStart = 0; // when raining, this is the timestamp when the rain animation was started
    private long rainStormStart = 0;


    //timer syntax came from this stack overflow answer: https://stackoverflow.com/questions/22072796/how-to-repaint-a-jpanel-every-x-seconds
	Timer timer = new Timer (50, this);
	Timer timer2 = new Timer(50, this);

	private static JPanel topbar = new JPanel();

	private JButton tomorrow;
	private JTextField moneyDisplay;
	private JTextField dayDisplay;
	private JButton water;
	private JButton waterAll;
	private JButton harvest;
	private JButton fertilize;
	private JButton information;
	private JRadioButtonMenuItem sunflowerButton;
	private JRadioButtonMenuItem tulipButton;
	private JRadioButtonMenuItem roseButton;
	private JRadioButtonMenuItem tomatoButton;
	private JRadioButtonMenuItem pepperButton;
	private JRadioButtonMenuItem pumpkinButton;
	private JTextField restore;
	private JTextField saveAs;
	private JLabel restoreLabel;
	private JLabel saveLabel;

	private Image image; 
	private Image unlucky;
	private Image cant;
	private Image check;
	private static Icon icon;
	private static Icon unluckyicon;
	private static Icon canticon;
	private static Icon checkicon;


	//constructor that sets up the initial configurations of the game
	public Main() {

		this.setPreferredSize(new Dimension(WIDTH,HEIGHT));

		garden = new Plant[3][3];

		try{
			watering  = new Rain();
		}
		catch(IOException e) {}

		try{
			wateringAll  = new RainAll();
		}
		catch(IOException e) {}

	//creates topbar to store buttons
		topbar.setLayout(new FlowLayout());
		topbar.setPreferredSize(new Dimension(600,70));
		topbar.setBackground(new Color(0, 153, 0));

	//creates menubar to fill with radiobutton plant options
		JMenuBar bar = new JMenuBar();
		JMenu menu = new JMenu("Plant");
		ButtonGroup G1 = new ButtonGroup();

	//imports images to use in JOptionPane popups, turns them into icons
		try {
			image = ImageIO.read(new File("graphics/sunflower icon.png"));
		}
		catch(IOException e){}

		try {
			unlucky = ImageIO.read(new File("graphics/unlucky icon.png"));
		}
		catch(IOException e){}

		try {
			cant = ImageIO.read(new File("graphics/cant.png"));
		}
		catch(IOException e){}

		try {
			check = ImageIO.read(new File("graphics/check.png"));
		}
		catch(IOException e){}

		checkicon = new ImageIcon(check);
		canticon = new ImageIcon(cant);
		unluckyicon = new ImageIcon(unlucky);
		icon = new ImageIcon(image);

	
	//creates various textfields and buttons, adds them to top bar, adds radio buttons to menu bar
		moneyDisplay = new JTextField("Money: $" + moneyAmount);
		topbar.add(moneyDisplay);
		moneyDisplay.setPreferredSize(new Dimension(100, 20));
		moneyDisplay.setEditable(false);

		dayDisplay = new JTextField("Day: "+ day);
		topbar.add(dayDisplay);
		dayDisplay.setPreferredSize(new Dimension(80,20));
		dayDisplay.setEditable(false);

		tomorrow = new JButton("Next Day");
		tomorrow.addActionListener(this);
		topbar.add(tomorrow);

		sunflowerButton = new JRadioButtonMenuItem("Sunflower ($2)", true);
		sunflowerButton.addActionListener(this);
		G1.add(sunflowerButton);
		menu.add(sunflowerButton);

		tulipButton = new JRadioButtonMenuItem("Tulip ($3)", false);
		tulipButton.addActionListener(this);
		G1.add(tulipButton);
		menu.add(tulipButton);

		roseButton = new JRadioButtonMenuItem("Rose ($4)", false);
		roseButton.addActionListener(this);
		G1.add(roseButton);
		menu.add(roseButton);

		tomatoButton = new JRadioButtonMenuItem("Tomato ($6)", false);
		tomatoButton.addActionListener(this);
		G1.add(tomatoButton);
		menu.add(tomatoButton);

		pepperButton = new JRadioButtonMenuItem("Pepper ($8)", false);
		pepperButton.addActionListener(this);
		G1.add(pepperButton);
		menu.add(pepperButton);

		pumpkinButton = new JRadioButtonMenuItem("Pumpkin ($10)", false);
		pumpkinButton.addActionListener(this);
		G1.add(pumpkinButton);
		menu.add(pumpkinButton);

		restoreLabel = new JLabel("Restore a file:");
		topbar.add(restoreLabel);

		restore = new JTextField("");
		restore.addActionListener(this);
		restore.setPreferredSize(new Dimension(65, 20));
		restore.setEditable(true);
		topbar.add(restore);
	 	
	 	saveLabel = new JLabel("Save As:");
	 	topbar.add(saveLabel);
	 	saveAs = new JTextField("");
	 	saveAs.addActionListener(this);
	 	saveAs.setPreferredSize(new Dimension(65, 20));
		saveAs.setEditable(true);
		topbar.add(saveAs);

		information = new JButton("Info");
		information.addActionListener(this);
		topbar.add(information);

		bar.add(menu);
		topbar.add(bar);
	
		water = new JButton("Water");
		water.addActionListener(this);
		topbar.add(water);

		waterAll = new JButton("Water All ($1)");
		waterAll.addActionListener(this);
		topbar.add(waterAll);

		harvest = new JButton("Harvest!");
		harvest.addActionListener(this);
		topbar.add(harvest);

		fertilize = new JButton("Fertilize ($2)");
		fertilize.addActionListener(this);
		topbar.add(fertilize);

		addMouseListener(this);
		addMouseListener(new GardenClick());

		addKeyListener(this);
		addKeyListener(new GardenKey());

	//setup for keyListener to work alongside top bar and textfields
		this.setFocusable(true);
      	this.requestFocus();




	} 

	//sets the coordinates to correspond to selected plot
		//called from either GardenKey or GardenClick when a KeyEvent or MouseEvent occurs
	public static void setCoordinates (int x, int y) {

		xpos = x;
		ypos = y;

	}

	//returns x coordinate of selected plot
	public static int getXPos(){
		return xpos;
	}

	//returns y coordinate of selected plot
	public static int getYPos(){
		return ypos;
	}

	//repaints the JFrame when the mouse is clicked
	@Override
	public void mouseClicked(MouseEvent e) {

		this.repaint();
		this.requestFocus();

	}

	public void mouseEntered(MouseEvent e) {}  
    public void mouseExited(MouseEvent e) {}  
    public void mousePressed(MouseEvent e) {}  
    public void mouseReleased(MouseEvent e) {}  

    //repaints the JFrame when a key is typed

    public void keyTyped(KeyEvent e) {

    	this.requestFocus();
		this.repaint();
		
	}

	public void keyReleased(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {}

	//draws the graphics displayed during the game
	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);    	

		//Draws the garden background and grid
		g.setColor(new Color(130,70,0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(new Color(51,0,0));
		g.drawLine(WIDTH/3, 0, WIDTH/3, HEIGHT);
		g.drawLine(2*(WIDTH/3), 0, 2*(WIDTH/3), HEIGHT);
		g.drawLine(0, HEIGHT/3, WIDTH, HEIGHT/3);
		g.drawLine(0, 2*(HEIGHT/3), WIDTH, 2*(HEIGHT/3));
		g.drawLine(0, 0, WIDTH, 0);
		g.drawLine(WIDTH, 0, WIDTH, HEIGHT);


		//Draws each plant at its current stage
		for (int i =0; i<3; i++) {

			for (int j=0; j<3; j++) {

				if (garden[i][j]!=null)
					garden[i][j].draw(g,i,j);

			}

		}

		dayDisplay.setText("Day: " + day+ "  ");
		moneyDisplay.setText("Money: $" + moneyAmount + "   "); 

		//Draws highlighted square around selected plot coordinate
		g.setColor(Color.GREEN);
		g.drawRect(xpos*plotSize, ypos*plotSize, plotSize, plotSize);
		g.setColor(new Color(102, 51, 0));

		//Rain animation for Water Button
		if(rainStart > 0) {

			try {
				watering.draw(g, xWater, yWater);
					}
			catch(Exception e) {}
			
		}

		//Rain animation for Water All Button
		if(rainStormStart>0){
			try{
				wateringAll.draw(g);
			}
			catch(Exception e){}
		}


    }

    // creates new plant object and places it in the array at the specified location, making sure the user has enough money to do so
	private void buyAndPlant(int x, int y, String p) {

		Plant plant;

		if (p == "sunflower") {
			plant = new Sunflower();
		} 

		else if(p == "rose") {
			plant = new Rose();
		} 

		else if(p == "tulip") {
			plant = new Tulip();
		} 

		else if(p == "tomato") {
			plant = new Tomato();
		} 

		else if(p == "pumpkin") {
			plant = new Pumpkin();
		} 

		else if(p == "pepper") {
			plant = new Pepper();
		} 

		else {
			System.err.print("ERROR: Not a real plant type.");
			return;
		}
    
    	if (changeMoney(-plant.getPrice()))
    	{
			garden[x][y] = plant;
			numPlanted++;
    	}
	}

//adds the value of the plant to player's money and, if the plant has been harvested its maximum number of times, removes plant from the garden
	private void HarvestAndSell(int x, int y) {

		changeMoney(garden[x][y].harvest());

		if (garden[x][y].lastHarvest()) {
			garden[x][y] = null;
		}

	}

	// nextDay() advances the game to the next day, growing all plants and removing under/over watered plants, decreases water level for each plant.
	//Once it reaches day 30 it ends the game.
	//randomly 1/37.5 of the time a healthy plant dies for an unexpected reason
	//if a plant is removed, a popup panel appears explaining why 
	private void nextDay() {
		boolean noWater = false;
		boolean tooMuchWater = false;
		int counter =0;
		

		if (day<30) {

			day++;

			for (int i =0; i<3; i++) {

				for (int j=0; j<3; j++){

					if (garden[i][j] != null) {

					
						if (garden[i][j].getWaterLevel() == 0) {

							garden[i][j]= null;
							noWater = true;

						}

						else if(garden[i][j].getWaterLevel() >= 5) {

							garden[i][j]= null;
							tooMuchWater = true;

						}
						
						//randomly removes plants 4/150 times, displays reason
						//source for syntax of JOptionPane with icon:
							//https://docs.oracle.com/javase/6/docs/api/javax/swing/JOptionPane.html#showMessageDialog(java.awt.Component,%20java.lang.Object,%20java.lang.String,%20int,%20javax.swing.Icon)

						else {

							int rand = (int)(Math.random()*150);
							if (rand==0 )
								JOptionPane.showMessageDialog(this, "Unlucky! A bunny ate your plant last night.", "unlucky", JOptionPane.INFORMATION_MESSAGE, unluckyicon);
							if (rand==1 )
								JOptionPane.showMessageDialog(this, "Unlucky! A very localized hailstorm killed your plant.", "unlucky", JOptionPane.INFORMATION_MESSAGE, unluckyicon);
							if (rand==2 )
								JOptionPane.showMessageDialog(this, "Unlucky! Your neighbor's dog dug up your plant. Woof.", "unlucky", JOptionPane.INFORMATION_MESSAGE, unluckyicon);
							if (rand==3 )
								JOptionPane.showMessageDialog(this, "Unlucky! A tractor just squashed your plant.", "unlucky", JOptionPane.INFORMATION_MESSAGE, unluckyicon);
		
							if(rand<4 )
								garden[i][j]=null;

							if(rand>3){
								garden[i][j].grow();
								garden[i][j].waterLevel--;			
							}
						}
					}	
				}
			}


			if(noWater)
				JOptionPane.showMessageDialog(this, "Oh no! You forgot to water your plant(s)!", "no water", JOptionPane.INFORMATION_MESSAGE, unluckyicon);

			if(tooMuchWater)
				JOptionPane.showMessageDialog(this, "Oh no! You watered your plant(s) too much!", "too much water", JOptionPane.INFORMATION_MESSAGE, unluckyicon);
		}

		else endGame(); 

	}

//helper method for changing moneyAmount, will change money by amount num if the player can afford it and return true, or return false and give message if they cannot
	private boolean changeMoney(int num) {

		if(moneyAmount + num < 0) {
			JOptionPane.showMessageDialog(this, "Not enough money", "money warning", JOptionPane.WARNING_MESSAGE, canticon);
			return false;
		}

		else {
			moneyAmount+=num;
			return true;
		}
		
	}
//if plant is not fertilized, changes isFertilized to true, calls fertilize method of plant. otherwise, gives message that plant is already fertilized
	private void fertilize(int x, int y) {

		if (garden[x][y].isFertilized==false) {

			if(changeMoney(-2));
				garden[x][y].fertilize();

		}

		else
			JOptionPane.showMessageDialog(this, "You've already fertilized this plant!", "already fertilized", JOptionPane.WARNING_MESSAGE, canticon);

	}

// endGame() alerts user that they can no longer continue to play and invites them to start a new game, resets texts fields and variables, repaints frame to initial state
	private void endGame() {
		JOptionPane.showMessageDialog(this, "Game Over. You planted " + numPlanted + " plants and ended with $" + moneyAmount +". \n Click OK to start a new game!",
			"game over", JOptionPane.INFORMATION_MESSAGE, icon);
		moneyAmount = 5;
		day = 0;
		for (int i=0; i<3;i++) {
			for (int j=0;j<3;j++) {
				garden[i][j]=null;
			}
		}
		saveAs.setText("");
		restore.setText("");
		numPlanted=0;
		repaint();
		
	}
	
	// handles the actions from all buttons. If all plants are null and there is not enough money to buy anything, calls endgGame().
	@Override
	public void actionPerformed(ActionEvent e) {

		//prevents planting a plant where there is already a plant
    	if(e.getSource() == sunflowerButton || e.getSource() == tulipButton || e.getSource() == roseButton || 
      		e.getSource() == tomatoButton || e.getSource() == pumpkinButton || e.getSource() == pepperButton) {

      		if(garden[xpos][ypos] != null)
      			JOptionPane.showMessageDialog(this, "There is already a plant in this plot!", "already planted", JOptionPane.WARNING_MESSAGE, canticon);

      	}
     	//creates a new plant a given plot
     	if(garden[xpos][ypos] == null) {

    		if (e.getSource()==sunflowerButton)
    			this.buyAndPlant(xpos, ypos, "sunflower");
      		if (e.getSource()==roseButton)
      			this.buyAndPlant(xpos,ypos, "rose");
      		if (e.getSource()==tulipButton)
      			this.buyAndPlant(xpos,ypos, "tulip");
      		if (e.getSource()==tomatoButton)
      			this.buyAndPlant(xpos,ypos, "tomato");
     	 	if (e.getSource()==pumpkinButton)
      			this.buyAndPlant(xpos,ypos, "pumpkin");
    	  	if (e.getSource()==pepperButton)
      			this.buyAndPlant(xpos,ypos, "pepper"); 
      			
      	}

      	//advances the day by calling nextDay()
    	if (e.getSource() == tomorrow) 
     		this.nextDay();	

     	//checks if plot can be watered, waters it, starts timer to prompt animation
    	if (e.getSource() == water) {

    		if (garden[xpos][ypos] == null)
				JOptionPane.showMessageDialog(this, "You cannot water an empty plot", "water empty plot", JOptionPane.WARNING_MESSAGE, canticon);
			
    		else {
     			garden[xpos][ypos].water();
     			watering.reset();
    			rainStart = System.currentTimeMillis();
    			xWater=xpos;
    			yWater=ypos;
    			timer.start();
     		}

		}
		//waters all plots with plants as long as the entire garden is not empty and player can afford 
		if (e.getSource()== waterAll) {

			//Checks if garden is empty
			boolean gardenEmpty = true;
			for(int i=0; i<3; i++) {
				for(int j=0; j<3; j++) {
					if(garden[i][j] != null) {
     					gardenEmpty = false;
					}
				}
				
			}

			//If garden is not empty and the user has enough money then it will water all the plants
			if (!gardenEmpty){
				if (changeMoney(-1)){
				for(int i=0; i<3; i++) {
					for(int j=0; j<3; j++) {
						if(garden[i][j] != null) {
     						garden[i][j].water();
						}
					}
				}
				wateringAll.reset();
				rainStormStart = System.currentTimeMillis();
				timer2.start();
				}
				
			}
			else {
				JOptionPane.showMessageDialog(this, "You cannot water empty plots", "water empty plots", JOptionPane.WARNING_MESSAGE, canticon); 
			}

		}
		
		//calls fertilize method if plot is not empty
		if (e.getSource()== fertilize) {

			if (garden[xpos][ypos] == null)
				JOptionPane.showMessageDialog(this, "You cannot fertilize an empty plot", "fertilize empty plot", JOptionPane.WARNING_MESSAGE, canticon);
    		else
     			this.fertilize(xpos,ypos);
			
		}

		//opens popup with information on how to play when info button is selected
		if (e.getSource() == information) {
			JOptionPane.showMessageDialog(this, "You may navigate the grid with either WASD or with mouse clicks."
				+"\nPlant button: plants seeds of the selected flower or vegetable in the\nhighlighted square." 
				+ "\nFertilize button: after planting a seed you may fertilize the plant,\nincreasing the yield possibility (you might make more money when\nharvesting). A plant can be fertilized once each harvest cycle."
				+ "\nWater button: waters the plant you currently have selected. The water\nlevel must remain between 1 and 4 to keep your plant alive, and the level \ndecreases by one each day, so be sure to water it regularly but be\ncareful not to overwater it!" 
				+ "\nYou may water the plant multiple times in one day, and the water level\ncarries over to the next day, just be sure to pay attention to\nhow much you've watered it."
				+ "\nHarvest button: harvests and sells the plant you currently have selected, \nearning you money. You may harvest vegetables multiple times."
				+ "\n\nRestoring a File and Saving a File: you may save or open a file by typing\nin the name of the file in the text boxes (omitting the file type) and\nhitting enter." 
				+ "\nEnd Game: The game ends if you run out of money/have no way to make\nany money through plants, or if you reach day 30.\nTry to make as much money as possible."
				+"\nHappy planting!", "information", JOptionPane.INFORMATION_MESSAGE, icon);
		}


		//calls harvest and sell method if plot is not empty and plant is ready
		if (e.getSource() == harvest) {
			
			if ((garden[xpos][ypos] != null)&&garden[xpos][ypos].canHarvest)
				HarvestAndSell(xpos,ypos);
			else if(garden[xpos][ypos] == null)
				JOptionPane.showMessageDialog(this, "You cannot harvest an empty plot", "harvest empty plot", JOptionPane.WARNING_MESSAGE, canticon);
			else if(!(garden[xpos][ypos].canHarvest))
				JOptionPane.showMessageDialog(this, "This plant is not ready to harvest!", "plant not ready", JOptionPane.WARNING_MESSAGE, canticon);

		}

		//checks if all the plants are dead and user has no money after every action, if so they cannot do anything so prompts endgame method
		boolean alldead = true;
		
		for (int i =0; i<3; i++) {
			for (int j=0; j<3; j++){
				if (garden[i][j]!=null)
					alldead=false;
			}
		}

		if(moneyAmount < 2 && alldead)
			endGame();

		//while timer is running (to make water animation), updates amount of time it has been running, and stops it if it has reached 600 milliseconds	
	    if(e.getSource()==timer){
	    	long timeRaining = System.currentTimeMillis() - rainStart;
	    	if (timeRaining > 600) {
	    		timer.stop();
	    		rainStart = 0;
	    	}
	  			  }

	  	//while timer2 is running (to make waterAll animation), updates amount of time it has been running, and stops it if it has reached 600 milliseconds	
	  	if (e.getSource()==timer2){
	  		long timeRainStorm = System.currentTimeMillis()-rainStormStart;
	  		if (timeRainStorm > 600) {
	    		timer2.stop();
	    		rainStormStart = 0;
	    	}
	    }

	    //takes user input from the textfield and calls restore for that file name
	    String addInput;
      	try { 
			if (e.getSource()==restore) {
				addInput = restore.getText();
			readInput(addInput);
			}
		}
		catch (IOException o) { 
			o.getMessage();
		}

		//takes user input from the textfield and calls save to create and save a file with that name
		String saveName;
		try {
			if (e.getSource()==saveAs) {
				saveName = saveAs.getText();
				save(saveName);
			}
		}

		catch (IOException o) { 
			o.printStackTrace(); 
		}
	  	
    	repaint();
    	this.requestFocus();


	}
		//reads input from a text file and sets up game from saved file
	private void readInput(String input) throws IOException {

		ArrayList<String> setUp = new ArrayList<String>();
		//read from input file line by line into arrayList<String> setUP
		try { 
			setUp = FilesUtil.readTextFileByLines(input);
		}
		catch (IOException e) { 
			e.printStackTrace(); 
			JOptionPane.showMessageDialog(this, "File not found", "unknown file", JOptionPane.ERROR_MESSAGE, canticon);
		}
//extract information from file via array
		int money = Integer.parseInt(setUp.get(0));
		moneyAmount= money; 
		int theday = Integer.parseInt(setUp.get(1));
		day = theday;
		Plant toAdd;
		int theType;
		int theStage;
		int theWaterLevel;
		int theHarvest;
		int theX;
		int theY;
		int fertilizeState;
		int harvestable;

		for(int i = 2; i<69; i+=8){
			theType =Integer.parseInt(setUp.get(i+2));
			theStage = Integer.parseInt(setUp.get(i+3));
			theWaterLevel = Integer.parseInt(setUp.get(i+4));
			theHarvest = Integer.parseInt(setUp.get(i+5));
			theX= Integer.parseInt(setUp.get(i));
			theY = Integer.parseInt(setUp.get(i+1));
			fertilizeState = Integer.parseInt(setUp.get(i+6));
			harvestable = Integer.parseInt(setUp.get(i+7));

		//sets up game to configuration specified by file
				toAdd=null;	
			if (theType==0){
				toAdd= new Pepper(); }
			if (theType==1){
				toAdd=new Pumpkin(); }
			if (theType==2){
				toAdd= new Rose(); }
			if (theType==3){
				toAdd= new Sunflower(); }
			if (theType==4){
				toAdd= new Tomato();}
			if (theType==5){
				toAdd= new Tulip();}
			if (theType!=-1){
				toAdd.setStage(theStage);
				toAdd.setWaterLevel(theWaterLevel);
				toAdd.setHarvestCounter(theHarvest);
			if (fertilizeState==1)
				toAdd.fertilize();
			if (harvestable ==1)
				toAdd.canHarvest=true;
		}

			garden[theX][theY]=toAdd;

			} 

		restore.setText("");
		saveAs.setText("");
	}
		// called from saveAs textfield, creates text file that saves the present configuration.
	private void save(String name) throws IOException{
		//creates string with necessary information for saving game configuration line by line.
		String toOutput="" + moneyAmount + '\n' + day;
		Plant p = null;
		for (int i=0; i<3;i++) {
			for (int j=0; j<3;j++){

				toOutput = toOutput.concat(""+ '\n'+i + '\n'+ j);
				p=garden[i][j];

				if (p == null){
					toOutput = toOutput.concat('\n'+ "-1" + '\n'+ "0"+ '\n'+ "0"+ '\n'+ "0"+ '\n'+ "0"+ '\n'+ "0");
				}

				else {

					if(p instanceof Pepper)
						toOutput= toOutput.concat('\n'+ "0");
					if(p instanceof Pumpkin)
						toOutput= toOutput.concat('\n'+ "1");
					if(p instanceof Rose)
						toOutput= toOutput.concat('\n'+ "2");
					if(p instanceof Sunflower)
						toOutput= toOutput.concat('\n'+ "3");
					if(p instanceof Tomato)
						toOutput= toOutput.concat('\n'+ "4");
					if(p instanceof Tulip)
						toOutput= toOutput.concat('\n'+ "5");

					toOutput= toOutput.concat(""+'\n'+p.getStage());
					toOutput= toOutput.concat(""+ '\n'+ p.getWaterLevel());
					toOutput= toOutput.concat(""+ '\n'+ p.harvestCounter);


					if(p.isFertilized)
						toOutput= toOutput.concat(""+ '\n'+ "1");
					else 
						toOutput= toOutput.concat(""+ '\n'+ "0");
					if(p.canHarvest)
						toOutput= toOutput.concat(""+ '\n'+ "1");
					else 
						toOutput= toOutput.concat(""+ '\n'+ "0");
				}

			}

		}
		//writes string to text file
		FilesUtil.writeToTextFile(name, toOutput);
		JOptionPane.showMessageDialog(this, "File saved as "+ name , "file saved", JOptionPane.INFORMATION_MESSAGE, checkicon);

	}


//main method that sets up main JFrame for the game
	public static void main(String[] args) throws IOException {
		
		JFrame frame = new JFrame("Garden");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Main mainInstance = new Main();
	
		frame.add(topbar, BorderLayout.NORTH);
		frame.add(mainInstance, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		mainInstance.requestFocus();
		JOptionPane.showMessageDialog(mainInstance, "Welcome to the garden! \nStart playing a new game, or you can enter the file name of a \nsaved game in “restore a file” to start where you left off. \nClick the info button for help playing.", "Welcome!", JOptionPane.INFORMATION_MESSAGE, icon);


	}

}





