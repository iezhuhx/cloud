<?xml version="1.0" encoding="utf-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Order Confirmed</title>
</head>
<body>
	<h1>Order Confirmed</h1>
	<!-- 客户端请求中包含了 _eventId 参数
这种方式一般用在 state 之间的 transition ，
通过指定 _eventId 参数的值，表明了客户的行为，
从而导致相应事件的发生，在 Spring Web Flow 的定义文件中可以通过 evaluate 元素来指定要处理的业务逻辑
	 -->
	<a href="${flowExecutionUrl}&_eventId=returnToIndex">Return to
		index</a>
</body>
</html>