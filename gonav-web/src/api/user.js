import request from '@/utils/request'

const userApi = {
  // 发送验证码
  sendCode: (email, type) => {
    return request.post('/public/sendCode', null, {params:{ "email":email, "type":type }})
  },
  register: (data) => {
    return request.post('/public/user/register', data)
  },
  resetPwd: (data) => {
    return request.post('/public/user/resetPwd', data)
  },
}

export default userApi;