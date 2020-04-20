package GUI;

import Base.BrowserHandler;
import Base.ThreadBase;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GUI {

    public static WidgetTableModel widgetTableModel;
    public static JLabel tablePreview;
    private static boolean isSelected;
    private static int SELECTED_ROW;

    public GUI() {

        widgetTableModel = new WidgetTableModel();
        JFrame frame = new JFrame();
        tablePreview = new JLabel("No data");

        try {
            BufferedImage image = ImageIO.read(getClass().getResource("/rss.png"));
            frame.setIconImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JTable table = new JTable(widgetTableModel);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setRowHeight(30);
        table.setTableHeader(null);

        table.setDefaultRenderer(String.class, new DefaultTableCellRenderer() {
            final Color oddColor = new Color(0x25, 0x25, 0x25);
            final Color evenColor = new Color(0x1a, 0x1a, 0x1a);
            final Color titleColor = new Color(0xdf, 0x6e, 0x1d);

            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBackground(row % 2 == 0 ? oddColor : evenColor);
                setForeground(titleColor);
                setFont(new Font("Arial", Font.BOLD, 14));
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

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                boolean left = false;
                if (SwingUtilities.isRightMouseButton(e)) {
                    //do something
                } else if (SwingUtilities.isLeftMouseButton(e)) {
                    left = true;
                }

                if (!isSelected) {
                    int row = table.rowAtPoint(e.getPoint());
                    int col = table.columnAtPoint(e.getPoint());
                    System.out.println("row: "+row);
                    System.out.println("col: " + col);
                    if (row >= 0 && col >= 0) {
                        isSelected = true;
                        SELECTED_ROW = table.getSelectedRow();
                        System.out.println("selected: row#"+SELECTED_ROW);
                    }
                } else if (left) {
//                    table.getSelectionModel().clearSelection();
                    System.out.println("unselected row#"+SELECTED_ROW+"--->selected row#"+table.getSelectedRow());
                    SELECTED_ROW = table.getSelectedRow();
                    isSelected=true;
                }
            }
        });

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    try {
                        BrowserHandler browserHandler = new BrowserHandler();
                        browserHandler.openURL(widgetTableModel.getThread(SELECTED_ROW).link);
                    } catch (NullPointerException ignored) { }
                }
            }
        });


        frame.setSize(520, 300);
        frame.setUndecorated(true);
        frame.setOpacity(0.85f);
        frame.setLocationRelativeTo(null);
        SwingUtilities.invokeLater(() -> frame.setVisible(true));

    }

    public static void enablePreview() {
        tablePreview.setEnabled(true);
        tablePreview.updateUI();
    }

    public static void disablePreview() {
        tablePreview.setEnabled(false);
        tablePreview.updateUI();
    }

}


