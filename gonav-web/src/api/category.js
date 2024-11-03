import request from '@/utils/request'

const categoryApi = {
  // --------------------------------- 公开 ----------------------------------
  getCategoryByUsername: (username) => {
    return request.get('/public/category', {
      params: {
        username
      }
    })
  },
  // --------------------------------- 用户 ----------------------------------
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
  getCategoryList: () => {
    return request.get('/user/category/now');
  },
  deleteCategory: (id) => {
    return request.delete('/user/category', {params:{"id":id}})
  },
  updateCategory: (data) => {
    return request.post('/user/category', data)
  },
  // -------------------------------- 超级管理 --------------------------------
  getAllCategory: (pageNum, pageSize, searchContent, orderBy) => {
    return request.get('/admin/category',{
      params: {
        pageNum,
        pageSize,
        searchContent,
        orderBy
      }
    })
  },
  deleteCategoryAdmin: (id) => {
    return request.delete('/admin/category', {params:{"id":id}})
  },
}

export default categoryApi;