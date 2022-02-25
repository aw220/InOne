# 说明

简化日常挖洞流程，目标是联动常用工具，避免工具切换对思维的割裂。

# 功能

## FOFA
目前只完成了FOFA部分基础功能。

## Fiora
加入了Fiora，用于后期联动进行PoC管理，感谢大佬@bit4woo的开源项目。

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
1. Table Size:展示表格的大小。
2. PreSearch:预搜索，会在已加载数据快展示完时进行后台获取数据。
3. 可选则是否查询Cert、Header。
4. Total Size:仅供参考，目前fofa很多东西搜不到了。
10. export待开发。。。
