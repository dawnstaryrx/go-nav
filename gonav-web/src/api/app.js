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
  clickApp: (id) => {
    return request.post('/public/app/click', {
      id: id
    }, {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    });
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
  addApp: (data) => {
    return request.put('/user/app', data)
  },
  getAppPageList: (pageNum, pageSize, categoryId, status, searchContent) => {
    return request.get('/user/app/page', {
      params: {
        pageNum,
        pageSize,
        categoryId,
        status,
        searchContent
      }
    });
  },
  deleteApp: (id) => {
    return request.delete('/user/app', {params:{"id":id}})
  },
  updateApp: (data) => {
    return request.post('/user/app', data)
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