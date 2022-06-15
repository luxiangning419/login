#系统说明
## Introduction
用户注册、查看用户、修改、删除功能，用户使用邮箱进行注册，注册后给用户发邮件

## 配置参数修改
修改user-login/user-message下 application.yml相关数据库配置、kafka配置、邮件配置

##数据库
创建mysql数据库user<br>
创建表：<br>
执行user-message resources下 mail.sql<br>
执行user-login resources下user.sql<br>

### How to build

执行脚本
sh user_manage.sh build

### 启动登录注册模块
sh user_manage.sh login

## 启动消息通知模块

sh user_manage.sh msg

