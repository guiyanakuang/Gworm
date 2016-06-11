package com.gyak.http.ajax;

import com.gyak.http.Htmlable;
import com.gyak.proterty.NotInitRequestProperties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * Created by guiyanakuang@gmail.com on 2016/6/11.
 */
public class ChromeProxy implements Htmlable {

    public ChromeProxy(String path) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\guiya\\Desktop\\chromedriver.exe");
    }

    @Override
    public String getHtml(String url) throws NotInitRequestProperties {
        WebDriver driver = new ChromeDriver();
        driver.get(url);
        new WebDriverWait(driver, 100);
        String source = driver.getPageSource();
        driver.close();
        return source;
    }
}
