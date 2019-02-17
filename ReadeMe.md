## Installation

1. Download Selenium chrome driver
http://chromedriver.chromium.org/downloads
2. By default application configured to search it in
``C:\chromedriver\chromedriver.exe``
Edit FirstTest if you choose another location 
``System.setProperty( "webdriver.chrome.driver", "C:/chromedriver/chromedriver.exe" );``
3. run ``mvn clean test``

## Library
The library introduce four new entities: WebComponent and WebElementProxy, WebComponentList and WebElementList.

WebElementProxy is proxy around web element, so when you call any method on WebElement proxy try to find WEbElement in DOM.
WebComponent may contain other components, elements, and lists of both types.

