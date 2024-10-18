import request from '@/utils/request'

const categoryApi = {
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
  }
}

export default categoryApi;