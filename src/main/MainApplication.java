package main;

import connectivity.Dbmanager;
import connectivity.QueryManager;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import model.User;
import view.Login;

/**
 *
 * @author UDP
 */
public final class MainApplication {

    /** Define name*/
    public static final String NAME = "ApplicationName";
    public static final String CURRENCY = "\u20AC";
    /** static fonts which are used within the application */
    public static final Font FONT_10_PLAIN = new Font("Verdana", Font.PLAIN, 10);
    public static final Font FONT_10_BOLD = new Font("Verdana", Font.BOLD, 10);
    public static final Font FONT_12_BOLD = new Font("Verdana", Font.BOLD, 12);
    public static final Font FONT_16_BOLD = new Font("Verdana", Font.BOLD, 16);
    public static final Font FONT_25_BOLD = new Font("Verdana", Font.BOLD, 25);
    /** database manager */
    private Dbmanager dbManager;
    private QueryManager queryManager;
    /** the main window */
    private JFrame mainWindow;
    /** singleton of the application */
    private static MainApplication instance = new MainApplication();
    /** the current user */
    private User user;

    public int ScreenHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    public int ScreenWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();

    private MainApplication() {
    }

    /**
     * Start alle basiselementen
     */
    public void initialize() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Error setting LookAndFeelClassName: " + e);
        }
        // create and initialize the connectivity
        dbManager = new Dbmanager();
        dbManager.openConnection();
        queryManager = new QueryManager(dbManager);
    }

    /**
     * Laad de GUI en de KeyListener
     */
    public void startup() {
        mainWindow = new JFrame(NAME);
        getMainWindow().setExtendedState(JFrame.MAXIMIZED_BOTH);
        getMainWindow().setUndecorated(true);

        InputKeyListener keylisten = new InputKeyListener(getMainWindow());
        keylisten.start();

        getMainWindow().getContentPane().setLayout(new BorderLayout());
        showPanel(new Login());
        getMainWindow().setVisible(true);
    }

    /**
     * Verwissel van scherm in de GUI
     * @param panel
     */
    public void showPanel(JPanel panel) {
        getMainWindow().getContentPane().removeAll();
        getMainWindow().getContentPane().add(panel, BorderLayout.CENTER);
        getMainWindow().getContentPane().validate();
        getMainWindow().getContentPane().repaint();
    }

    /**
     * Werkt samen met Shutdown om de applicatie op de "juiste" manier af te sluiten
     * Juist manier houdt in eerste de Database connectie sluiten en het scherm verbergen
     */
    public void exit() {
        getMainWindow().setVisible(false);
        shutdown();
    }

    private void shutdown() {
        getMainWindow().dispose();
        dbManager.closeConnection();
        System.exit(0);
    }

    /**
     * @return the instance of this class
     */
    public static MainApplication getInstance() {
        return instance;
    }

    /**
     * @return the queryManager
     */
    public static QueryManager getQueryManager() {
        return getInstance().queryManager;
    }

    /**
     * Laadt de applicatie indien mogelijk
     * @param args
     */
    public static void main(String args[]) {
        final MainApplication applicatie = MainApplication.getInstance();
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    applicatie.initialize();
                    applicatie.startup();
                } catch (Exception e) {
                    System.out.println("Application" + applicatie.getClass().getName() + "failed to launch");
                }
            }
        });
    }

    /**
     * @return the mainWindow
     */
    public JFrame getMainWindow() {
        return mainWindow;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }
}
