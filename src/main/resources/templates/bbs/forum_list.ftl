<#assign base=request.contextPath />
<!DOCTYPE html>
<html class="x-admin-sm">
<head>
    <meta charset="UTF-8">
    <title>板块列表</title>
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
                        <div class="layui-input-inline layui-show-xs-block">
                            <button class="layui-btn"  lay-submit="" lay-filter="add"><i class="layui-icon"></i>增加板块</button>
                        </div>
                    </form>
                </div>
                <div class="layui-card-body ">
                    <table class="layui-table" lay-filter="forumList" id="forumList"></table>
                </div>
            </div>
        </div>
    </div>
</div>
<#--工具条模板-->
<script type="text/html" id="tools">
    <button class="layui-btn layui-btn-danger layui-btn-sm" lay-event="delete"><i class="layui-icon">&#xe640;</i>删除</button>
    <button class="layui-btn layui-btn-normal layui-btn-sm" lay-event="update"><i class="layui-icon">&#xe642;</i>编辑</button>
</script>
<script>
    layui.use(['form', 'jquery', 'layer', 'table'], function(){
        var form = layui.form;
        var $ = layui.jquery;
        var layer = layui.layer;
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
                var forumList = table.render({
                    elem: '#forumList'
                    , url: '${base}/forum/list/all'
                    , title: '板块列表'
                    , cols: [[
                        {field: 'name', title: '板块名'}
                        , {field: 'updateTime', title: '创建时间'}
                        , {title: '操作', align: 'center', templet: '#tools'}
                    ]]
                    , toolbar: true
                    , response: {
                        statusCode: 200
                    }
                    , parseData: function (res) {
                        return {
                            "code": res.status,
                            "msg": res.message,
                            "data": res.data
                        };
                    }
                });

                //监听表格
                table.on('tool(forumList)',function(obj){
                    var data = obj.data;

                    //删除
                    if(obj.event === 'delete') {
                        layer.confirm('确定删除?', {icon: 3, title: '提示'}, function (index) {
                            $.ajax({
                                type: "POST",
                                contentType: 'application/json',
                                url: "${base}/admin/forum/removeForum/" + data.id,
                                success: function (res) {
                                    obj.del();
                                    layer.msg(res.message, {icon: 1});
                                },
                                error: function ajaxTokenError(jqXHR) {
                                    failReqHandler("${base}", jqXHR);
                                }
                            });
                            layer.close(index);
                        });
                    } else if (obj.event === 'update') { //编辑
                        layer.prompt({
                            formType: 3,
                            title: '更新',
                            value: data.name
                        }, function(value, index) {
                            $.ajax({
                                type: "POST",
                                url: "${base}/admin/forum/saveForum",
                                data: {
                                    id: data.id
                                    , name: value
                                },
                                success: function (res) {
                                    obj.update({
                                        name: value
                                    });
                                    layer.msg(res.message, {icon: 1});
                                },
                                error: function ajaxTokenError(jqXHR) {
                                    failReqHandler("${base}",jqXHR);
                                }
                            });
                            layer.close(index);
                        });
                    }
                });

                //监听增加
                form.on('submit(add)', function(){
                    layer.prompt({
                        formType: 3,
                        title: '增加板块'
                    }, function(value, index) {
                        $.ajax({
                            type: "POST",
                            url: "${base}/admin/forum/saveForum",
                            data: {
                                name: value
                            },
                            success: function (res) {
                                forumList.reload();
                                layer.msg(res.message, {icon: 1});
                            },
                            error: function ajaxTokenError(jqXHR) {
                                failReqHandler("${base}",jqXHR);
                            }
                        });
                        layer.close(index);
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