package GUI;

import Base.*;
import Network.ThreadsParser;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class GUI {

    public static WidgetTableModel widgetTableModel;
    public static JPanel bottomPanel;
    public static JTable table;

    public static JLabel counterLabel;
    public static JLabel placeholderLabel;
    public static JLabel threadViewsLabel;
    public static JLabel threadPostsCountLabel;

    public static boolean isSelected;
    public static int SELECTED_ROW = -1;

    private static final Color blueColor = new Color(0x33, 0x66, 0xcc);
    private static final Color darkGreyColor = new Color(0x25, 0x25, 0x25);
    private static final Color blackColor = new Color(0x1a, 0x1a, 0x1a);
    private static final Color titleColor = new Color(0xdf, 0x6e, 0x1d);
    private static final Color selectedTitleColor = new Color(255, 153, 0, 255);

    public GUI() {

        setupLookAndFeel();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setIconImage(ResourceHandler.getApplicationIcon());

        widgetTableModel = new WidgetTableModel();
        Font bottomLabelFont = new Font("Arial", Font.BOLD, 12);

        counterLabel = new JLabel("No data");
        placeholderLabel = new JLabel("No data");
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(blackColor);
        bottomPanel.setPreferredSize(new Dimension(520, 30));
        counterLabel.setForeground(blueColor);
        counterLabel.setFont(bottomLabelFont);
        bottomPanel.add(counterLabel);
        frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        RoundButton updateThreadListButton = new RoundButton("");
        updateThreadListButton.setPreferredSize(new Dimension(26, 25));
        updateThreadListButton.setToolTipText("Update List");
        updateThreadListButton.setBackground(blackColor);
        updateThreadListButton.setBorderPainted(false);
        updateThreadListButton.setIcon(ResourceHandler.getUpdateButtonIcon());
        updateThreadListButton.setOpaque(true);
        updateThreadListButton.addActionListener(e -> {
            ThreadsParser.getJsonData();
            threadPostsCountLabel.setText("");
            threadViewsLabel.setText("");
        });

        RoundButton exitButton = new RoundButton("");
        exitButton.setPreferredSize(new Dimension(25, 25));
        exitButton.setToolTipText("Close");
        exitButton.setBackground(blackColor);
        exitButton.setBorderPainted(false);
        exitButton.setIcon(ResourceHandler.getExitButtonIcon());
        exitButton.setOpaque(true);
        exitButton.addActionListener(e -> exit());

        threadViewsLabel = new JLabel();
//        threadViewsLabel.setIcon(ResourceHandler.getViewsLabelIcon());
        threadViewsLabel.setForeground(blueColor);
        threadViewsLabel.setFont(bottomLabelFont);
        bottomPanel.add(threadViewsLabel);

        threadPostsCountLabel = new JLabel();
        threadPostsCountLabel.setForeground(blueColor);
        threadPostsCountLabel.setFont(bottomLabelFont);
        bottomPanel.add(threadPostsCountLabel);

        table = new JTable(widgetTableModel);
        table.setBackground(blackColor);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setRowHeight(30);
        table.setTableHeader(null);

        table.setDefaultRenderer(String.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBackground(row % 2 == 0 ? darkGreyColor : blackColor);
                setForeground(titleColor);
                if (isSelected) {
                    setForeground(selectedTitleColor);
                }
                setFont(new Font("Arial", Font.PLAIN, 14));
                return this;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("2ch RSS");
        Font titleFont = new Font("Arial", Font.BOLD, 20);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setPreferredSize(new Dimension(0, 30));
        titleLabel.setFont(titleFont);
        frame.getContentPane().add(titleLabel, BorderLayout.NORTH);

        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem openThreadInBrowser = new JMenuItem("Open in browser");
        openThreadInBrowser.setIcon(ResourceHandler.getWebsiteButtonIcon());
        openThreadInBrowser.addActionListener(e -> {
            BrowserHandler browserHandler = new BrowserHandler();
            browserHandler.openURL(widgetTableModel.getThread(SELECTED_ROW).link);
        });
        popupMenu.add(openThreadInBrowser);

        JMenuItem downloadAllAttachedFilesButton = new JMenuItem("Download all files");
        downloadAllAttachedFilesButton.setIcon(ResourceHandler.getFilesButtonIcon());
        downloadAllAttachedFilesButton.addActionListener(e -> {
            System.out.println("downloading...");
            ThreadBase selectedThread = widgetTableModel.getThread(SELECTED_ROW);
            Downloader.downloadImages(selectedThread, AttachedFileTypes.ALL);
        });
        popupMenu.add(downloadAllAttachedFilesButton);
        
        JMenuItem downloadAttachedImagesButton = new JMenuItem("Download images");
        downloadAttachedImagesButton.setIcon(ResourceHandler.getImageButtonIcon());
        downloadAttachedImagesButton.addActionListener(e -> {
            System.out.println("downloading...");
            ThreadBase selectedThread = widgetTableModel.getThread(SELECTED_ROW);
            Downloader.downloadImages(selectedThread, AttachedFileTypes.IMAGES);
        });
        popupMenu.add(downloadAttachedImagesButton);

        JMenuItem downloadAttachedVideosButton = new JMenuItem("Download video");
        downloadAttachedVideosButton.setIcon(ResourceHandler.getVideoButtonIcon());
        downloadAttachedVideosButton.addActionListener(e -> {
            System.out.println("downloading...");
            ThreadBase selectedThread = widgetTableModel.getThread(SELECTED_ROW);
            Downloader.downloadImages(selectedThread, AttachedFileTypes.VIDEOS);
        });
        popupMenu.add(downloadAttachedVideosButton);

        JMenuItem hideButton = new JMenuItem("Hide thread");
        hideButton.setIcon(ResourceHandler.getViewsLabelIcon());
        hideButton.addActionListener(e -> System.out.println("thread hidden..."));
        popupMenu.add(hideButton);

        table.setComponentPopupMenu(popupMenu);

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

        CustomMouseListener cml = new CustomMouseListener();
        table.addMouseListener(cml);

        bottomPanel.add(updateThreadListButton);
        bottomPanel.add(exitButton);

        frame.getRootPane().getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE,
                InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK), "searchComb");
        frame.getRootPane().getActionMap().put("searchComb", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("SEARCH FIELD ENABLED");
            }
        });

        frame.setSize(520, 300);
        frame.setUndecorated(true);
        frame.setOpacity(0.90f);
        frame.setLocationRelativeTo(null);
        SwingUtilities.invokeLater(() -> frame.setVisible(true));

    }

    public static void setThreadsCounter(int number) {
        counterLabel.setText("Threads: " + number);
    }

    public static void setThreadViews(int number) {
        threadViewsLabel.setText("Views: " + number);
    }

    public static void setThreadPostsCount(int number) {
        threadPostsCountLabel.setText("Posts: " + number);
    }

    private void exit() {
        System.exit(0);
    }
    
    private static void setupLookAndFeel() {
        System.out.println("os name: " + System.getProperty("os.name"));
        if (System.getProperty("os.name").equalsIgnoreCase("linux")) {
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
            System.out.println("mac os default");
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}


