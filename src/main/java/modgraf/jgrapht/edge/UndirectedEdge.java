package modgraf.jgrapht.edge;

import modgraf.jgrapht.Vertex;

/**
 * Klasa reprezentuje krawędzie nieskierowane nieważone.
 *
 * @author Daniel Pogrebniak
 */
public class UndirectedEdge extends ModgrafEdge
{
	public UndirectedEdge(Vertex source, Vertex target) {
		super(source, target);
	}

	private static final long serialVersionUID = -5079900449134164558L;

	@Override
    public boolean equals(Object obj) {
        if (obj instanceof UndirectedEdge) {
            UndirectedEdge other = (UndirectedEdge) obj;
            return (other.getSource().equals(getSource()) && other.getTarget().equals(getTarget()))
                    || (other.getSource().equals(getTarget()) && other.getTarget().equals(getSource()));
        } else
            return false;
    }
    
    @Override
    public int hashCode() {
    	if (getSource() != null && getTarget() != null)
    		return getSource().getId().hashCode()^getTarget().getId().hashCode();
    	else
    		return super.hashCode();
    }
}
