<#assign base=request.contextPath />
<!DOCTYPE html>
<html class="x-admin-sm">
<head>
    <meta charset="UTF-8">
    <title>欢迎页面</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
    <link rel="stylesheet" href="${base}/css/font.css">
    <link rel="stylesheet" href="${base}/css/xadmin.css">
    <script src="${base}/lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="${base}/js/xadmin.js"></script>
    <script type="text/javascript" src="${base}/js/store.legacy.min.js"></script>
    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-body ">
                    <blockquote class="layui-elem-quote" id="welcomeInfo"></blockquote>
                </div>
            </div>
        </div>
        <div class="layui-col-md12">
            <div class="layui-card" id="systemInfo">
                <#-- 系统信息模板 -->
                <script id="systemInfoTemplate" type="text/html">
                    <div class="layui-card-header">系统信息</div>
                    <div class="layui-card-body ">
                        <table class="layui-table">
                            <tbody>
                            <tr>
                                <th>Java版本</th>
                                <td>{{ d.java }}</td></tr>
                            <tr>
                                <th>JVM</th>
                                <td>{{ d.JVM }}</td></tr>
                            <tr>
                                <th>服务器版本</th>
                                <td>{{ d.server }}</td></tr>
                            <tr>
                                <th>操作系统</th>
                                <td>{{ d.osName }}</td></tr>
                            <tr>
                                <th>系统架构</th>
                                <td>{{ d.osArch }}</td></tr>
                            <tr>
                                <th>系统版本</th>
                                <td>{{ d.osVersion }}</td></tr>
                            <tr>
                                <th>CPU核数</th>
                                <td>{{ d.cpu }}</td></tr>
                            </tbody>
                        </table>
                    </div>
                </script>
            </div>
        </div>
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">开发团队</div>
                <div class="layui-card-body ">
                    <table class="layui-table">
                        <tbody>
                        <tr>
                            <th>开发者</th>
                            <td>Lucien,ChenWenjie,ShiXiaoquan,FenFeng</td></tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <style id="welcome_style"></style>
        <div class="layui-col-md12">
            <blockquote class="layui-elem-quote layui-quote-nm">感谢layui,百度Echarts,jquery,本系统由x-admin提供技术支持。</blockquote></div>
    </div>
</div>
</div>
<script>
    layui.use(['jquery', 'layer', 'laytpl'], function() {
        var $ = layui.jquery;
        var laytpl = layui.laytpl;
        var layer = layui.layer;

        //init
        $(document).ready(function () {
            if (store.enabled) {
                var user = store.get('user');
                if (typeof(user) === undefined){
                    layer.msg("请重新登录！", {icon: 5, anim: 6}, function () {
                        location.href = '${base}/admin/login';
                    });
                }else {
                    //欢迎信息
                    $("#welcomeInfo").html('欢迎管理员：<span class="x-red">' + user.nickname + '</span>');

                    //系统信息
                    $.ajax({
                        headers: {
                            "UT-Token": user.token.access_token
                        },
                        type: "GET",
                        url: "${base}/admin/systemInfo",
                        success: function(res) {
                            laytpl($("#systemInfoTemplate").html()).render(res.data, function(html){
                                $("#systemInfo").html(html);
                            });
                        },
                        error: function ajaxTokenError (jqXHR) {
                            var res = $.parseJSON(jqXHR.responseText);
                            if (res.status == 401) {
                                layer.msg("请重新登录！", {icon: 5}, function () {
                                    location.href = '${base}/admin/login';
                                });
                            }else {
                                layer.msg(res.message, {icon: 5});
                            }
                        }
                    });
                }
            }else {
                layer.msg("请关闭无痕模式！", {icon: 5});
            }
        });
    });
</script>
</body>
</html>