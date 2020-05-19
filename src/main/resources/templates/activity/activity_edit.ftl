<#assign base=request.contextPath />
<!DOCTYPE html>
<html class="x-admin-sm">
<head>
    <meta charset="UTF-8">
    <title>编辑活动</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
    <link rel="stylesheet" href="${base}/css/font.css">
    <link rel="stylesheet" href="${base}/css/xadmin.css">
    <script src="${base}/lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="${base}/js/xadmin.js"></script>
    <script type="text/javascript" src="${base}/js/store.legacy.min.js"></script>
    <script type="text/javascript" src="${base}/js/failReqHandler.js"></script>
    <script type="text/javascript" src="${base}/js/wangEditor.min.js"></script>
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
            <form class="layui-form" id="myForm" lay-filter="myForm">
                <input type="hidden" name="id" lay-verify="required" class="layui-input"/>
                <div class="layui-card">
                    <div class="layui-card-body">
                        <span>活动标题</span>
                        <input type="text" name="title" required="" lay-verify="title" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-card">
                    <div class="layui-card-body">
                        <div class="layui-inline">
                            <input type="text" class="layui-input" id="startTime" name="startTime" placeholder="选择活动开始时间" lay-verify="required" readonly="readonly">
                        </div>
                        <div class="layui-inline">
                            -
                        </div>
                        <div class="layui-inline">
                            <input type="text" class="layui-input" id="endTime" name="endTime" placeholder="选择活动截至时间" lay-verify="required" readonly="readonly">
                        </div>
                    </div>
                </div>
                <div class="layui-card">
                    <div class="layui-card-body">
                        <input type="hidden" name="cover" value="" required lay-verify="required" id="cover">
                        <button type="button" class="layui-btn layui-btn-primary" id="upload">
                            <i class="layui-icon">&#xe67c;</i>上传活动封面
                        </button>
                        <img class="layui-upload-img" id="demo" height="100" width="100">
                        <span id="demoText"></span>
                    </div>
                </div>
                <div class="layui-card">
                    <div class="layui-card-body ">
                        <span>活动内容</span>
                        <div id="content"></div>
                    </div>
                </div>
                <div class="layui-card">
                    <div class="layui-card-body ">
                        <input value="更新活动" class="layui-btn layui-btn-normal" lay-submit lay-filter="push" type="submit">
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script>
    layui.use(['form', 'jquery', 'layer', 'laydate', 'upload'], function(){
        var form = layui.form;
        var $ = layui.jquery;
        var layer = layui.layer;
        var laydate = layui.laydate;
        var upload = layui.upload;

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

                //富文本
                var E = window.wangEditor;
                var editor = new E('#content');
                editor.customConfig.uploadImgServer = '${base}/admin/uploads';
                editor.customConfig.uploadFileName = 'file';
                editor.customConfig.uploadImgHeaders = {
                    "UT-Token": user.token.access_token
                };
                editor.customConfig.customAlert = function (info) {
                    layer.msg(info, {icon: 5});
                };
                editor.create();

                //赋值
                $.ajax({
                    type: "GET",
                    url: "${base}/activity/detail/" + ${Request["activityId"]!"0"},
                    success: function(res){
                        var data = res.data;
                        form.val('myForm',{
                            id: data.id
                            , title: data.title
                            , startTime: data.startTime
                            , endTime: data.endTime
                            , cover: data.cover
                        });
                        $('#demo').attr('src', data.cover);
                        editor.txt.html(data.content);
                    },
                    error:function (jqXHR) {
                        failReqHandler("${base}",jqXHR);
                    }
                });

                //更新
                form.on('submit(push)', function(data){
                    if ($.trim(editor.txt.text()) === ""){
                        layer.msg("活动不能没有文字内容！", {icon: 5});
                        return false;
                    }

                    var data = data.field;
                    $.ajax({
                        type: "POST",
                        url: "${base}/admin/activity/saveActivity",
                        data: {
                            id: data.id
                            , title: data.title
                            , content: editor.txt.html()
                            , startTime: data.startTime
                            , endTime: data.endTime
                            , cover: data.cover
                        },
                        dataType: "json",
                        success: function(res){
                            layer.msg(res.message, {icon: 1}, function () {
                                var index = parent.layer.getFrameIndex(window.name);
                                parent.layer.close(index);
                                parent.layui.table.reload('activityList');
                            });
                        },
                        error:function (jqXHR) {
                            failReqHandler("${base}",jqXHR);
                        }
                    });
                    return false;
                });

                // 开始时间
                laydate.render({
                    elem: '#startTime',
                    type: 'datetime'
                });

                // 结束时间
                laydate.render({
                    elem: '#endTime',
                    type: 'datetime'
                });

                //演示失败状态，并实现重传
                function reupload() {
                    var demoText = $('#demoText');
                    demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-warm layui-btn-xs demo-reload">重试</a>');
                    demoText.find('.demo-reload').on('click', function () {
                        uploadInst.upload();
                    });
                }


                //封面上传
                var index;
                var uploadInst = upload.render({
                    elem: '#upload'
                    , url: '${base}/admin/uploads'
                    , accept: 'images'
                    , acceptMime: 'image/*'
                    , before: function (obj) {
                        //预读本地文件示例，不支持ie8
                        index = layer.load(2);
                        obj.preview(function (index, file, result) {
                            $('#demo').attr('src', result);
                        });
                    }
                    , done: function (data) {
                        if (data.status === 200) {
                            $('#cover').val(data.data[0]);
                            layer.msg(data.message, {icon: 1});
                            $('#demoText').html("");
                            layer.close(index);
                        } else {
                            layer.msg(data.message, {icon: 5});
                            reupload();
                            layer.close(index);
                        }
                    }
                    , error: function () {
                        reupload();
                        layer.close(index);
                    }
                });
            }
        }else {
            top.layer.msg("请关闭无痕模式！", {icon: 5});
        }
    });
</script>
</body>
</html>