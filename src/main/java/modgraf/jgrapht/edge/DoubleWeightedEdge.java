package modgraf.jgrapht.edge;

/**
 * Reprezentuje krawędzie podwójnie ważone.
 *
 * @author Daniel Pogrebniak
 */
public interface DoubleWeightedEdge 
{
	public void 	setCost(double cost);
	public double 	getCost();
	public void 	setCapacity(double capacity);
	public double 	getCapacity();
}
