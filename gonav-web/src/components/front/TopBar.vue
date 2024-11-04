<template>
  <div class="header-navbar-container">
    <nav class="navbar navbar-expand-lg bg-body-tertiary header-navbar-top">
      <div class="container-fluid header-navbar-top-content">
        <!-- 带有默认Logo和网站标题 -->
        <!-- <div v-if="site.name === '' && site.logo === ''" class="d-flex align-items-center">
          <img class="img-navbar-brand" src="@/assets/logo.png" alt="导航站" width="36" height="36" />
          <router-link class="navbar-brand ms-2 noto-serif-sc-brand"  to="/">Go导航</router-link>
        </div>
        <div v-else class="d-flex align-items-center">
          <img v-if="site.logo !== ''" class="img-navbar-brand" :src="site.logo" alt="导航站" width="36" height="36" />
          <router-link v-if="site.name !== ''" class="navbar-brand ms-2 noto-serif-sc-brand"  to="/">{{ site.name }}</router-link>
        </div> -->
        <!-- 不带有默认Logo和网站标题 -->
        <!-- <div class="d-flex align-items-center">
          <img v-if="site.logo !== ''" class="img-navbar-brand" :src="site.logo" alt="导航站" width="36" height="36" />
          <router-link v-if="site.name !== ''" class="navbar-brand ms-2 noto-serif-sc-brand"  to="/">{{ site.name }}</router-link>
        </div> -->
        <!-- 只使用默认Logo和网站标题 -->
        <div class="d-flex align-items-center">
          <img class="img-navbar-brand" src="@/assets/logo.png" alt="导航站" width="36" height="36" />
          <router-link class="navbar-brand ms-2 noto-serif-sc-brand"  to="/">Go导航</router-link>
        </div>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarText">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <!-- <li class="nav-item header-navbar-top-item">
              <router-link class="nav-link noto-serif-sc-text" aria-current="page" to="/hello">Hello</router-link>
            </li> -->
            <li v-for="item in topMenus" class="nav-item header-navbar-top-item">
              <a class="nav-link noto-serif-sc-text" :href="item.url"> {{ item.title }} </a>
            </li>
          </ul>
        </div>
        <ul class="navbar-nav ms-auto d-none d-lg-flex">
          <li v-if="site.githubLogo ==='true'" class="nav-item header-navbar-top-icon">
            <a class="nav-link" href="https://github.com/dawnstaryrx/go-nav" target="_blank">
              <svg xmlns="http://www.w3.org/2000/svg" class="icon" viewBox="0 0 1024 1024" fill="currentColor" aria-label="github icon" style="width:1.25rem;height:1.25rem;vertical-align:middle;"><path d="M511.957 21.333C241.024 21.333 21.333 240.981 21.333 512c0 216.832 140.544 400.725 335.574 465.664 24.49 4.395 32.256-10.07 32.256-23.083 0-11.69.256-44.245 0-85.205-136.448 29.61-164.736-64.64-164.736-64.64-22.315-56.704-54.4-71.765-54.4-71.765-44.587-30.464 3.285-29.824 3.285-29.824 49.195 3.413 75.179 50.517 75.179 50.517 43.776 75.008 114.816 53.333 142.762 40.79 4.523-31.66 17.152-53.377 31.19-65.537-108.971-12.458-223.488-54.485-223.488-242.602 0-53.547 19.114-97.323 50.517-131.67-5.035-12.33-21.93-62.293 4.779-129.834 0 0 41.258-13.184 134.912 50.346a469.803 469.803 0 0 1 122.88-16.554c41.642.213 83.626 5.632 122.88 16.554 93.653-63.488 134.784-50.346 134.784-50.346 26.752 67.541 9.898 117.504 4.864 129.834 31.402 34.347 50.474 78.123 50.474 131.67 0 188.586-114.73 230.016-224.042 242.09 17.578 15.232 33.578 44.672 33.578 90.454v135.85c0 13.142 7.936 27.606 32.854 22.87C862.25 912.597 1002.667 728.747 1002.667 512c0-271.019-219.648-490.667-490.71-490.667z"></path></svg>
            </a>
          </li>
          <li v-if="(!token) || token===''" class="nav-item header-navbar-top-icon">
            <router-link class="nav-link" to="/login">
              <svg t="1727966561987" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="5152" width="20" height="20"><path d="M521.7 82c-152.5-0.4-286.7 78.5-363.4 197.7-3.4 5.3 0.4 12.3 6.7 12.3h70.3c4.8 0 9.3-2.1 12.3-5.8 7-8.5 14.5-16.7 22.4-24.5 32.6-32.5 70.5-58.1 112.7-75.9 43.6-18.4 90-27.8 137.9-27.8 47.9 0 94.3 9.3 137.9 27.8 42.2 17.8 80.1 43.4 112.7 75.9 32.6 32.5 58.1 70.4 76 112.5C865.7 417.8 875 464.1 875 512c0 47.9-9.4 94.2-27.8 137.8-17.8 42.1-43.4 80-76 112.5s-70.5 58.1-112.7 75.9c-43.6 18.4-90 27.8-137.9 27.8-47.9 0-94.3-9.4-137.9-27.8-42.2-17.8-80.1-43.4-112.7-75.9-7.9-7.9-15.3-16.1-22.4-24.5-3-3.7-7.6-5.8-12.3-5.8H165c-6.3 0-10.2 7-6.7 12.3C234.9 863.2 368.5 942 520.6 942c236.2 0 428-190.1 430.4-425.6C953.4 277.1 761.3 82.6 521.7 82zM395.025 624v-76h-314c-4.4 0-8-3.6-8-8v-56c0-4.4 3.6-8 8-8h314v-76c0-6.7 7.8-10.5 13-6.3l141.9 112c4.1 3.2 4.1 9.4 0 12.6l-141.9 112c-5.2 4.1-13 0.4-13-6.3z" fill="currentColor" p-id="5153"></path></svg>
            </router-link>
          </li>
          <li v-else class="nav-item dropdown header-navbar-top-icon">
            <a class="nav-link dropdown-toggle no-caret" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
              <svg t="1728716103482" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="5754" width="20" height="20"><path d="M768 298.666667c0 141.386667-114.613333 256-256 256S256 440.053333 256 298.666667 370.613333 42.666667 512 42.666667s256 114.613333 256 256z m108.18 446.486666A211.94 211.94 0 0 0 725.333333 682.666667H298.666667a213.333333 213.333333 0 0 0-213.333334 213.333333v32a53.393333 53.393333 0 0 0 53.333334 53.333333h746.666666a53.393333 53.393333 0 0 0 53.333334-53.333333v-32a211.94 211.94 0 0 0-62.486667-150.846667z" fill="currentColor" p-id="5755"></path></svg>
            </a>
            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
              <li><a class="dropdown-item" style="pointer-events: none;">{{ greeting }} ！ {{ nowUser.nickname }}</a></li>
              <li><a class="dropdown-item" href="/manage/index">管理</a></li>
              <li><hr class="dropdown-divider"></li>
              <li><a class="dropdown-item" href="#" @click.prevent="logout()">登出</a></li>
            </ul>
          </li>
        </ul>
      </div>
    </nav>
  </div>
