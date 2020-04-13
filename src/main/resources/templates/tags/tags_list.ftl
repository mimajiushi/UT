<#assign base=request.contextPath />
<!DOCTYPE html>
<html class="x-admin-sm">
<head>
    <meta charset="UTF-8">
    <title>标签列表</title>
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
                            <button class="layui-btn"  lay-submit="" lay-filter="add"><i class="layui-icon"></i>增加标签</button>
                            <button class="layui-btn layui-btn-danger" lay-submit="" lay-filter="delAll"><i class="layui-icon"></i>批量删除</button>
                        </div>
                    </form>
                </div>
                <div class="layui-card-body ">
                    <table class="layui-table" lay-filter="tagsList" id="tagsList"></table>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/html" id="toolTemplate">
    <button class="layui-btn-normal layui-btn layui-btn-xs" lay-event="update"><i class="layui-icon">&#xe642;</i>更新</button>
    <button class="layui-btn-danger layui-btn layui-btn-xs" lay-event="delete"><i class="layui-icon">&#xe640;</i>删除</button>
</script>
<script>
    layui.use(['jquery', 'layer', 'table', 'form'], function() {
        var $ = layui.jquery;
        var layer = layui.layer;
        var table = layui.table;
        var form = layui.form;

        if (store.enabled) {
            var user = store.get('user');
            if (!user) {
                layer.msg("请重新登录！", {icon: 5, anim: 6}, function () {
                    location.href = '${base}/admin/login';
                });
            } else {
                //给ajax加上token
                $.ajaxSetup({
                    headers: {
                        "UT-Token": user.token.access_token
                    }
                });

                //批量删除
                form.on('submit(delAll)', function() {
                    var obj = table.checkStatus('tagsList');
                    var data = obj.data;

                    if(data.length === 0) {
                        layer.msg('请选择一行', {icon: 5});
                    }else {
                        var ids = [];
                        for(var i=0;i<data.length;i++) {
                            ids.push(data[i].id)
                        }
                        layer.confirm('确定删除?', {icon: 3, title: '提示'}, function (index) {
                            $.ajax({
                                type: "POST",
                                dataType : 'json',
                                contentType : 'application/json',
                                url: "${base}/admin/delTags",
                                data: JSON.stringify(ids),
                                success: function (res) {
                                    tagsList.reload();
                                    layer.msg(res.message, {icon: 1});
                                },
                                error: function ajaxTokenError(jqXHR) {
                                    failReqHandler("${base}",jqXHR);
                                }
                            });
                            layer.close(index);
                        });
                    }
                    return false;
                });

                //监听增加
                form.on('submit(add)', function(){
                    layer.prompt({
                        formType: 3,
                        title: '增加标签',
                    }, function(value, index) {
                        $.ajax({
                            type: "POST",
                            url: "${base}/admin/saveTag",
                            data: {
                                parentId: 0
                                , name: value
                            },
                            success: function (res) {
                                tagsList.reload();
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

                //监听表格工具栏
                table.on('tool(tagsList)',function(obj) {
                    var data = obj.data;

                    //更新
                    if (obj.event === 'update') {
                        layer.prompt({
                            formType: 3,
                            title: '更新',
                            value: data.name
                        }, function(value, index) {
                            $.ajax({
                                type: "POST",
                                url: "${base}/admin/saveTag",
                                data: {
                                    id: data.id
                                    , parentId: data.parentId
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
                    } else if (obj.event === 'delete'){ //删除
                        layer.confirm('确定删除?', {icon: 3, title: '提示'}, function (index) {
                            $.ajax({
                                type: "POST",
                                dataType : 'json',
                                contentType : 'application/json',
                                url: "${base}/admin/delTags",
                                data: JSON.stringify([data.id]),
                                success: function (res) {
                                    obj.del();
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

                //渲染表格
                var tagsList = table.render({
                    elem: '#tagsList'
                    , url: '${base}/tags/listAllTags'
                    , title: '标签列表'
                    , cols: [[
                        {type: 'checkbox'}
                        , {field: 'id', title: 'ID'}
                        , {field: 'name', title: '名称'}
                        , {field: 'updateTime', title: '更新时间'}
                        , {field: 'detail', title: '操作', align: 'center', templet: '#toolTemplate'}
                    ]]
                    , toolbar: true
                    , response: {
                        statusCode: 200
                    }
                    , parseData: function (res) {
                        return {
                            "data": res.data,
                            "code": res.status,
                            "msg": res.message
                        };
                    }
                });
            }
        }else {
            layer.msg("请关闭无痕模式！", {icon: 5});
        }
    });
</script>
</body>
</html>