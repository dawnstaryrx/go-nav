// import { useTokenStore } from '@/stores/token.js'
const routes = [
  {
    path: '/',
    name: 'index',
    title: '首页',
    component: () => import('@/views/HomePage.vue'), 
  },
]
export default routes