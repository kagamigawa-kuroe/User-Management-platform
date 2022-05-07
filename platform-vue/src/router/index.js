import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import index from "../views/index";
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
        meta: {
          title: index
        },
        component: index
      },
      {
        path: '/userCenter',
        name: 'UserCenter',
        meta: {
          title: "user center"
        },
        component: UserCenter
      },
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

  let token = localStorage.getItem("token")

  if(to.path=='/login'){
    next()
  }else if (token && !token){
    next({path:'/login'})
  } else if(!hasRoute){
    axios.get("/sys/menu/nav",{
      headers:{
        Authorization:localStorage.getItem("token")
      }
    }).then(res => {
      console.log(res.data.data)
      // get menuList
      store.commit("setMenuList",res.data.data.nav)
      // get permission
      store.commit("setPermList",res.data.data.authoritys)
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
