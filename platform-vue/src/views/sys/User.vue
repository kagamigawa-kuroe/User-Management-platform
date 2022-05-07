<template>
  <div>
    <el-form :inline="true">
      <el-form-item>
        <el-input
            v-model="searchForm.username"
            placeholder="The user name"
            clearable
        >
        </el-input>
      </el-form-item>

      <el-form-item>
        <el-button @click="getUserList">search</el-button>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="dialogVisible = true" v-if="hasAuth('sys:user:save')">new</el-button>
      </el-form-item>
      <el-form-item>
        <el-popconfirm title="Are you sure to delete?" @confirm="delHandle(null)">
          <el-button type="danger" slot="reference" :disabled="delBtlStatu" v-if="hasAuth('sys:user:delete')" >delete</el-button>
        </el-popconfirm>
      </el-form-item>
    </el-form>

    <el-table
        ref="multipleTable"
        :data="tableData"
        tooltip-effect="dark"
        style="width: 100%"
        border
        stripe
        @selection-change="handleSelectionChange">

      <el-table-column
          type="selection"
          width="55">
      </el-table-column>

      <el-table-column
          label="portrait"
          width="100">
        <template slot-scope="scope">
          <el-avatar size="small" :src="scope.row.avatar"></el-avatar>
        </template>
      </el-table-column>

      <el-table-column
          prop="username"
          label="user name"
          width="120">
      </el-table-column>
      <el-table-column
          prop="code"
          label="role name">
        <template slot-scope="scope">
          <el-tag size="small" type="info" v-for="item in scope.row.sysRoles">{{item.name}}</el-tag>
        </template>

      </el-table-column>
      <el-table-column
          prop="email"
          label="email">
      </el-table-column>
      <el-table-column
          prop="phone"
          label="phone number">
      </el-table-column>

      <el-table-column
          prop="statu"
          label="status">
        <template slot-scope="scope">
          <el-tag size="small" v-if="scope.row.statu === 1" type="success">normal</el-tag>
          <el-tag size="small" v-else-if="scope.row.statu === 0" type="danger">disable</el-tag>
        </template>

      </el-table-column>
      <el-table-column
          prop="created"
          width="200"
          label="create time"
      >
      </el-table-column>
      <el-table-column
          prop="icon"
          width="260px"
          label="operation">

        <template slot-scope="scope">
          <el-button type="text" @click="roleHandle(scope.row.id)">assign roles</el-button>
          <el-divider direction="vertical"></el-divider>

          <el-button type="text" @click="repassHandle(scope.row.id, scope.row.username)">reset passwd</el-button>
          <el-divider direction="vertical"></el-divider>

          <el-button type="text" @click="editHandle(scope.row.id)">edit</el-button>
          <el-divider direction="vertical"></el-divider>

          <template>
            <el-popconfirm title="are you sure to delete？" @confirm="delHandle(scope.row.id)">
              <el-button type="text" slot="reference">delete</el-button>
            </el-popconfirm>
          </template>

        </template>
      </el-table-column>

    </el-table>

    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        layout="total, sizes, prev, pager, next, jumper"
        :page-sizes="[10, 20, 50, 100]"
        :current-page="current"
        :page-size="size"
        :total="total">
    </el-pagination>


    <!--新增对话框-->
    <el-dialog
        title="提示"
        :visible.sync="dialogVisible"
        width="600px"
        :before-close="handleClose">

      <el-form :model="editForm" :rules="editFormRules" ref="editForm">
        <el-form-item label="用户名" prop="username" label-width="100px">
          <el-input v-model="editForm.username" autocomplete="off"></el-input>
          <el-alert
              title="original passwd is 888888"
              :closable="false"
              type="info"
              style="line-height: 12px;"
          ></el-alert>
        </el-form-item>

        <el-form-item label="email"  prop="email" label-width="100px">
          <el-input v-model="editForm.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="phone number"  prop="phone" label-width="100px">
          <el-input v-model="editForm.phone" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="status"  prop="statu" label-width="100px">
          <el-radio-group v-model="editForm.statu">
            <el-radio :label="0">disable</el-radio>
            <el-radio :label="1">normal</el-radio>
          </el-radio-group>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetForm('editForm')">cancel</el-button>
        <el-button type="primary" @click="submitForm('editForm')">confirm</el-button>
      </div>
    </el-dialog>

    <!-- 分配权限对话框 -->
    <el-dialog title="assign roles" :visible.sync="roleDialogFormVisible" width="600px">

      <el-form :model="roleForm">
        <el-tree
            :data="roleTreeData"
            show-checkbox
            ref="roleTree"
            :check-strictly=checkStrictly
            node-key="id"
            :default-expand-all=true
            :props="defaultProps">
        </el-tree>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="roleDialogFormVisible=false">cancel</el-button>
        <el-button type="primary" @click="submitRoleHandle('roleForm')">confirm</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "Role",
  data() {
    return {
      searchForm: {},
      delBtlStatu: true,
      total: 0,
      size: 10,
      current: 1,
      dialogVisible: false,
      editForm: {
      },
      tableData: [],
      editFormRules: {
        username: [
          {required: true, message: 'input name', trigger: 'blur'}
        ],
        email: [
          {required: true, message: 'input email', trigger: 'blur'}
        ],
        statu: [
          {required: true, message: 'choose status', trigger: 'blur'}
        ]
      },
      multipleSelection: [],
      roleDialogFormVisible: false,
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      roleForm: {},
      roleTreeData:  [],
      treeCheckedKeys: [],
      checkStrictly: true
    }
  },
  created() {
    this.getUserList()
    this.$axios.get("/sys/role/list").then(res => {
      this.roleTreeData = res.data.data.records
    })
  },
  methods: {
    toggleSelection(rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row);
        });
      } else {
        this.$refs.multipleTable.clearSelection();
      }
    },
    handleSelectionChange(val) {
      console.log("choose")
      console.log(val)
      this.multipleSelection = val;
      this.delBtlStatu = val.length == 0
    },
    handleSizeChange(val) {
      console.log(`${val} data every page`);
      this.size = val
      this.getUserList()
    },
    handleCurrentChange(val) {
      console.log(`The current page: ${val}`);
      this.current = val
      this.getUserList()
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
      this.dialogVisible = false
      this.editForm = {}
    },
    handleClose() {
      this.resetForm('editForm')
    },
    getUserList() {
      this.$axios.get("/sys/user/list", {
        params: {
          username: this.searchForm.username,
          current: this.current,
          size: this.size
        }
      }).then(res => {
        this.tableData = res.data.data.records
        this.size = res.data.data.size
        this.current = res.data.data.current
        this.total = res.data.data.total
      })
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('/sys/user/' + (this.editForm.id?'update' : 'save'), this.editForm)
              .then(res => {
                this.$message({
                  showClose: true,
                  message: 'Congratulations, the operation is successful',
                  type: 'success',
                  onClose:() => {
                    this.getUserList()
                  }
                });
                this.dialogVisible = false
              })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    editHandle(id) {
      this.$axios.get('/sys/user/info/' + id).then(res => {
        this.editForm = res.data.data
        this.dialogVisible = true
      })
    },
    delHandle(id) {
      var ids = []
      if (id) {
        ids.push(id)
      } else {
        this.multipleSelection.forEach(row => {
          ids.push(row.id)
        })
      }
      console.log(ids)
      this.$axios.post("/sys/user/delete", ids).then(res => {
        this.$message({
          showClose: true,
          message: 'Congratulations, the operation is successful',
          type: 'success',
          onClose:() => {
            this.getUserList()
          }
        });
      })
    },
    roleHandle (id) {
      this.roleDialogFormVisible = true
      this.$axios.get('/sys/user/info/' + id).then(res => {
        this.roleForm = res.data.data
        let roleIds = []
        res.data.data.sysRoles.forEach(row => {
          roleIds.push(row.id)
        })
        this.$refs.roleTree.setCheckedKeys(roleIds)
      })
    },
    submitRoleHandle(formName) {
      var roleIds = this.$refs.roleTree.getCheckedKeys()
      console.log(roleIds)
      this.$axios.post('/sys/user/role/' + this.roleForm.id, roleIds).then(res => {
        this.$message({
          showClose: true,
          message: 'Congratulations, the operation is successful',
          type: 'success',
          onClose:() => {
            this.getUserList()
          }
        });
        this.roleDialogFormVisible = false
      })
    },
    repassHandle(id, username) {
      this.$confirm('this action will set the passed of【' + username + '】, are you sure?', 'hint', {
        confirmButtonText: 'confirm',
        cancelButtonText: 'cancel',
        type: 'warning'
      }).then(() => {
        this.$axios.post("/sys/user/repass", id).then(res => {
          this.$message({
            showClose: true,
            message: 'Congratulations, the operation is successful',
            type: 'success',
            onClose: () => {
            }
          });
        })
      })
    }
  }
}
</script>

<style scoped>
.el-pagination {
  float: right;
  margin-top: 22px;
}
</style>
