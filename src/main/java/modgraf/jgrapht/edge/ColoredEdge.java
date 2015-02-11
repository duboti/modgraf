package modgraf.jgrapht.edge;

import modgraf.jgrapht.Vertex;

/**
 * Reprezentuje krawędzie w algorytmie kolorowania krawędzi.
 */
public class ColoredEdge extends UndirectedEdge
{
	private static final long serialVersionUID = -3045595141936494922L;
	private int color = -1;
	
	public ColoredEdge(Vertex source, Vertex target) 
	{
		super(source, target);
	}

	public int getColor()
	{
		return color;
	}

	public void setColor(int color)
	{
		this.color = color;
	}
	
	@Override
	public String toString()
    {
        return "(" + getSource().getName() + ":" + getTarget().getName() + ").c:" + getColor();
    }
	
	public String getOtherVertex(String vertex)
	{
		if (vertex.equals(getSource().getId()))
			return getTarget().getId();
		if (vertex.equals(getTarget().getId()))
			return getSource().getId();
		return null;
	}
}
