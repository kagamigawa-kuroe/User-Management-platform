<template>
  <el-container>
    <el-aside width="200px">
      <SideMenu></SideMenu>
    </el-aside>
    <el-container>
      <el-header>
        <strong>Background management system</strong>
        <div class="header-avater">
          <el-avatar :src="userInfo.avater"></el-avatar>
          <el-dropdown>
            <span class="el-dropdown-link">
               {{userInfo.username}}<i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">

              <router-link to="/userCenter">
                <el-dropdown-item>
                  Personal home page
                </el-dropdown-item>
              </router-link>

              <el-dropdown-item @click.native="logout">Logout</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <el-dropdown-item>Logout</el-dropdown-item>
        </div>
      </el-header>
      <el-main>
        <Tabs></Tabs>
        <div style="margin:0 15px;">
          <router-view></router-view>
        </div>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import SideMenu from "@/views/inc/SideMenu";
import Tabs from "./inc/Tabs"
export default {
  name: "Home",
  components:{
    SideMenu,
    Tabs
  },
  data(){
    return {
      userInfo:{
        id:"",
        username:"",
        avater:""
      }
    }
  },
  created() {
    this.getUserInfo()
  },
  methods:{
    getUserInfo(){
      this.$axios.get("/sys/userInfo").then(res =>{
        this.userInfo = res.data.data
      })
    },
    logout() {
      this.$axios.post("/logout").then(res => {
        localStorage.clear()
        sessionStorage.clear()
        this.$store.commit("resetState")
        this.$router.push("/login")
      })
    }
  }
}
</script>

<style scoped>
.header-avater{
  float: right;
  width: 210px;
  display: flex;
  justify-content: space-around;
  align-items: center;
}

.el-submenu{

}

.el-container{
  padding: 0;
  margin: 0;
  height: 100%;
}
.el-header {
  background-color: lightskyblue;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #D3DCE6;
  color: #333;
  line-height: 200px;
}

.el-main {
  background-color: #E9EEF3;
  color: #333;
  padding: 0px;
}

.el-dropdown-link {
  cursor: pointer;
  color: #409EFF;
}

a {
  text-decoration: none;
}
</style>