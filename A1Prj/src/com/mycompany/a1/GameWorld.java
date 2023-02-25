package com.mycompany.a1;
import java.util.Random;
import java.util.Vector;
import java.lang.Math;

import com.codename1.charts.util.ColorUtil;
import com.codename1.charts.models.Point;

public class GameWorld<E> {
	final private int gameWorldWidth = 1000;
	final private int gameWorldLength = 1000;
	private int lives = 3;
	private int gameClock = 0;
	private Vector<GameObject> objectList;
	
	public GameWorld() {
	}
	
	public void init() {
		//code here to create the initial game objects/setup
		this.gameClock = 0;
		objectList = new Vector<GameObject>();
		
		Flag fl1 = new Flag(1);
		objectList.add(fl1);
		Flag fl2 = new Flag(2);
		objectList.add(fl2);
		Flag fl3 = new Flag(3);
		objectList.add(fl3);
		Flag fl4 = new Flag(4);
		objectList.add(fl4);
		
		Ant a1 = new Ant();
		objectList.add(a1);
		
		Spider s1 = new Spider();
		objectList.add(s1);
		Spider s2 = new Spider();
		objectList.add(s2);
		
		FoodStation fd1 = new FoodStation();
		objectList.add(fd1);
		FoodStation fd2 = new FoodStation();
		objectList.add(fd2);
	}
	
	// Steer-able interface for allowing the player to control the ant
	public interface ISteerable{
		public void headingRight();
		public void headingLeft();
	}
	
	// implementation of classes that represent all of the objects present in the game world. This includes: Flags, foodStations, spiders, and the ant.
	// also below are the abstract classes gameObject, steerable, and fixed which the other classes inherit.
	public abstract class GameObject{
		//Fields
		private int size;
		private Point location;
		private int color;
		
		//Constructors
		public GameObject(int color) {
			this.color = color;
			Random rn = new Random();
			float x = (float)rn.nextFloat() * gameWorldWidth;
			float y = (float)rn.nextFloat() * gameWorldLength;
			this.location = new Point(x,y);
		}
		public GameObject(int color, int size) {
			this.color = color;
			this.size = size;
			Random rn = new Random();
			float x = (float)rn.nextFloat() * gameWorldWidth;
			float y = (float)rn.nextFloat() * gameWorldLength;
			this.location = new Point(x,y);
		}
		
		//setters and getters
		public void setX(float x) {
			location.setX(x);
		}
		public float getX() {
			return location.getX();
		}
		public void setY(float y) {
			location.setY(y);
		}
		public float getY() {
			return location.getY();
		}
		
		public int getSize() {
			return size;
		}
		public void setSize(int size) {
			this.size = size;
		}
		
		public Point getLocation() {
			return location;
		}
		public void setLocation(float x, float y) {
			this.location = new Point(x, y);
		}
		
		public int getColor() {
			return color;
		}
		public void setColor(int color) {
			this.color = color;
		}
		
		// toString override
		public String getColortoString() {
			return "["+ ColorUtil.red(this.color) + "," + ColorUtil.green(this.color) + "," + ColorUtil.blue(this.color)+"]";
		}
	}
	
	// abstract class Fixed is extended by the Flag and FoodStation classes below.
	public abstract class Fixed extends GameObject{
		//Constructors
		public Fixed(int color) {
			super(color);
		}
		public Fixed(int color, int size) {
			super(color, size);
		}
	}
	
	// Flags act as way points for the player and the main objective. Once all 9 flags are collected the player wins the game.
	public class Flag extends Fixed{
		//Fields
		private int sequenceNumber;
		//Constructors
		public Flag(int sequenceNumber) {
			super(ColorUtil.YELLOW, 10);
			this.sequenceNumber = sequenceNumber;
		}
		
		//setters and getters
		public void setColor(int color) {
			System.out.println("Cannot change color");
		}
		public int getSequenceNumber() {
			return this.sequenceNumber;
		}
		public void setSequenceNumber(int sequenceNumber) {
			this.sequenceNumber = sequenceNumber;
		}
		public void setLocation(int size) {
		}
		
