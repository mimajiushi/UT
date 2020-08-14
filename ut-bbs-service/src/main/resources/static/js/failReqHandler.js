function failReqHandler(baseUrl, jqXHR) {
    var res = $.parseJSON(jqXHR.responseText);
    if (res.status == 401) {
        layer.msg("请重新登录！", {icon: 5}, function () {
            goLogin(baseUrl);
        });
    } else {
        layer.msg(res.message, {icon: 5});
    }
}

function goLogin(baseUrl) {
    location.href = baseUrl + '/admin/login';
}