import request from '@/utils/request'

const settingApi = {
  // --------------------------------- 公开 ----------------------------------
  getSetting: (key) => {
    return request.get('/public/setting', {
      params: {
        key
      }
    })
  },
  getBottomSetting: () => {
    return request.get('/public/setting/bottom')
  },
  getSiteSetting: () => {
    return request.get('/public/setting/site')
  },

  // -------------------------------- 超级管理 --------------------------------
  updateSetting: (data) => {
    return request.post('/admin/setting', data)
  }
}

export default settingApi
