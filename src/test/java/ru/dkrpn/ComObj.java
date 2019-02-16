package ru.dkrpn;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public abstract class ComObj {
    public WebDriver driver;
    public ComObj parent;
    public By componentSelector;
    WebElement componentElement;

    public static <T extends ComObj> T definePage(Class<T> classDef, WebDriver driver) {
        return ComObj.define(classDef, driver, null, By.cssSelector("body"), null);
    }

    public static <T extends ComObj> T define(Class<T> classDef, By componentSelector, ComObj parent) {
        return ComObj.define(classDef, parent.driver, parent, componentSelector, null);
    }

    public static <T extends ComObj> T define(Class<T> classDef, By componentSelector, WebDriver driver) {
        return ComObj.define(classDef, driver, null, componentSelector, null);
    }

    public static <T extends ComObj> T define(Class<T> classDef, WebElement componentElement, ComObj parent) {
        return ComObj.define(classDef, parent.driver, parent, null, componentElement);
    }

    public static <T extends ComObj> T define(Class<T> classDef, WebElement componentElement, WebDriver driver) {
        return ComObj.define(classDef, driver, null, null, componentElement);
    }

    public static <T extends ComObj> T define(Class<T> classDef,
                                              WebDriver driver,
                                              ComObj parent,
                                              By componentSelector,
                                              WebElement componentElement) {
        try {
            T obj = classDef.newInstance();
            obj.driver = driver;
            obj.parent = parent;
            obj.componentSelector = componentSelector;
            obj.componentElement = componentElement;
            return obj;

        } catch (InstantiationException | IllegalAccessException  e) {
            throw new RuntimeException("can't instantiate " + classDef.getName());
        }
    }

    public boolean isCached() {
        return this.componentElement != null;
    }

    public WebElement element(By locator) {
        return this.getComponentElement().findElement(locator);
    }

    public List<WebElement> elements(By locator) {
        return this.getComponentElement().findElements(locator);
    }

    public <T extends ComObj> List<T> frozenComponents(Class<T> clazz, By locator) {
        return elements(locator)
                .stream()
                .map(panel -> ComObj.define(clazz, panel, this))
                .collect(Collectors.toList());
    }

    public WebElement getComponentElement() {
        if (this.componentElement != null) {
            return this.componentElement;
        }
        if (this.componentSelector == null) {
            throw new RuntimeException("component element or component selector should be defined: " + this.getClass().getName());
        }
        return this.parent != null ?
                this.parent.getComponentElement().findElement(this.componentSelector) : driver.findElement(this.componentSelector);
    }

    public WebElement weProxy(By selector) {
        return WebElementProxy.define(selector, this, false);
    }
}
