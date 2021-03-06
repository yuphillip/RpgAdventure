/*Phillip Yu*/
package Game;

import Events.BattleEvent;
import Events.Boss;
import Events.HealEvent;
import People.Person;
import Events.Room;

import java.util.Scanner;



public class Runner {
	

	private static boolean gameOn = true;
	
	public static void main(String[] args)
	{
		String vrBuilding = "";
		Room[][] building = new Room[7][7];
		int Health = 650;
		int Gold = 0;

		//Fill the building with normal rooms
		for (int x = 0; x<building.length; x++)
		{
			for (int y = 0; y < building[x].length; y++)
			{
				building[x][y] = new Room(x,y);
			}
		}
		for (int i = 0; i < building.length-1; i++) {
			for (int w = 0; w < building[i].length-1; w++) {
				vrBuilding += "[]";
			}
			vrBuilding += "\n";
		}
		System.out.println(vrBuilding);
		System.out.println("You have" + " " + Health + " " + "health and" + " " + Gold + " " + "gold. Fight battles " +
				"risking your life to gain gold, buy a better weapon and defeat the boss.");


		//Create a random winning room.
		int x = (int)(Math.random()*building.length);
		int y = (int)(Math.random()*building.length);
		if (x==0 && y==0)
		{
			x = (int)(Math.random()*building.length);
			y = (int)(Math.random()*building.length);
		}
		building[x][y] = new Boss(x, y, Health);

		int v = (int)(Math.random()*building.length);
		int w = (int)(Math.random()*building.length);
		if(v==x && w==y)
		{
			while(v==x && w==y)
			{
				v = (int)(Math.random()*building.length);
				w = (int)(Math.random()*building.length);
			}
		}
		building[v][w] = new HealEvent(v,w,Health);

		int b1 = 0;
		int e1 = 0;
		for(int i = 0;i<=4;i++)
		{
			int b = (int) (Math.random() * building.length);
			int e = (int) (Math.random() * building.length);
			while((b==x && e==y)||(b==v && e==w))
			{
				b = (int) (Math.random() * building.length);
				e = (int) (Math.random() * building.length);
			}
			while(b1 == b && e1 == e)
			{
				b = (int) (Math.random() * building.length);
				e = (int) (Math.random() * building.length);
			}
			b1 = b;
			e1 = e;
			building[b][e] = new BattleEvent(b, e, Health);
		}

		 
		 //Setup player 1 and the input scanner
		Person player1 = new Person("FirstName", "FamilyName", 0,0);
		building[0][0].enterRoom(player1);
		Scanner in = new Scanner(System.in);
		while(gameOn)
		{
			System.out.println("Where would you like to move? (Use WASD to move.)");
			String move = in.nextLine();
			if(validMove(move, player1, building))
			{
				System.out.println("Your coordinates: row = " + player1.getxLoc() + " col = " + player1.getyLoc());
				
			}
			else {
				System.out.println("Please choose a valid move.");
			}
			if (Health == 0)
			{
				gameOff();
			}
			
			
		}
		in.close();
	}

	/**
	 * Checks that the movement chosen is within the valid game map.
	 * @param move the move chosen
	 * @param p person moving
	 * @param map the 2D array of rooms
	 * @return
	 */
	public static boolean validMove(String move, Person p, Room[][] map)
	{
		move = move.toLowerCase().trim();
		switch (move) {
			case "w":
				if (p.getxLoc() > 0)
				{
					map[p.getxLoc()][p.getyLoc()].leaveRoom(p);
					map[p.getxLoc()-1][p.getyLoc()].enterRoom(p);
					return true;
				}
				else
				{
					return false;
				}
			case "d":
				if (p.getyLoc()< map[p.getyLoc()].length -1)
				{
					map[p.getxLoc()][p.getyLoc()].leaveRoom(p);
					map[p.getxLoc()][p.getyLoc() + 1].enterRoom(p);
					return true;
				}
				else
				{
					return false;
				}

			case "s":
				if (p.getxLoc() < map.length - 1)
				{
					map[p.getxLoc()][p.getyLoc()].leaveRoom(p);
					map[p.getxLoc()+1][p.getyLoc()].enterRoom(p);
					return true;
				}
				else
				{
					return false;
				}

			case "a":
				if (p.getyLoc() > 0)
				{
					map[p.getxLoc()][p.getyLoc()].leaveRoom(p);
					map[p.getxLoc()][p.getyLoc()-1].enterRoom(p);
					return true;
				}
				else
				{
					return false;
				}
			default:
				break;
					
		}
		return true;
	}
	public static void gameOff()
	{
		gameOn = false;
	}
	


}
