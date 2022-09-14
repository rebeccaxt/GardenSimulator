import java.awt.*;  
import java.awt.event.*;  
import javax.swing.*;

//keylistner to traverse the plots with wasd keys
public class GardenKey extends JPanel implements KeyListener {

		private int x = Main.getXPos(); 
		private int y = Main.getYPos();

		//constructor
		GardenKey() {

			this.setFocusable(true);
      		this.requestFocus();
			addKeyListener(this);
			

		}

		//sets wasd keys to traverse the grid of plots
		@Override
		public void keyTyped(KeyEvent e) {

			 x= Main.getXPos();
			 y=Main.getYPos();

			char c = e.getKeyChar();
		
			if(c == 'w') {
				if(y > 0)
					y--;
			}

				
			if(c == 's') {
				if(y < 2)
					y++;
			}
				
			if(c == 'a') {
				if(x > 0)
					x--;
			}
				
			if(c == 'd') {
				if(x < 2)
					x++;
			}

			Main.setCoordinates(x, y);

		}

		public void keyReleased(KeyEvent e) {}

		public void keyPressed(KeyEvent e) {}


}