import request from '@/utils/request'
import { param } from 'jquery';

const categoryApi = {
  // --------------------------------- 公开 ----------------------------------
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
}

export default categoryApi;