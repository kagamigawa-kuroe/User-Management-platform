<template>
  <div>

    <el-form :inline="true">
      <el-form-item>
        <el-button type="primary" @click="dialogVisible = true">New</el-button>
      </el-form-item>
    </el-form>

    <el-table
      :data="tableData"
      style="width: 100%;margin-bottom: 20px;"
      row-key="id"
      border
      stripe
      default-expand-all>
    <el-table-column
        prop="name"
        label="name"
        sortable
        width="180">
    </el-table-column>
    <el-table-column
        prop="perms"
        label="permission"
        sortable
        width="180">
    </el-table-column>
      <el-table-column
          prop="icon"
          label="icon">
      </el-table-column>

      <el-table-column
          prop="type"
          label="type">
        <template slot-scope="scope">
          <el-tag size="small" v-if="scope.row.type === 0">directory</el-tag>
          <el-tag size="small" v-else-if="scope.row.type === 1" type="success">menu</el-tag>
          <el-tag size="small" v-else-if="scope.row.type === 2" type="info">button</el-tag>
        </template>
      </el-table-column>

      <el-table-column
          prop="path"
          label="path of menu">
      </el-table-column>

      <el-table-column
          prop="component"
          label="component">
      </el-table-column>

      <el-table-column
          prop="orderNum"
          label="order">
      </el-table-column>

      <el-table-column
          prop="status"
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
          <el-button type = "text" @click="editHandle(scope.row.id)">edit</el-button>
          <el-divider direction="vertical"></el-divider>

          <template>
            <el-popconfirm title="Are you sure to delete this?" @confirm="delHandle">
              <el-button type="text" slot="reference">Delete</el-button>
            </el-popconfirm>
          </template>

        </template>
      </el-table-column>

  </el-table>

    <el-dialog
        title="Tips"
        :visible.sync="dialogVisible"
        width="30%"
        :before-close="handleClose">
      <el-form :model="editForm" :rules="editFormRules" ref="editForm" label-width="100px" class="demo-editForm">

        <el-form-item label="upper menu" prop="parentId">
          <el-select v-model="editForm.parentId" placeholder="choose previous menu">

            <template v-for="item in tableData">
              <el-option :label="item.name" :value="item.id"></el-option>

              <template v-for="child in item.children">
                <el-option :label="child.name" :value="child.id">
                  <span>{{ "- " + child.name }}</span>
                </el-option>
              </template>

            </template>

          </el-select>
        </el-form-item>

        <el-form-item label="menu name" prop="name" label-width="100px">
          <el-input v-model="editForm.name" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="permission" prop="perms" label-width="100px">
          <el-input v-model="editForm.perms" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="icon" prop="icon" label-width="100px">
          <el-input v-model="editForm.icon" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="menu path" prop="path" label-width="100px">
          <el-input v-model="editForm.path" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="component" prop="component" label-width="100px">
          <el-input v-model="editForm.component" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="type" prop="type" label-width="100px">
          <el-radio-group v-model="editForm.type">
            <el-radio :label=0>directory</el-radio>
            <el-radio :label=1>menu</el-radio>
            <el-radio :label=2>button</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="status" prop="statu" label-width="100px">
          <el-radio-group v-model="editForm.statu">
            <el-radio :label=0>disable</el-radio>
            <el-radio :label=1>normal</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="order number" prop="orderNum" label-width="100px">
          <el-input-number v-model="editForm.orderNum" :min="1" label=""order number>1</el-input-number>
        </el-form-item>


        <el-form-item>
          <el-button type="primary" @click="submitForm('editForm')">create</el-button>
          <el-button @click="resetForm('editForm')">reset</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "Menu",
  data() {
    return {
      dialogVisible:false,
      editForm: {
      },
      editFormRules: {
        parentId: [
          {required: true, message: 'choose menu', trigger: 'blur'}
        ],
        name: [
          {required: true, message: 'inpute name', trigger: 'blur'}
        ],
        perms: [
          {required: true, message: 'imput permission', trigger: 'blur'}
        ],
        type: [
          {required: true, message: 'choose type', trigger: 'blur'}
        ],
        orderNum: [
          {required: true, message: 'input order number', trigger: 'blur'}
        ],
        statu: [
          {required: true, message: 'choose status', trigger: 'blur'}
        ]
      },
      tableData: []
    }
  },
  created() {
    this.getMenuTree()
  },
  methods:{
    getMenuTree(){
      this.$axios.get("/sys/menu/list").then(res=>{
        this.tableData = res.data.data
      })
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('/sys/menu/' + (this.editForm.id?'update' : 'save'), this.editForm)
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
    editHandle(id) {
      this.$axios.get('/sys/menu/info/' + id).then(res => {
        this.editForm = res.data.data
        this.dialogVisible = true
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
      this.dialogVisible = false
      this.editForm = {}
    },
    handleClose() {
      this.resetForm('editForm')
    },
    delHandle(id) {
      this.$axios.post("/sys/menu/delete/" + id).then(res => {
        this.$message({
          showClose: true,
          message: 'Congratulations, the operation is successful',
          type: 'success',
          onClose:() => {
            this.getMenuTree()
          }
        });
      })
    }
  }
}
</script>

<style scoped>

</style>