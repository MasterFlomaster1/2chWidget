package GUI;

import Base.ThreadBase;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class WidgetTableModel extends AbstractTableModel {

    private List<ThreadBase> threadsList = new ArrayList<>();

    @Override
    public int getRowCount() {
        return threadsList.size();
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return threadsList.get(rowIndex).subject;
        }
        return null;
    }

    public void updateData(List<ThreadBase> newThreads) {
        this.threadsList = newThreads;
        //Request listeners to update table data
        fireTableDataChanged();
    }

    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
        }
        return Object.class;
    }


}
