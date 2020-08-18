package сom.company.GUI;

import java.awt.*;
import javax.swing.*;

/**
 * Класс,который отвечает за визуальные эффекты
 */
public class ComboRenderer extends DefaultListCellRenderer {
    private ListCellRenderer defaultRenderer;
    
    public ComboRenderer(ListCellRenderer defaultRenderer) {
        this.defaultRenderer = defaultRenderer;
    }
    
    @Override
    public Component getListCellRendererComponent(JList list, Object value,
        int index, boolean isSelected, boolean cellHasFocus) {
        Component c = defaultRenderer.getListCellRendererComponent(list, value,
            index, isSelected, cellHasFocus);
        if (c instanceof JLabel) {
        if (isSelected) {
            if(MainForm.getNightMare())
                c.setBackground(ImageMaster.violet);
            else
                c.setBackground(ImageMaster.darkRed);
            c.setForeground(Color.white);
        } else {
            if(MainForm.getNightMare())
                c.setBackground(Color.black);
            else
                c.setBackground(ImageMaster.darkestGray);
        }
        } else {
            if(MainForm.getNightMare())
                c.setBackground(Color.black);
            else
                c.setBackground(ImageMaster.darkestGray);
        c = super.getListCellRendererComponent(list, value, index, isSelected,
            cellHasFocus);
        }
        return c;
    }
}