</template>

<script>
import {useTokenStore} from "@/stores/token.js";
import { useUserInfoStore } from "@/stores/userInfo.js";
import { useRouter } from 'vue-router';
import menuApi from '@/api/menu.js';
import { onMounted, ref } from "vue";
import settingApi from "@/api/setting";

export default {
  setup() {
    const tokenStore = useTokenStore();
    const token = tokenStore.token.token;
    const router = useRouter()
    const topMenus = ref([])
    const nowUser = ref({})
    const userInfoStore = useUserInfoStore();
    const site = ref({
      name: '',
      logo: '',
      githubLogo: ''
    })
    // 定义登出方法
    const logout = () => {
      if (token == null || token == '') {
        // 如果 token 为 null，用户尚未登录，直接重定向到登录页
        router.push("/login");
      } else {
        // 清除 token（根据你的 tokenStore 实现可能需要调整）
        tokenStore.removeToken(); // 假设你有一个 setToken 方法
        userInfoStore.removeInfo();
        // 重定向到登录页或主页
        router.push("/login");
      }
    };
    const getTopMenu = async () => {
      try {
        const res = await menuApi.getMenuList(0);
        topMenus.value = res.data;
        console.log(topMenus.value);
      } catch (error) {
        console.error(error);
      }
    };
    const getSiteSetting = async () => {
      try {
        const res = await settingApi.getSiteSetting();
        site.value = res.data;
      } catch (error) {
      }
    };
    const getNowUser = () => {
      nowUser.value = userInfoStore.info;
      console.log("now", nowUser.value);
    }
    const greeting = ref("");

    const setGreeting = () => {
      const hour = new Date().getHours();
      if (hour >= 5 && hour < 8) {
        greeting.value = "早上好";
      } else if (hour >= 8 && hour < 11) {
        greeting.value = "上午好";
      } else if (hour >= 11 && hour < 14) {
        greeting.value = "中午好";
      } else if (hour >= 14 && hour < 18) {
        greeting.value = "下午好";
      } else if (hour >= 18 && hour < 22) {
        greeting.value = "晚上好";
      } else {
        greeting.value = "不要熬夜";
      }
    };
    onMounted(() => {
      getSiteSetting();
      getTopMenu();
      getNowUser();
      setGreeting();
    });
    return {
      token,
      logout,
      topMenus,
      nowUser,
      greeting,
      site
    };
  }
};
</script>

