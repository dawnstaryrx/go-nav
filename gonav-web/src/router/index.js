import { createRouter, createWebHistory} from 'vue-router';
import { useTokenStore } from '@/stores/token';
import {useUserInfoStore} from '@/stores/userInfo'
// 导入路由页面的配置
import routes from './routes'

const router = createRouter({
  history: createWebHistory(),
  routes,
});

// 全局导航守卫
router.beforeEach((to, from, next) => {
  const tokenStore = useTokenStore();
  const isLoggedIn = tokenStore.token.token == null || tokenStore.token.token == "" ? false : true;
  if (to.path == "/login" && isLoggedIn) {
    // 已登录，重定向到主页
    next({ path: "/" });
  } else {
    next();
  }
  // const userInfoStore = useUserInfoStore();
  // const notBlank = tokenStore.token.token == null || tokenStore.token.token == "" || userInfoStore.role == null || userInfoStore.role === 0 ? false : true;
  // if (to.path == "/login" && notBlank) {
  //   // 已登录，重定向到主页
  //   next({ path: "/" });
  // } else {
  //   next();
  // }
});

export default router;