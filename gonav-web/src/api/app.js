import request from '@/utils/request'

const appApi = {
  // --------------------------------- 公开 ----------------------------------
  getAppByUsernameAndCategoryIdPublic: (username, categoryId) => {
    return request.get('/public/app', {
      params: {
        username,
        categoryId
      }
    })
  },
  // --------------------------------- 用户 ----------------------------------
  getAppByUsernameAndCategoryIdUser: (username, categoryId) => {
    return request.get('/user/app', {
      params: {
        username,
        categoryId
      }
    })
  },
  addCategory: (data) => {
    return request.put('/user/category', data)
  },
  getCategoryPageList: (pageNum, pageSize) => {
    return request.get('/user/category', {
      params: {
        pageNum,
        pageSize
      }
    });
  },
  deleteCategory: (id) => {
    return request.delete('/user/category', {params:{"id":id}})
  },
  updateCategory: (data) => {
    return request.post('/user/category', data)
  },
  // -------------------------------- 超级管理 --------------------------------
  getAllCategory: (pageNum, pageSize) => {
    return request.get('/admin/category',{
      params: {
        pageNum,
        pageSize
      }
    })
  },
  deleteCategoryAdmin: (id) => {
    return request.delete('/admin/category', {params:{"id":id}})
  },
}

export default appApi;