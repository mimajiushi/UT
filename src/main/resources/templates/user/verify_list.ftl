<#assign base=request.contextPath />
<!DOCTYPE html>
<html class="x-admin-sm">
<head>
    <meta charset="UTF-8">
    <title>审核列表</title>
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
                            <select name="status" lay-filter="selectStatus">
                                <option value="">状态</option>
                                <option value="0">审核中</option>
                                <option value="1">审核通过</option>
                                <option value="-1">审核不通过</option>
                            </select>
                        </div>
                    </form>
                </div>
                <div class="layui-card-body ">
                    <table class="layui-table" lay-filter="verifyList" id="verifyList"></table>
                </div>
            </div>
        </div>
    </div>
</div>
<#--详情模板-->
<script type="text/html" id="detailTemplate">
    <div class="layui-fluid">
        <div class="layui-row layui-col-space15">
            <div class="layui-col-md12">
                <div class="layui-card">
                    <div class="layui-card-body ">
                        <table class="layui-table" lay-filter="userDetail" id="userDetail">
                            <colgroup>
                                <col width="150">
                                <col width="200">
                                <col>
                            </colgroup>
                            <tbody>
                            <tr>
                                <td>创建时间</td>
                                <td>{{ d.createTime }}</td>
                            </tr>
                            <tr>
                                <td>更新时间</td>
                                <td>{{ d.updateTime }}</td>
                            </tr>
                            <tr>
                                <td>用户编号</td>
                                <td>{{ d.uid }}</td>
                            </tr>
                            <tr>
                                <td>学校</td>
                                <td>{{ d.school }}</td>
                            </tr>
                            <tr>
                                <td>学历</td>
                                <td>{{ d.degreeId }}</td>
                            </tr>
                            <tr>
                                <td>角色</td>
                                <td>{{ d.roles }}</td>
                            </tr>
                            <tr>
                                <td>年级</td>
                                <td>{{ d.grade }}</td>
                            </tr>
                            <tr>
                                <td>专业</td>
                                <td>{{ d.subject }}</td>
                            </tr>
                            <tr>
                                <td>姓名</td>
                                <td>{{ d.realName }}</td>
                            </tr>
                            <tr>
                                <td>证件照正面</td>
                                <td>{{#  if(!!d.credentialsPhotoFront){ }}
                                    <div id="layer-photos-one">
                                        <img src="{{= d.credentialsPhotoFront}}" height="50"  onclick="photo('layer-photos-one')">
                                    </div>
                                    {{#  } }}</td>
                            </tr>
                            <tr>
                                <td>证件照背面</td>
                                <td>{{#  if(!!d.credentialsPhotoReverse){ }}
                                    <div id="layer-photos-two">
                                        <img src="{{= d.credentialsPhotoReverse}}" height="50"  onclick="photo('layer-photos-two')">
                                    </div>
                                    {{#  } }}</td>
                            </tr>
                            <tr>
                                <td>状态</td>
                                <td>{{ d.status }}</td>
                            </tr>
                            <tr>
                                <td>理由</td>
                                <td>{{ d.reason }}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</script>
<#--相册模板-->
<script type="text/html" id="photoTemplate">
    {{#  if(!!d.credentialsPhotoFront && !!d.credentialsPhotoReverse){ }}
    <div class="layer-photos-{{ d.id }}">
        <img src="{{= d.credentialsPhotoFront}}" height="45">
        <img src="{{= d.credentialsPhotoReverse}}" height="45">
    </div>
    {{#  } }}
</script>
<#--状态模板-->
<script type="text/html" id="statusTemplate">
    {{#  if(d.status == '审核不通过'){ }}
    <span class="layui-btn layui-btn-danger layui-btn-mini">审核未通过</span>
    {{#  }else if(d.status == '审核通过'){ }}
    <span class="layui-btn layui-btn layui-btn-mini">审核通过</span>
    {{#  }else if(d.status == '审核中'){ }}
    <span class="layui-btn layui-btn-normal layui-btn-mini" lay-event="change">审核</span>
    {{#  } }}
</script>
<script>
    layui.use(['form', 'jquery', 'layer', 'table', 'laytpl'], function(){
        var form = layui.form;
        var $ = layui.jquery;
        var layer = layui.layer;
        var laytpl = layui.laytpl;
        var table = layui.table;

        window.photo = function(obj) {
            layer.photos({
                photos: '#'+obj
                ,anim: 5
            });
        };

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

                //监听表格
                table.on('tool(verifyList)',function(obj){
                    var data = obj.data;

                    //照片点击
                    if(obj.event === 'photo'){
                        layer.photos({
                            photos: '.layer-photos-' + data.id
                            ,anim: 5
                        });
                    } else if (obj.event === 'change') { //状态审核
                        layer.confirm("是否通过审核？",{
                            btn: ['通过', '不通过'], title: ""
                        }, function(index) {
                            $.ajax({
                                type: "POST",
                                url: "${base}/admin/verifyUserInfo",
                                data: {id: data.id, status: 1},
                                success: function(res) {
                                    obj.update({
                                        status: "审核通过"
                                    });
                                    layer.close(index);
                                    layer.msg(res.message, {icon: 1});
                                },
                                error: function ajaxTokenError (jqXHR) {
                                    var res = $.parseJSON(jqXHR.responseText);
                                    if (res.status == 401) {
                                        layer.msg("请重新登录！", {icon: 5}, function () {
                                            location.href = '${base}/admin/login';
                                        });
                                    }else {
                                        layer.close(index);
                                        layer.msg(res.message, {icon: 5});
                                    }
                                }
                            });
                        }, function(index) {
                            $.ajax({
                                type: "POST",
                                url: "${base}/admin/verifyUserInfo",
                                data: {id: data.id, status: -1},
                                success: function(res) {
                                    obj.update({
                                        status: '审核不通过'
                                    });
                                    layer.close(index);
                                    layer.msg(res.message, {icon: 1});
                                },
                                error: function ajaxTokenError (jqXHR) {
                                    var res = $.parseJSON(jqXHR.responseText);
                                    if (res.status == 401) {
                                        layer.msg("请重新登录！", {icon: 5}, function () {
                                            location.href = '${base}/admin/login';
                                        });
                                    }else {
                                        layer.close(index);
                                        layer.msg(res.message, {icon: 5});
                                    }
                                }
                            });
                        });
                    }else if (obj.event === 'detail') {
                        laytpl($('#detailTemplate').html()).render(data, function(html){
                            layer.open({
                                type: 1
                                ,title: '详情'
                                ,content: html
                                ,maxmin: true
                                ,area: ['600px', '400px']
                            });
                        });
                    }
                });

                //渲染表格
                var verifyList = table.render({
                    elem: '#verifyList'
                    , id: 'id'
                    , url: '${base}/admin/listUserInfoByParam'
                    , title: '用户列表'
                    , cols: [[
                        {field: 'uid', title: '用户编号'}
                        , {field: 'realName', title: '姓名'}
                        , {field: 'status', title: '状态', templet: '#statusTemplate'}
                        , {field: 'credentialsPhoto', title: '证件照', event: 'photo', templet: '#photoTemplate'}
                        , {field: 'detail', title: '操作', align: 'center', templet: function () {
                                return  ' <a title="详情" href="javascript:;">' +
                                    '<i class="layui-icon" lay-event="detail">&#xe63c;</i></a>';
                            }}
                    ]]
                    , toolbar: true
                    , page: true
                    ,request: {
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

                //下拉
                form.on('select(selectStatus)', function(obj) {
                    verifyList.reload({
                        where: {
                            //设定异步数据接口的额外参数
                            status: obj.value
                        },
                        page: {
                            curr: 1 //重新从第 1 页开始
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