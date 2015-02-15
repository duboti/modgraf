package modgraf.algorithm;

import com.mxgraph.model.mxGraphModel;
import com.mxgraph.view.mxGraph;
import layout.TableLayout;
import modgraf.jgrapht.Vertex;
import modgraf.jgrapht.edge.ModgrafEdge;
import modgraf.view.Editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

import static com.mxgraph.util.mxConstants.STYLE_FILLCOLOR;
import static com.mxgraph.util.mxConstants.STYLE_STROKEWIDTH;

/**
 * Klasa bazowa dla wszystkich algorytmów.
 * 
 * @author Daniel Pogrebniak
 * 
 * @see ActionListener
 */
public abstract class ModgrafAbstractAlgorithm implements ActionListener {
	/**
	 * To najważniejszy obiekt w programie. Dzięki niemu algorytmy mają dostęp
	 * do grafów z warstwy matematycznej i wizualnej.
	 */
	protected Editor editor;
	/**
	 * Wierzchołek startowy. Pole jest uzupełniane, gdy zostanie dokonany
	 * wybór z listy <code>startVertexComboBox</code>.
	 */
	protected Vertex startVertex;
	/**
	 * Wierzchołek końcowy. Pole jest uzupełniane, gdy zostanie dokonany
	 * wybór z listy <code>endVertexComboBox</code>.
	 */
	protected Vertex endVertex;
	/**
	 * Lista nazw istniejących wierzchołków. Wybierany jest wierzchołek
	 * startowy.
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
    /**
     * Mapa z tekstami
     */
	protected Properties lang;
    /**
     * Mapa z ustawieniami
     */
	protected Properties prop;

    /**
     * Konstruktor
     *
     * @param e edytor
     */
	protected ModgrafAbstractAlgorithm(Editor e) {
		editor = e;
		lang = editor.getLanguage();
		prop = e.getProperties();
	}

    /**
     * Wyświetla domyślnej wielkości okno z parametrami startowymi
     */
    protected void openParamsWindow() {
        Integer width = new Integer(prop.getProperty("algorithmParamsWindow-width"));
        Integer height = new Integer(prop.getProperty("algorithmParamsWindow-height"));
        openParamsWindow(new Dimension(width, height));
    }

    /**
     * Jeśli istnieją w grafie krawędzie to zostanie im ustawiona domyślna
     * grubość. Następnie ustawiona zostanie domyślna grubość obramowania
     * wierzchołków i zostanie wyświetlone okno z parametrami startowymi
     * algorytmu.
     */
	protected void openParamsWindow(Dimension paramsWindowSize) {
		if (isEdgeExists()) {
			clearBoldLines();
			editor.getGraphComponent().refresh();
			createParamsWindow(paramsWindowSize);
		}
	}

	/**
	 * Jeśli istnieją w grafie krawędzie to zostanie im ustawiona domyślna
	 * grubość. Następnie ustawiona zostanie domyślna grubość obramowania
	 * wierzchołków i zostanie uruchomiony algorytm bez pytania o parametry
	 * startowe algorytmu.
	 */
	protected void startAlgorithmWithoutParams() {
		if (isEdgeExists()) {
			clearBoldLines();
			editor.getGraphComponent().refresh();
			findAndShowResult();
		}
	}

	/**
	 * @return true jeśli graf zawiera krawędzie.
	 */
	protected boolean isEdgeExists() {
		Set<ModgrafEdge> edges = editor.getGraphT().edgeSet();
		if (edges.size() == 0) {
			JOptionPane.showMessageDialog(editor.getGraphComponent(),
					lang.getProperty("warning-missing-edges"),
					lang.getProperty("warning"), JOptionPane.WARNING_MESSAGE);
			return false;
		} else
			return true;
	}

	/**
	 * Metoda ustawia domyślną grubość dla wszystkich krawędzi.
	 */
	protected void clearBoldEdges() {
		mxGraph graph = editor.getGraphComponent().getGraph();
		graph.selectEdges();
		graph.setCellStyles(STYLE_STROKEWIDTH, null);
		graph.clearSelection();
	}

	/**
	 * Metoda ustawia domyślną grubość obramowania wszystkim wierzchołkom.
	 */
	protected void clearBoldVertex() {
		mxGraph graph = editor.getGraphComponent().getGraph();
		graph.selectVertices();
		graph.setCellStyles(STYLE_STROKEWIDTH, null);
		graph.clearSelection();
	}

