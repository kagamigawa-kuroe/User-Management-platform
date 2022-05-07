<template>
  <div>
    <el-form :inline="true">

      <el-form-item>
        <el-input v-model="searchForm.name"
                  placeholder="name"
                  clearable>
        </el-input>
      </el-form-item>

      <el-form-item>
        <el-button @click="getRolelist">search</el-button>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="dialogVisible = true">New</el-button>
      </el-form-item>

      <el-form-item>
        <el-popconfirm
            title="Are you sure to delete?"
            @confirm="delHandle(null)"
        >
          <el-button type="danger" slot="reference" :disabled="ButtonStatus">Delete</el-button>
        </el-popconfirm>
      </el-form-item>
    </el-form>

    <el-table
        ref="multipleTable"
        :data="tableData"
        style="width: 100%"
        border
        stripe
        @selection-change="handleSelectionChange">
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>

      <el-table-column
          property="name"
          label="Name"
          width="120">
      </el-table-column>

      <el-table-column
          property="code"
          label="Code"
          show-overflow-tooltip>
      </el-table-column>

      <el-table-column
          property="remark"
          label="describe"
          show-overflow-tooltip>
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
          prop=""
          label="operation">

        <template slot-scope="scope">

          <el-button type = "text" @click="permHandle(scope.row.id)">edit permisson</el-button>
          <el-divider direction="vertical"></el-divider>

          <el-button type = "text" @click="editHandle(scope.row.id)">edit</el-button>
          <el-divider direction="vertical"></el-divider>

          <template>
            <el-popconfirm title="Are you sure to delete this?" @confirm="delHandle(scope.row.id)">
              <el-button type="text" slot="reference">Delete</el-button>
            </el-popconfirm>
          </template>

        </template>
      </el-table-column>

    </el-table>

    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="current"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="size"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>

    <el-dialog
        title="Tips"
        :visible.sync="dialogVisible"
        width="30%"
        :before-close="handleClose">
      <el-form :model="editForm" :rules="editFormRules" ref="editForm" label-width="100px" class="demo-editForm">

        <el-form-item label="name" prop="name" label-width="100px">
          <el-input v-model="editForm.name" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="code" prop="code" label-width="100px">
          <el-input v-model="editForm.code" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="describe" prop="remark" label-width="100px">
          <el-input v-model="editForm.icon" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="statu" prop="statu" label-width="100px">
          <el-radio-group v-model="editForm.statu">
            <el-radio :label=0>disable</el-radio>
            <el-radio :label=1>normal</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm('editForm')">create</el-button>
          <el-button @click="resetForm('editForm')">reset</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-dialog
        title="Edit permisson"
        :visible.sync="permdialogVisible"
        width="30%">
      <el-form :model="permForm">
        <el-tree
            :data="perTreedata"
            show-checkbox
            node-key="id"
            :check-strictly=true
            :default-expand-all="true"
            ref="permTree"
            :props="defaultProps">
        </el-tree>
      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="permdialogVisible = false">Cancel</el-button>
    <el-button type="primary" @click="submitPermFormHandle('permForm')">Confirm</el-button>
  </span>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "Role",
  data(){
    return{
      permForm:{},
      permdialogVisible:false,
      dialogVisible:false,
      editFormRules: {
        name: [
          {required: true, message: 'input name', trigger: 'blur'}
        ],
        code: [
          {required: true, message: 'input code', trigger: 'blur'}
        ],
        statu: [
          {required: true, message: 'choose status', trigger: 'blur'}
        ]
      },
      editForm:{

      },

      searchForm:{

      },
      ButtonStatus:true,
      tableData: [],
      multipleSelection: [],
      currentPage1: 5,
      currentPage2: 5,
      currentPage3: 5,
      currentPage4: 4,
      total:0,
      size:10,
      current:1,
      perTreedata: [],
      defaultProps: {
        children: 'children',
        label: 'name'
      }
    }
  },
  created() {
    this.getRolelist()
    this.$axios.get('/sys/menu/list').then(res =>{
      this.perTreedata = res.data.data
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
      this.multipleSelection = val
      this.ButtonStatus = val.length == 0
    },
    handleSizeChange(val) {
      this.size = val
      this.getRolelist()
    },
    handleCurrentChange(val) {
      console.log(`current page: ${val}`);
      this.current = val
      this.getRolelist()
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
      this.dialogVisible = false
      this.editForm = {}
    },
    handleClose() {
      this.resetForm('editForm')
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('/sys/role/' + (this.editForm.id?'update' : 'save'), this.editForm)
              .then(res => {
                this.$message({
                  showClose: true,
                  message: 'create successfully',
                  type: 'success',
                  onClose:() => {
                    this.getMenuTree()
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
    getRolelist(){
      this.$axios.get("/sys/role/list",{params:{
        name:this.searchForm.name,
          current:this.current,
          size:this.size

        }}).then(res=>{
        this.tableData = res.data.data.records;
        this.size=res.data.data.size;
        this.current=res.data.data.current;
        this.total=res.data.data.total;
      })
    },
    editHandle(id) {
      this.$axios.get('/sys/role/info/' + id).then(res => {
        this.editForm = res.data.data
        this.dialogVisible = true
      })
    },
    delHandle(id) {
      var ids = []
      if (id){
        ids.push(id)
      } else {
        this.multipleSelection.forEach(row =>{
          ids.push(row.id)
        })
      }
      this.$axios.post("/sys/role/delete", ids).then(res => {
        this.$message({
          showClose: true,
          message: 'Congratulations, the operation is successful',
          type: 'success',
          onClose:() => {
            this.getRolelist()
          }
        });
      })
    },

    permHandle(id) {
      this.permdialogVisible = true
      this.$axios.get("/sys/role/info/" + id).then(res => {
        this.$refs.permTree.setCheckedKeys(res.data.data.menuIds)
        this.permForm = res.data.data
      })
    },
    submitPermFormHandle(formName){
      var menuIds = this.$refs.permTree.getCheckedKeys()

      this.$axios.post('/sys/role/perm/' + this.permForm.id, menuIds).then(res => {
        this.$message({
          showClose: true,
          message: 'Congratulations, the operation is successful',
          type: 'success',
          onClose:() => {
            this.getRoleList()
          }
        });
        this.permdialogVisible = false
        this.resetForm(formName)
      })
    }
  }
}
</script>

<style scoped>
.el-pagination{
  float: right;
  margin-top: 22px;
}
</style>