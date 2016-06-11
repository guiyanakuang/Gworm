package com.gyak.test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.gyak.gworm.GwormBox;
import com.gyak.gworm.exception.NotFindGwormConfigException;
import com.gyak.http.ajax.ChromeProxy;
import com.gyak.proterty.NotInitRequestProperties;
import com.gyak.proterty.RequestProperties;

/**
 * Created by guiyanakuang@gmail.com on 2016/6/11.
 */
public class ChromeProxyTest {

    private final String NAME = "QunarFlight";
    private final String URL_ID = "Flight";
    private final String REQUEST_FILE = "request.properties";
    private final String WORM_CONFIG = "qunar.json";

    private final String FLIGHT_LIST = "flightList";
    private final String FLIGHT_NAME = "flightName";
    private final String FLIGHT_LINE = "flightLine";

    private final String TEST_URL = "http://flight.qunar.com/site/roundtrip_list_new.htm?fromCity=%E5%8D%97%E6%98%8C&toCity=%E5%B9%BF%E5%B7%9E&fromDate=2016-06-13&toDate=2016-06-16&fromCode=KHN&toCode=CAN&from=qunarindex&lowestPrice=null";


    public void test() throws NotInitRequestProperties, NotFindGwormConfigException {
        //ChromeDriver下载地址 https://sites.google.com/a/chromium.org/chromedriver/
        //其他WebDriver下载地址 http://www.seleniumhq.org/download/
        //修改为自己的路径
        ChromeProxy chromeProxy = new ChromeProxy("C:\\Users\\guiya\\Desktop\\chromedriver.exe");
        GwormBox gwormBox = GwormBox.getInstance();
        RequestProperties rp = RequestProperties.getInstance();
        rp.initProperties(ClassLoader.getSystemResourceAsStream(REQUEST_FILE));
        gwormBox.addGworm(WORM_CONFIG, NAME, chromeProxy);
        JsonObject jsonObject = gwormBox.getJson(NAME, TEST_URL, URL_ID);
        JsonArray jsonArray = jsonObject.get(FLIGHT_LIST).getAsJsonArray();
        for (int i=0; i<jsonArray.size(); i++) {
            JsonObject obj = jsonArray.get(i).getAsJsonObject();
            System.out.println(obj.get(FLIGHT_NAME).getAsString());
            System.out.println(obj.get(FLIGHT_LINE).getAsString());
        }

    }

    public static void main(String[] args) {
        ChromeProxyTest chromeProxyTest = new ChromeProxyTest();
        try {
            chromeProxyTest.test();
        } catch (NotInitRequestProperties notInitRequestProperties) {
            notInitRequestProperties.printStackTrace();
        } catch (NotFindGwormConfigException e) {
            e.printStackTrace();
        }
    }
}
