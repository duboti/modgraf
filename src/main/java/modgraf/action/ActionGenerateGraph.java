package modgraf.action;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

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
import org.jgrapht.generate.CompleteGraphGenerator;
import org.jgrapht.generate.RandomGraphGenerator;

public class ActionGenerateGraph implements ActionListener
{
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
		COMPLETE, RANDOM, RING, SCALE_FREE, COMPLETE_BIPARTITE; //RANDOM_FLOW;
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
		int number = 0;
		try 
		{
			number = new Integer(input);
		}
		catch(NumberFormatException e)
		{
			JOptionPane.showMessageDialog(editor.getGraphComponent(),
					lang.getProperty("warning-not-proper-number")+
					lang.getProperty("from")+" 2 "+
					lang.getProperty("to")+" 50!", 
					lang.getProperty("warning"), JOptionPane.WARNING_MESSAGE);
			return;
		}
		CompleteGraphGenerator<Vertex, ModgrafEdge> generator = new CompleteGraphGenerator<>(number);
//		RandomGraphGenerator<Vertex, ModgrafEdge> generator2 = new RandomGraphGenerator<>(aNumOfVertexes, aNumOfEdges);
		boolean directed = false;
		if (graphType.getSelectedIndex() == 1)
			directed = true;
		Integer edge = (Integer)edges.getSelectedItem();
		int edgeWeightDegree = edge.intValue();
		Graph<Vertex, ModgrafEdge> target = editor.createNewGraphT(directed, edgeWeightDegree);
		ModgrafVertexFactory factory = new ModgrafVertexFactory();
		generator.generateGraph(target, factory, null);
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
             {30, 30, 30}};
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
		return paramsPanel;
	}

	private JFormattedTextField createNumOfVerticesField(PreferencesTab pt)
	{
		numOfVerticesField = new JFormattedTextField(
				pt.createNumberFormatter(2, 50));
		numOfVerticesField.setToolTipText(
				pt.createHint(1, 50));
		numOfVerticesField.setColumns(PreferencesTab.FIELD_SIZE);
		return numOfVerticesField;
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
