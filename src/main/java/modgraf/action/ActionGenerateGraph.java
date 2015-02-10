package modgraf.action;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import java.util.Set;

import javax.swing.*;

import modgraf.jgrapht.DoubleWeightedGraph;
import modgraf.jgrapht.ModgrafVertexFactory;
import modgraf.jgrapht.Vertex;
import modgraf.jgrapht.edge.ModgrafEdge;
import modgraf.view.Editor;
import modgraf.view.properties.GeneralTab;
import modgraf.view.properties.PreferencesTab;

import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.generate.*;

/**
 * Klasa odpowiada za wygenerowanie grafu o zadanych parametrach.
 *
 * @author Daniel Pogrebniak
 *
 * @see ActionListener
 */
public class ActionGenerateGraph implements ActionListener
{
    public static final int MAX_NUMBER_OF_EDGES = 999;
    public static final int MIN_NUMBER_OF_EDGES = 0;
    public static final int MAX_NUMBER_OF_VERTICES = 50;
    public static final int MIN_NUMBER_OF_VERTICES = 2;
    private Editor editor;
	private Properties lang;
	private JFrame frame;
	private JComboBox<String> graphType;
	private JComboBox<Integer> edges;
	private JFormattedTextField numOfVerticesField;
	private JFormattedTextField numOfEdgesField;
	private Type type;
	
	public enum Type
	{
		COMPLETE, RANDOM, RING, SCALE_FREE, COMPLETE_BIPARTITE //,RANDOM_FLOW
	}
	
	public ActionGenerateGraph(Editor e, Type t)
	{
		editor = e;
		type = t;
		lang = editor.getLanguage();
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		openParamsWindow();
	}
	
	private void generate()
	{
		String input = numOfVerticesField.getText();
		if (input == null)
			return;
		int numberOfVertices;
		int numberOfEdges = 0;
		try
		{
			numberOfVertices = Integer.valueOf(input);
		}
		catch(NumberFormatException e)
		{
			JOptionPane.showMessageDialog(editor.getGraphComponent(),
					lang.getProperty("warning-not-proper-number")+
					lang.getProperty("from")+" "+MIN_NUMBER_OF_VERTICES+" "+
					lang.getProperty("to")+" "+MAX_NUMBER_OF_VERTICES+"! ",
					lang.getProperty("warning"), JOptionPane.WARNING_MESSAGE);
			return;
		}
        try
        {
            if (numOfEdgesField != null)
                numberOfEdges = Integer.valueOf(numOfEdgesField.getText());
        }
        catch(NumberFormatException e)
        {
            JOptionPane.showMessageDialog(editor.getGraphComponent(),
                    lang.getProperty("warning-not-proper-number")+
                    lang.getProperty("from")+" "+MIN_NUMBER_OF_EDGES+" "+
                    lang.getProperty("to")+" "+MAX_NUMBER_OF_EDGES+"! ",
                    lang.getProperty("warning"), JOptionPane.WARNING_MESSAGE);
            return;
        }
        GraphGenerator<Vertex, ModgrafEdge, Vertex> generator;
        switch (type)
        {
            case COMPLETE:
                generator = new CompleteGraphGenerator<>(numberOfVertices);
                break;
            case RANDOM:
                generator = new RandomGraphGenerator<>(numberOfVertices, numberOfEdges);
                break;
            case RING:
                generator = new RingGraphGenerator<>(numberOfVertices);
                break;
            case SCALE_FREE:
                generator = new ScaleFreeGraphGenerator<>(numberOfVertices);
                break;
            case COMPLETE_BIPARTITE:
                generator = new CompleteBipartiteGraphGenerator<>(numberOfVertices, numberOfEdges);
                break;
            default:
                return;
        }
		boolean directed = false;
		if (graphType.getSelectedIndex() == 1)
			directed = true;
		Integer edgeWeightDegree = (Integer)edges.getSelectedItem();
		Graph<Vertex, ModgrafEdge> target = editor.createNewGraphT(directed, edgeWeightDegree);
		ModgrafVertexFactory factory = new ModgrafVertexFactory();
        try
        {
            generator.generateGraph(target, factory, null);
        }
        catch (IllegalArgumentException e)
        {
            JOptionPane.showMessageDialog(editor.getGraphComponent(),
                    lang.getProperty("warning-not-generate"),
                    lang.getProperty("warning"), JOptionPane.WARNING_MESSAGE);
            return;
        }
		String grfFile = saveGrf(target);
		ActionOpenGrf actionOpen = new ActionOpenGrf(editor);
		actionOpen.createGraphFromGrfFile(grfFile);
		actionOpen.setMxGeometryOnCircle();
	}

