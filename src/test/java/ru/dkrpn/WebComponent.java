package ru.dkrpn;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.function.Function;

public abstract class WebComponent {
    public WebDriver driver;
    public WebComponent parent;
    public By componentLocator;
    WebElement componentElement;

    public static <T extends WebComponent> T definePage(Class<T> classDef, WebDriver driver) {
        return WebComponent.define(classDef, driver, null, By.cssSelector("body"), null);
    }

    public static <T extends WebComponent> T define(Class<T> classDef, By componentSelector, WebComponent parent) {
        return WebComponent.define(classDef, parent.driver, parent, componentSelector, null);
    }

    public static <T extends WebComponent> T define(Class<T> classDef, By componentSelector, WebDriver driver) {
        return WebComponent.define(classDef, driver, null, componentSelector, null);
    }

    public static <T extends WebComponent> T define(Class<T> classDef, WebElement componentElement, WebComponent parent) {
        return WebComponent.define(classDef, parent.driver, parent, null, componentElement);
    }

    public static <T extends WebComponent> T define(Class<T> classDef, WebElement componentElement, WebDriver driver) {
        return WebComponent.define(classDef, driver, null, null, componentElement);
    }

    public static <T extends WebComponent> T define(Class<T> classDef,
                                                    WebDriver driver,
                                                    WebComponent parent,
                                                    By componentSelector,
                                                    WebElement componentElement) {
        try {
            T obj = classDef.newInstance();
            obj.driver = driver;
            obj.parent = parent;
            obj.componentLocator = componentSelector;
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


    public WebElement getComponentElement() {
        if (this.componentElement != null) {
            return this.componentElement;
        }
        if (this.componentLocator == null) {
            throw new RuntimeException("component element or component selector should be defined: " + this.getClass().getName());
        }
        return this.parent != null ?
                this.parent.getComponentElement().findElement(this.componentLocator) : driver.findElement(this.componentLocator);
    }

    /**
     * Allows to create list of components
     * @return
     */
    public ByFinder<WebElementList> listBy() {
        return this.byClauseConstructor((By by) -> new WebElementList(this, by));
    }

    /**
     * Allows to create list of components
     * @param clazz
     * @param <T>
     * @return
     */
    public <T extends WebComponent> ByFinder<WebComponentList<T>> listBy(Class<T> clazz) {
        return this.byClauseConstructor((By by) -> new WebComponentList<T>(clazz, this, by));
    }

    public ByFinder<WebElement> by() {
        return this.by(false);
    }

    public ByFinder<WebElement> by(boolean useCache) {
        return this.byClauseConstructor((By by) -> WebElementProxy.define(by, this, useCache));
    }

    private <T> ByFinder<T> byClauseConstructor(Function<By, T> construct) {
        class InnerByFinder implements ByFinder<T> {
            Function<By, T> construct;
            InnerByFinder(Function<By, T> lambdaT) {
                this.construct = lambdaT;
            }

            public T id(String id) {
                return construct.apply(By.id(id));
            }

            public T linkText(String linkText) {
                return construct.apply(By.linkText(linkText));
            }

            public T partialLinkText(String partialLinkText) {
                return construct.apply(By.partialLinkText(partialLinkText));
            }

            public T name(String name) {
                return construct.apply(By.name(name));
            }

            public T tagName(String tagName) {
                return construct.apply(By.tagName(tagName));
            }

            public T xpath(String xpathExpression) {
                return construct.apply(By.xpath(xpathExpression));
            }

            public T className(String className) {
                return construct.apply(By.className(className));
            }

            public T cssSelector(String cssSelector) {
                return construct.apply(By.cssSelector(cssSelector));
            }
        }
        return new InnerByFinder(construct);
    }
}
