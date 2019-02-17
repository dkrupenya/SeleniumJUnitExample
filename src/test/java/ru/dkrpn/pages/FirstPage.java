package ru.dkrpn.pages;

import org.openqa.selenium.By;
import ru.dkrpn.WebComponent;
import ru.dkrpn.WebComponentList;
import ru.dkrpn.components.MainMenuComponent;
import ru.dkrpn.components.PanelComponent;

public class FirstPage extends WebComponent {

    public FirstPage() {
        componentLocator = By.cssSelector("body");
    }

    public WebComponentList<PanelComponent> panels = componentListBy(PanelComponent.class).cssSelector("article.fusion-post-grid.post");

    public MainMenuComponent mainMenu = componentBy(MainMenuComponent.class).cssSelector(".fusion-main-menu");

}
