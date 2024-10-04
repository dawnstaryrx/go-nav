// import { useTokenStore } from '@/stores/token.js'
const routes = [
  {
    path: '/',
    name: 'index',
    title: '首页',
    component: () => import('@/views/HomePage.vue'), 
  },
  {
    path: '/hello',
    name: 'hello',
    title: '你好',
    component: () => import('@/views/HelloPage.vue'), 
  },
]
export default routes