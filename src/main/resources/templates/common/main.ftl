<#assign base=request.contextPath />
<!doctype html>
<html class="x-admin-sm">
<head>
    <meta charset="UTF-8">
    <title>管理系统</title>
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link rel="stylesheet" href="${base}/css/font.css">
    <link rel="stylesheet" href="${base}/css/xadmin.css">
    <link rel="stylesheet" href="${base}/css/theme.min.css">
    <script src="${base}/lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="${base}/js/store.legacy.min.js"></script>
    <script type="text/javascript" src="${base}/js/xadmin.js"></script>
    <script type="text/javascript" src="${base}/js/failReqHandler.js"></script>
    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script>
        // 是否开启刷新记忆tab功能
        // var is_remember = false;
    </script>
</head>
<body class="index">
<!-- 顶部开始 -->
<div class="container">
    <div class="logo">
        <a href="${base}/admin/index">管理系统</a></div>
    <div class="left_open">
        <a><i title="展开左侧栏" class="iconfont">&#xe699;</i></a>
    </div>
    <ul class="layui-nav right" lay-filter="">
        <li class="layui-nav-item">
            <a href="javascript:;" id="nickname"></a>
            <dl class="layui-nav-child">
                <!-- 二级菜单 -->
                <dd>
                    <a onclick="xadmin.open('个人信息','${base}/admin/info')">个人信息</a></dd>
                <dd>
                    <a id="logout">注销</a></dd>
            </dl>
        </li>
    </ul>
</div>
<!-- 顶部结束 -->
<!-- 中部开始 -->
<!-- 左侧菜单开始 -->
<div class="left-nav">
    <div id="side-nav">
        <ul id="nav">
            <li>
                <a href="javascript:;">
                    <i class="iconfont left-nav-li" lay-tips="用户管理">&#xe6b8;</i>
                    <cite>用户管理</cite>
                    <i class="iconfont nav_right">&#xe697;</i></a>
                <ul class="sub-menu">
                    <li>
                        <a onclick="xadmin.add_tab('审核列表','${base}/admin/verifyList')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>审核列表</cite></a>
                    </li>
                </ul>
            </li>
            <li>
                <a onclick="">
                    <i class="iconfont left-nav-li" lay-tips="标签管理">&#xe723;</i>
                    <cite>标签管理</cite>
                    <i class="iconfont nav_right">&#xe697;</i></a>
                <ul class="sub-menu">
                    <li>
                        <a onclick="xadmin.add_tab('标签列表','${base}/admin/tagsList')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>标签列表</cite></a>
                    </li>
                </ul>
            </li>
            <li>
                <a onclick="">
                    <i class="iconfont left-nav-li" lay-tips="活动管理">&#xe6bb;</i>
                    <cite>活动管理</cite>
                    <i class="iconfont nav_right">&#xe697;</i></a>
                <ul class="sub-menu">
                    <li>
                        <a onclick="xadmin.add_tab('活动列表','${base}/admin/activityList')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>活动列表</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('发布活动','${base}/admin/activityAdd')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>发布活动</cite></a>
                    </li>
                </ul>
            </li>
            <li>
                <a onclick="">
                    <i class="iconfont left-nav-li" lay-tips="系统管理">&#xe6ae;</i>
                    <cite>系统管理</cite>
                    <i class="iconfont nav_right">&#xe697;</i></a>
                <ul class="sub-menu">
                    <li>
                        <a onclick="xadmin.add_tab('查看日志','${base}/admin/log')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>查看日志</cite></a>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
</div>
<!-- 左侧菜单结束 -->
<!-- 右侧主体开始 -->
<div class="page-content">
    <div class="layui-tab tab" lay-filter="xbs_tab" lay-allowclose="false">
        <ul class="layui-tab-title">
            <li class="home">
                <i class="layui-icon">&#xe68e;</i>我的桌面</li></ul>
        <div class="layui-unselect layui-form-select layui-form-selected" id="tab_right">
            <dl>
                <dd data-type="this">关闭当前</dd>
                <dd data-type="other">关闭其它</dd>
                <dd data-type="all">关闭全部</dd></dl>
        </div>
        <div class="layui-tab-content">
            <div class="layui-tab-item layui-show">
                <iframe src='${base}/admin/welcome' frameborder="0" scrolling="yes" class="x-iframe"></iframe>
            </div>
        </div>
        <div id="tab_show"></div>
    </div>
</div>
<div class="page-content-bg"></div>
<style id="theme_style"></style>
<!-- 右侧主体结束 -->
<!-- 中部结束 -->
<script>
    layui.use(['jquery','layer'], function() {
        var $ = layui.jquery;
        var layer = layui.layer;

        if (store.enabled) {
            var user = store.get('user');

            if (!user) {
                layer.msg("请重新登录！", {icon: 5, anim: 6}, function () {
                    goLogin("${base}");
                });
            } else {

                //注销
                $('#logout').click(function () {
                    layer.confirm('确定注销?', {icon: 3, title: '提示'}, function (index) {
                        store.remove('user');
                        store.remove('token');
                        goLogin("${base}");
                        layer.close(index);
                    });
                });

                //init
                $(document).ready(function () {
                    $("#nickname").html(user.nickname);
                });
            }
        }else {
            top.layer.msg("请关闭无痕模式！", {icon: 5});
        }
    });
</script>
</body>
</html>