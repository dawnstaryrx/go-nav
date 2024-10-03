//定制请求的实例

//导入axios  npm install axios
import axios from 'axios';
import router from "@/router";
// import {ElMessage} from 'element-plus'
// import {useTokenStore} from "@/stores/token.js";
//定义一个变量,记录公共的前缀  ,  baseURL
const baseURL = '/api';
const instance = axios.create({baseURL})

instance.interceptors.request.use(
    (config) => {
        // 添加token
        // const tokenStore = useTokenStore();
        // if (tokenStore.token) {
        //     config.headers.token = tokenStore.token.token;
        // }
        return config;
    },
    (err) => {
        Promise.reject(err);
    }
)
//添加响应拦截器
instance.interceptors.response.use(
    result => {
        if (result.data.code === 0) {
            return result.data;
        }
        // ElMessage.error(result.data.message ? result.data.message : "服务异常")
        return Promise.reject(result.data)

    },
    err => {
        if (err.response.status === 401) {
            // ElMessage.error("请先登陆");
            router.push("/login")
        } else {
            // ElMessage.error("服务异常")
        }

        return Promise.reject(err);//异步的状态转化成失败的状态
    }
)

export default instance;