		// toString override
		public String toString() {
			return ("Flag: loc = " + Math.round(this.getX() * 10.0) / 10.0  + ","+ Math.round(this.getY() * 10.0) / 10.0 +
					"  color = " + this.getColortoString() +
					"  size = " + this.getSize()+
					"  seqNumber = " + this.getSequenceNumber()
					);
		}
	}
	
	// Food Stations add food to the Ant, allowing the ant to last longer.
	public class FoodStation extends Fixed{
		//Fields
		private int capacity;
		
		//Constructors
		public FoodStation() {
			super(ColorUtil.GREEN);
			super.setSize(new Random().nextInt(40) + 10);
			this.capacity = this.getSize();
		}
		public boolean checkFoodStation() {
			if(capacity == 0)
				return false;
			else
				return true;
		}
		
		//Setters and getters
		public void setCapacity(int capacity) {
			this.capacity = capacity;
		}
		public int getCapacity() {
			return capacity;
		}
		public void setSize(int size) {
		}
		public void setColor(int color) {
		}
		
		//toString override
		public String toString() {
			return ("FoodStation: loc= " + Math.round(this.getX()* 10.0)/ 10.0  +","+ Math.round(this.getY()*10.0)/10.0 +
					"  color= " + this.getColortoString() +
					"  size= " + this.getSize()+
					"  capacity="+ this.getCapacity()
					);
		}
	}
	
	//abstract class movable is extended by the ant and spider classes below.
	public abstract class Moveable extends GameObject{
		//Fields
		private int heading, speed;
		
		//Constructors
		public Moveable(int color) {
			super(color);
		}
		public Moveable(int color, int size) {
			super(color, size);
		}
		
		//Methods
		public void move() {
			float radian = (90 - this.getHeading()) * (float)Math.PI / 180 ;
			float newX = this.getX() + (float)Math.cos(radian) * this.getSpeed();
			float newY = this.getY() + (float)Math.sin(radian) * this.getSpeed();
			this.setLocation(newX, newY);
		}
		public void checkBoundary() {
			
		}
		
		//Setters and getters
		public void setSpeed(int speed) {
			this.speed = speed;
		}
		public int getSpeed() {
			return speed;
		}
		public void setHeading(int heading) {
			this.heading = heading;
		}
		public int getHeading() {
			return heading;
		}
	}
	
	// The Ant is controllable by the player and is the main character of the game. The goal is to move the ant to all the checkpoints
	// while maintaining their food levels and avoiding spiders.
	public class Ant extends Moveable implements ISteerable{
		//Fields
		private int maximumSpeed, foodLevel, foodConsumptionRate, healthLevel, lastFlagReached;
		
		//Constructors
		public Ant() {
			super(ColorUtil.BLACK, 30);
			super.setSpeed(0);
			super.setHeading(0);
			this.maximumSpeed = 30;
			this.foodConsumptionRate = 1;
			this.foodLevel = 20;
			this.healthLevel = 10;
			this.lastFlagReached = 1;
			
		}
		
		//Methods
		@Override
		public void headingLeft() {
			int temp = this.getHeading() - 5;
			if(temp < 0) {
				this.setHeading(temp + 360);
			}
			else if(temp > 360) {
				this.setHeading(temp - 360);
			}
			else {
				this.setHeading(temp);
			}
		}
		@Override
		public void headingRight() {
			int temp = this.getHeading() + 5;
			if(temp < 0) {
				this.setHeading(temp + 360);
			}
			else if(temp > 360) {
				this.setHeading(temp - 360);
			}
			else {
				this.setHeading(temp);
			}
		}
		
		public void decreaseSpeed() {
			if (this.getSpeed() > 0) {
				this.setSpeed(this.getSpeed() + 1);
			}
			else {
				System.out.println("Minimum Speed Achieved!");
			}
		}
		public void increaseSpeed() {
			if (this.getSpeed() < this.getMaximumSpeed() ) {
				this.setSpeed(this.getSpeed() + 1);
			}
			else {
				System.out.println("Maximum Speed Achieved!");
			}
		}
		
		public void resetAnt(float x, float y) {
			this.setX(x);
			this.setY(y);
			this.setHeading(0);
			this.maximumSpeed = 30;
			this.foodConsumptionRate = 1;
			this.foodLevel = 20;
			this.healthLevel = 10;
			this.lastFlagReached = 1;
			lives--;
		}
		
