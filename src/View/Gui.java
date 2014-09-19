package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.GeneralPath;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Controller.Controller;
//import View.*;
//import Model.*;

public class Gui extends JFrame implements Runnable ,KeyListener ,MouseMotionListener ,MouseListener,Serializable,WindowListener {
	

	private JPanel contentPane;
	private Image i;
	private Graphics doubleG;
	private boolean up;
	private int startingX = -1;
	private int startingY = -1;
	private int previousX = 0;
	private int INFINITY = 100000000;
	private Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	private Iterator iterator;
	private Controller control = Controller.getInstance();
	private int firstStringY = 80;
	private int secondStringY = 110;
	private int firstPathY = control.getMaxHeight() + secondStringY;;
	private int secondPathY = 3* control.getMaxHeight() + secondStringY;
	private long beginTime;
	private long currentTime;
	
	public Gui(){
		AudioPlayer audio = new AudioPlayer();
		beginTime = System.currentTimeMillis();
//		System.out.println(JFrame.EXIT_ON_CLOSE);
//		System.out.println(  );
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
//		setSize(d.width,d.height-45);
		setBounds(0,0,d.width,d.height-45);
		
		contentPane = new JPanel();
		
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		addWindowListener(this);
		
		contentPane.setFocusable(true);
		
		Thread thread = new Thread(this);
		thread.start();
	}// end constructor.
	
	@Override
	public void update(Graphics g) {
		
		if( i == null ){
			i = createImage( this.getSize().width , this.getSize().height );
			doubleG = i.getGraphics();
		}
		
//		doubleG.setColor( getBackground() );
		doubleG.setColor( Color.WHITE );
		
		doubleG.fillRect(0, 0, this.getSize().width,this.getSize().height );
		
		doubleG.setColor( getForeground() );
		up = true;
		paint( doubleG );
		
		g.drawImage(i, 0, 0, this );
		
	}//end method update.
	
	@Override
	public void paint(Graphics g) {
//		super.paint(g);
		if( !up ){
			update(g);
		}
		
		 Font bigFont = new Font("Serif", Font.BOLD, 30);
		 control.checkState();
		 //First Texts.
		 g.setColor(Color.BLACK);
		 g.setFont( bigFont );
		g.drawString("User1: ", 20, firstStringY);
		 g.setColor(Color.RED);
		g.drawString( control.getPlayerOne().getScore()+"" , 120,firstStringY);
		g.setColor(Color.BLACK);
		g.drawString("State: ", 20, secondStringY);
		 g.setColor(Color.RED);
		g.drawString( control.getPlayerOneState() , 120, secondStringY);
		
		
		//Second Texts.
		 g.setColor(Color.BLACK);
		 g.setFont( bigFont );
		g.drawString("User2: ", d.width-200, 80);
		 g.setColor(Color.RED);
		g.drawString( control.getPlayerTwo().getScore()+"" , d.width - 100,80);
		g.setColor(Color.BLACK);
		g.drawString("State: ", d.width-200, 110);
		 g.setColor(Color.RED);
		g.drawString( control.getPlayerTwoState() , d.width - 100, 110);
		
		Graphics2D graphics = (Graphics2D) g;
		g.setColor(Color.BLACK);	
		graphics.setColor( Color.BLACK );
		graphics.fill( getFirstPath() );
		graphics.draw( getFirstPath() );
		graphics.fill( getSecondPath() );
		graphics.draw( getSecondPath() );
		graphics.fill( getThirdPath() );
		graphics.draw( getThirdPath() );
		graphics.fill( getFourthPath() );
		graphics.draw( getFourthPath() );
		
		
		iterator = control.getIterator();
		while( iterator.hasNext() ){
			Object obj = iterator.next();
			Shape s = control.getShape( obj );
			
			graphics.setPaint( control.getColor( obj ) );
			graphics.fill( s );
			
			graphics.setColor(Color.BLACK);
			graphics.draw( s );
		}//end while.
		
		//Printing players:
		Image clown = new ImageIcon( System.getenv("ahmed") + "Clown.png").getImage();
		g.drawImage(clown, control.getPlayerOne().getX(), control.getPlayerOne().getYPositionForClown(), control.getPlayerOne().getWidthOfClown()
										, control.getPlayerOne().getHeightOfClown(), null);
		g.drawImage(clown, control.getPlayerTwo().getX(), control.getPlayerTwo().getYPositionForClown(), control.getPlayerTwo().getWidthOfClown()
				, control.getPlayerTwo().getHeightOfClown(), null);
		
		control.checkColors();
		up = false;
		
	}//end method Paint.
	
	@Override
	public void run() {
		while( true ){
			
			control.update();
			
			
			repaint();
			try {
				Thread.sleep( 17 );
				currentTime = System.currentTimeMillis();
				int timeDiff = 2*125814;
				if( currentTime - beginTime >= timeDiff ){
					
					JOptionPane.showMessageDialog(null,"The Time of the game is over.", "Time Error.",JOptionPane.INFORMATION_MESSAGE );
					dispose();
					break;
				}
			} catch (InterruptedException e) {
				System.out.println("here");
			}
		}
	}//end method.
		
