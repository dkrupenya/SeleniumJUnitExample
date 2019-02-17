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
    public WebElement componentElement;

    public static <T extends WebComponent> T definePage(Class<T> clazz, WebDriver driver) {
        return new WebComponent.Builder<T>(clazz, driver).build();
    }

    public static class Builder<T extends WebComponent> {

        private Class<T> clazz;
        private WebDriver driver;
        private WebComponent parent;
        private By componentLocator;
        private WebElement componentElement;

        public Builder(Class<T> clazz, WebDriver driver) {
            // mandatory fields
            this.clazz = clazz;
            this.driver = driver;
        }

        public Builder<T> parent(WebComponent parent){
            this.parent = parent;
            return this;
        }

        public Builder<T> locator(By componentLocator){
            this.componentLocator = componentLocator;
            return this;
        }

        public Builder<T> element(WebElement componentElement){
            this.componentElement = componentElement;
            return this;
        }

        public T build() {
            try {
                if (this.driver == null) {
                    throw new RuntimeException("Component must have driver");
                }

                T obj = clazz.newInstance();
                obj.driver = this.driver;
                obj.parent = this.parent;
                obj.componentLocator = this.componentLocator == null ? obj.componentLocator : this.componentLocator;
                obj.componentElement = this.componentElement == null ? obj.componentElement : this.componentElement;

                if ((this.componentLocator == null && this.componentElement == null)) {
                    throw new RuntimeException("Component must have locator or element");
                }
                return obj;

            } catch (InstantiationException | IllegalAccessException  e) {
                throw new RuntimeException("can't instantiate " + clazz.getName());
            }

        }

    }

    public boolean usingCache() {
        return this.componentElement != null;
    }

    public WebElement realElement(By locator) {
        return this.getComponentElement().findElement(locator);
    }

    public List<WebElement> realElements(By locator) {
        return this.getComponentElement().findElements(locator);
    }

    public WebElement getComponentElement() {
        if (this.componentElement != null) {
            return this.componentElement;
        }
        if (this.componentLocator == null) {
            throw new RuntimeException("component realElement or component selector should be defined: "
                    + this.getClass().getName());
        }
        return this.parent != null
                ? this.parent.getComponentElement().findElement(this.componentLocator)
                : driver.findElement(this.componentLocator);
    }

    public <T extends WebComponent> ByFinder<WebComponentList<T>> componentListBy(Class<T> clazz) {
        return this.byClauseConstructor((By by) -> new WebComponentList<T>(clazz, this, by));
    }

    public <T extends WebComponent> ByFinder<T> componentBy(Class<T> clazz) {

        return this.byClauseConstructor((By by) -> new Builder<T>(clazz, this.driver).parent(this).locator(by).build());
    }

    public ByFinder<WebElementList> elementListBy() {
        return this.byClauseConstructor((By by) -> new WebElementList(this, by));
    }


    public ByFinder<WebElementProxy> elementBy() {
        return this.elementBy(false);
    }

    public ByFinder<WebElementProxy> elementBy(boolean useCache) {
        return this.byClauseConstructor((By by) -> WebElementProxyInvocationHandler.define(by, this, useCache));
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
