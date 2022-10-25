# RouteVulScan
Burpsuite - Route Vulnerable scanning  递归式被动检测脆弱路径的burp插件

***

## 贡献

* 合作者 [@deep0](https://github.com/deep0)
* 参考项目 [HAE](https://github.com/gh0stkey/HaE)

## 介绍

RouteVulScan是使用java语言基于burpsuite api开发的可以递归检测脆弱路径的burp插件。

插件可以通过被动扫描的方式，递归对每一层路径进行路径探测，并通过设定好的正则表达式匹配相应包的关键字，展示在VulDisplay界面。可以自定义相关路径、匹配信息、与漏洞名称等。

插件重点是那些简单而有害的漏洞。这些漏洞通常不是固定路径，但可能位于路径的任何层。在这种情况下，非常容易忽视这些漏洞，而如果使用路径爆破，则非常耗时和麻烦。

所以插件主打是发送数量小、准确的payload，尽可能覆盖面广的探测一些容易忽略的漏洞。



## 使用

装载插件：``` Extender - Extensions - Add - Select File - Next ```

初次装载插件会在burpsuite当前目录下生成Config_yaml.yaml配置文件，用来储存匹配规则，该文件默认在当前burp目录下。

插件支持在线更新，点击Update按钮更新最新规则。需要注意的是，**如果你有自己添加的规则，最好先备份**，因为在线更新会直接覆盖规则文件。部分网络需要挂代理，在线更新使用的是burp网络，所以可以直接配置burp的顶级代理。

<img src="./img/update.jpg">

## 功能介绍

* 被动扫描，使用Burpsuite IScannerCheck接口，在流量初次流经burp时进行扫描，重复流量不会进行扫描。

  * 对流经流量的每一层路径进行规则探测，并进行正则匹配，符合规则则展示在VulDisplay界面
  * 如https://www.baidu.com/aaa/bbb，则会对https://www.baidu.com/、https://www.baidu.com/aaa/、https://www.baidu.com/bbb分别进行探测，如果存在点后缀，则会跳过。

* 使用线程池增加扫描速度，默认线程10，可自行调节（线程个数最多与规则个数相等，多了也没用）

* Extend Switch按钮，插件主开关，默认关闭

* Carry Head 按钮，携带原始的请求头，默认关闭

* Filter_Host 输入框，可以只扫描指定host的url，*代表全部，如 *.baidu.com

* VulDisplay界面右键可删除选中的行，或全部删除

  <img src="./img/remove.jpg">

* 右键请求可选择将当前请求发送到插件进行主动扫描，插件会将站点地图中，与当前请求使用一样host的历史路径全部进行扫描

  <img src="./img/Active_scan.jpg">

  


## 更新计划

* 2022-06-19 右键选择请求发送到插件扫描【✓】
* 2022-06-30 域名过滤【✓】
* 2022-06-19 UI界面增加数据包大小【✓】 
* 2022-06-22 VulDisplay界面添加删除功能【✓】
* 2022-06-30 插件功能开关【✓】
* 2022-06-30 带原始请求头访问【✓】
* 2022-06-30 可自定义post/get请求【✓】
* 2022-07-01 配置文件在线更新【✓】
* 2022-10-18 添加分类，提供可根据个人习惯对规则进行分类处理【✓】
* 2022-10-18 添加选择，每个规则设置为可选的形式，可自由选择想要的规则【✓】

## 开心值

[![Stargazers over time](https://starchart.cc/F6JO/RouteVulScan.svg)](https://starchart.cc/F6JO/RouteVulScan)

## 最后

***

### 如有正则、BUG、需求等欢迎提Issues

​	

