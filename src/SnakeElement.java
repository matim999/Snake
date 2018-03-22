public class SnakeElement extends Element{
	public SnakeElement(Point a)
	{
		super(a, false);
	}
	public SnakeElement(Element se)
    {
    	super(se);
    }
	public void setSz(int x)
	{
		this.sz = x;
	    this.ifApple= false;
	}
	public void replace(Point point)
	{
	    this.a.setX(point.getX());
	    this.a.setY(point.getY());
	}
	public void FigureMove(int distance, char direction)
	{
		switch(direction)
		{
		case 'L':
			a.addToX(-distance);
			break;
		case 'R':
			a.addToX(distance);
			break;
		case 'U':
			a.addToY(-distance);
			break;
		case 'D':
			a.addToY(distance);
			break;
		}
	}
}
