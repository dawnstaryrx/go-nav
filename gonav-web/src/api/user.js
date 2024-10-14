import request from '@/utils/request'

const userApi = {
  // 发送验证码
  sendCode: (email, type) => {
    return request.post('/public/sendCode', null, {params:{ "email":email, "type":type }})
  },
  // 注册
  register: (data) => {
    return request.post('/public/user/register', data)
  },
  // 重置密码
  resetPwd: (data) => {
    return request.post('/public/user/resetPwd', data)
  },
  // 密码登录
  loginPassword: (data) => {
    return request.post('/public/user/login/password', data)
  },
  // 验证码登录
  loginCode: (data) => {
    return request.post('/public/user/login/code', data)
  },
  // LinuxDo登录
  loginLinuxDo: (code) => {
    return request.post('/public/user/login/linuxdo', { code })
  }
}

export default userApi;