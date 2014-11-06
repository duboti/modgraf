package modgraf.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;

import modgraf.view.Editor;

/**
 * Klasa odpowiada za zapisanie grafu do aktualnie otwartego pliku.
 * 
 * @author Daniel Pogrebniak
 *
 * @see ActionListener
 */
public class ActionSave implements ActionListener, WindowListener
{
	private Editor editor;
	
	public ActionSave(Editor e)
	{
		editor = e;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		if (editor.isModified())
		{
			saveFile();
		}
	}

	private void saveFile()
	{
		if (editor.getCurrentFile() == null)
			new ActionSaveAs(editor).saveFileAs();
		else
			new ActionSaveAs(editor).saveFile(editor.getCurrentFile().getAbsolutePath());
	}

	@Override
	public void windowActivated(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0)
	{
		if (editor.isModified())
		{
			int save = JOptionPane.showConfirmDialog(editor.getGraphComponent(),
					editor.getLanguage().getProperty("question-save-before-exit"));
			if (save == JOptionPane.YES_OPTION)
			{
				saveFile();
				System.exit(0);
			}
			if (save == JOptionPane.NO_OPTION)
			{
				System.exit(0);
			}
		}
		else
			System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
}
