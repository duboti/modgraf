package modgraf.jgrapht.edge;

import modgraf.jgrapht.Vertex;

/**
 * Klasa reprezentuje krawędzie skierowane nieważone.
 *
 * @author Daniel Pogrebniak
 */
public class DirectedEdge extends ModgrafEdge
{
	public DirectedEdge(Vertex source, Vertex target) {
		super(source, target);
	}

	private static final long serialVersionUID = 1093675432180665489L;

	@Override
    public boolean equals(Object obj) {
        if (obj instanceof DirectedEdge) {
            DirectedEdge other = (DirectedEdge) obj;
            return other.getSource().equals(getSource()) && other.getTarget().equals(getTarget());
        } else
            return false;
    }
    
    @Override
    public int hashCode() {
    	if (getSource() != null && getTarget() != null)
    		return (getSource().getId()+getTarget().getId()).hashCode();
    	else
    		return super.hashCode();
    }
}