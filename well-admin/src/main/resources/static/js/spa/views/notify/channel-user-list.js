const ChannelUserList = {
    template: `
    <div>
        <el-row class="tool">
            <el-col :span="12" class="tool-left">
                <el-button type="danger" size="small" icon="el-icon-delete" @click.native="removeBatch" :disabled="removeArr.length <= 0">批量删除</el-button>
            </el-col>
            <el-col :span="12" class="tool-right">
                <el-button type="primary" icon="el-icon-plus" size="small" @click="openDialog(0)">新增</el-button>
            </el-col>
        </el-row>
    
        <el-table :data="tableData" ref="multiTable" row-key="userId" height="480" size="mini" @selection-change="selectChange" stripe border>
            <el-table-column type="selection" label="1" width="50" align="center"></el-table-column>
            <el-table-column type="index" label="序号" :index="genIndex" align="center" fixed="left"></el-table-column>
            <el-table-column prop="openId" label="openId" width="260" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column prop="userId" label="用户Id" width="160" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column prop="fullName" label="用户名字" width="120" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column prop="channelId" label="通道Id" width="160" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column prop="channelName" label="通道名字" width="120" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column prop="state" label="状态" width="80" align="center">
                <template slot-scope="scope">
                    <el-tag v-if="0 === scope.row.state" size="mini" type="danger">关闭</el-tag>
                    <el-tag v-else size="mini" type="success">启用</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间" min-width="150" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column prop="updateTime" label="更新时间" min-width="150" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="操作" width="160" align="center" fixed="right">
                <template slot-scope="scope">
                    <el-popover placement="top" trigger="click" :ref="scope.row.userId + 'del'">
                        <p>确定对<span class="popover-tip-op">{{scope.row.fullName}}</span>进行<span class="popover-tip-op">删除</span>操作?</p>
                        <div style="text-align: right; margin: 0;">
                            <el-button type="text" size="mini" @click.native="hideDelPopover(scope.row.userId, 'del')">取消</el-button>
                            <el-button type="primary" size="mini" @click.native="remove(scope.row)">确定</el-button>
                        </div>
                    </el-popover>
    
                    <el-button type="warning" size="mini" @click.native="openDialog(scope.row.userId)">修改</el-button>
                    <el-button type="danger" size="mini" v-popover="scope.row.userId + 'del'">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
    
        <div class="table-page">
            <el-pagination background layout="total, prev, pager, next, sizes, jumper"
                           @current-change="pageChange"
                           @size-change="sizeChange"
                           :page-sizes="[10, 20, 50, 80, 100]"
                           :current-page.sync="paging.page"
                           :page-size.sync="paging.size"
                           :total="paging.total">
            </el-pagination>
        </div>
    
        <el-dialog :title="opFlag ? '新增' : '修改'" :visible.sync="visible" :close-on-click-modal="false">
            <el-form :model="dataForm" :rules="dataRule" ref="dataForm" label-width="90px" label-position="right">
                <el-form-item label="用户Id" prop="userId" v-show="!opFlag">
                    <el-input v-model="dataForm.userId" disabled></el-input>
                </el-form-item>
                <el-form-item label="用户名字" prop="fullName">
                    <el-input v-model="dataForm.fullName" placeholder="用户名字" clearable></el-input>
                </el-form-item>
                <el-form-item label="openId" prop="openId">
                    <el-input v-model="dataForm.openId" placeholder="openId" clearable></el-input>
                </el-form-item>
                <el-form-item label="通道名字" prop="channelId">
                    <el-select v-model="dataForm.channelId" placeholder="请选择">
                        <el-option v-for="item in channelList"
                                   :key="item.value"
                                   :label="item.view"
                                   :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="状态" prop="state">
                    <el-switch v-model="dataForm.state"
                               inactive-color="#f56c6c"
                               inactive-text="关闭"
                               active-text="启用"
                               :inactive-value="0"
                               :active-value="1"/>
                </el-form-item>
            </el-form>
    
            <span slot="footer" class="dialog-footer">
                <el-button type="text" size="mini" @click="visible = false">取消</el-button>
                <el-button type="primary" size="mini" @click="dataFormSubmit()">确定</el-button>
            </span>
        </el-dialog>
    </div>
    `,
    data() {
        return {
            opFlag: true,
            visible: false,
            dataForm: {
                userId: 0,
                fullName: '',
                openId: '',
                channelId: '',
                state: 1
            },
            dataRule: {
                fullName: [
                    {required: true, message: '用户名字不能为空', trigger: 'blur'}
                ]
            },
            channelList: [],
            paging: {
                page: 1,
                size: 10,
                total: 0,
            },
            removeArr: [],
            tableData: []
        }
    },
    mounted() {
        this.listData();
    },
    methods: {
        openDialog(userId) {
            let that = this;
            that.visible = true;
            that.dataForm.userId = userId || 0;
            that.opFlag = that.dataForm.userId === 0;
            that.$nextTick(() => {
                that.$refs.dataForm.resetFields();
            });

            post(CHANNEL_LIST_SELECT, {}, function (res) {
                let data = res.data;
                if (data.success) {
                    that.channelList = data.data;

                    if (that.dataForm.userId > 0) {
                        post(CHANNEL_USER_INFO, {"userId": userId}, function (res) {
                            let data = res.data;
                            if (data.success) {
                                Object.assign(that.dataForm, data.data);
                            }
                        });
                    }
                } else {
                    that.channelList = [];
                }
            })
        },

        dataFormSubmit() {
            let that = this;
            that.$refs.dataForm.validate((valid) => {
                if (valid) {
                    let url;
                    let params = that.dataForm;
                    if (that.opFlag) {
                        delete params["userId"];
                        url = CHANNEL_USER_SAVE;
                    } else {
                        url = CHANNEL_USER_MODIFY;
                    }

                    post(url, params, function (res) {
                        let data = res.data;
                        if (data.success) {
                            that.visible = false;
                            that.listData();
                        }
                    })
                }
            });
        },

        genIndex(index) {
            return this.paging.size * (this.paging.page - 1) + index + 1;
        },

        listData() {
            let that = this;
            const params = that.getParams();

            post(CHANNEL_USER_LIST, params, function (res) {
                let data = res.data;
                if (data.success) {
                    that.paging.total = data.extra.total;
                    that.tableData = data.data;
                } else {
                    that.$message.error(data.msg);
                    that.tableData = [];
                }
            });
        },

        getParams() {
            let that = this;
            return {
                // 页码
                page: that.paging.page,
                // 页面大小
                size: that.paging.size
            };
        },

        pageChange(page) {
            this.paging.page = page;

            this.listData();
        },

        sizeChange(size) {
            this.paging.page = 1;
            this.paging.size = size;

            this.listData();
        },

        remove(row) {
            this.removeArr = row.userId ? [row.userId] : row;
            this.removeOp(row);
        },

        removeBatch() {
            let that = this;
            removeMsgBox(that, function () {
                that.removeOp()
            });
        },

        removeOp(row) {
            let that = this;
            post(CHANNEL_USER_REMOVE, {"idArr": this.removeArr}, function (res) {
                let data = res.data;
                if (data.success) {
                    that.listData();
                }

                if (row && row.userId) {
                    that.hideDelPopover(row.userId, 'del');
                }
            });
        },

        selectChange(rows) {
            this.removeArr = rows.map(item => {
                return item.userId;
            });
        },

        hideDelPopover(id, type) {
            this.$refs[id + type].doClose();
        },
    }
};
