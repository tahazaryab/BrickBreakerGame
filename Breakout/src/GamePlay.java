import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;



public class GamePlay extends JPanel implements KeyListener, ActionListener{
	
	private boolean play = false;
	private int score = 0;
	
	private int totalbricks = 21;
	
	private Timer timer;
	private int delay = 8;
	
	private int playerX = 310;
	
	private int ballposX = 120;
	private int ballposY = 350;
	private int ballXDir = -1;
	private int ballYDir = -2;
	
	private Bricks map;
	
	public GamePlay() {
		
		map = new Bricks(3, 7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer (delay, this);
		timer.start();
	}
	
	public void paint(Graphics g) {
		//background
		g.setColor(Color.BLACK);
		g.fillRect(1,1,692,592);
		
		//drawing the map
		map.draw((Graphics2D)g);
		
		//borders
		g.setColor(Color.CYAN);
		g.fillRect(0,0,3,592);
		g.fillRect(0,0,692,3);
		g.fillRect(691,0,3,592);
		
		//scores
		g.setColor(Color.WHITE);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString(""+score, 590, 30);
		
		//paddle
		g.setColor(Color.ORANGE);
		g.fillRect(playerX, 550, 100, 8);
		
		//ball
		g.setColor(Color.CYAN);
		g.fillOval(ballposX, ballposY, 20, 20);
		
		if(totalbricks <=0) {
			play = false;
			ballXDir = 0;
			ballYDir = 0;
			g.setColor(Color.RED);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("You Won!", 190, 300);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter to Restart", 230, 340);
		}
		
		if(ballposY>570)
		{
			play = false;
			ballXDir = 0;
			ballYDir = 0;
			g.setColor(Color.RED);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Game Over, Score: "+score, 190, 300);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter to Restart", 230, 340);
		}
		g.dispose();
		
	}
	
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		timer.start();
		
		if(play) {
			
			if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
				ballYDir = -ballYDir;
			}
			
			A: for(int i=0;i<map.map.length;i++) {
				for(int j=0;j<map.map[0].length;j++) {
					if(map.map[i][j]>0) {
						int brickX = j*map.brickWidth + 80;
						int brickY = i*map.brickWidth + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect)) {
							map.setBrickValue(0, i, j);
							totalbricks--;
							score+=5;
							
							if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
								ballXDir = -ballXDir;
							}
							else {
								ballYDir = -ballYDir;
							}
							
							break A;
						}
						
					}
				}
			}
			
			ballposX += ballXDir;
			ballposY += ballYDir;
			if(ballposX<0) {
				ballXDir = -ballXDir;
			}
			if(ballposY<0) {
				ballYDir = -ballYDir;
			}
			if(ballposX>670) {
				ballXDir = -ballXDir;
			}
		}
		
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(playerX>=600) {
				playerX=600;
			}
			else {
				moveRight();
			}
		}
		if(arg0.getKeyCode() == KeyEvent.VK_LEFT) {
			if(playerX<10) {
				playerX=10;
			}
			else {
				moveLeft();
			}
		}
		if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
			if(!play) {
				play = true;
				ballposX = 120;
				ballposY = 350;
				ballXDir = -1;
				ballYDir = -2;
				playerX = 310;
				score = 0;
				totalbricks = 21;
				map = new Bricks(3,7);
				
				repaint();
			}
		}
	}
		
	

	@Override
	public void keyReleased(KeyEvent arg0) {
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
	
	public void moveRight() {
		play=true;
		playerX+=20;
	}
	public void moveLeft() {
		play=true;
		playerX-=20;
	}

	
}
