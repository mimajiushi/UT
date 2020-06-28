# UT-APP

<p>
  <a href="https://gitee.com/Lewage59/UT-WeChat"><img src="https://img.shields.io/badge/前端项目-UT_WeChat%20-orange.svg"></a>
  <a href="https://docs.spring.io/spring-boot/docs/2.3.1.RELEASE/reference/html/"><img src="https://img.shields.io/badge/Spring%20Boot-2.3.1.RELEASE-brightgreen.svg"></a>
  <a href="https://www.mysql.com/"><img src="https://img.shields.io/badge/Mysql-5.7-bringhtgreen.svg"></a>
  <a href="https://mp.baomidou.com/"><img src="https://img.shields.io/badge/Mybatis_Plus-3.3.1-blue.svg"></a>
  <a href="https://mp.baomidou.com/"><img src="https://img.shields.io/badge/Netty-4.1.42-brightgreen.svg"></a>
    <a href="https://redis.io/"><img src="https://img.shields.io/badge/redis-5.0.x-red.svg"></a>
    <a href="https://github.com/alibaba/fastjson"><img src="https://img.shields.io/badge/fastjson-1.2.61-bringhtgreen.svg"></a>
    <a href="https://www.layui.com/"><img src="https://img.shields.io/badge/layui-2.4.5-red.svg"></a>
    <a href="https://github.com/google/guava"><img src="https://img.shields.io/badge/Guava-28_jre-ff69b4.svg"></a>
    <a href="https://github.com/looly/hutool"><img src="https://img.shields.io/badge/hutool-5.0.3-yellow.svg"></a>
    <a href="https://developer.qiniu.com/kodo/sdk/1239/java"><img src="https://img.shields.io/badge/七牛云_SDK-7.2.18-blue.svg"></a>
</p>

#### 特别说明

由于最近面试失利，开发者现在在恶补中，开发进度可能会暂停一段时间。

#### 介绍 & 各种杂图

参考我的博客链接：[介绍文档](https://wenjie.store/archives/ut%E7%9A%84%E4%BB%8B%E7%BB%8D%E5%92%8C%E6%9D%82%E5%9B%BE)

#### 实现选型

- 考虑到数据的量级只是一个学校，同时为了降低软件部署的成本，目前仅打算使用MySQL、redis实现后端，最终目标是希望一台2G的服务器+七牛cdn就能流畅运行应用（之后会考虑兼容其它cdn，如阿里oss等）
- 日后等熟悉更多开源组件的原理后，会增加不同的框架，开不同的分支来学习。

#### 开发者须知

- 统一使用Gitee：https://gitee.com/wenjie2018/UT-APP （集成了CI/CD）
- 代码风格：风格约束配置请参考 -> https://halo.run/archives/code-style  不懂的也可以问我。
- 提交PR： 提交的PR后都会经过jenkins的编译、打包测试，通过了会自动在PR下留言测试结果， **请务必保证测试结果通过** （现阶段PR里面一个失败一个成功，可以参考下）。  
- 自定义环境：如果你因为某种原因，需要更改`application.yml`的一些配置，比如开启debug，那么你完全可以在本地的`{user.home}/.ut`目录下创建`application.yml`文件，它可以有选择性地覆盖项目中默认的配置项。

#### 包结构

```
run.ut.app
├── api -- controller层的接口抽象层，主要用于分离Swagger2文档
├── cache -- 跟缓存有关的业务
├── config -- 项目相关的配置类
├── controller -- 存放controller
├── core -- 目前是放对返回对象的增强的逻辑
├── event -- 自定义各种时间
├── exception -- 异常定义、处理相关。
├── handler -- 一些处理器
├── listener -- 监听事件并处理
├── mail -- 邮件相关代码
├── mapper -- MyBaits的mapper
├── model -- 存放DOMAIN、DTO、VO、Param等实体
├── netty -- netty的一些代码，目前只用于搭建WebSocket服务
├── schedule -- 存放定时任务，目前的定时任务大多都是测试用
├── security -- 自定义认证拦截逻辑
├── service -- 业务层代码
└── utils -- 工具类

```

#### 计划实现

- websocket实时推送消息（Netty实现）...✔
- 组队...✔
- bbs...✔
- 校园活动发布/订阅...✔
- 用户个人信息的完善（绑定邮箱等）...✔
- 后台管理可动态修改一些配置，如oss配置等...✔
- sql、索引的优化（当前在索引方面完善得差不多了，但还剩下比较多的坏sql）..ing

（到此再稳定下现有的功能后就相当于第一个Release版本了，大概~）

---

- 聊天...ing（研究方案中）
- 校园活动内容支持markdown...ing（待讨论）
- 上传的图片支持存到本地磁盘...ing（待讨论）
- 举报机制..ing（计划中，因为客服功能的存在，有可能不需要）
- 活动开始前一天或一段时间，发送邮件推送提醒...ing（待讨论）
- ...更多

以上功能优先级从上至下递减

#### 参与贡献

1.  Fork 本仓库
2.  新建 UT_APP_XXX 分支
3.  提交代码
4.  新建 Pull Request

#### 鸣谢

- 特别感谢[@施晓权](https://gitee.com/sxq2017)提供的服务器，用于部署jenkins等应用。


