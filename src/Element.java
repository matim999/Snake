import java.awt.Color;
import java.awt.Graphics;

public class Element {
	private boolean eaten = false;
	private Color figureColor;
	protected boolean ifApple;
	protected Point a;
	protected int sz = size;
	public static int size = 30;
	public void setColor(Color color)
    {
    	this.figureColor = color;
    }
	public void setEaten()
	{
		this.eaten = true;
	}
    public Element(Point a)
    {
    	this.a = new Point(a);
    	figureColor = new Color(0,0,0);
    }
    public Element(Element se)
    {
    	this.a = new Point(se.getA());
    	figureColor = new Color(0,0,0);
    }
    public Element(Point a, boolean ifApple)
    {
    	this.a = new Point(a);
    	this.ifApple = ifApple;
    	this.changeColor();
    }
    public void drawFigure(Graphics g)
    {
    	int x[] = {(int)this.a.getX(), (int)this.a.getX()+sz, (int)this.a.getX()+sz, (int)this.a.getX()};
	    int y[] = {(int)this.a.getY(), (int)this.a.getY(), (int)this.a.getY()+sz, (int)this.a.getY()+sz};
	    if(this.eaten)
	    {
	    	//System.out.println("(" + x[0] + " " + y[0] + ")" + "(" + x[1] + " " + y[1] + ")" + "(" + x[2] + " " + y[2] + ")" + "(" + x[3] + " " + y[3] + ")");
	    	x[0] -= 1;
	    	y[0] -= 1;
	    	x[1] -= 1;
	    	y[1] -= 1;
	    	x[2] -= 1;
	    	y[2] -= 1;
	    	x[3] -= 1;
	    	y[3] -= 1;
	    	//System.out.println("(" + x[0] + " " + y[0] + ")" + "(" + x[1] + " " + y[1] + ")" + "(" + x[2] + " " + y[2] + ")" + "(" + x[3] + " " + y[3] + ")");
	    }
    	g.setColor(this.figureColor);
    	g.drawPolygon(x, y, 4);
    	g.fillPolygon(x, y, 4);
    }
    public void changeColor()
    {
    	if(!ifApple)
    		figureColor = new Color(0,0,0);
    	else
    		figureColor = new Color(5,255,5);
    }
    public void changeColorEaten()
    {
    	this.sz = Element.size+2;
    	figureColor = new Color(255,0,0);
    }
    public void changeColorSnake()
    {
    	this.sz = Element.size;
    	figureColor = new Color(0,0,0);
    	this.eaten = false;
    }
	public Point getA()
	{
		return this.a;
	}
}
