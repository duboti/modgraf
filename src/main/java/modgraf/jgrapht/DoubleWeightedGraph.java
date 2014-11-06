package modgraf.jgrapht;

import org.jgrapht.Graph;

/**
 * Interfejs odpowiada za tworzenie grafów podwójnie ważonych.
 * 
 * @author Daniel Pogrebniak
 * 
 * @see Graph
 *
 */
public interface DoubleWeightedGraph<V, E> extends Graph<V, E>
{
	public static double DEFAULT_EDGE_COST 		= 1.0;
	public static double DEFAULT_EDGE_CAPACITY  = 1.0;
	
	public void 	setEdgeCost(E e, double cost);
	public double 	getEdgeCost(E e);
	
	public void 	setEdgeCapacity(E e, double capacity);
	public double 	getEdgeCapacity(E e);
}
