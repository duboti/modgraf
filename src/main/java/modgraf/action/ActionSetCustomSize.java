package modgraf.action;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.naming.directory.InvalidAttributeValueException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import modgraf.view.Editor;

/**
 * Klasa odpowiada za ustawienie dowolnego rozmiaru wierzchołka.
 * 
 * @author Daniel Pogrebniak
 *
 * @see ActionListener
 */
public class ActionSetCustomSize implements ActionListener
{
	private Editor editor;
	private JTextField height;
	private JTextField width;
	private JFrame frame;
	private Properties lang;
	
	public ActionSetCustomSize(Editor e)
	{
		editor = e;
		lang = editor.getLanguage();
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		openParamsWindow();
	}

	private void openParamsWindow()
	{
		JPanel paramsPanel = createParamsPanel();
		frame = new JFrame(lang.getProperty("frame-vartex-params"));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setPreferredSize(new Dimension(190, 150));
		frame.add(paramsPanel);
		frame.pack();
		frame.setLocationRelativeTo(editor.getGraphComponent());
		frame.setVisible(true);
	}

	private JPanel createParamsPanel()
	{
		JPanel paramsPanel = new JPanel();
		paramsPanel.add(new JLabel(lang.getProperty("message-write-numbers")));//TODO from 0 to 1000
		paramsPanel.add(new JLabel(lang.getProperty("label-height")));
		Dimension dimension = new Dimension(80, 20);
		height = new JTextField("50");
		height.setPreferredSize(dimension);
		paramsPanel.add(height);
		paramsPanel.add(new JLabel(lang.getProperty("label-width")));
		width = new JTextField("50");
		width.setPreferredSize(dimension);
		paramsPanel.add(width);
		JButton button = new JButton(lang.getProperty("button-change-size"));
		button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					int h = Integer.parseInt(height.getText());
					if (h < 0 || h > 1000)
						throw new InvalidAttributeValueException();
                    int w = Integer.parseInt(width.getText());
					if (w < 0 || w > 1000)
						throw new InvalidAttributeValueException();
					new ActionSetSize(editor, h, w).actionPerformed(null);
					frame.dispose();
				}
				catch(NumberFormatException e1)
				{
					JOptionPane.showMessageDialog(editor.getGraphComponent(),
							lang.getProperty("warning-not-number"),
							lang.getProperty("warning"), JOptionPane.WARNING_MESSAGE);
				} 
				catch (InvalidAttributeValueException e1)
				{
					JOptionPane.showMessageDialog(editor.getGraphComponent(),
							lang.getProperty("warning-not-proper-number"), //TODO from 0 to 1000
							lang.getProperty("warning"), JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		paramsPanel.add(button);
		return paramsPanel;
	}

}
