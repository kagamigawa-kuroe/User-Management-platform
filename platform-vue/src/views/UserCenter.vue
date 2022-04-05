<template>
  <div style="text-align: center;">
    <h2>hello！{{ userInfo.username }} </h2>

    <el-form :model="passForm" status-icon :rules="rules" ref="passForm" label-width="100px">
      <el-form-item label="old passwd" prop="currentPass">
        <el-input type="password" v-model="passForm.currentPass" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="new passwd" prop="password">
        <el-input type="password" v-model="passForm.password" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="confirm" prop="checkPass">
        <el-input type="password" v-model="passForm.checkPass" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('passForm')">submit</el-button>
        <el-button @click="resetForm('passForm')">reset</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>

export default {
  name: "Login",
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('please input your password again'));
      } else if (value !== this.passForm.password) {
        callback(new Error('The two passwords are inconsistent!'));
      } else {
        callback();
      }
    };
    return {
      userInfo: {

      },
      passForm: {
        password: '',
        checkPass: '',
        currentPass: ''
      },
      rules: {
        password: [
          { required: true, message: 'please input the new password', trigger: 'blur' },
          { min: 6, max: 12, message: 'betwwen 6 to 8 characters', trigger: 'blur' }
        ],
        checkPass: [
          { required: true, validator: validatePass, trigger: 'blur' }
        ],
        currentPass: [
          { required: true, message: 'please input the passwd now', trigger: 'blur' },
        ]
      }
    }
  },
  created() {
    this.getUserInfo()
  },
  methods: {
    getUserInfo() {
      this.$axios.get("/sys/userInfo").then(res => {

        this.userInfo = res.data.data;
      })
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {

          const _this = this
          this.$axios.post('/sys/user/updatePass', this.passForm).then(res => {

            _this.$alert(res.data.msg, '提示', {
              confirmButtonText: '确定',
              callback: action => {
                this.$refs[formName].resetFields();
              }
            });
          })

        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>

<style scoped>
.el-form {
  width: 420px;
  margin: 50px auto;
}
</style>