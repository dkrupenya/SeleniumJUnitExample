package ru.dkrpn;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class WebElementProxy implements InvocationHandler {

    public static WebElement define(By selector, ComObj rootElement, boolean useCache){
        return (WebElement) Proxy.newProxyInstance(
                null,
                new Class[] { WebElement.class },
                new WebElementProxy(selector, rootElement, useCache));
    }

    private By selector;
    private ComObj rootElement;
    private boolean useCache;

    private WebElement element;

    private WebElementProxy(By selector, ComObj rootElement, boolean useCache) {
        this.selector = selector;
        this.rootElement = rootElement;
        this.useCache = useCache;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (this.useCache) {
            if (this.element == null) {
                this.element = this.rootElement.element(this.selector);
            }
            return method.invoke(this.element, args);
        } else {
           // no cache
            WebElement tmpElement = this.rootElement.element(this.selector);
            return method.invoke(tmpElement, args);
        }
    }

}
