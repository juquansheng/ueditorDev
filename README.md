Ueditor
===========================

###########环境依赖
java

maven

########### 项目介绍

百度富文本编辑器ueditor,增加了自定义资源文件上传路径的功能

配置config下的defaultUpload（必需）与proxyPath参数

defaultUpload:是否启用原有上传方式，即上传到本服务指定路径下

proxyPath：即自定义文件上传的java类的路径，需继承com.baidu.ueditor.upload.ProxyStorageManager，并且需给返回state增加
state.putInfo("size", filesize)、state.putInfo("url", filePath)、state.putInfo("title", path)这些属性

####联系邮箱
cexy_weiyan@qq.com
