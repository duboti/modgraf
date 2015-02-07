package modgraf.view;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JMenuItem;

public class AlgorithmMenuItems 
{
	public enum DirectedType {
        directed,
        undirected,
        both
    }

    public enum EdgeWeight {
        unweighted,
        weighted,
        doubleWeighted,
        anyWeighted,
        any
    }

    private Set<JMenuItem> directed;
	private Set<JMenuItem> undirected;
	private Set<JMenuItem> unweighted;
	private Set<JMenuItem> weighted;
	private Set<JMenuItem> doubleWeighted;
	private Set<JMenuItem> anyWeighted;
	private Set<JMenuItem> all;

	public AlgorithmMenuItems() 
	{
		directed = new HashSet<>();
		undirected = new HashSet<>();
		unweighted = new HashSet<>();
		weighted = new HashSet<>();
		doubleWeighted = new HashSet<>();
		anyWeighted = new HashSet<>();
		all = new HashSet<>();
	}

	public void addAlgorithm(JMenuItem algorithm, DirectedType directedType, EdgeWeight edgeWeight)
	{
		if (directedType == DirectedType.directed)
			directed.add(algorithm);
		if (directedType == DirectedType.undirected)
			undirected.add(algorithm);

        switch (edgeWeight) {
            case unweighted:
                unweighted.add(algorithm);
                break;
            case weighted:
                weighted.add(algorithm);
                break;
            case doubleWeighted:
                doubleWeighted.add(algorithm);
                break;
            case anyWeighted:
                anyWeighted.add(algorithm);
                break;
        }

        if (directedType == DirectedType.both && edgeWeight == EdgeWeight.any)
            all.add(algorithm);
	}
	
	public void enableSpecifiedAlgorithms (boolean directed, int egdeWeight)
	{
		if (directed)
			setEnabled(this.directed);
		else
			setEnabled(undirected);

		if (egdeWeight == 0)
		{
			unsetEnabled(weighted);
			unsetEnabled(doubleWeighted);
            unsetEnabled(anyWeighted);
			setEnabled(unweighted);
		}
		if (egdeWeight == 1)
		{
			unsetEnabled(unweighted);
			unsetEnabled(doubleWeighted);
			setEnabled(weighted);
            setEnabled(anyWeighted);
		}
		if (egdeWeight == 2)
		{
			unsetEnabled(unweighted);
			unsetEnabled(weighted);
			setEnabled(doubleWeighted);
            setEnabled(anyWeighted);
		}
		if (directed)
			unsetEnabled(undirected);
		else
			unsetEnabled(this.directed);
        setEnabled(all);
	}

	private void unsetEnabled(Set<JMenuItem> items) 
	{
		setEnabled(items, false);
	}

	private void setEnabled(Set<JMenuItem> items) 
	{
		setEnabled(items, true);
	}
	
	private void setEnabled(Set<JMenuItem> items, boolean value) 
	{
		for (JMenuItem item : items)
			item.setEnabled(value);
	}
}
