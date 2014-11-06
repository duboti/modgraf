package modgraf.jgrapht.edge;

import modgraf.jgrapht.Vertex;

public class UndirectedEdge extends ModgrafEdge
{
	public UndirectedEdge(Vertex source, Vertex target) {
		super(source, target);
	}

	private static final long serialVersionUID = -5079900449134164558L;

	@Override
    public boolean equals(Object obj) {
    	ModgrafEdge other = (ModgrafEdge) obj;
    	return (other.getSource().equals(getSource()) && other.getTarget().equals(getTarget()))
    		|| (other.getSource().equals(getTarget()) && other.getTarget().equals(getSource()));
    }
    
    @Override
    public int hashCode() {
    	if (getSource() != null && getTarget() != null)
    		return getSource().getId().hashCode()^getTarget().getId().hashCode();
    	else
    		return super.hashCode();
    }
}
