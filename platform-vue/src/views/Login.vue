<template>

  <el-row type="flex" class="row-bg" justify="center" :style="{backgroundImage:`url(${image})`}">
    <el-col  :xl="6" :lg="7">
      <h1><strong>Welcome to platform</strong></h1>
      <el-image :src="require('/Users/whz/learning/User-Management-platform/platform-vue/src/assets/contact.jpeg')" style="height: 180px;width: 180px"></el-image>
      <p><strong>Contact me:</strong></p>
      <p><strong>https://github.com/kagamigawa-kuroe</strong></p>
    </el-col>

    <el-col :span="1">
      <el-divider direction="vertical"></el-divider>
    </el-col>

    <el-col  :xl="6" :lg="7">
      <el-form :model="loginForm" :rules="rules" ref="loginForm" label-width="120px" class="demo-loginForm">

        <el-form-item label="Username" prop="username" style="width: 380px">
          <el-input v-model="loginForm.username"></el-input>
        </el-form-item>

        <el-form-item label="Password" prop="password" style="width: 380px">
          <el-input v-model="loginForm.password" type="password"></el-input>
        </el-form-item>

        <el-form-item label="Auth code" prop="code"  style="width: 380px;">
          <el-input v-model="loginForm.code"  style="width: 120px; float: left" maxlength="5"></el-input>
          <el-image :src="codeimage" class="codeimage" @click="getimage"></el-image>
        </el-form-item>

        <el-form-item >
          <el-button type="primary" @click="submitForm('loginForm')">立即创建</el-button>
          <el-button @click="resetForm('loginForm')">重置</el-button>
        </el-form-item>

      </el-form>
    </el-col>

  </el-row>
</template>

<script>
import qs from 'qs'
export default {
  name:"login",
  data() {
    return {
      loginForm: {
        username: 'admin',
        password: '',
        code: '',
        token:''
      },

      image: require('/Users/whz/learning/User-Management-platform/platform-vue/src/assets/bgn.png'),

      rules: {
        username: [
          { required: true, message: 'Please input your name', trigger: 'blur' },
        ],
        password: [
          { required: true, message: 'Please input your password', trigger: 'blur' },
        ],
        code: [
          { required: true, message: 'Please input code', trigger: 'blur' },
          { min: 5, max: 5, message: 'Code should be in 5 characters', trigger: 'blur' }
        ]
      },
      codeimage: null
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('/login?'+ qs.stringify(this.loginForm)).then(res =>{
            const jwt = res.headers['authorization']
            this.$store.commit('SET_TOKEN',jwt);
            this.$router.push("/index")
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    getimage(){
      this.$axios.get('/captcha').then(res =>{
        this.loginForm.token=res.data.data.token
        this.codeimage=res.data.data.codeimage
        this.loginForm.code= ''
      })
    }
  },
  created(){
    this.getimage();
  }
}
</script>

<style scoped>
.el-row {
  background-color: #fafafa;
  height: 100%;
  display: flex;
  align-items: center;
  text-align: center;
  justify-content: center;
}
.el-divider {
  height: 200px;
}
.codeimage {
  float: left;
  margin-left: 8px;
  border-radius: 4px;
}
</style>