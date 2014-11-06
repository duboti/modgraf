package modgraf.jgrapht.edge;

import modgraf.jgrapht.Vertex;

public class DirectedEdge extends ModgrafEdge
{
	public DirectedEdge(Vertex source, Vertex target) {
		super(source, target);
	}

	private static final long serialVersionUID = 1093675432180665489L;

	@Override
    public boolean equals(Object obj) {
    	ModgrafEdge other = (ModgrafEdge) obj;
    	return other.getSource().equals(getSource()) && other.getTarget().equals(getTarget());
    }
    
    @Override
    public int hashCode() {
    	if (getSource() != null && getTarget() != null)
    		return (getSource().getId()+getTarget().getId()).hashCode();
    	else
    		return super.hashCode();
    }
}