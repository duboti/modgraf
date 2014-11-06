package modgraf.algorithm;

import static com.mxgraph.util.mxConstants.STYLE_STROKEWIDTH;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import modgraf.jgrapht.Vertex;
import modgraf.jgrapht.edge.ModgrafEdge;
import modgraf.view.Editor;

import org.jgrapht.Graph;

import com.mxgraph.model.mxGraphModel;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

/**
 * Klasa bazowa dla wszystkich algorytmów.
 * 
 * @author Daniel Pogrebniak
 * 
 * @see ActionListener
 *
 */
public abstract class ModgrafAbstractAlgorithm implements ActionListener
{
	/**
	 * To najważniejszy obiekt w programie. Dzięki niemu 
	 * algorytmy mają dostęp do grafów z warstwy matematycznej i wizualnej.
	 */
	protected Editor editor;
	/**
	 * Id wierzchołka startowego. Pole jest uzupełniane, gdy zostanie 
	 * dokonany wybór z listy <code>startVertexComboBox</code>.
	 */
	protected Vertex startVertex;
	/**
	 * Id wierzchołka końcowego. Pole jest uzupełniane, gdy zostanie 
	 * dokonany wybór z listy <code>endVertexComboBox</code>.
	 */
	protected Vertex endVertex;
	/**
	 * Lista nazw istniejących wierzchołków. Wybierany jest wierzchołek startowy.
	 */
	protected JComboBox<Vertex> startVertexComboBox;
	/**
	 * Lista nazw istniejących wierzchołków. Wybierany jest wierzchołek końcowy.
	 */
	protected JComboBox<Vertex> endVertexComboBox;
	/**
	 * Okno z parametrami startowymi algorytmu.
	 */
	protected JFrame frame;
	protected Properties lang;
	protected Properties prop;
	
	protected ModgrafAbstractAlgorithm(Editor e)
	{
		editor = e;
		lang = editor.getLanguage();
		prop = e.getProperties();
	}
	
	/**
	 * Jeśli istnieją w grafie krawędzie to zostanie im ustawiona domyślna grubość. 
	 * Następnie ustawiona zostanie domyślna grubość obramowania wierzchołków i 
	 * zostanie wyświetlone okno z parametrami startowymi algorytmu.
	 */
	protected void openParamsWindow()
	{
		if (isEdgeExists())
		{
			clearBoldLines();
			editor.getGraphComponent().refresh();
			createParamsWindow();
		}
	}
	
	/**
	 * Jeśli istnieją w grafie krawędzie to zostanie im ustawiona domyślna grubość. 
	 * Następnie ustawiona zostanie domyślna grubość obramowania wierzchołków i 
	 * zostanie uruchomiony algorytm bez pytania o parametry startowe algorytmu.
	 */
	protected void startAlgorithmWithoutParams()
	{
		if (isEdgeExists())
		{
			clearBoldLines();
			editor.getGraphComponent().refresh();
			findAndShowResult();
		}
	}
	
	/**
	 * @return true jeśli graf zawiera krawędzie.
	 */
	protected boolean isEdgeExists()
	{
		Set<ModgrafEdge> edges = editor.getGraphT().edgeSet();
		if (edges.size() == 0)
		{
			JOptionPane.showMessageDialog(editor.getGraphComponent(),
					lang.getProperty("warning-missing-edges"),
					lang.getProperty("warning"), JOptionPane.WARNING_MESSAGE);
			return false;
		}
		else
			return true;
	}
	
	/**
	 * Metoda ustawia domyślną grubość dla wszystkich krawędzi.
	 */
	protected void clearBoldEdges()
	{
		mxGraph graph = editor.getGraphComponent().getGraph();
		graph.selectEdges();
		graph.setCellStyles(STYLE_STROKEWIDTH, null);
		graph.clearSelection();
	}
	
	/**
	 * Metoda ustawia domyślną grubość obramowania wszystkim wierzchołkom.
	 */
	protected void clearBoldVertex()
	{
		mxGraph graph = editor.getGraphComponent().getGraph();
		graph.selectVertices();
		graph.setCellStyles(STYLE_STROKEWIDTH, null);
		graph.clearSelection();
	}

	protected void clearBoldLines()
	{
		mxGraph graph = editor.getGraphComponent().getGraph();
		graph.selectAll();
		graph.setCellStyles(STYLE_STROKEWIDTH, null);
		graph.clearSelection();
	}
	
