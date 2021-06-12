package GUI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CustomMouseListener implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1 && GUI.SELECTED_ROW != GUI.table.getSelectedRow()) {
            GUI.isSelected = true;
            GUI.SELECTED_ROW = GUI.table.getSelectedRow();
            GUI.setThreadViews(GUI.widgetTableModel.getThread(GUI.SELECTED_ROW).views);
            GUI.setThreadPostsCount(GUI.widgetTableModel.getThread(GUI.SELECTED_ROW).posts_count);
//            e.consume();
        }

        if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
            System.out.println("BROWSER ACTION");
//            BrowserHandler browserHandler = new BrowserHandler();
//            browserHandler.openURL(GUI.widgetTableModel.getThread(GUI.SELECTED_ROW).link);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
