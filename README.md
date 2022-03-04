# 说明
一个BurpSuite插件，想法是简化日常挖洞流程，目标是联动常用工具，避免工具切换对思维的割裂。

## 已加入的项目
### 🗡Fiora
计划用于后期联动进行PoC管理，感谢大佬@bit4woo的开源项目。
<br>
<br>
# 用法
## FOFA
### 配置
1. Config页面配置邮箱、Key、常用浏览器的绝对路径
### 左键
1. 除Cert、Header外的列，双击某一条可以加入语法到搜索框。
2. Cert、Header列可以双击进行复制想要的部分，由于技术原因，里面会有html标签，无视它即可😂。
### 右键
1. Copy:复制的部分是鼠标右键所在的列，可多选复制，Cert、Header列用右键整体复制不会出现html标签，可放心复制。
2. Send to Fiora:可以将选中的行右键发送到Fiora的target部分，进行PoC测试。
3. Open in Browser:在host列右键会有此功能，将会用Config配置路径的浏览器打开。
### 搜索
1. ☑️Table Size:展示表格的大小。
2. ☑️PreSearch:预搜索，会在已加载数据快展示完时进行后台获取数据。
3. ☑️Seo:可以查询host为URL的爱站权重。
4. 可选则是否查询各个信息，信息种类选的多了会返回空数据。
5. Total Size:仅供参考，目前fofa很多东西搜不到了。
10. export待开发。。。

## Fiora
https://github.com/bit4woo/Fiora

# 界面
![image](https://user-images.githubusercontent.com/47287118/156734253-474dcaf5-2bf6-420f-bda4-d18d8a64aba8.png)
![image](https://user-images.githubusercontent.com/47287118/156734327-a63e623f-2bbe-4b3b-9556-a1104cf9f61f.png)
![image](https://user-images.githubusercontent.com/47287118/156734399-ddb1bdc9-cb9c-4089-88ea-c907eab5f4cb.png)
![image](https://user-images.githubusercontent.com/47287118/156734475-59112a40-a248-42ae-bce7-e7cfbf217d14.png)
