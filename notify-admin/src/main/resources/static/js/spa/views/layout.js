const Layout = {
    template: `
    <el-container class="full-height">
        <el-header class="header">
            <div :class="['fl logo', collapseFlag ? 'logo-collapse-width' : 'logo-width']">
                <a href="/">
                    <span>{{collapseFlag ? sysNameMin : sysNameMax}}</span>
                </a>
            </div>
            
            <div class="fl collapse" @click.prevent="setCollapse">
                <i v-if="collapseFlag" class="el-icon-s-unfold"></i>
                <i v-else class="el-icon-s-fold"></i>
            </div>

            <ul class="nav">
                <li>
                    <el-dropdown size="medium" show-timeout="10" style="line-height: 60px;">
                        <div class="dropdown-content">
                            <span>zxk175</span>
                            <img class="avatar" src="//res.zxk175.com/img/avatar.png" alt="avatar">
                            <i class="el-icon-arrow-down" style="font-weight: bold;"></i>
                        </div>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item icon="el-icon-edit">修改密码</el-dropdown-item>
                            <el-dropdown-item divided icon="el-icon-circle-close"  @click.native="logout">退出系统</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </li>
            </ul>
        </el-header>

        <el-container class="full-height">
            <el-aside width="auto">
                <el-menu unique-opened
                         router
                         :collapse="collapseFlag"
                         :default-active="$route.path"
                         :class="['el-menu-vertical full-height']">
                    <template v-for="(item, index) in $router.options.routes">
                        <el-submenu v-if="item.children && item.children.length > 1" :index="index + ''">
                            <template slot="title">
                                <i :class="item.meta && item.meta.icon"></i>
                                <span slot="title">{{item.meta.title}}</span>
                            </template>
                            <el-menu-item v-for="child in item.children" :index="child.path" :key="child.path">
                                <i :class="child.meta && child.meta.icon"></i>
                                <span slot="title">{{child.meta.title}}</span>
                            </el-menu-item>
                        </el-submenu>
                        <el-menu-item v-if="item.children && item.children.length == 1" :index="item.children[0].path">
                            <i :class="item.children[0].meta.icon"></i>
                            <span slot="title">{{item.children[0].meta.title}}</span>
                        </el-menu-item>
                    </template>
                </el-menu>
            </el-aside>

            <el-main>
                <transition name="fade" mode="out-in">
                    <router-view></router-view>
                </transition>
            </el-main>
        </el-container>
    </el-container>
    `,
    data() {
        return {
            sysNameMin: 'WS',
            sysNameMax: 'Well-System',
            collapseFlag: false,
        }
    },
    methods: {
        // 折叠菜单
        setCollapse() {
            this.collapseFlag = !this.collapseFlag;
        },

        logout() {
            sessionStorage.setItem("token", "");
            this.$router.push({name: "login"});
        }
    }
};
