import request from '@/utils/request'

const menuApi = {
  // --------------------------------- 公开 ----------------------------------
  getMenuList: (type) => {
    return request.get('/public/menu', {
      params: {
        type
      }
    })
  },

  // -------------------------------- 超级管理 --------------------------------
  addMenu: (data) => {
    return request.put('/admin/menu', data)
  },
  deleteMenu: (id) => {
    return request.delete('/admin/menu', { params: { "id": id } })
  },
  updateMenu: (data) => {
    return request.post('/admin/menu', data)
  }
}

export default menuApi
