package modgraf.jgrapht.edge;

import modgraf.jgrapht.DoubleWeightedGraph;
import modgraf.jgrapht.Vertex;

/**
 * Reprezentuje krawędzie podwójnie ważone skierowane.
 * 
 * @author Daniel Pogrebniak
 * 
 * @see ModgrafEdge
 */
public class DirectedDoubleWeightedEdge extends DirectedEdge implements DoubleWeightedEdge
{
	public DirectedDoubleWeightedEdge(Vertex source, Vertex target) {
		super(source, target);
	}

	private static final long serialVersionUID = -5227131908049340762L;
	private double cost = DoubleWeightedGraph.DEFAULT_EDGE_COST;
	private double capacity = DoubleWeightedGraph.DEFAULT_EDGE_CAPACITY;

	@Override
	public void setCost(double cost)
	{
		this.cost = cost;
	}

	@Override
	public double getCost()
	{
		return cost;
	}
	
	@Override
	public void setCapacity(double capacity)
	{
		this.capacity = capacity;
	}
	
	@Override
	public double getCapacity()
	{
		return capacity;
	}
	
	@Override
	public String toString()
    {
        return "("+getSource().getName()+":"+getTarget().getName()+").{"+capacity+","+cost+"}";
    }
}
