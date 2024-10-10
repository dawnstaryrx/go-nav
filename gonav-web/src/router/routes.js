// import { useTokenStore } from '@/stores/token.js'
const routes = [
  {
    path: '/hello',
    name: 'hello',
    title: '你好',
    component: () => import('@/views/HelloPage.vue'), 
  },
  {
    path: '/',
    name: 'index',
    title: '首页',
    component: () => import('@/views/HomePage.vue'), 
  },
  {
    path: '/login',
    name: 'login',
    title: '登录',
    component: () => import('@/views/login/LoginPage.vue'), 
  },
  {
    path: '/register',
    name: 'register',
    title: '注册',
    component: () => import('@/views/login/RegisterPage.vue'),
  },
  {
    path: '/reset-pwd',
    name: 'reset-pwd',
    title: '重置密码',
    component: () => import('@/views/login/ResetPwdPage.vue'),
  }
]
export default routes