	protected void changeVertexFillColor(String vertexId, String color)
	{
		mxGraph graph = editor.getGraphComponent().getGraph();
		mxGraphModel model = (mxGraphModel)graph.getModel();
		graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, color,
				new Object[] { model.getCell(vertexId) });
	}
	
	protected void setCellStyle(String id, String style, String value)
	{
		mxGraph graph = editor.getGraphComponent().getGraph();
		mxGraphModel model = (mxGraphModel)graph.getModel();
		graph.setCellStyles(style, value,
				new Object[] { model.getCell(id) });
	}
	
	/**
	 * Metoda ustawia krawędzi <code>edge</code> grubość <code>width</code>.
	 * 
	 * @param edge
	 * @param width
	 */
	protected void changeEdgeStrokeWidth(ModgrafEdge edge, int width)
	{
		Graph<Vertex, ModgrafEdge> graphT = editor.getGraphT();
		Vertex source = graphT.getEdgeSource(edge);
		Vertex target = graphT.getEdgeTarget(edge);
		changeEdgeStrokeWidth(source, target, width);
	}
	
	/**
	 * Metoda znajduje krawędź między wierzchołkami o id <code>source</code> i 
	 * <code>target</code>, a następnie ustawia jej grubość <code>width</code>.
	 * 
	 * @param source
	 * @param target
	 * @param width
	 */
	protected void changeEdgeStrokeWidth(Vertex source, Vertex target, int width)
	{
		mxGraph graph = editor.getGraphComponent().getGraph();
		mxGraphModel model = (mxGraphModel)graph.getModel();
		String edgeId = editor.getEdgeId(source.getId(), target.getId());
		graph.setCellStyles(STYLE_STROKEWIDTH, Integer.toString(width),
				new Object[] { model.getCell(edgeId) });
	}

	/**
	 * Metoda ustawia wierzchołkowi o id <code>vertexId</code> grubość obramowania 
	 * <code>width</code>.
	 * 
	 * @param vertex
	 * @param width
	 */
	protected void changeVertexStrokeWidth(Vertex vertex, int width)
	{
		mxGraph graph = editor.getGraphComponent().getGraph();
		mxGraphModel model = (mxGraphModel)graph.getModel();
		graph.setCellStyles(STYLE_STROKEWIDTH, Integer.toString(width),
				new Object[] { model.getCell(vertex.getId()) });
	}
	
	/**
	 * Metoda tworzy okno z parametrami startowymi algorytmu.
	 */
	protected void createParamsWindow()
	{
		JPanel paramsPanel = createParamsPanel();
		frame = new JFrame(lang.getProperty("frame-algorithm-params"));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setPreferredSize(new Dimension(200, 150));
		frame.add(paramsPanel);
		frame.pack();
		frame.setLocationRelativeTo(editor.getGraphComponent());
		frame.setVisible(true);
	}
	
	/**
	 * @return panel zawierający dwie listy wierzchołków i przycisk "Uruchom algorytm".
	 */
	protected JPanel createParamsPanel()
	{
		JPanel paramsPanel = new JPanel();
		paramsPanel.setLayout(new BoxLayout(paramsPanel, BoxLayout.Y_AXIS));
		Vector<Vertex> vertexVector = new Vector<>(editor.getGraphT().vertexSet());
//		Collections.sort(vertexVector);
		startVertexComboBox = new JComboBox<>(vertexVector);
		endVertexComboBox = new JComboBox<>(vertexVector);
		paramsPanel.add(new JLabel(lang.getProperty("label-start-vertex")));
		paramsPanel.add(startVertexComboBox);
		paramsPanel.add(new JLabel(lang.getProperty("label-end-vertex")));
		paramsPanel.add(endVertexComboBox);
		JButton start = new JButton(lang.getProperty("button-run-algorithm"));
		start.addActionListener(new ActionListener(){	
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				startActionButton();
			}});
		paramsPanel.add(start);
		return paramsPanel;
	}

	/**
	 * Metoda inicjuje pola <code>startVertex</code> i <code>endVertex</code>, 
	 * a następnie wywołuje <code>findAndShowResult()</code>.
	 */
	protected void startActionButton()
	{
		startVertex=(Vertex) startVertexComboBox.getSelectedItem();
		endVertex = (Vertex) endVertexComboBox.getSelectedItem();
		if (startVertex.equals(endVertex))
		{
			frame.dispose();
			JOptionPane.showMessageDialog(editor.getGraphComponent(),
					lang.getProperty("warning-not-different-vertices"),
					lang.getProperty("warning"), JOptionPane.WARNING_MESSAGE);
			return;
		}
		frame.dispose();
		findAndShowResult();
	}
	
	/**
	 * Metoda abstrakcyjna, która powinna znajdować rozwiązanie i prezentować je 
	 * zarówno graficznie jak i tekstowo.
	 */
	protected abstract void findAndShowResult();
	
	/**
	 * @return nazwę widoczna w menu <i>Algorytmy</i>.
	 */
	public abstract String getName();
}
