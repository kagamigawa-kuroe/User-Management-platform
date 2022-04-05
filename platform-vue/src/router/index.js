import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import index from "../views/index";
import User from "@/views/sys/User";
import Menu from "@/views/sys/Menu";
import Role from "@/views/sys/Role";
import UserCenter from "@/views/UserCenter";

import axios from "axios";
import store from "../store"
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: Home,
    children:[
      {
        path: '/index',
        name: 'index',
        component: index
      },
      {
        path: '/userCenter',
        name: 'UserCenter',
        component: UserCenter
      },
      // {
      //   path: '/sys/users',
      //   name: 'User',
      //   component: User
      // },
      // {
      //   path: '/sys/role',
      //   name: 'Role',
      //   component: Role
      // },
      // {
      //   path: '/sys/menu',
      //   name: 'Menu',
      //   component: Menu
      // }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to,from,next) =>{

  let hasRoute = store.state.menus.hasRoute

  if(!hasRoute){
    axios.get("/sys/menu/nav",{
      headers:{
        Authorization:localStorage.getItem("token")
      }
    }).then(res => {
      console.log(res.data.data)
      // get menuList
      store.commit("setMenuList",res.data.data.nav)
      // get permission
      store.commit("setPermList",res.data.data.nav)

      //Dynamic binding
      let newRoutes = router.options.routes

      res.data.data.nav.forEach(menu =>{
        if(menu.children){
          menu.children.forEach(e =>{

            let route = menuToRoute(e)

            if(route){
              newRoutes[0].children.push(route)
            }
          })
        }
      })
      router.addRoutes(newRoutes)
      hasRoute = true
      store.commit("changeRouteStatus",hasRoute)
    })
  }

  next()
})

const menuToRoute = (menu) => {
  if(!menu.component){
    return null
  }

  let route = {
    name:menu.name,
    path:menu.path,
    meta:{
      icon:menu.icon,
      title:menu.title
    },
  }

  route.component = () => import('@/views/'+menu.component+'.vue')
  return  route
}

export default router
