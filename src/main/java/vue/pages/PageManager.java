package vue.pages;
import vue.utils.Templates;

import javax.swing.*;
import java.awt.*;


public class PageManager {
    private static PageManager instance;
    private JFrame frame;

    private PageManager() {
        frame = new JFrame("Restaurant App");
        Templates t = new Templates();
        frame.setIconImage(t.loadImage("img/Whiteboard.png").getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setMaximumSize(new Dimension(1920, 1080));
        frame.setLayout(new BorderLayout());
    }

    public static PageManager getInstance() {
        if (instance == null) {
            instance = new PageManager();
        }
        return instance;
    }

    public void showPage(PageContent page) {
        frame.getContentPane().removeAll();

        frame.getContentPane().setLayout(new BorderLayout());

        frame.getContentPane().add(Templates.createTopBar(), BorderLayout.NORTH);
        frame.getContentPane().add(page.getContentPanel(), BorderLayout.CENTER);


        frame.revalidate();
        frame.repaint();
    }



    public void start() {
        frame.setVisible(true);
    }
}
