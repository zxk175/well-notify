const NotFound = {template: '<p>页面不存在</p>'};

const routes = [
    {
        path: '*',
        component: NotFound,
    },
    {
        path: '/login',
        name: 'login',
        component: Login,
        meta: {
            title: '登录',
            icon: 'el-icon-menu'
        }
    },
    {
        path: '/',
        component: Layout,
        redirect: 'home',
        children: [
            {
                path: '/home',
                name: 'home',
                component: Home,
                meta: {
                    title: '首页',
                    icon: 'el-icon-menu'
                }
            }
        ]
    },
    {
        path: '/channel',
        component: Layout,
        children: [
            {
                path: '/channel-list',
                component: ChannelList,
                meta: {
                    title: '通道管理',
                    icon: 'el-icon-menu'
                }
            }
        ]
    },
    {
        path: '/channel-user',
        component: Layout,
        children: [
            {
                path: '/channel-user-list',
                component: ChannelUserList,
                meta: {
                    title: '通道用户',
                    icon: 'el-icon-menu'
                }
            }
        ]
    },
    {
        path: '/wx',
        component: Layout,
        children: [
            {
                path: '/wx-user-list',
                component: WxUserList,
                meta: {
                    title: '微信用户',
                    icon: 'el-icon-menu'
                }
            }
        ]
    }
];

const router = new VueRouter({
    routes
});

router.beforeEach((to, from, next) => {
    if (to.meta.title) {
        window.document.title = to.meta.title
    }

    if ("login" === to.name) {
        next();
    } else {
        if (sessionStorage.getItem("token")) {
            next();
        } else {
            next({name: "login"});
        }
    }
});

new Vue({
    el: '#app',
    router
});