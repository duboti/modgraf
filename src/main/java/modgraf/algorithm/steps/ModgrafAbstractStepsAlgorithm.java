package modgraf.algorithm.steps;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import modgraf.algorithm.ModgrafAbstractAlgorithm;
import modgraf.jgrapht.Vertex;
import modgraf.view.Editor;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;

public abstract class ModgrafAbstractStepsAlgorithm extends ModgrafAbstractAlgorithm
{
	private JButton pause;
	private JButton play;
	private JButton fastForward;
	private State currentState;
	private JButton clear;
	private boolean useClassLoader;

	protected enum State
	{
		Stop,
		Run,
		End;
	}
	
	protected ModgrafAbstractStepsAlgorithm(Editor e)
	{
		super(e);
		currentState = State.Stop;
		String useClassLoaderString = e.getProperties().getProperty("use-class-loader");
		useClassLoader = Boolean.parseBoolean(useClassLoaderString);
	}

	@Override
	protected void findAndShowResult()
	{
		createStepsWindow();
	}
	
	private void createStepsWindow()
	{
		JPanel stepsPanel = createStepsPanel();
		frame = new JFrame(lang.getProperty("frame-algorithm-steps"));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setPreferredSize(editor.createDimensionFromProperty(
				"frame-algorithm-steps-width", "frame-algorithm-steps-height"));
		frame.add(stepsPanel);
		frame.pack();
		frame.setLocationRelativeTo(editor.getGraphComponent());
		frame.setVisible(true);
	}

	private JPanel createStepsPanel()
	{
		JPanel stepsPanel = new JPanel();
		stepsPanel.setLayout(new BoxLayout(stepsPanel, BoxLayout.X_AXIS));
		pause = new JButton();
		play = new JButton();
		fastForward = new JButton();
		clear = new JButton(lang.getProperty("button-disable-show-distances"));
		addIcon(pause, "icons/pause-48.png");
		addIcon(play, "icons/play-48.png");
		addIcon(fastForward, "icons/fast_forward-48.png");
		addActionListeners();
		changeState(State.Stop);
		stepsPanel.add(pause);
		stepsPanel.add(play);
		stepsPanel.add(fastForward);
		stepsPanel.add(clear);
		return stepsPanel;
	}

	private void addIcon(JButton button, String icon)
	{
		if (useClassLoader)
			button.setIcon(new ImageIcon(getClass().getClassLoader().getResource(icon)));
		else
			button.setIcon(new ImageIcon(icon));
	}

	private void addActionListeners()
	{
		final FastForwardThread fastForwardThread = new FastForwardThread();
		play.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				runOneStep();
				editor.getGraphComponent().refresh();
			}
		});
		fastForward.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				(new Thread(fastForwardThread)).start();
			}
		});
		pause.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				changeState(State.Stop);
			}
		});
		clear.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				disableShowDistances();
				editor.getGraphComponent().refresh();
			}
		});
	}

	protected void changeState(State state)
	{
		if (state == State.Run)
		{
			pause.setEnabled(true);
			play.setEnabled(false);
			fastForward.setEnabled(false);
			clear.setEnabled(false);
			currentState = State.Run;
		}
		if (state == State.Stop)
		{
			pause.setEnabled(false);
			play.setEnabled(true);
			fastForward.setEnabled(true);
			clear.setEnabled(true);
			currentState = State.Stop;
		}
		if (state == State.End)
		{
			pause.setEnabled(false);
			play.setEnabled(false);
			fastForward.setEnabled(false);
			clear.setEnabled(true);
			currentState = State.End;
		}
	}
	
	protected void disableShowDistances() 
	{
		mxGraphModel model = (mxGraphModel) editor.getGraphComponent().getGraph().getModel();
		for(Vertex vertex : editor.getGraphT().vertexSet())
		{
			mxCell cell = (mxCell) model.getCell(vertex.getId());
			cell.setValue(vertex.getName());
		}
	}

	protected abstract void runOneStep(); 
	
	class FastForwardThread implements Runnable
	{

		@Override
		public void run()
		{
			changeState(State.Run);
			while (currentState == State.Run)
			{
				runOneStep();
				editor.getGraphComponent().refresh();
				try
				{
					Long time = new Long(prop.getProperty("step-sleep-time-ms"));
					Thread.sleep(time);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
}
