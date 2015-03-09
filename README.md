# Gworm
Gworm是一个java版的用以提取指定网址中特定部分数据，以json格式返回的库。使用场合举例：获取电商平台的搜索结果、博客内容、对没有提供api接口的网站通过提取html制作DIY的接口。

# 使用说明
本库需要引用[Jsoup][2]、[Dom4j][3]。
```java
//初始化单例GwormBox
GwormBox gwormBox = GwormBox.getInstance();
//初始化request参数
RequestProperties rp = RequestProperties.getInstance();
rp.initProperties(new FileInputStream(new File("request.properties")));
//添加爬去规则,amazonKey对应规则文件的路径amazon.xml
gwormBox.addWormConfigPath("amazonKey", "amazon.xml");
//返回链接 http://www.amazon.cn/s/ref=nb_sb_noss_2?field-keywords=算法 内提取的数据（json格式）
String json = gwormBox.getJson("amazonKey", "http://www.amazon.cn/s/ref=nb_sb_noss_2?field-keywords=算法" , "amazonSearch");
```
# 规则文件
amazon.xml 如下

```xml
<?xml version="1.0" encoding="UTF-8" ?>   
<gworm>   
	<url id = "amazonSearch">   
	    <array id = "amazonSearchArray"  rule = "#rightResultsATF .s-item-container" >
	        <object>
	            <value id = "productName" rule = "h2" get = "text" />
	            <value id = "productPrice" rule = ".a-color-price" get = "text" />
	            <value id = "productImg" rule = "img" get = "attr src" />
	            <value id = "productUrl" rule = "a" get = "attr href" />
	        </object>
	    </array>
	</url>
</gworm> 
		
```
所有规则都必须写在gworm标签中间。 下一级标签为url，通过id 区分处理不同内容的网址。下一级标签为array或object ，提取数组信息使用array，提取单一信息使用object， array与object标签可以互相嵌套， array与object的id 属性可以忽略。最后接value标签，对应提取项。rule属性用于 css选择器，object标签可以忽略 rule属性。get属性用以表明如何提取数据，可以有三种方式， text：提取css选择器对应的 Elements的文本段，attr 属性：提取css 选择器对应的Elements中指定属性的值，html：提取css 选择器对应的Elements。

# 爬取参数
request.properties 如下
```properties
Accept=text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8
Accept-Encoding=gzip, deflate, sdch
Accept-Language=zh-CN,zh;q=0.8
Connection=keep-alive
User-Agent=Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.94 Safari/537.36
```

# 实例链接

使用上述配置获取亚马逊搜索[巧克力][1]的json数据。


  [1]: http://120.26.41.209/servlet/GetJson?query=%E5%B7%A7%E5%85%8B%E5%8A%9B
  [2]: http://jsoup.org/
  [3]: http://dom4j.sourceforge.net/
