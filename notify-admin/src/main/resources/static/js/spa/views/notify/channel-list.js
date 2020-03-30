const ChannelList = {
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
    
        <el-table :data="tableData" ref="multiTable" row-key="channelId" height="480" size="mini" @selection-change="selectChange" stripe border>
            <el-table-column type="selection" label="1" width="50" align="center"></el-table-column>
            <el-table-column type="index" label="序号" :index="genIndex" align="center" fixed="left"></el-table-column>
            <el-table-column prop="channelId" label="通道Id" width="160" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column prop="channelName" label="通道名字" width="160" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column prop="state" label="状态" width="80" align="center">
                <template slot-scope="scope">
                    <el-tag v-if="0 === scope.row.state" size="mini" type="danger">关闭</el-tag>
                    <el-tag v-else-if="1 === scope.row.state" size="mini" type="success">启用</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间" min-width="140" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column prop="updateTime" label="更新时间" min-width="140" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="操作" width="160" align="center" fixed="right">
                <template slot-scope="scope">
                    <el-popover placement="top" trigger="click" :ref="scope.row.channelId + 'del'">
                        <p>确定对<span class="popover-tip-op">{{scope.row.channelName}}</span>进行<span class="popover-tip-op">删除</span>操作?</p>
                        <div style="text-align: right; margin: 0;">
                            <el-button type="text" size="mini" @click.native="hideDelPopover(scope.row.channelId, 'del')">取消</el-button>
                            <el-button type="primary" size="mini" @click.native="remove(scope.row)">确定</el-button>
                        </div>
                    </el-popover>
    
                    <el-button type="warning" size="mini" @click.native="openDialog(scope.row.channelId)">修改</el-button>
                    <el-button type="danger" size="mini" v-popover="scope.row.channelId + 'del'">删除</el-button>
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
                <el-form-item label="通道Id" prop="channelId" v-show="!opFlag">
                    <el-input v-model="dataForm.channelId" disabled></el-input>
                </el-form-item>
                <el-form-item label="通道名字" prop="channelName">
                    <el-input v-model="dataForm.channelName" placeholder="通道名字" clearable></el-input>
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
                channelId: '',
                channelName: '',
                state: 1
            },
            dataRule: {
                channelName: [
                    {required: true, message: '通道名字不能为空', trigger: 'blur'}
                ]
            },
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
        openDialog(channelId) {
            let that = this;
            that.visible = true;
            that.dataForm.channelId = channelId || 0;
            that.opFlag = that.dataForm.channelId === 0;
            that.$nextTick(() => {
                that.$refs.dataForm.resetFields();
            });

            if (!that.opFlag) {
                post(CHANNEL_INFO, {"channelId": channelId}, function (res) {
                    let data = res.data;
                    if (data.success) {
                        Object.assign(that.dataForm, data.data);
                    }
                });
            }
        },

        dataFormSubmit() {
            let that = this;
            that.$refs.dataForm.validate((valid) => {
                if (valid) {
                    let params = that.dataForm;
                    if (that.opFlag) {
                        delete params["channelId"];
                    }

                    let url;
                    if (that.opFlag) {
                        url = CHANNEL_SAVE;
                    } else {
                        url = CHANNEL_MODIFY;
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

            postAuth(CHANNEL_LIST, params, function (res) {
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
            this.removeArr = row.channelId ? [row.channelId] : [0];
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
            post(CHANNEL_REMOVE, {"idArr": this.removeArr}, function (res) {
                let data = res.data;
                if (data.success) {
                    that.listData();
                }

                if (row && row.channelId) {
                    that.hideDelPopover(row.channelId, 'del');
                }
            });
        },

        selectChange(rows) {
            this.removeArr = rows.map(item => {
                return item.channelId;
            });
        },

        hideDelPopover(id, type) {
            this.$refs[id + type].doClose();
        },
    }
};
