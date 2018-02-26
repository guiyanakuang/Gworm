# Gworm
Gworm是一个java版的爬虫框架，以json格式作为返回。

## 目录

* [依赖包](#依赖包)
* [参数文件](#参数文件)
  * [爬取规则](#爬取规则)
  * [请求参数](#请求参数)
* [示例程序](#示例程序)
  * [初始化](#初始化)
  * [链接生成器](#链接生成器)
  * [数据操作](#数据操作)
  * [异步加载](#异步加载)

## 依赖包
* JSON库 : Gson 2.6.2
* DOM提取 : jsoup 18.3
* WebDriver : selenium-java 2.53.0

```xml
#pom.xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.gyak.gworm</groupId>
    <artifactId>gworm</artifactId>
    <version>1.0</version>

    <dependencies>
        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>2.6.2</version>
        </dependency>
        <dependency>
            <groupId>org.jsoup</groupId>
            <artifactId>jsoup</artifactId>
            <version>1.8.3</version>
        </dependency>
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-java</artifactId>
            <version>2.53.0</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.5.1</version>
                <configuration>
                    <source>1.7</source>
                    <target>1.7</target>
                </configuration>
            </plugin>
        </plugins>

    </build>

</project>
```

## 参数文件
使用Gworm爬虫框架需要配置两份文件，爬虫规则文件、请求参数文件。

### 爬取规则

```json
//src\test\resources\jd.json
[
  {
    "id": "Book",
    "gwormArray": {
      "id": "bookList",
      "rule": "#plist .gl-item",
      "list": [
        {
          "id": "bookName",
          "rule": ".p-name",
          "get": "text"
        },
        {
          "id": "bookPage",
          "rule": ".p-name a",
          "get": "href"
        },
        {
          "id": "bookAuthor",
          "rule": ".author_type_1 a",
          "get": "text"
        }
      ]
    }
  }
]
```

### 请求参数
```properties
Accept=text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8
Accept-Encoding=gzip, deflate, sdch
Accept-Language=zh-CN,zh;q=0.8
Connection=keep-alive
User-Agent=Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.94 Safari/537.36
```

## 示例程序

通过几段示例程序使用Gworm爬取京东20页的图书信息

### 初始化

`GwormBox`顾名思义为爬虫箱子，使用单例模式初始化，保存所有的爬虫规则。`RequestProperties`为整个爬虫框架配置请求参数，`addGworm`方法添加一套爬取规则。
```java
//src\test\java\com\gyak\test\Test.java
GwormBox gwormBox = GwormBox.getInstance();
RequestProperties rp = RequestProperties.getInstance();
//REQUEST_FILE 请求参数文件名
rp.initProperties(ClassLoader.getSystemResourceAsStream(REQUEST_FILE));
//WORM_CONFIG 爬取配置文件名
//NAME 对配置设置的别名
gwormBox.addGworm(WORM_CONFIG, NAME);
```        

### 链接生成器

url生成器用来生产爬取目标，方便串行、并行的爬取目标，接口为`UrlGeneration`
```java
//src\test\java\com\gyak\test\Test.java
class JdUrlGeneration implements UrlGeneration {

    private int currentPage = 1;
    private final String page = "http://list.jd.com/list.html?cat=1713,3258,3297&page=%d&trans=1&JL=6_0_0";

    @Override
    public int getStart() {
        return 1;
    }

    @Override
    public int getEnd() {
        return 20;
    }

    @Override
    public void next() {
        currentPage ++;
    }

    @Override
    public HasUrl getCurrentbindObj() {
        return new HasUrl() {
            private String url = String.format(page, currentPage);
            @Override
            public String getUrl() {
                return url;
            }
        };
    }

}
```

### 数据操作

`GwormCoordinate`规则定位器，传入`GwormAction`中用来指定使用的规则，实现虚函数`action`用来处理已经爬回的json数据，`bindObj`对应URL生成器绑定的对象。 
```java
//src\test\java\com\gyak\test\Test.java
UrlGeneration jd = new JdUrlGeneration();
//NAME 爬虫规则的别名
//URL_ID 规则中指定链接
GwormCoordinate coordinate = new GwormCoordinate(NAME, URL_ID); 
//concurrency 并发数
GwormAction ga = new GwormAction(concurrency, jd, coordinate) {
    /**
     * 爬取结果的处理函数
     * @param json 爬取的JSON
     * @param bind 对应url生成器绑定的对象
     */
    @Override
    public void action(String json, Object bindObj) {
        JsonArray jsonArray = json.get(BOOK_LIST).getAsJsonArray();
        for (int i=0;i<jsonArray.size();i++) {
            JsonObject obj = jsonArray.get(i).getAsJsonObject();
            String bookName = obj.get(BOOK_NAME).getAsString();
            String bookPage = obj.get(BOOK_PAGE).getAsString();
            String bookAuthor = obj.get(BOOK_AUTHOR).getAsString();
            String bookComment = obj.get(BOOK_COMMENT).getAsString();
            System.out.println("书名：" + bookName);
            System.out.println("购买链接：" + bookPage);
            System.out.println("作者：" + bookAuthor);
            System.out.println("￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣");
        }
    }

};

ga.work(); //启动爬取
```

### 异步加载

对于有些通过异步来加载数据的网页，提取这部分数据必须运行js后方能获得，对于这些网页可以自行实现[Htmlable](/src/main/java/com/gyak/http/Htmlable.java)（例如调用Chrome来获取html）接口。

最新添加了ChromeProxy类，实现了调用Chrome来爬取异步加载页面，具体示例详见[ChromeProxy](/src/test/java/com/gyak/test/ChromeProxyTest.java)
```java
ChromeProxy chromeProxy = new ChromeProxy("C:\\Users\\guiya\\Desktop\\chromedriver.exe");
```
[ChromeDriver下载地址](https://sites.google.com/a/chromium.org/chromedriver/)

[其他WebDriver下载地址](http://www.seleniumhq.org/download/)

运行测试代码时，请修改为自己WebDriver放置的路径。
