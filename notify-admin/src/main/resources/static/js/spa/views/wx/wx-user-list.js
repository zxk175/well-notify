const WxUserList = {
    template: `
    <div>
        <el-table :data="tableData" ref="multiTable" row-key="openId" height="480" size="mini" stripe border>
            <el-table-column type="selection" label="1" width="50" align="center"></el-table-column>
            <el-table-column type="index" label="序号" :index="genIndex" align="center" fixed="left"></el-table-column>
            <el-table-column prop="avatar" label="微信头像" width="100" align="center" show-overflow-tooltip>
                <template slot-scope="scope">
                    <el-image style="width: 30px; height: 30px" :src="scope.row.avatar" fit="contain"></el-image>
                </template>
            </el-table-column>
            <el-table-column prop="nickName" label="微信昵称" width="240" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column prop="openId" label="openId" width="260" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column prop="subscribe" label="关注状态" width="100" align="center">
                <template slot-scope="scope">
                    <el-tag v-if="0 === scope.row.subscribe" size="mini" type="danger">取消</el-tag>
                    <el-tag v-else size="mini" type="success">关注</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="subscribeTime" label="关注时间" min-width="140" align="center" show-overflow-tooltip></el-table-column>
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
    </div>
    `,
    data() {
        return {
            paging: {
                page: 1,
                size: 10,
                total: 0,
            },
            tableData: []
        }
    },
    mounted() {
        this.listData();
    },
    methods: {
        genIndex(index) {
            return this.paging.size * (this.paging.page - 1) + index + 1;
        },

        listData() {
            let that = this;
            const params = that.getParams();

            post(WX_USER_LIST, params, function (res) {
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
    }
};
