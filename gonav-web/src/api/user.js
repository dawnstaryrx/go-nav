import request from '@/utils/request'
import { param } from 'jquery'

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
  },
  // GitHub登录
  loginGitHub: (code) => {
    return request.post('/public/user/login/github', { code })
  },
  // 获取当前用户信息
  getCurrentUser: () => {
    return request.get('/user/current')
  },
  // 获取用户分页列表
  getUserPageList: (pageNum, pageSize, searchContent, orderBy) => {
    return request.get('/admin/user/page', {
      params: {
        "pageNum":pageNum,
        "pageSize":pageSize,
        "searchContent":searchContent,
        "orderBy":orderBy,
      }
    })
  },
  // 删除用户
  deleteUser: (id) => {
    return request.delete('/admin/user/' + id)
  },
  // 修改用户状态
  updateUserRole: (role, userId) => {
    return request.post("/admin/user", null, {
      params: {
        "role":role,
        "userId":userId
      }
    })
  },
  // 修改用户信息
  updateUser: (data) => {
    return request.post("/user/user", data)
  }
}

export default userApi;