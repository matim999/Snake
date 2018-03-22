import static org.junit.Assert.*;

import java.io.*;
import java.io.IOException;
import java.lang.reflect.*;

public class Test {
	
	@org.junit.Test
	public void TestMoveRightWithAppleInNextMoveAndTestScoreIncrease() {
		GUI gui = new GUI();
		Snake.clearLists();
		Snake test = new Snake();
		Point p = new Point(1+(Element.size+2)*(GUI.width/2), 1+(Element.size+2)*(GUI.height/2));
		for(int i=0; i<5; i++)
		{
			p.setX(p.getX()-(Element.size+2));
			SnakeElement quad = new SnakeElement(p);
			test.add(quad);
		}
		test.addApple();
		Apple apple = test.getApple();
		apple.getA().setX(1+(Element.size+2)*(GUI.width/2));
		apple.getA().setY(1+(Element.size+2)*(GUI.height/2));
		GUI.score = 0;
		assertEquals(0, GUI.score);
		assertTrue(test.moveObject('R'));
		assertEquals(1, GUI.score);
	}
	
	@org.junit.Test
	public void TestMoveWithCollisionsInNextMoveIfGoRightOrDown() {			//	. . . . . . . . 
		Snake test = positionWithCollisionsOnRightAndDown();				//	. . . & * . . .
		assertTrue(test.moveObject('L'));									//	. . * * * . . .
		positionWithCollisionsOnRightAndDown();								//	. . . . . . . . 
		assertTrue(test.moveObject('U'));									//	. . . . . . . . 
		positionWithCollisionsOnRightAndDown();								// & - głowa węża
		assertFalse(test.moveObject('D'));									// * - kolejne elementy weza
		positionWithCollisionsOnRightAndDown();								// . - pola siatki
		assertFalse(test.moveObject('D'));									// Ten test przedstawia powyższy scenariusz
	}
	
	@org.junit.Test
	public void HighscoreTestOverride() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		GUI gui = new GUI();
		Method stopAfterCollision = GUI.class.getDeclaredMethod("stopAfterCollision");
		stopAfterCollision.setAccessible(true);
		int hs, oldhs;
		byte c;
		try {
			FileInputStream in = new FileInputStream("hs.txt");
			oldhs = (int)in.read();
			in.close();
			GUI.score = oldhs + 10;
			stopAfterCollision.invoke(gui);
			in = new FileInputStream("hs.txt");
			hs = (int)in.read();
			in.close();
			assertEquals(oldhs+10, hs);
			System.out.println( oldhs);
			FileOutputStream out = new FileOutputStream("hs.txt");
			out.write((byte)(oldhs));
			out.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private Snake positionWithCollisionsOnRightAndDown()
	{
		Snake.clearLists();
		Snake test = new Snake();
		Point[] p = new Point[5];
		p[0] = new Point(1+(Element.size+2)*5, 1+(Element.size+2)*5);
		p[1] = new Point(1+(Element.size+2)*6, 1+(Element.size+2)*5);
		p[2] = new Point(1+(Element.size+2)*6, 1+(Element.size+2)*6);
		p[3] = new Point(1+(Element.size+2)*5, 1+(Element.size+2)*6);
		p[4] = new Point(1+(Element.size+2)*4, 1+(Element.size+2)*6);
		for(int i=0; i<5; i++)
		{
			SnakeElement quad = new SnakeElement(p[i]);
			test.add(quad);
		}
		test.addApple();
		return test;
	}
}
