<#assign base=request.contextPath />
<!doctype html>
<html  class="x-admin-sm">
<head>
    <meta charset="UTF-8">
    <title>后台登录</title>
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link rel="stylesheet" href="${base}/css/font.css">
    <link rel="stylesheet" href="${base}/css/login.css">
    <link rel="stylesheet" href="${base}/css/xadmin.css">
    <script src="${base}/lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="${base}/js/store.legacy.min.js"></script>
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body class="login-bg">

<div class="login layui-anim layui-anim-up">
    <div class="message">管理登录</div>
    <div id="darkbannerwrap"></div>

    <form method="post" class="layui-form" >
        <div class="layui-form-item">
            <input name="phoneNumber" id="phoneNumber" placeholder="手机号"  type="text" lay-verify="required|phone" class="layui-input-normal" >
        </div>
        <hr class="hr15">
        <div class="layui-form-item layui-col-space10">
            <div class="layui-col-xs7">
                <input name="smsCode" lay-verify="required" type="text" placeholder="验证码" class="layui-input-normal" style="width: 100%">
            </div>
            <div class="layui-col-xs5">
                <button id="getVerifyCode" type="button" class="layui-btn layui-btn-normal" style="width: 100%;height: 50px;">获取验证码</button>
            </div>
        </div>
        <hr class="hr15">
        <div class="layui-form-item">
            <input value="登录" lay-submit lay-filter="login" id="login" style="width:100%;" type="submit">
        </div>
        <hr class="hr15" >
    </form>
</div>

<script>
    layui.use(['form','jquery','layer'], function(){
        var form = layui.form;
        var $ = layui.jquery;
        var layer = layui.layer;

        //登录
        form.on('submit(login)', function(data){
            var data = data.field;
            $("#login").attr("disabled", true);
            $.ajax({
                type: "POST",
                url: "${base}/user/webPageLogin",
                data: {phoneNumber:$.trim(data.phoneNumber),smsCode:$.trim(data.smsCode)},
                dataType: "json",
                success: function(res){
                    if (store.enabled) {
                        store.set('user', res.data);
                        layer.msg('登陆成功！', {icon: 1}, function () {
                            location.href = '${base}/admin/index'
                        });
                    }else {
                        layer.msg("请关闭无痕模式！", {icon: 5});
                        $("#login").attr("disabled", false);
                    }
                },
                error:function (jqXHR) {
                    var res = $.parseJSON(jqXHR.responseText);
                    layer.msg(res.message, {icon: 5});
                    $("#login").attr("disabled", false);
                }
            });
            return false;
        });

        // 验证手机号
        function isPhoneNo(phone) {
            var pattern = /^1[34578]\d{9}$/;
            return pattern.test(phone);
        }

        //倒计时
        var wait = 60;
        function time(o) {
            if (wait == 0) {
                $(o).attr("disabled", false);
                $(o).attr("class", "layui-btn layui-btn-normal");
                $(o).html("获取验证码");
                wait = 60;
            } else {
                $(o).attr("disabled", true);
                $(o).attr("class", "layui-btn layui-btn-disabled");
                $(o).html(wait + "秒后重新发送");
                wait--;
                setTimeout(function () {time(o);},1000);
            }
        }

        //发送验证码
        $('#getVerifyCode').click(function () {
            if ($.trim($("#phoneNumber").val()).length == 0) {
                layer.msg("手机号没有输入！", {icon: 5, anim: 6});
                $("#phoneNumber")
            } else {
                if (isPhoneNo($.trim($("#phoneNumber").val())) == false) {
                    layer.msg("手机号码不正确！", {icon: 5, anim: 6});
                }else {
                    $.ajax({
                        type: "POST",
                        url: "${base}/user/sendSms",
                        data: {phoneNumber:$.trim($("#phoneNumber").val())},
                        dataType: "json",
                        success: function(res){
                                var bu = $('#getVerifyCode');
                                time(bu);
                        },
                        error:function (jqXHR) {
                            var res = $.parseJSON(jqXHR.responseText);
                            layer.msg(res.message, {icon: 5});
                        }
                    });
                }
            }

        });

    });
</script>
</body>
</html>