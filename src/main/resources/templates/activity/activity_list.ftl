<#assign base=request.contextPath />
<!DOCTYPE html>
<html class="x-admin-sm">
<head>
    <meta charset="UTF-8">
    <title>活动列表</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
    <link rel="stylesheet" href="${base}/css/font.css">
    <link rel="stylesheet" href="${base}/css/xadmin.css">
    <style>
        .layui-table-cell{
            height:auto !important;
        }
    </style>
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
<div class="x-nav">
    <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right" onclick="location.reload()" title="刷新">
        <i class="layui-icon layui-icon-refresh" style="line-height:30px"></i>
    </a>
</div>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-body ">
                    <form class="layui-form layui-col-space5">
                        <div class="layui-inline">
                            <input type="text" name="key" lay-verify="search" placeholder="请输入活动标题" autocomplete="off" class="layui-input">
                        </div>
                        <div class="layui-inline">
                            <button class="layui-btn"  lay-submit="" lay-filter="search"><i class="layui-icon">&#xe615;</i></button>
                        </div>
                    </form>
                </div>
                <div class="layui-card-body ">
                    <table class="layui-table" lay-filter="activityList" id="activityList"></table>
                </div>
            </div>
        </div>
    </div>
</div>
<#--相册模板-->
<script type="text/html" id="photoTemplate">
    <div class="layer-photos-{{ d.id }}">
        <img src="{{= d.cover}}" height="45">
    </div>
</script>
<#--内容模板-->
<script type="text/html" id="contentTemplate">
    <div class="layui-fluid">
        <div class="layui-card">
            <div class="layui-card-body ">
                {{ d.content }}
            </div>
        </div>
    </div>
</script>
<#--工具条模板-->
<script type="text/html" id="tools">
    <button class="layui-btn layui-btn-warm layui-btn-xs"  lay-event="content">
        <i class="layui-icon">&#xe63c;</i>查看内容
    </button>
</script>
<script>
    layui.use(['form', 'jquery', 'layer', 'table', 'laytpl'], function(){
        var form = layui.form;
        var $ = layui.jquery;
        var layer = layui.layer;
        var laytpl = layui.laytpl;
        var table = layui.table;

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

                //渲染表格
                var activityList = table.render({
                    elem: '#activityList'
                    , url: '${base}/activity/list/activities?order=create_time'
                    , title: '活动列表'
                    , cols: [[
                        {type: 'radio'}
                        , {field: 'title', title: '标题'}
                        , {field: 'cover', title: '封面图', event: 'photo', templet: '#photoTemplate'}
                        , {field: 'appointmentCount', title: '预约数'}
                        , {field: 'startTime', title: '开始时间'}
                        , {field: 'endTime', title: '结束时间'}
                        , {title: '查看内容', align: 'center', templet: '#tools'}
                    ]]
                    , toolbar: 'default'
                    , page: true
                    , request: {
                        pageName: 'pageNum'
                        ,limitName: 'pageSize'
                    }
                    , response: {
                        statusCode: 200
                    }
                    , parseData: function (res) {
                        return {
                            "code": res.status,
                            "msg": res.message,
                            "count": res.data.total,
                            "data": res.data.rows
                        };
                    }
                });

                //搜索
                form.on('submit(search)', function(data){
                    var data = data.field;
                    activityList.reload({
                        where: {
                            title: data.key
                        }
                        ,page: {
                            curr: 1
                        }
                    });
                    return false;
                });


                //监听事件
                table.on('toolbar(activityList)', function(obj){
                    var checkStatus = table.checkStatus('activityList');
                    switch(obj.event){
                        case 'add':
                            xadmin.open('添加活动','${base}/admin/activityAdd',800,600);
                            break;
                        case 'delete':
                            if (checkStatus.data.length < 1){
                                layer.msg("请选择操作行！", {icon: 5});
                                return;
                            }
                            layer.confirm('确定删除?', {icon: 3, title: '提示'}, function (index) {
                                $.ajax({
                                    type: "POST",
                                    url: "${base}/admin/activity/delActivity/" + checkStatus.data[0].id,
                                    success: function (res) {
                                        activityList.reload();
                                        layer.msg(res.message, {icon: 1});
                                    },
                                    error: function ajaxTokenError(jqXHR) {
                                        failReqHandler("${base}",jqXHR);
                                    }
                                });
                                layer.close(index);
                            });
                            break;
                        case 'update':
                            if (checkStatus.data.length < 1){
                                layer.msg("请选择操作行！", {icon: 5});
                                return;
                            }
                            xadmin.open('编辑活动','${base}/admin/activityEdit/'+ checkStatus.data[0].id,800,600);
                            break;
                    }
                });

                //监听表格
                table.on('tool(activityList)',function(obj){
                    var data = obj.data;

                    //照片点击
                    if(obj.event === 'photo'){
                        layer.photos({
                            photos: '.layer-photos-' + data.id
                            ,anim: 5
                        });
                    } else if (obj.event === 'content') { //查看内容
                        $.ajax({
                            type: "GET",
                            url: "${base}/activity/detail/" + data.id,
                            success: function (res) {
                                laytpl($('#contentTemplate').html()).render(res.data, function(html){
                                    layer.open({
                                        type: 1
                                        ,title: '内容'
                                        ,content: html
                                        ,maxmin: true
                                        ,area: ['800px', '600px']
                                    });
                                });
                            },
                            error: function ajaxTokenError(jqXHR) {
                                failReqHandler("${base}",jqXHR);
                            }
                        });
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