		//Setters and Getters
	    public int getMaximumSpeed() {
			return maximumSpeed;
		}
		public void setMaximumSpeed(int maximumSpeed) {
			this.maximumSpeed = maximumSpeed;
		}
		
		public int getFoodLevel() {
			return foodLevel;
		}
		public void setFoodLevel(int foodLevel) {
			this.foodLevel = foodLevel;
		}
		
		public int getFoodConsumptionRate() {
			return foodConsumptionRate;
		}
		public void setFoodConsumptionRate(int FoodConsumptionRate) {
			this.foodConsumptionRate = FoodConsumptionRate;
		}
		
		public int getHealthLevel() {
			return healthLevel;
		}
		public void setHealthLevel(int healthLevel) {
			this.healthLevel = healthLevel;
		}
		public int getLastFlagReached() {
			return lastFlagReached;
		}
		public void setLastFlagReached(int lastFlagReached) {
			this.lastFlagReached = lastFlagReached;
		}
		public void setSize(int size) {}
		
		//toString override
		public String toString() {
			return ("Ant: loc=" + Math.round(this.getX()* 10.0)/ 10.0  +","+ Math.round(this.getY()*10.0)/10.0+
					"  color= " + this.getColortoString()+
					"  speed= " + this.getSpeed()+
					"  heading= " + this.getHeading()+
					"  size= " + this.getSize()+
					"  maxSpeed=" + this.getMaximumSpeed()+
					"  FoodLevel=" + this.getFoodLevel()+
					"  HealthLevel=" + this.getHealthLevel()
					);
		}
	}
	
	//Spiders represent the main active threat to the player in game. They damage the player and reduce their maximum speed
	// on collision.
	public class Spider extends Moveable{
		Random random = new Random();
		//Constructors
		public Spider() {
			super(ColorUtil.GRAY);

			super.setSize(new Random().nextInt(40) + 10);
			super.setHeading(random.nextInt(359));
			super.setSpeed(random.nextInt(5) + 5);
		}
		
		//Setters and getters
		public void setSize(int size) {
		}
		public void setColor(int color) {
		}
		public void setHeading() {
			this.setHeading(super.getHeading() + random.nextInt(10) - 5);
		}
		public void boundarySpider() {
			if ((this.getSize() + this.getX()) > 1000 || (this.getX() + this.getSize() > 0)) {
				this.setHeading(random.nextInt(180));
			}
			if ((this.getSize() + this.getY()) > 1000 || (this.getSize() + this.getY()) < 0 ) {
				this.setHeading(random.nextInt(180));
			}
		}
		
		//toString override
		public String toString() {
			return ("Spider: loc= " + Math.round(this.getX()* 10.0)/ 10.0  +","+ Math.round(this.getY()*10.0)/10.0 +
					"  color= " + this.getColortoString() + 
					"  size= " + this.getSize()+
					"  heading="+ this.getHeading()+
					"  speed= " + this.getSpeed()
					);
		}
	}
	
	//methods which relate to the commands that the user can input to effect change on the game world.
	//additional methods here to manipulate the world objects and related game state data
	// Game Methods
		// Press 'a'
		public void accelerate() {
			System.out.println("Ant accelerates!");
			for (int i=0; i<objectList.size(); i++) {
				if (objectList.elementAt(i) instanceof GameWorld.Ant) {
					Ant aObj = (Ant) objectList.elementAt(i);
					aObj.increaseSpeed();
				}
			}
		}
		// Press 'b'
		public void brake() {
			System.out.println("Ant brakes!");
			for (int i=0; i<objectList.size(); i++) {
				if (objectList.elementAt(i) instanceof GameWorld.Ant) {
					Ant aObj = (Ant) objectList.elementAt(i);
					aObj.decreaseSpeed();
				}
			}
		}
		// Press'l'
		public void headingLeft() {
			System.out.println("Ant turns left!");
			for (int i=0; i<objectList.size(); i++) {
				if (objectList.elementAt(i) instanceof GameWorld.Ant) {
					Ant aObj = (Ant) objectList.elementAt(i);
					aObj.headingLeft();
				}
			}
		}
		// Press 'r'
		public void headingRight() {
			System.out.println("Ant turns right!");
			for (int i=0; i<objectList.size(); i++) {
				if (objectList.elementAt(i) instanceof GameWorld.Ant) {
					Ant aObj = (Ant) objectList.elementAt(i);
					aObj.headingRight();
				}
			}
		}

