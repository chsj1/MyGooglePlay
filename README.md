# MyGooglePlay
简单的应用市场
###服务器搭建
* 文件说明
	* GooglePlayServer:java ee工程,我们的服务器
	* GooglePlayServer.war:java ee工程的war包形式
	* WebInfos:资源文件
	* GooglePlayServerAndroid:手机端的服务器,可以运行servlet

* 搭建方式:
	* war包方式:
		1. 把war放到tomact的`D:\java_web\apache-tomcat-7.0.54-windows-x64\apache-tomcat-7.0.54\webapps`目录下面就可以,然后启动tomcat会自动解压war包.
		2. 启动tomcat,自动解压war包,并运行程序 
		3. 修改`webapps\GooglePlayServer\WEB-INF\classes`目录下system.properties为`#dir=F:/google应用市场/d01/服务器`修改`WebInfos`所在的目录,需要注意要么用"/"或者"\\"
		4. 在pc和手机上分别验证
				1.http://localhost:8080/
				2.http://localhost:8080/GooglePlayServer/
				3.http://localhost:8080/GooglePlayServer/home?index=0
				4.http://本地ip:8080/GooglePlayServer/home?index=0
				5.http://10.0.3.2:8080/GooglePlayServer/home?index=0(genymotion)
				6.5.http://10.0.2.2:8080/GooglePlayServer/home?index=0(genymotion)
		5. `注意:tomcat必须使用7以上版本`

	* 源码形式.
		1. 用java ee 版eclipse导入工程GooglePlayServer.
		2. 修改工程目录下system.properties为 `#dir=F:/google应用市场/d01/服务器`修改`WebInfos`所在的目录,需要注意要么用"/"或者"\\"
		3. 部署java ee工程到tomcat,然后运行
		4. 在pc和手机上分别验证

	* 把服务器放到手机上.
		1. 导入android项目
		2. 把WebInfos拷贝到 `外置 sdcard`根目录,只能用真机或者genymotion
		3. 运行工程,启动服务器
		4. 手机上分别验证
			1. 127.0.0.1:8090/home?index=1
	* 注意:tomcat必须使用7.0以上
