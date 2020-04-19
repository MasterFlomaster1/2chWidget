package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.Date;

public class GUI {


    public GUI() {

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JTable table = new JTable(new WidgetTableModel());
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setRowHeight(30);
        table.setTableHeader(null);

        table.setDefaultRenderer(String.class, new DefaultTableCellRenderer() {
            final Color oddColor = new Color(0x25, 0x25, 0x25);
            final Color evenColor = new Color(0x1a, 0x1a, 0x1a);
            final Color titleColor = new Color(0x3a, 0xa2, 0xd7);

            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBackground(row % 2 == 0 ? oddColor : evenColor);
                setForeground(titleColor);
//                setFont(font);
                return this;
            }
        });

        table.setBackground(new Color(0x1a, 0x1a, 0x1a));


        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
//        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("2ch RSS");
        Font titleFont = new Font("Arial", Font.BOLD, 20);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(new Color(0x1a, 0x1a, 0x1a));
        titleLabel.setPreferredSize(new Dimension(0, 40));
        titleLabel.setFont(titleFont);
        frame.getContentPane().add(titleLabel, BorderLayout.NORTH);

        MouseAdapter listener = new MouseAdapter() {
            int startX;
            int startY;

            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    startX = e.getX();
                    startY = e.getY();
                }
            }

            public void mouseDragged(MouseEvent e) {
                Point cursorCoordinates = e.getLocationOnScreen();
                frame.setLocation(cursorCoordinates.x - startX, cursorCoordinates.y - startY);
            }
        };
        titleLabel.addMouseListener(listener);
        titleLabel.addMouseMotionListener(listener);

        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                frame.setShape(new RoundRectangle2D.Double(0, 0, frame.getWidth(), frame.getHeight(), 20, 20));
            }
        });

        frame.setSize(520, 300);
        frame.setUndecorated(true);
        frame.setOpacity(0.85f);
        frame.setLocationRelativeTo(null);

        SwingUtilities.invokeLater(() -> frame.setVisible(true));

    }

}


