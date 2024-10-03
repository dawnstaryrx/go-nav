import { createRouter, createWebHistory} from 'vue-router';
// 导入路由页面的配置
import routes from './routes'

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;