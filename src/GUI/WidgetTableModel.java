package GUI;

import Base.ThreadBase;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WidgetTableModel extends AbstractTableModel {

    @Override
    public int getRowCount() {
        return threadsList.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return threadsList.get(rowIndex).getSubject();
        }
        return null;
    }

    private List<ThreadBase> threadsList = new ArrayList<>();

    public void updateData(List<ThreadBase> newThreads) {
        this.threadsList = newThreads;
        //Request listeners to update table data
        fireTableDataChanged();
    }

    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return Date.class;
        }
        return Object.class;
    }


}
