package modgraf.action;

import modgraf.view.Editor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Klasa odpowiada za zmianę ustawień edycji grafu.
 *
 * @author Daniel Pogrebniak
 *
 * @see ActionListener
 */
public class ActionChangeGraphSetting implements ActionListener {

    private final Editor editor;
    private final JCheckBoxMenuItem item;
    private final Settings type;

    public enum Settings {
        selectable,
        connectable
    }

    public ActionChangeGraphSetting(Editor e, JCheckBoxMenuItem item, Settings type) {
        editor = e;
        this.item = item;
        this.type = type;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (type) {
            case selectable:
                editor.getGraphComponent().getGraph().setCellsSelectable(item.getState());
                break;
            case connectable:
                editor.getGraphComponent().setConnectable(item.getState());
                break;
        }
    }
}
