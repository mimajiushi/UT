# UT-APP

<p>
  <a href="https://gitee.com/Lewage59/UT-WeChat"><img src="https://img.shields.io/badge/前端项目-UT_WeChat%20-orange.svg"></a>
  <a href="https://docs.spring.io/spring-boot/docs/2.3.2.RELEASE/reference/html/"><img src="https://img.shields.io/badge/Spring%20Boot-2.3.2.RELEASE-brightgreen.svg"></a>
  <a href="https://spring.io/projects/spring-cloud-alibaba"><img src="https://img.shields.io/badge/Spring%20Cloud%20Alibaba-2.2.1.RELEASE-yellow.svg"></a>
  <a href="https://spring.io/projects/spring-cloud-alibaba"><img src="https://img.shields.io/badge/Spring%20Cloud%20-Hoxton.SR7-brightgreen.svg"></a>
  <a href="https://www.mysql.com/"><img src="https://img.shields.io/badge/Mysql-5.7-bringhtgreen.svg"></a>
  <a href="https://mp.baomidou.com/"><img src="https://img.shields.io/badge/Mybatis_Plus-3.3.1-blue.svg"></a>
  <a href="https://mp.baomidou.com/"><img src="https://img.shields.io/badge/Netty-4.1.42-brightgreen.svg"></a>
    <a href="https://redis.io/"><img src="https://img.shields.io/badge/redis-5.0.x-red.svg"></a>
    <a href="https://github.com/alibaba/fastjson"><img src="https://img.shields.io/badge/fastjson-1.2.61-blue.svg"></a>
    <a href="https://www.layui.com/"><img src="https://img.shields.io/badge/layui-2.4.5-red.svg"></a>
    <a href="https://github.com/google/guava"><img src="https://img.shields.io/badge/Guava-28_jre-ff69b4.svg"></a>
    <a href="https://github.com/looly/hutool"><img src="https://img.shields.io/badge/hutool-5.0.3-yellow.svg"></a>
    <a href="https://developer.qiniu.com/kodo/sdk/1239/java"><img src="https://img.shields.io/badge/七牛云_SDK-7.2.18-blue.svg"></a>
    <a href="http://dubbo.apache.org/"><img src="https://img.shields.io/badge/dubbo-2.7.7-purple.svg"></a>
    <a href="https://nacos.io/"><img src="https://img.shields.io/badge/nacos-1.3.1-blue.svg"></a>
</p>

#### 特别说明

由于开发者是学生&都在实习，所以进度会以往慢很多。

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


#### 预期架构图

![架构图](https://www.wenjie.store/blog/img/UT%E6%9E%B6%E6%9E%84%E5%9B%BE_1597655043923.png)

#### 服务结构

```
ut
├── ut-common -- 一些公共类
├── ut-bbs-service -- 提供给前端的接口
├── ut-chat-service -- 推送、聊天（未完成）服务，核心就是Netty搭建的websocke服务
├── ut-service-center -- mysql、redis相关的服务都在这里
├── ut-gateway（未构建） -- 主要是用于负载ut-bbs-service、ut-chat-service，其中如何负载websocket是目前的难点之一。

```

#### 计划实现

- websocket实时推送消息（Netty实现）...✔
- 组队...✔
- bbs...✔
- 校园活动发布/订阅...✔
- 用户个人信息的完善（绑定邮箱等）...✔
- 后台管理可动态修改一些配置，如oss配置等...✔
- sql、索引的优化（当前在索引方面完善得差不多了，但还剩下比较多的坏sql，后续打算引入搜索引擎替代掉）..ing
- 以nacos+dubbo为辅助，将消息推送拆出来（学习中）...ing

（到此再稳定下现有的功能后就相当于第一个Release版本了，大概~）

---

- 聊天...ing（研究方案中，在上面的介绍文档中补充了IM服务架构和时序图）
- 校园活动内容支持markdown...ing（待讨论）
- 上传的图片支持存到本地磁盘...ing（待讨论）
- 举报机制..ing（计划中，因为客服功能的存在，有可能不需要）
- 活动开始前一天或一段时间，发送邮件推送提醒...ing（待讨论）
- ...更多

以上功能优先级从上至下递减

#### 问题清单

- 推送模块问题
    - 因为负载均衡的原因，用户的websocket长连接会连到不同的ut-chat-service实例上，那么推送的时候如何找到用户连接的实例？
    - 保存用户所在的实例要用什么保存？
    - 找到用户所在实例后，如何定位到要调用的接口，要通过什么协议调用？
    - 如果推送 or 接受的消息是聊天消息（不可丢），要怎么保证消息一定送达 or 一定被处理？

- 数据库模块问题
    - es暂时未整合。
    - 同步组件暂时不知道选哪个。

#### 参与贡献

1.  Fork 本仓库
2.  新建 UT_APP_XXX 分支
3.  提交代码
4.  新建 Pull Request

#### 鸣谢

- 特别感谢[@施晓权](https://gitee.com/sxq2017)提供的服务器，用于部署jenkins等应用。


