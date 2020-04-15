<#assign base=request.contextPath />
<!DOCTYPE html>
<html class="x-admin-sm">
<head>
    <meta charset="UTF-8">
    <title>系统日志</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
    <link rel="stylesheet" href="${base}/css/font.css">
    <link rel="stylesheet" href="${base}/css/xadmin.css">
    <link rel="stylesheet" href="${base}/css/dark.css">
    <script src="${base}/lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="${base}/js/xadmin.js"></script>
    <script type="text/javascript" src="${base}/js/store.legacy.min.js"></script>
    <script type="text/javascript" src="${base}/js/highlight.pack.js"></script>
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
                    <form class="layui-form layui-col-space5">
                        <div class="layui-inline">
                            <input type="text" name="lines" lay-verify="number" placeholder="请输入行数" autocomplete="off" class="layui-input">
                        </div>
                        <div class="layui-inline">
                            <button class="layui-btn"  lay-submit="" lay-filter="search"><i class="layui-icon">&#xe615;</i></button>
                        </div>
                    </form>
                </div>
                <div class="layui-card-body">
                    <pre><code id="log" class='hljs'></code></pre>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    layui.use(['jquery', 'layer', 'form'], function() {
        var $ = layui.jquery;
        var layer = layui.layer;
        var form = layui.form;

        if (store.enabled) {
            var user = store.get('user');
            if (!user) {
                if (self === top) {
                    layer.msg("请重新登录！", {icon: 5, anim: 6}, function () {
                        goLogin("${base}");
                    });
                }
            } else {
                //给ajax加上token
                $.ajaxSetup({
                    headers: {
                        "UT-Token": user.token.access_token
                    }
                });

                //ini
                $.ajax({
                    type: "GET",
                    url: "${base}/admin/log/ut/spring_log_file",
                    success: function(res) {
                        $('#log').html(res.message);
                    },
                    error: function ajaxTokenError (jqXHR) {
                        failReqHandler("${base}",jqXHR);
                    }
                });

                //搜索
                form.on('submit(search)', function(data){
                    var data = data.field;
                    $.ajax({
                        type: "GET",
                        url: "${base}/admin/log/ut/spring_log_file",
                        data: {lines: data.lines},
                        success: function(res) {
                            $('#log').html(res.message);
                        },
                        error: function ajaxTokenError (jqXHR) {
                            failReqHandler("${base}",jqXHR);
                        }
                    });
                    return false;
                });
            }
        }else {
            layer.msg("请关闭无痕模式！", {icon: 5});
        }
    });
</script>
</body>
</html>