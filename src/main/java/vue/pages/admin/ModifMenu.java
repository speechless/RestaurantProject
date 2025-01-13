package vue.pages.admin;

import modele.Menu;
import requete.RequeteRestaurant;
import vue.pages.PageContent;

import javax.swing.*;

public class ModifMenu implements PageContent {
    private Menu menu;

    public ModifMenu(int id) {
        this.menu = RequeteRestaurant.getInstance().getMenu(id);
    }

    @Override
    public JPanel getContentPanel() {
        return null;
    }
}
