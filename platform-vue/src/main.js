import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Element from "element-ui"
import "element-ui/lib/theme-chalk/index.css"
import axios from "./axios";

// set global variable axios
Vue.prototype.$axios = axios

// use web UI library Element-ui
Vue.use(Element)
require('./mock.js')
Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
