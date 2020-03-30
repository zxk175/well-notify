const Login = {
    template: `
    <div class="login-container">
        <h2 class="login-title">欢迎登录</h2>
        <el-form ref="dataForm" :model="dataForm" :rules="dataRules" autocomplete="off" label-position="left">
            <el-form-item prop="mobile">
                <el-input v-model="dataForm.mobile" type="text" placeholder="请输入手机号"  clearable>
                    <template slot="prefix">
                        <i class="el-icon-user"/>
                    </template>
                </el-input>
            </el-form-item>
            
            <el-form-item prop="password">
                <el-input v-model="dataForm.password" type="password" placeholder="请输入密码" show-password clearable>
                    <template slot="prefix">
                        <i class="el-icon-lock"/>
                    </template>
                </el-input>
            </el-form-item>
            
            <el-checkbox v-model="dataForm.checked" style="margin: 0 0 20px 0;">记住密码</el-checkbox>
            
            <el-form-item style="margin-bottom: 0;">
                <el-button style="width: 100%;" type="primary" @click.native.prevent="handleSubmit" :loading="loginIng">
                    {{loginIngTxt}}
                </el-button>
            </el-form-item>
        </el-form>
    </div>
    `,
    data() {
        return {
            loginIng: false,
            loginIngTxt: "登录",
            dataForm: {
                mobile: '18820216400',
                password: '888888',
                checked: true
            },
            dataRules: {
                mobile: [{required: true, message: '请输入手机', trigger: 'blur'}],
                password: [{required: true, message: '请输入密码', trigger: 'blur'}]
            }
        }
    },
    methods: {
        handleSubmit() {
            const that = this;
            that.$refs.dataForm.validate((valid) => {
                if (valid) {
                    sessionStorage.setItem("token", "");
                    this.$router.push({name: "home"});
                } else {
                    return false;
                }
            });
        }
    }
};
