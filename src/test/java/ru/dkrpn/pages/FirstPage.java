package ru.dkrpn.pages;

import org.openqa.selenium.By;
import ru.dkrpn.ComObj;
import ru.dkrpn.ComObjList;
import ru.dkrpn.components.MainMenuComponent;
import ru.dkrpn.components.PanelComponent;

public class FirstPage extends ComObj {

    public ComObjList<PanelComponent> panels = by(PanelComponent.class).cssSelector("article.fusion-post-grid.post");

    public MainMenuComponent mainMenu = ComObj.define(MainMenuComponent.class, By.cssSelector(".fusion-main-menu"), this);

}
