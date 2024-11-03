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
  },
  {
    path: '/auth/linux-do/callback',
    name: 'linux-do-callback',
    title: 'linux-do回调',
    component: () => import('@/views/callback/LinuxDoPage.vue'),
  },
  {
    path: '/auth/github/callback',
    name: 'github-callback',
    title: 'github回调',
    component: () => import('@/views/callback/GitHubPage.vue'),
  },
  {
    path: '/nav/:username',
    name: 'user-nav',
    title: '用户导航',
    component: () => import('@/views/nav/NavPage.vue'),
  },
  {
    path: '/manage',
    name: 'manage',
    title: '管理',
    component: () => import('@/views/manage/BasePage.vue'),
    children: [
      {
        path: 'index',
        name: 'manage-index',
        title: '首页',
        component: () => import('@/views/manage/IndexPage.vue'),
      },
      {
        path: 'category',
        name: 'manage-category',
        title: '类别',
        component: () => import('@/views/manage/CategoryPage.vue'),
      },
      {
        path: 'app',
        name: 'admin-app',
        title: '应用',
        component: () => import('@/views/manage/AppPage.vue'),
      },
      {
        path: 'my',
        name: 'manage-my',
        title: '我的',
        component: () => import('@/views/manage/MyPage.vue'),
      },
      {
        path: 'admin',
        name: 'admin',
        children: [
          {
            path: 'user',
            name: 'admin-user',
            title: '用户管理',
            component: () => import('@/views/admin/UserAdminPage.vue'),
          },
          {
            path: 'menu',
            name: 'admin-menu',
            title: '菜单',
            component: () => import('@/views/admin/MenuPage.vue'),
          },
          {
            path: 'setting',
            name: 'admin-setting',
            title: '设置',
            component: () => import('@/views/admin/SettingPage.vue'),
          },
          {
            path: 'announce',
            name: 'admin-announce',
            title: '用户管理',
            component: () => import('@/views/admin/AnnouncePage.vue'),
          },
          {
            path: 'category',
            name: 'admin-category',
            title: '分类超级管理',
            component: () => import('@/views/admin/CategoryAdminPage.vue'),
          },
        ]
      }
      
    ],
  },
  {
    path: '/404',
    name: '404page',
    component: () => import('@/views/404/IndexPage.vue'),
  },
    // 未知路由重定向
  {
    path: '/:pathMatch(.*)',
    redirect: '/404',
    hidden: true
  }
]
export default routes