	private int errorWindow = 10;
	private int curve = 4;
	private int pathLong = 10;
	public Shape getFirstPath() {
		GeneralPath polyline = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 4); 
		polyline.moveTo( errorWindow , firstPathY  );
		polyline.lineTo( d.width / 3 ,firstPathY );
		polyline.lineTo( d.width / 3 - curve,  firstPathY + pathLong);
		polyline.lineTo( errorWindow + curve, firstPathY  + pathLong);
		polyline.closePath();
		
		return polyline;
	}
	
	public Shape getSecondPath(){
		GeneralPath polyline = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 4);
		polyline.moveTo( d.width - d.width/3, firstPathY  );
		polyline.lineTo( d.width  - errorWindow , firstPathY );
		polyline.lineTo( d.width - errorWindow  - curve,  firstPathY + pathLong);
		polyline.lineTo( d.width - d.width/3 + curve, firstPathY + pathLong);
		polyline.closePath();
		
		return polyline;
	}
	
	public Shape getThirdPath(){
		GeneralPath polyline = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 4);
		
		polyline.moveTo( errorWindow ,  secondPathY );
		polyline.lineTo( d.width / 5 , secondPathY );
		polyline.lineTo( d.width / 5 - curve,  secondPathY  + pathLong);
		polyline.lineTo( errorWindow + curve, secondPathY + pathLong);
		polyline.closePath();
		
		return polyline;
		
	}
	
	public Shape getFourthPath(){
		GeneralPath polyline = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 4);
		polyline.moveTo( d.width - d.width/5, secondPathY  );
		polyline.lineTo( d.width  - errorWindow , secondPathY );
		polyline.lineTo( d.width - errorWindow  - curve, secondPathY + pathLong);
		polyline.lineTo( d.width - d.width/5 + curve, secondPathY + pathLong);
		polyline.closePath();
		
		return polyline;
	}//end method.
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_LEFT){
			control.getPlayerOne().moveLeft();
		}else if (e.getKeyCode()==KeyEvent.VK_RIGHT){
			control.getPlayerOne().moveRight();
		}
		
	}//end method.
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mousePressed( MouseEvent mouse ) {
		startingX = mouse.getX();
		startingY = mouse.getY();
		previousX = INFINITY;
	}//end method.
	
	@Override
	public void mouseDragged(MouseEvent mouse) {
		
		int x = control.getPlayerTwo().getX();
		int width = control.getPlayerTwo().getWidthOfClown();
		int y = control.getPlayerTwo().getYPositionForClown();
		int height = control.getPlayerTwo().getHeightOfClown();
		
		if( startingX > x && startingX < ( x + width )
				&& startingY > y && startingY < ( y + height ) ){
			int newX = mouse.getX();
			int newY = mouse.getY();
			startingX = x + 50;
			if( previousX != INFINITY && newX  >= previousX ){
				control.getPlayerTwo().moveRight();
			}
			else if( previousX != INFINITY && newX < previousX ){
				control.getPlayerTwo().moveLeft();
			}
			else if( newX >= x ){
				control.getPlayerTwo().moveRight();
			}
			else if( newX < x ){
				control.getPlayerTwo().moveLeft();
			}
			else{
				System.out.println("in mouseDragged.");
			}
			previousX = newX;
		}//end if.
	}
	
	@Override
	public void mouseMoved(MouseEvent mouse) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseClicked(MouseEvent mouse) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseEntered(MouseEvent mouse) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseExited( MouseEvent mouse ) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseReleased( MouseEvent mouse ) {
		// TODO Auto-generated method stub
		
	}
	
	public void setIterator(Iterator it){
		iterator = it;
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		
	}//end class

	@Override
	public void windowClosing(WindowEvent e) {
		int confirmed = JOptionPane.showConfirmDialog(null, 
		        "Do you want to save the game?", "Saving",
		        JOptionPane.YES_NO_OPTION);
			
		    if (confirmed == JOptionPane.YES_OPTION) {
//		    	dispose();
		    	 String input = JOptionPane.showInputDialog(this, "Enter The name of the file:" , "File Name" , JOptionPane.QUESTION_MESSAGE);
		    	 if( input != null){
		    		 try {
							control.saveFile(input);
						} catch (InstantiationException | IllegalAccessException
								| IllegalArgumentException | InvocationTargetException
								| NoSuchMethodException | SecurityException
								| IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		    	 }
		    	 
		    }
			dispose();
	}//end method.

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public int getFirstPathY(){
		return firstPathY;
	}
	
	public int getSecondPathY(){
		return secondPathY;
	}
	
	
	
}//end class.