    /**
     * Metoda ustawia domyślną grubość dla wszystkich krawędzi i wierzchołków.
     */
	protected void clearBoldLines() {
		mxGraph graph = editor.getGraphComponent().getGraph();
		graph.selectAll();
		graph.setCellStyles(STYLE_STROKEWIDTH, null);
		graph.clearSelection();
	}

    /**
     * Metoda ustawia kolor wypełnienia dla wierzchołka o podanym id.
     *
     * @param vertexId id wierzchołka
     * @param color kolor
     */
	protected void changeVertexFillColor(String vertexId, String color) {
        setCellStyle(vertexId, STYLE_FILLCOLOR, color);
	}

    /**
     * Metoda ustawia dla obiektu o podanym id podaną wartość podanego stylu.
     *
     * @param id id obiektu
     * @param style nazwa stylu
     * @param value wartość stylu
     */
	protected void setCellStyle(String id, String style, String value) {
		mxGraph graph = editor.getGraphComponent().getGraph();
		mxGraphModel model = (mxGraphModel) graph.getModel();
		graph.setCellStyles(style, value, new Object[] { model.getCell(id) });
	}

	/**
	 * Metoda ustawia krawędzi <code>edge</code> grubość <code>width</code>.
	 * 
	 * @param edge krawędź
	 * @param width grubość
	 */
	protected void changeEdgeStrokeWidth(ModgrafEdge edge, Integer width) {
        setCellStyle(edge.getId(), STYLE_STROKEWIDTH, width.toString());
	}


	/**
	 * Metoda znajduje krawędź między wierzchołkami o id <code>source</code> i
	 * <code>target</code>, a następnie ustawia jej grubość <code>width</code>.
	 * 
	 * @param source wierzchołek startowy
	 * @param target wierzchołek końcowy
	 * @param width grubość
	 */
	protected void changeEdgeStrokeWidth(Vertex source, Vertex target, Integer width) {
		String edgeId = editor.getEdgeId(source.getId(), target.getId());
        setCellStyle(edgeId, STYLE_STROKEWIDTH, width.toString());
	}

	/**
	 * Metoda ustawia wierzchołkowi o id <code>vertexId</code> grubość
	 * obramowania <code>width</code>.
	 * 
	 * @param vertex wierzchołek
	 * @param width grubość obramowania
	 */
	protected void changeVertexStrokeWidth(Vertex vertex, Integer width) {
        setCellStyle(vertex.getId(), STYLE_STROKEWIDTH, width.toString());
	}

	/**
	 * Metoda tworzy okno z parametrami startowymi algorytmu.
	 */
	protected void createParamsWindow(Dimension paramsWindowSize) {
		JPanel paramsPanel = createParamsPanel();
        JPanel buttonPanel = createButtonPanel();
		frame = new JFrame(lang.getProperty("frame-algorithm-params"));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setPreferredSize(paramsWindowSize);
        frame.add(paramsPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
		frame.pack();
		frame.setLocationRelativeTo(editor.getGraphComponent());
		frame.setVisible(true);
	}

	/**
	 * @return panel zawierający dwie listy wierzchołków
	 */
    protected JPanel createParamsPanel() {
        double size[][] ={{0.47, 0.06, 0.47},{30, 30}};
        JPanel paramsPanel = new JPanel();
        paramsPanel.setLayout(new TableLayout(size));
        Vector<Vertex> vertexVector = new Vector<>(editor.getGraphT().vertexSet());
        startVertexComboBox = new JComboBox<>(vertexVector);
        endVertexComboBox = new JComboBox<>(vertexVector);
        paramsPanel.add(new JLabel(lang.getProperty("label-start-vertex")), "0 0 r c");
        paramsPanel.add(startVertexComboBox, "2 0 l c");
        paramsPanel.add(new JLabel(lang.getProperty("label-end-vertex")), "0 1 r c");
        paramsPanel.add(endVertexComboBox, "2 1 l c");
        return paramsPanel;
    }

    /**
     * @return panel z przyciskiem uruchamiającym algorytm
     */
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        JButton start = new JButton(lang.getProperty("button-run-algorithm"));
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                startActionButton();
            }
        });
        buttonPanel.add(start);
        return buttonPanel;
    }

	/**
	 * Metoda inicjuje pola <code>startVertex</code> i <code>endVertex</code>, a
	 * następnie wywołuje <code>findAndShowResult()</code>.
	 */
	protected void startActionButton() {
		startVertex = (Vertex) startVertexComboBox.getSelectedItem();
		endVertex = (Vertex) endVertexComboBox.getSelectedItem();
		if (startVertex.equals(endVertex)) {
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
	 * @return nazwa widoczna w menu <i>Algorytmy</i>.
	 */
	public abstract String getName();
}
