package modgraf.jgrapht;

import modgraf.jgrapht.edge.DoubleWeightedEdgeImpl;

import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.SimpleGraph;

/**
 * Klasa odpowiada za tworzenie nieskierowanych grafów podwójnie ważonych.
 * 
 * @author Daniel Pogrebniak
 * 
 * @see DoubleWeightedGraph
 * @see SimpleGraph
 *
 */
public class UndirectedDoubleWeightedGraph<V, E> extends SimpleGraph<V, E> implements DoubleWeightedGraph<V, E>
{
	private static final long serialVersionUID = 5484453318429102341L;
	
	public UndirectedDoubleWeightedGraph(EdgeFactory<V, E> ef)
    {
        super(ef);
    }

	@Override
	public void setEdgeCost(E e, double cost)
	{
		assert (e instanceof DoubleWeightedEdgeImpl) : e.getClass();
        ((DoubleWeightedEdgeImpl) e).setCost(cost);
	}

	@Override
	public void setEdgeCapacity(E e, double capacity)
	{
		assert (e instanceof DoubleWeightedEdgeImpl) : e.getClass();
        ((DoubleWeightedEdgeImpl) e).setCapacity(capacity);
	}

	@Override
	public double getEdgeCost(E e)
	{
		assert (e instanceof DoubleWeightedEdgeImpl) : e.getClass();
		return ((DoubleWeightedEdgeImpl) e).getCost();
	}

	@Override
	public double getEdgeCapacity(E e)
	{
		assert (e instanceof DoubleWeightedEdgeImpl) : e.getClass();
		return ((DoubleWeightedEdgeImpl) e).getCapacity();
	}

}
