import java.awt.*;  
import java.awt.event.*;  
import javax.swing.*;

//mouselistener to select different plots of the garden
public class GardenClick extends JPanel implements MouseListener {

		private int x;
		private int y;

		//constructor
		GardenClick() {

			addMouseListener(this);

		}

		//based on location of mouse, sets the x and y of the corresponding plot
		@Override
		public void mouseClicked(MouseEvent e) {

			if(e.getX() >= 0 && e.getX() < 200) {

				if(e.getY() >= 0 && e.getY() <= 200)
					{x = 0; y = 0;}
				if(e.getY() >= 200 && e.getY() < 400)
					{x = 0; y = 1;}
				if(e.getY() >= 400 && e.getY() < 600)
					{x = 0; y = 2;}

			}

			if(e.getX() >= 200 && e.getX() < 400) {

				if(e.getY() >= 0 && e.getY() <= 200)
					{x = 1; y = 0;}
				if(e.getY() >= 200 && e.getY() < 400)
					{x = 1; y = 1;}
				if(e.getY() >= 400 && e.getY() < 600)
					{x = 1; y = 2;}

			}

			if(e.getX() >= 400 && e.getX() < 600) {

				if(e.getY() >= 0 && e.getY() <= 200)
					{x = 2; y = 0;}
				if(e.getY() >= 200 && e.getY() < 400)
					{x = 2; y = 1;}
				if(e.getY() >= 400 && e.getY() < 600)
					{x = 2; y = 2;}

			}

			Main.setCoordinates(x, y);
		}

		public void mouseEntered(MouseEvent e) {}  
    	public void mouseExited(MouseEvent e) {}  
    	public void mousePressed(MouseEvent e) {}  
    	public void mouseReleased(MouseEvent e) {}  

}