		public void collisionFlag(int j) {
			System.out.println("Ant collidied with a Flag!");
			for (int i=0; i<objectList.size(); i++) {
				if (objectList.elementAt(i) instanceof GameWorld.Ant) {
					Ant aObj = (Ant) objectList.elementAt(i);
					int k = aObj.getLastFlagReached() + 1;
					if (k == j) {
						System.out.println("Ant Collected Flag!");
						aObj.setLastFlagReached(k);
					}
					else {
						System.out.println("Flag out of Order!");
					}
					if (k == 9)
					{
						System.out.println("Game Over, You Win! Total time: " + gameClock);
						System.exit(0);
					}
				}
			}
		}
		// Press 'g'
		public void collisionSpider() {
			System.out.println("Ant collided with a Spider!");
			for (int i=0; i<objectList.size(); i++) {
				if (objectList.elementAt(i) instanceof GameWorld.Ant) {
					Ant aObj = (Ant) objectList.elementAt(i);
					aObj.setHealthLevel(aObj.getHealthLevel() - 1);
					aObj.setMaximumSpeed((int)Math.round(aObj.getMaximumSpeed() * aObj.getHealthLevel() / 10.0));
					if(aObj.getSpeed() > aObj.getMaximumSpeed()) {
						aObj.setSpeed(aObj.getMaximumSpeed());
					}
				}
			}
		}
		// Press'e'
		public void collisionFood() {
			Random rn = new Random();
			System.out.println("Ant collided with a Food Station!");
			for (int i=0; i<objectList.size(); i++) {
				if (objectList.elementAt(i) instanceof GameWorld.Ant) {
					Ant aObj = (Ant) objectList.elementAt(i);
					int j = 0;
					boolean action = true;
					while(j<objectList.size() && action) {
						if(objectList.elementAt(j) instanceof GameWorld.FoodStation && rn.nextInt(1) > 0) {
							FoodStation fObj = (FoodStation) objectList.elementAt(j);
							aObj.setFoodLevel(fObj.getCapacity() + aObj.getFoodLevel());
							fObj.setCapacity(0);
							action = false;
						}
						j++;
					}
				}
			}
		}
		// Press 't'
		public void tick()
		{
			System.out.println("GameWorld advances!");
			
			for (int i=0; i<objectList.size(); i++) {
				if (objectList.elementAt(i) instanceof GameWorld.Spider) {
					Spider sObj = (Spider) objectList.elementAt(i);
					sObj.setHeading(i);
				}
				if (objectList.elementAt(i) instanceof GameWorld.Moveable) {
					Moveable mObj = (Moveable) objectList.elementAt(i);
					mObj.move();
				}
				if (objectList.elementAt(i) instanceof GameWorld.Ant) {
					Ant aObj = (Ant) objectList.elementAt(i);
					aObj.setFoodLevel(aObj.getFoodLevel() - 1);
				}
			}
			gameClock++;
		}
		// Press 'm'
		public void map() {
			System.out.println("I'm the Map!");
			for (int i=0; i<objectList.size(); i++) {
				System.out.println(objectList.elementAt(i).toString());
			}
		}
		// Press 'd'
		public void display() {
			System.out.println("Display!");
			System.out.println("The game clock is: " + getGameClock());
			System.out.println("The current amount of lives is: " + getLives());
			for (int i=0; i<objectList.size(); i++) {
				if (objectList.elementAt(i) instanceof GameWorld.Ant) {
					Ant aObj = (Ant) objectList.elementAt(i);
					System.out.println("The last flag reached is: " + aObj.getLastFlagReached());
					System.out.println("The current food level is: " + aObj.getFoodLevel());
					System.out.println("The current health level is: " + aObj.getHealthLevel());
				}
			}
		}
		public int getGameClock() {
			return this.gameClock;
		}
		public int getLives() {
			return this.getLives();
		}
		// Press 'x' 
		public void exit() {
			System.exit(0);
		}
}
