import request from '@/utils/request'

const testApi = {
  default: () => {
    return request.get('/default/hello')
  },
  public: () => {
    return request.get('/public/hello')
  },
  user: () => {
    return request.get('/user/hello')
  },
  admin: () => {
    return request.get('/admin/hello')
  },
}

export default testApi;