	private void openParamsWindow()
	{
		JPanel paramsPanel = createParamsPanel();
		JPanel buttonPanel = createButtonPanel();
		frame = new JFrame(lang.getProperty("frame-generate-graph"));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setPreferredSize(new Dimension(300, 200));
		frame.add(paramsPanel, BorderLayout.CENTER);
		frame.add(buttonPanel, BorderLayout.SOUTH);
		frame.pack();
		frame.setLocationRelativeTo(editor.getGraphComponent());
		frame.setVisible(true);
	}
	
	private JPanel createParamsPanel()
	{
		double size[][] =
            {{0.55, 0.05, 0.4},
             {30, 30, 30, 30}};
		PreferencesTab paramsPanel = new PreferencesTab(editor, size);
		GeneralTab gt = new GeneralTab(editor);
		graphType = gt.createGraphTypeComboBox();
		edges = gt.createEdgeTypeComboBox();
		paramsPanel.addLabel(0, "label-graph-type");
		paramsPanel.addLabel(1, "label-edge-type");
		paramsPanel.addLabel(2, "label-number-of-vertices");
		paramsPanel.addComponent(0, graphType);
		paramsPanel.addComponent(1, edges);
		paramsPanel.addComponent(2, createNumOfVerticesField(paramsPanel));
        if (type == Type.RANDOM)
        {
            paramsPanel.addLabel(3, "label-number-of-edges");
            paramsPanel.addComponent(3, createNumOfEdgesField(paramsPanel));
        }
        if (type == Type.COMPLETE_BIPARTITE)
        {
            paramsPanel.addLabel(3, "label-number-of-vertices");
            paramsPanel.addComponent(3, createNumOfEdgesField(paramsPanel));
        }
		return paramsPanel;
	}

    private JFormattedTextField createNumOfVerticesField(PreferencesTab pt)
	{
		numOfVerticesField = new JFormattedTextField(
				pt.createNumberFormatter(MIN_NUMBER_OF_VERTICES, MAX_NUMBER_OF_VERTICES));
		numOfVerticesField.setToolTipText(
				pt.createHint(MIN_NUMBER_OF_VERTICES, MAX_NUMBER_OF_VERTICES));
		numOfVerticesField.setColumns(PreferencesTab.FIELD_SIZE);
		return numOfVerticesField;
	}

    private JFormattedTextField createNumOfEdgesField(PreferencesTab pt)
    {
        numOfEdgesField = new JFormattedTextField(pt.createNumberFormatter(MIN_NUMBER_OF_EDGES, MAX_NUMBER_OF_EDGES));
        numOfEdgesField.setToolTipText(pt.createHint(MIN_NUMBER_OF_EDGES, MAX_NUMBER_OF_EDGES));
        numOfEdgesField.setColumns(PreferencesTab.FIELD_SIZE);
        return numOfEdgesField;
    }

	private JPanel createButtonPanel()
	{
		JPanel buttonPanel = new JPanel();
		JButton create = new JButton(lang.getProperty("button-new-graph"));
		create.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				frame.dispose();
				//TODO tworzenie grafu w oddzielnym wątku lub komunikat, że może to długo potrwać
				generate();
				editor.setCurrentFile(null);
				editor.setModified(false);
				editor.enableAllMenuElements();
			}
		});
		buttonPanel.add(create);
		return buttonPanel;
	}
	
	private String saveGrf(Graph<Vertex, ModgrafEdge> graphT)
	{
		String newLine = "\n";
		StringBuilder grf = createGrfHeader(graphT, newLine);
		createGrfEdges(graphT, grf, newLine);
		return grf.toString();
	}

	private StringBuilder createGrfHeader(Graph<Vertex, ModgrafEdge> graphT, String newLine)
	{
		StringBuilder grf = new StringBuilder();
		grf.append(newLine);
		grf.append(newLine);
		String type = null;
		if (graphT instanceof DirectedGraph)
			type = "skierowany";
		if (graphT instanceof UndirectedGraph)
			type = "nieskierowany";
		grf.append(type);
		grf.append(newLine);
		grf.append(newLine);
		return grf;
	}

	private void createGrfEdges(Graph<Vertex, ModgrafEdge> graphT,
			StringBuilder grf, String newLine)
	{
		Set<ModgrafEdge> egdeSet = graphT.edgeSet();
		for (ModgrafEdge edge : egdeSet)
		{
			grf.append(graphT.getEdgeSource(edge));
			grf.append("\t");
			grf.append(graphT.getEdgeTarget(edge));
			grf.append("\t");
			if (graphT instanceof WeightedGraph)
				grf.append(graphT.getEdgeWeight(edge));
			if (graphT instanceof DoubleWeightedGraph)
			{
				DoubleWeightedGraph<Vertex, ModgrafEdge> dwGraphT = (DoubleWeightedGraph<Vertex, ModgrafEdge>) graphT;
				grf.append(dwGraphT.getEdgeCapacity(edge));
				grf.append("\t");
				grf.append(dwGraphT.getEdgeCost(edge));
			}
			grf.append(newLine);
		}
		grf.append(newLine);
	}
}
