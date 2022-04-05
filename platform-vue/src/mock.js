const Mock = require('mockjs')

const Random = Mock.Random

let Result = {
    code:200,
    msg:'success',
    data:null
}

Mock.mock('/codeimage','get',() => {

    Result.data = {
        token: Random.string(32),
        codeimage: Random.dataImage('80x40','p7n5w')
    }

    return Result
})

Mock.mock('/login','post',() => {
    Result.code = 200
    Result.msg = "验证码错误"
    return Result
})

Mock.mock('/sys/userInfo','get',() => {

    Result.data = {
        id:"1",
        username:"test",
        avater:"https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
    }

    return Result
})

Mock.mock('/logout','post',() => {

    return Result
})

Mock.mock('/sys/menu/nav','get',() => {
    let nav = [
        {
            name: 'SysManga',
            title: 'system management',
            icon: 'el-icon-s-operation',
            path: '',
            component: '',
            children: [
                {
                    name: 'User',
                    title: 'user management',
                    icon: 'el-icon-s-custom',
                    path: '/sys/users',
                    component: 'sys/User',
                    children: []
                },
                {
                    name: 'Role',
                    title: 'role management',
                    icon: 'el-icon-rank',
                    path: '/sys/role',
                    component: 'sys/Role',
                    children: []
                },
                {
                    name: 'Menu',
                    title: 'menu management',
                    icon: 'el-icon-menu',
                    path: '/sys/menu',
                    component: 'sys/Menu',
                    children: []
                }
            ]
        },
        {
            name: 'SysTools',
            title: 'system tools',
            icon: 'el-icon-s-tools',
            path: '',
            component: '',
            children: [
                {
                    name: 'SysDict',
                    title: 'Digital dictionary',
                    icon: 'el-icon-s-order',
                    path: '/sys/dicts',
                    component: '',
                    children: []
                },
            ],
        }
    ];
    let authoritys = []

    Result.data = {
        nav:nav,
        authoritys:authoritys
    }

    return Result
})