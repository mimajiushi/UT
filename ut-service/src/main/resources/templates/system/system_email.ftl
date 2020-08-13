<#assign base=request.contextPath />
<!DOCTYPE html>
<html class="x-admin-sm">
<head>
    <meta charset="UTF-8">
    <title>mail设置</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
    <link rel="stylesheet" href="${base}/css/font.css">
    <link rel="stylesheet" href="${base}/css/xadmin.css">
    <script src="${base}/lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="${base}/js/xadmin.js"></script>
    <script type="text/javascript" src="${base}/js/store.legacy.min.js"></script>
    <script type="text/javascript" src="${base}/js/failReqHandler.js"></script>
    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="layui-fluid">
    <div class="layui-col-space15">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
            <legend>邮箱设置</legend>
        </fieldset>
        <div class="layui-card">
            <div class="layui-card-body ">
                <form class="layui-form" id="myForm" lay-filter="myForm">
                    <div class="layui-form-item">
                        <label class="layui-form-label">开启邮箱服务</label>
                        <div class="layui-input-inline">
                            <input type="radio" name="email_enabled" value="true" title="开启">
                            <input type="radio" name="email_enabled" value="false" title="关闭">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">发件人名字</label>
                        <div class="layui-input-inline">
                            <input type="text" name="email_from_name" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">Email地址</label>
                        <div class="layui-input-inline">
                            <input type="text" name="email_username" lay-verify="required|email" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">Email端口</label>
                        <div class="layui-input-inline">
                            <input type="text" name="email_ssl_port" lay-verify="required|number" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">Email协议</label>
                        <div class="layui-input-inline">
                            <input type="text" name="email_protocol" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">Email授权码</label>
                        <div class="layui-input-inline">
                            <input type="text" name="email_password" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">STMP地址</label>
                        <div class="layui-input-inline">
                            <input type="text" name="email_host" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-input-block">
                            <button type="submit" class="layui-btn layui-btn-normal" lay-submit="" lay-filter="save">保存</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    layui.use(['form', 'jquery', 'layer'], function(){
        var form = layui.form;
        var $ = layui.jquery;
        var layer = layui.layer;

        if (store.enabled) {
            var user = store.get('user');
            if (!user) {
                top.layer.msg("请重新登录！", {icon: 5, anim: 6}, function () {
                    goLogin("${base}");
                });
            } else {
                //给ajax加上token
                $.ajaxSetup({
                    headers: {
                        "UT-Token": user.token.access_token
                    }
                });

                //init
                $(document).ready(function () {
                    var index = layer.load(2);
                    $.ajax({
                        type: "GET",
                        url: "${base}/admin/option/emailOptions",
                        success: function(res){
                            var data = res.data;
                            var map = {};
                            for(var i = 0; i < data.length; i++) {
                                var key = data[i].key;
                                var value = data[i].value;
                                map[key] = value;
                            }
                            form.val("myForm", map);
                            layer.close(index);
                        },
                        error:function (jqXHR) {
                            failReqHandler("${base}",jqXHR);
                            layer.close(index);
                        }
                    });
                });


                //保存
                form.on('submit(save)', function(data){

                    var data = data.field;
                    $.ajax({
                        type: "POST",
                        url: "${base}/admin/option/saveOptionsWithMapView",
                        contentType:"application/json",
                        dataType : "json",
                        data: JSON.stringify(data),
                        success: function(res){
                            layer.msg(res.message, {icon: 1});
                        },
                        error:function (jqXHR) {
                            failReqHandler("${base}",jqXHR);
                        }
                    });
                    return false;
                });
            }
        }else {
            top.layer.msg("请关闭无痕模式！", {icon: 5});
        }
    });
</script>
</body>
</html>