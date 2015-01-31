package modgraf.view;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JMenuItem;

public class AlgorithmMenuItems 
{
	private Set<JMenuItem> directed;
	private Set<JMenuItem> undirected;
	private Set<JMenuItem> unweighted;
	private Set<JMenuItem> weighted;
	private Set<JMenuItem> doubleWeighted;
	private Set<JMenuItem> all;

	public AlgorithmMenuItems() 
	{
		directed = new HashSet<>();
		undirected = new HashSet<>();
		unweighted = new HashSet<>();
		weighted = new HashSet<>();
		doubleWeighted = new HashSet<>();
		all = new HashSet<>();
	}
	
	/**
	 * 
	 * @param algorithm
	 * @param directedType 0=directed, 1=undirected, 2=directed or undirected
	 * @param egdeWeight 0=unweighted, 1=weighted, 2=doubleWeighted, 3=all
	 */
	public void addAlgorithm(JMenuItem algorithm, int directedType, int egdeWeight)
	{
		if (directedType == 0)
			directed.add(algorithm);
		if (directedType == 1)
			undirected.add(algorithm);

		if (egdeWeight == 0)
			unweighted.add(algorithm);
		if (egdeWeight == 1)
			weighted.add(algorithm);
		if (egdeWeight == 2)
			doubleWeighted.add(algorithm);

        if (directedType == 2 && egdeWeight == 3)
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
			setEnabled(unweighted);
		}
		if (egdeWeight == 1)
		{
			unsetEnabled(unweighted);
			unsetEnabled(doubleWeighted);
			setEnabled(weighted);
		}
		if (egdeWeight == 2)
		{
			unsetEnabled(unweighted);
			unsetEnabled(weighted);
			setEnabled(doubleWeighted);
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
