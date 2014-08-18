## 测试： 通过REST API 发送推送消息

1. 下载 https://github.com/avoscloud/avoscloud-demo/tree/master/android/AVOSCloud-Push 代码

2. 安装到模拟器或手机, 记下显示的id

  (如 id: "ec59d6cf-d013-47eb-9b67-9eef91147218" )

3. 下载本项目代码

4. 修改src/main/java/test/testavospush/App.java

  将`private static final String TEST_IID = "ec59d6cf-d013-47eb-9b67-9eef91147218";`修改为2步骤中记录的InstallationId

5. 通过maven跑起来

    $ mvn clean compile && mvn exec:java -Dexec.mainClass=test.testavospush.App

    返回结果:{"createdAt":"2014-08-12T18:15:25.957Z","objectId":"53e9e93de4b0e5fa161af8e3"}

6. 此时应用通知栏应该出现了通知信息

