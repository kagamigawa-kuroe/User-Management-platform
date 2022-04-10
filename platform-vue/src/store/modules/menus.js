import Vue from 'vue'
import Vuex from 'vuex'
import store from "@/store";

Vue.use(Vuex)

export default{
    state: {
        menuList: [],
        permList: [],
        hasRoute: false,
        editableTabsValue: 'Index',
        editableTabs: [{
            title: 'home',
            name: 'index',
        }]
    },
    mutations: {
        setPermList(state,perm){
            state.permList = perm
        },
        setMenuList(state,menus){
            state.menuList = menus
        },
        changeRouteStatus(state,hasRoute){
            state.hasRoute = hasRoute
            sessionStorage.setItem("hasRoute",hasRoute)
        },
        addTab(state,tab) {
            let index = state.editableTabs.findIndex(e => e.name === tab.name)
            if (index === -1) {
                state.editableTabs.push({
                    title: tab.title,
                    name: tab.name
                })
            }
            state.editableTabsValue = tab.name
        },
        resetState:(state) =>{
            state.menuList = ''
            state.permList = ''
            state.hasRoute = ''
            state.editableTabsValue = 'index'
            state.editableTabs = [{
                title: 'home',
                name: 'index',
            }]
        }
    },
    actions: {
    },
}


