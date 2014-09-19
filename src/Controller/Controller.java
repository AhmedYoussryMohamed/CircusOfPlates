package Controller;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Shape;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import Model.Player;
import Model.PlayerOne;
import Model.PlayerTwo;
import Model.SaveProject;
import Model.ShapeFactory;
import Model.ShapePool;
import Model.Shapes;
import States.PlayerStateDraw;
import States.PlayerStateLose;
import States.PlayerStateWin;
import View.AudioPlayer;
import View.FirstWindow;
import View.Gui;
import View.SoundEffect;

public class Controller implements Serializable{
	
	String path = System.getenv("ahmed");
	private Class [] type = { Color.class };
	private Class firstClass = null;
	private Class secondClass = null;
	private static Controller control = null;
	private ShapeFactory factory;
	private long startTime;
	private long currentTime;
	private long beginGameTime;
//	private Iterator iterator;
	private ShapePool pool = null;
	private Player pOne = null;
	private Player pTwo = null;
	private ArrayList<Shapes> list;
	
	private Controller(){
	}
	
	public static Controller getInstance(){
		if(control == null){
			control = new Controller();
		}
		return control;
	}
	
	public void operate() throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		
		dynamicLoading();
		factory = ShapeFactory.getInstance();
		factory.setFirstClass( firstClass );
		factory.setSecondClass( secondClass );
		pool = ShapePool.getInstance();
		pOne = PlayerOne.getInstance();
		pTwo = PlayerTwo.getInstance();
		list = pool.getShapesArray();
		Iterator iterator = pool.getIterator();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FirstWindow frame = new FirstWindow();
					startTime= System.currentTimeMillis();
					beginGameTime = System.currentTimeMillis();
//					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		
	}//end method operate;
	
	public void dynamicLoading() throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		
		File file = new File(path);
		URL url = file.toURL(); 
		URL[] urls = new URL[]{url};
	    ClassLoader cl = new URLClassLoader(urls);
	    firstClass = cl.loadClass("ShapeOne");
	    secondClass = cl.loadClass("ShapeTwo");
	    
	}//end method
	
	public Class getFirstClass(){
		return firstClass;
	}
	
	public Class getSecondClass(){
		return secondClass;
	}
	
	public void update(){
		
		currentTime =System.currentTimeMillis();
		long timeDiff = 125814;
//		System.out.println(currentTime-startTime);
		
		if(currentTime-startTime > timeDiff){
			startTime = currentTime;
			AudioPlayer audio = new AudioPlayer();
		}
		
		
		//Observer Pattern.
		Iterator iterator = pool.getIterator();;
		ArrayList<Player> playersList = new ArrayList<Player>();
		playersList.add( pOne );
		playersList.add( pTwo );
		pool.update();
		while( iterator.hasNext() ){
			Shapes s = (Shapes) iterator.next();
			Random r = new Random(); 
			int z = r.nextInt(3);
			if( s.getShapeState() ){
				s.update();
				Iterator it = playersList.iterator();
				while( it.hasNext() ){
					Player p = (Player) it.next();
					if( s.getShapeState() ){
						p.update(s);
					}
				}//end while
				
			}//end if.
			
		}//end while.

		
	}//end method.
	
	public Player getPlayerOne(){
		return pOne;
	}
	
	public Player getPlayerTwo(){
		return pTwo;
	}
	
	public Shape getShape( Object element ){
		Shapes s = (Shapes) element;
		return s.getShape();
	}
	
	public Color getColor( Object element ){
		Shapes s = (Shapes) element;
		return s.getColor();
	}
	
	public ArrayList<Shapes> getListOfShapes(){
		return list;
	}
	
	public Iterator getIterator(){
		Iterator iterator = list.iterator();
		return iterator;
	}
	
	public void checkColors(){
		pOne.checkColors();
		pTwo.checkColors();
	}
	
	public void makeSoundEffect(){
		SoundEffect se = new SoundEffect();
	}
	
	public void checkState(){
		if( pOne.getScore() > pTwo.getScore() ){
			PlayerStateWin win = new PlayerStateWin();
			pOne.setCurrentState( win );
			PlayerStateLose lose = new PlayerStateLose();
			pTwo.setCurrentState( lose );
		}
		else if( pOne.getScore() < pTwo.getScore() ){
			PlayerStateWin win = new PlayerStateWin();
			PlayerStateLose lose = new PlayerStateLose();
			pOne.setCurrentState( lose );
			pTwo.setCurrentState( win );
		}
		else if( pOne.getScore() == pTwo.getScore() ){
			if( pOne.getLastScoreTime() < pTwo.getLastScoreTime() ){
				PlayerStateWin win = new PlayerStateWin();
				pOne.setCurrentState( win );
				PlayerStateLose lose = new PlayerStateLose();
				pTwo.setCurrentState( lose );
			}
			else if( pOne.getLastScoreTime() > pTwo.getLastScoreTime() ){
				PlayerStateWin win = new PlayerStateWin();
				PlayerStateLose lose = new PlayerStateLose();
				pOne.setCurrentState( lose );
				pTwo.setCurrentState( win );
			}else{
				PlayerStateDraw draw = new PlayerStateDraw();
				pOne.setCurrentState(draw);
				pTwo.setCurrentState(draw);
			}//end else
		}//end else if.
	}//end method.

	public String getPlayerOneState(){
		return pOne.getCurrentState();
	}
	
	public String getPlayerTwoState(){
		return pTwo.getCurrentState();
	}
	
	public int getMaxHeight(){
		return pool.getMaxHeight();
	}
	
	public void saveFile(String fileName) throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		SaveProject s = new SaveProject();
		s.saveFile(fileName);
	}
	
	public void loadFile(String fileName) throws FileNotFoundException, ClassNotFoundException, IOException{
		SaveProject s = new SaveProject();
		control = s.load(fileName);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui frame = new Gui();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			});
	}//end method.
	
	
	public int getFirstPathY(){
		return pool.getFirstPathY();
	}
	
	public int getSecondPathY(){
		return pool.getSecondPathY();
	}
	
	public void addToQueue( Shapes s ){
		pool.addToQueue(s);
	}
	
}//end class.
