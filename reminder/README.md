# reminder

包含各种消息的“提醒器”（reminder）

## 1.停水/停电通知提醒器

定时从新闻网站上爬取停水、停电的通知（水务、电力网上完全找不到通知消息，只能从新闻网爬了），如果该消息包含了居住地区，则发起提醒。

这里使用了腾讯云短信服务，短信模版中不能把url或者url的部分作为变量（只能填写固定的url）。

为此另外写了一个HTTP Server，短信中填写的是固定url链接，点击之后重定向到另外的url链接。

短信提醒涉及的两个url：
http://ultronxr.xyz:88/reminder/water
http://ultronxr.xyz:88/reminder/power


