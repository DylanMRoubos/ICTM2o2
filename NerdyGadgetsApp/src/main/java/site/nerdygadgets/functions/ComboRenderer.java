package site.nerdygadgets.functions;

import javax.swing.*;
import java.awt.*;
/**
 * ComboRenderer class
 * Rederer class for comboboxes
 *
 * @author Tristan Scholten & Jordy Wielaard
 * @version 1.0
 * @since 14-05-2020
 */
public class ComboRenderer extends JLabel implements ListCellRenderer {
    private String title;

    public ComboRenderer(String title) {
        this.title = title;
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) {
        if (index == -1 && value == null)
            setText(title);
        else
            setText(value.toString());
        return this;
    }
}