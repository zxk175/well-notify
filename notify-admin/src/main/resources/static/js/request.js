let ctx = window.location.origin;

axios.defaults.timeout = 60 * 1000;
axios.defaults.withCredentials = true;

axios.interceptors.request.use(function (config) {
    config.headers = {
        'Content-Type': 'application/json',
        'X-Requested-With': 'XMLHttpRequest'
    };

    if (config.auth) {
        config.headers.token = getToken();
    }

    return config
}, function (err) {
    error('请求超时');

    return Promise.resolve(err)
});

axios.interceptors.response.use(function (data) {
    checkSuccess(data);

    return data;
}, function (err) {
    checkError(err);

    return Promise.resolve(err);
});

function get(url, success, error) {
    return axios({
        url: url,
        method: 'get'
    }).then(success).catch(error)
}

function getAuth(url, params, success) {
    return axios({
        url: url,
        auth: true,
        method: 'get',
        data: params
    }).then(success)
}

function post(url, params, success) {
    return axios({
        url: url,
        method: 'post',
        data: params
    }).then(success)
}

function postAuth(url, params, success) {
    return axios({
        url: url,
        auth: true,
        method: 'post',
        data: params
    }).then(success)
}

function del(url, success) {
    return axios({
        url: url,
        method: 'delete'
    }).then(success)
}

function put(url, params, success) {
    return axios({
        url: url,
        method: 'put',
        data: params
    }).then(success)
}

function putAuth(url, params, success) {
    return axios({
        url: url,
        auth: true,
        method: 'put',
        data: params
    }).then(success)
}

function toLoginPage() {
    location.href = ctx + "/#/login";
}

function getToken() {
    let token = sessionStorage.getItem("token");
    if (token) {
        return token;
    } else {
        toLoginPage();
        // 推出调用函数 即父函数
        throw new Error("请先登录");
    }
}

function getQueryString(name) {
    let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    // search,查询？后面的参数，并匹配正则
    let regMatch = window.location.search.substr(1).match(reg);

    if (regMatch == null) {
        return null;
    }

    return unescape(regMatch[2]);
}

function checkSuccess(data) {
    let code = data.data.code;
    // 请求成功不显示消息
    if (code === 0) {
        return;
    }

    if (data.data.success) {
        success(data.data.msg);
    } else {
        error(data.data.msg);
    }
}

function checkError(err) {
    if (err.response) {
        const status = err.response.status;
        switch (status) {
            case 401:
                error("未授权，请登录");
                break;
            case 403:
                error("拒绝访问");
                break;
            case 404:
                error(`请求地址不存在: ${err.response.config.url}`);
                break;
            case 500:
                error("服务器内部错误");
                break;
            case 502:
                error("网络错误或服务器关闭");
                break;
            default:
                error(`(${status}) 未知错误`);
                break;
        }
    } else {
        let msg = err.message;
        if (msg && msg.indexOf("timeout") > -1) {
            error("请求超时");
        } else if (msg && msg.indexOf("Network") > -1) {
            error("网络错误或服务器关闭");
        } else {
            error(msg || '未知错误');
        }
    }
}

function success(msg) {
    this.ELEMENT.Message.success({
        message: msg,
        type: 'success',
        offset: 30,
        duration: 1200,
        showClose: true
    });
}

function error(msg) {
    this.ELEMENT.Message.error({
        message: msg,
        type: 'error',
        offset: 30,
        duration: 2000,
        showClose: true
    });
}

function removeMsgBox(that, callback) {
    const h = that.$createElement;
    this.ELEMENT.MessageBox({
        title: "提示",
        type: 'warning',
        message: h('p', null, [
            h('span', null, '确定对选中数据进行['),
            h('span', {style: 'color: red;'}, '批量删除'),
            h('span', null, ']操作?'),
        ]),
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消',
    }).then(() => {
        callback();
    }).catch(() => {
        this.ELEMENT.Message.info("已取消删除");
    });
}

function getReferer() {
    if (document.referrer) {
        return document.referrer;
    } else {
        return false;
    }
}