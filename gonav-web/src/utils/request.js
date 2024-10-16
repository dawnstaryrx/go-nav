//定制请求的实例

//导入axios  npm install axios
import axios from 'axios';
import router from "@/router";
import alertUtil from "./alert";
// import {ElMessage} from 'element-plus'
import {useTokenStore} from "@/stores/token.js";
//定义一个变量,记录公共的前缀  ,  baseURL
const baseURL = '/api';
const instance = axios.create({baseURL})

instance.interceptors.request.use(
    (config) => {
        // 添加 token
        const tokenStore = useTokenStore();
        if (tokenStore.token) {
            config.headers.Authorization = `Bearer ${tokenStore.token.token}`; // 使用 Authorization 头
        }
        return config;
    },
    (err) => {
        Promise.reject(err);
    }
)
//添加响应拦截器
instance.interceptors.response.use(
  result => {
    if (result.data.code == 0) {
      return result.data;
    }
    alertUtil.message(result.data.message ? result.data.message : "服务异常", "danger")
    return Promise.reject(result.data)
  },
    err => {
      const originalRequest = err.config;
      if(err.response.status == 401 && !originalRequest.url.includes('/api/public/refreshToken')){
        const tokenStore = useTokenStore();
        const refreshToken = tokenStore.token.refreshToken;
        if (refreshToken) {
        // 发送刷新 token 请求
        return new Promise(function(resolve, reject) {
          axios.post('/api/public/refreshToken', null, { params: { refreshToken } })
            .then(({ data }) => {
              if (data.code == 0) { // 确保刷新成功
                // 更新 tokenStore
                tokenStore.setToken(data.data);
                // 更新 Authorization 头
                instance.defaults.headers.common['Authorization'] = `Bearer ${data.data.token}`;
                originalRequest.headers.Authorization = `Bearer ${data.data.token}`;
                // 重试原始请求
                resolve(instance(originalRequest));
              } else {
                  tokenStore.removeToken();
                  router.push('/login');
                  alertUtil.message(data.message || "请重新登录", "danger");
                  reject(new Error(response.data.message || "刷新令牌失败"));
              }
            })
            .catch((err) => {
                tokenStore.removeToken();
                router.push('/login');
                alertUtil.message("刷新令牌失败，请重新登录", "danger");
                reject(err);
            })
        });
      }
      }
      else if (err.response.status == 401) {
          alertUtil.message("请先登陆", "danger")
          router.push("/login")
      } else {
          alertUtil.message("服务异常", "danger")
      }

      return Promise.reject(err);//异步的状态转化成失败的状态
    }
)

export default instance;