package modgraf.jgrapht.edge;

import modgraf.jgrapht.Vertex;

public class DirectedTripleWeightedEdge extends DirectedDoubleWeightedEdge {

	private static final long serialVersionUID = 8845967074459639948L;

	private double flow = 1;

	private boolean consistentWithFlow;

	public DirectedTripleWeightedEdge(Vertex source, Vertex target) {
		super(source, target);
	}

	public double getFlow() {
		return flow;
	}

	public void setFlow(double flow) {
		this.flow = flow;
	}

	public boolean isConsistentWithFlow() {
		return consistentWithFlow;
	}

	public void setConsistentWithFlow(boolean consistentWithFlow) {
		this.consistentWithFlow = consistentWithFlow;
	}

	@Override
	public String toString() {
		return "isCons: " + this.isConsistentWithFlow() + ", flow: " + this.getFlow() + ", capacity: " + this.getCapacity()
				+ ", cost: " + this.getCost();
	}
}
