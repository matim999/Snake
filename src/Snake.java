import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JComponent;

public class Snake extends JComponent
{
	/**
	 * abeeee
	 */
	private static final long serialVersionUID = 1L;
	private static SnakeElement last;
	private static java.util.List<SnakeElement> eatenApples = new ArrayList<>();
	private static java.util.List<SnakeElement> SnakeElements = new ArrayList<>();
	//private java.util.List<SnakeElement> apples = new ArrayList<>();
	private static Apple apple;
	public Snake()
	{
	}
	public void paintComponent(Graphics g)
	{
		for(Element x: SnakeElements)
		{
			x.drawFigure(g);
		}
		if(apple != null)
			apple.drawFigure(g);
		for(Element x: eatenApples)
		{
			x.drawFigure(g);
		}
	}
	public void add(SnakeElement q)
	{
		SnakeElements.add(q);
	}
	public void add(Apple q)
	{
		SnakeElements.add(new SnakeElement(q));
	}
	public void addApple()
	{
		Random generator = new Random();
		int c1 = generator.nextInt(GUI.width);
		int c2 = generator.nextInt(GUI.height);
		Apple apple2 = new Apple(new Point(c1*(Element.size+2)+1, c2*(Element.size+2)+1));
		if(!checkCollision(apple2))
			apple = apple2;
		else
			addApple();
	}
	public boolean moveObject(char direction)
	{
		SnakeElement next = new SnakeElement(SnakeElements.get(0));
		next.FigureMove(Element.size+2, direction);
		for(int i=SnakeElements.size()-1; i>=1; i--)
		{
			SnakeElements.get(i).replace(SnakeElements.get(i-1).getA());
		}
		if(!checkCollision(next))
		{
			if(next.getA().getX() < 0)
				SnakeElements.get(0).replace(new Point((Element.size+2)*(GUI.width-1)+1, SnakeElements.get(0).getA().getY()));
			else if(next.getA().getX() > (GUI.width-1)*(Element.size+2)+1)
				SnakeElements.get(0).replace(new Point(1, SnakeElements.get(0).getA().getY()));
			else if(next.getA().getY() < 0)
				SnakeElements.get(0).replace(new Point(SnakeElements.get(0).getA().getX(), (GUI.height-1)*(Element.size+2)+1));
			else if(next.getA().getY() > (GUI.height-1)*(Element.size+2)+1)
				SnakeElements.get(0).replace(new Point(SnakeElements.get(0).getA().getX(), 1));
			else
				SnakeElements.get(0).FigureMove(Element.size+2, direction);
		}
		else
			return false;
		
		if(last!=null)
		{
			last.changeColorSnake();
			eatenApples.remove(last);
		}
		
		if(!eatenApples.isEmpty())
		{
			if(eatenApples.get(0).getA().equals(SnakeElements.get(SnakeElements.size()-1).getA()))
			{
				last = eatenApples.get(0);
				SnakeElements.add(last);
			}
		}
		if(checkApple())
		{
			GUI.score++;
			GUI.showScore();
			if(GUI.score%20 == 0)
				GUI.adjustSpeed();
		}
		return true;
	}
	private boolean checkCollision(Element next)
	{
		for(Element x: SnakeElements)
		{
			if(next.getA().equals(x.getA()))
			{
				return true;
			}
		}
		return false;
	}
	private boolean checkApple()
	{
		if(SnakeElements.get(0).getA().equals(apple.getA()))
		{
			eatenApples.add(new SnakeElement(apple));
			eatenApples.get(eatenApples.size()-1).changeColorEaten();;
			eatenApples.get(eatenApples.size()-1).setEaten();
			addApple();
			return true;
		}
		return false;
	}
	public static void clearLists()
	{
		SnakeElements.clear();
		eatenApples.clear();
		apple = null;
		last = null;
	}
	public Apple getApple()
	{
		return Snake.apple;
	}
	public SnakeElement getHead()
	{
		return Snake.SnakeElements.get(0);
	}
}
