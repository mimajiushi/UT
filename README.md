# UT-APP

#### 介绍

- 本项目致力于打造成一个 **每个学校都可单独部署的组队微信小程序**，打破有心组队之人因人脉不广、消息闭塞而难以寻找队友的痛点。

- 项目目前处于开发阶段，由于开发者本人目前还是学生，所以开发进度会比较慢，如果你对本项目有兴趣，欢迎参与贡献 or 提一些需求（issues）。

- 此项目为Java后台接口，小程序前端移步至 -> https://gitee.com/wenjie2018/UT-WeChat （仍处于开发阶段）

#### 简单规划

- 先把组队功能完善好了，之后再听取各方意见完善其它功能。

#### 实现选型

- 考虑到数据的量级只是一个学校，同时为了降低软件部署的成本，目前仅打算使用MySQL、redis实现后端，最终目标是希望一台2G的服务器+七牛cdn就能流畅运行应用（之后会考虑兼容其它cdn，如阿里oss等）

#### 开发者须知

- 统一使用gitee：https://gitee.com/wenjie2018/UT-APP （集成了CI/CD）
- 代码风格：风格约束配置请参考 -> https://halo.run/archives/code-style  不懂的也可以问我。
- 提交PR： 提交的PR后都会经过jenkins的编译、打包测试，通过了会自动在PR下留言测试结果， **请务必保证测试结果通过** （现阶段PR里面一个失败一个成功，可以参考下）。  
- 自定义环境：如果你因为某种原因，需要更改`application.yml`的一些配置，比如开启debug，那么你完全可以在本地的`{user.home}/.ut`目录下创建`application.yml`文件，它可以有选择性地覆盖项目中默认的配置项。

#### 计划实现

- websocket实时推送消息（Netty实现）...✔
- 组队...ing（后端基本完成）
- bbs...ing（后端基本完成）
- 校园活动发布/订阅...ing（后端基本完成）

#### 参与贡献

1.  Fork 本仓库
2.  新建 UT_APP_XXX 分支
3.  提交代码
4.  新建 Pull Request

#### 鸣谢

- 特别感谢[@施晓权](https://gitee.com/sxq2017)提供的服务器，用于部署jenkins等应用。


