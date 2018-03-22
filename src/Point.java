
public class Point {
	private int x = 0;
	private int y = 0;
    public Point()
    {
    }
    public Point(int valueX, int valueY)
    {
        this.x = valueX;
        this.y = valueY;
    }
    public Point(Point point)
    {
        this.x = point.x;
        this.y = point.y;
    }
    public Point(int valueX)
    {
        this.x = valueX;
        this.y = 0;
    }
    public int getX()
    {
        return this.x;
    }
    public int getY()
    {
        return this.y;
    }
    public int[] getCoordinates()
    {
    	int[] coordinates = {this.x, this.y};
        return coordinates;
    }
    public void setX(int value)
    {
        this.x = value;
    }
    public void setY(int value)
    {
        this.y = value;
    }
    public void addToX(int value)
    {
        this.x += value;
    }
    public void addToY(int value)
    {
        this.y += value;
    }
    public String toString() {
		return "(x=" + x + ", y=" + y + ")";
	}
    public void replace(char coordinate, int value)
    {
        if (coordinate == 'x' || coordinate == 'X')
        {
            this.x = value;
        }
        else if (coordinate == 'y' || coordinate == 'Y')
        {
            this.y = value;
        }
        else
        {
            System.out.println("Podano zla wspolrzedna");
        }
    }
    public boolean equals(Point p)
    {
    	if(this.getX()==p.getX() && this.getY()==p.getY())
    		return true;
    	return false;
    }
}

