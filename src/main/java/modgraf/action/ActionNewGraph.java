package modgraf.action;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import modgraf.jgrapht.Vertex;
import modgraf.jgrapht.edge.ModgrafEdge;
import modgraf.view.Editor;
import modgraf.view.properties.GeneralTab;
import modgraf.view.properties.PreferencesTab;

import org.jgrapht.Graph;

import com.mxgraph.view.mxGraph;

/**
 * Klasa odpowiada za stworzenie nowego grafu.
 * 
 * @author Daniel
 *
 * @see ActionListener
 */
public class ActionNewGraph implements ActionListener 
{
	private Editor editor;
	private Properties lang;
	private JFrame frame;
	private JComboBox<String> graphType;
	private JComboBox<Integer> edges;
	
	public ActionNewGraph(Editor e)
	{
		editor = e;
		lang = editor.getLanguage();
	}
	
	/**
	 * Metoda (w razie potrzeby) wyświetla okno z informacją o istnieniu niezapisanych 
	 * zmian, a następnie okno z parametrami nowego grafu.<br>
	 * Metoda jest wywoływana z menu <i>Plik --> Nowy</i>.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		if (!editor.isModified()
				|| JOptionPane.showConfirmDialog(editor.getGraphComponent(),
						lang.getProperty("question-changes-exist")) 
						== JOptionPane.YES_OPTION)
		{
			openParamsWindow();
		}
	}
	
	/**
	 * Metoda tworzy i otwiera okno z parametrami nowego grafu.
	 */
	private void openParamsWindow()
	{
		JPanel paramsPanel = createParamsPanel();
		JPanel buttonPanel = createButtonPanel();
		frame = new JFrame(lang.getProperty("frame-new-graph"));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setPreferredSize(new Dimension(300, 140));
		frame.add(paramsPanel, BorderLayout.CENTER);
		frame.add(buttonPanel, BorderLayout.SOUTH);
		frame.pack();
		frame.setLocationRelativeTo(editor.getGraphComponent());
		frame.setVisible(true);
	}

	/**
	 * @return Panel z parametrami nowego grafu.
	 */
	private JPanel createParamsPanel()
	{
		double size[][] =
            {{0.55, 0.05, 0.4},
             {30, 30}};
		PreferencesTab paramsPanel = new PreferencesTab(editor, size);
		GeneralTab gt = new GeneralTab(editor);
		graphType = gt.createGraphTypeComboBox();
		edges = gt.createEdgeTypeComboBox();
		paramsPanel.addLabel(0, "label-graph-type");
		paramsPanel.addLabel(1, "label-edge-type");
		paramsPanel.addComponent(0, graphType);
		paramsPanel.addComponent(1, edges);
		return paramsPanel;
	}

	/**
	 * @return Panel z przyciskiem "Nowy graf".
	 */
	private JPanel createButtonPanel()
	{
		JPanel buttonPanel = new JPanel();
		JButton create = new JButton(lang.getProperty("button-new-graph"));
		create.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				boolean directed = false;
				if (graphType.getSelectedIndex() == 1)
					directed = true;
				Integer edge = (Integer)edges.getSelectedItem();
				int edgeWeightDegree = edge.intValue();
				Graph<Vertex, ModgrafEdge> graphT = editor.createNewGraphT(directed, edgeWeightDegree);
				editor.setGraphT(graphT);
				mxGraph graph = editor.createNewMxGraph();
				editor.getGraphComponent().setGraph(graph);
				editor.enableAllMenuElements();
				editor.setTextAboutNewGraph(directed, edgeWeightDegree, false);
				editor.setCurrentFile(null);
				editor.setModified(true);
				editor.setModified(false);
				editor.getGraphComponent().refresh();
				frame.dispose();
			}
		});
		buttonPanel.add(create);
		return buttonPanel;
	}
}

