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
    Result.code = 400
    Result.msg = "验证码错误"
    return Result
})