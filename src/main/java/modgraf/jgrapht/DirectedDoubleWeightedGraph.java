package modgraf.jgrapht;

import modgraf.jgrapht.edge.DoubleWeightedEdge;
import modgraf.jgrapht.edge.DoubleWeightedEdgeImpl;

import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.SimpleDirectedGraph;

/**
 * Klasa odpowiada za tworzenie skierowanych grafów podwójnie ważonych.
 * 
 * @author Daniel Pogrebniak
 * 
 * @see DoubleWeightedGraph
 * @see SimpleDirectedGraph
 *
 */
public class DirectedDoubleWeightedGraph<V, E> extends SimpleDirectedGraph<V, E> implements DoubleWeightedGraph<V, E>
{
	private static final long serialVersionUID = -8581178535061693287L;

    public DirectedDoubleWeightedGraph(EdgeFactory<V, E> ef)
    {
        super(ef);
    }
    
	@Override
	public void setEdgeCost(E e, double cost)
	{
		assert (e instanceof DoubleWeightedEdgeImpl) : e.getClass();
        ((DoubleWeightedEdge) e).setCost(cost);
	}

	@Override
	public void setEdgeCapacity(E e, double capacity)
	{
		assert (e instanceof DoubleWeightedEdgeImpl) : e.getClass();
        ((DoubleWeightedEdge) e).setCapacity(capacity);
	}

	@Override
	public double getEdgeCost(E e)
	{
		assert (e instanceof DoubleWeightedEdgeImpl) : e.getClass();
		return ((DoubleWeightedEdge) e).getCost();
	}

	@Override
	public double getEdgeCapacity(E e)
	{
		assert (e instanceof DoubleWeightedEdgeImpl) : e.getClass();
		return ((DoubleWeightedEdge) e).getCapacity();
	}
}