<style scoped>
.header-navbar-container{
  width: 100%;
  position: fixed;
  backdrop-filter: blur(6px);
  z-index: 1000;
}
.header-navbar-top {
  opacity: 0.90;
  font-family: 'STHeiti';
  margin: auto;
  box-shadow: 0px 2px 5px 2px rgba(0, 0, 0, 0.1);
}
.noto-serif-sc-brand {
  font-family: "Noto Serif SC", serif;
  font-optical-sizing: auto;
  font-weight: 900;
  font-style: normal;
}
.noto-serif-sc-text {
  font-family: "Noto Serif SC", serif;
  font-optical-sizing: auto;
  font-weight: 700;
  font-style: normal;
  font-size: 18px;
}
.header-navbar-top-content {
  max-width: 1100px;
  margin: 0 auto;
  padding: 0;
}
.navbar-brand:hover{
  color: #de7622 !important;
}
.header-navbar-top-icon{

}
.header-navbar-top-item:hover>a{
  color: #de7622 !important;
}
.header-navbar-top-icon:hover>a{
  color: #de7622 !important;
}
.header-navbar-top-item{
  position: relative;  /* 确保伪元素相对于链接定位 */;
  text-decoration: none;  /* 移除默认的下划线 */
}
.header-navbar-top-item::after{
  content: '';
  position: absolute;
  bottom: -2px;             /* 下划线位于文本的下方 */
  left: 50%;             /* 起始位置在中间 */
  width: 0;              /* 默认宽度为 0 */
  height: 2px;           /* 下划线的高度 */
  background-color: #de7622; /* 下划线的颜色 */
  transition: width 0.3s ease, left 0.3s ease;  /* 添加动画效果 */
  
}
.header-navbar-top-item:hover::after{
  width: 100%;           /* 下划线扩展至整个文本宽度 */
  left: 0;               /* 从左边展开 */
}
.router-link-active {
  color: #de7622 !important;
}
.no-caret::after {
  display: none !important;
}
</style>