<template>
  <div class="d-flex flex-column flex-shrink-0 p-3 bg-light left-bar-container" style="width: 200px;">
    <ul style="margin-top: 60px;" class="nav nav-pills flex-column mb-auto">
      <li class="nav-item">
        <!-- :class="currentRoute.path === '/manage/index' ? 'nav-link active' : 'nav-link link-dark'" -->
        <router-link to="/manage/index" class="nav-link link-dark" active-class="nav-link active nav-white" aria-current="page">
          <i class="bi bi-house"></i>
          首页
        </router-link>
      </li>
      <li>
        <router-link to="/manage/category" :class="currentRoute.path === '/manage/category' ? 'nav-link active' : 'nav-link link-dark'">
          <i class="bi bi-bookmarks"></i>
          分类管理
        </router-link>
      </li>
      <li>
        <router-link to="/manage/app" :class="currentRoute.path === '/manage/app' ? 'nav-link active' : 'nav-link link-dark'">
          <i class="bi bi-google-play"></i>
          应用管理
        </router-link>
      </li>
      <li>
        <router-link to="/manage/my" :class="currentRoute.path === '/manage/my' ? 'nav-link active' : 'nav-link link-dark'">
          <i class="bi bi-person"></i>
          个人中心
        </router-link>
      </li>
      <!-- 超级管理菜单 -->
      <li v-if="nowUser.role && nowUser.role === 2" class="nav-item">
        <a :class="currentRoute.path.startsWith('/manage/admin') ? 'nav-link active' : 'nav-link link-dark'" 
          data-bs-toggle="collapse" 
          href="#collapseExample" 
          role="button" 
          :aria-expanded="currentRoute.path.startsWith('/manage/admin')" >
          <i class="bi bi-airplane-engines"></i>
          超级管理
        </a>
        <div :class="['collapse', currentRoute.path.startsWith('/manage/admin') ? 'show' : '']" 
            id="collapseExample" 
            style="margin-left: 25px; margin-bottom: 10px; background-color: white; border-radius: 10%;">
          <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
            <li>
              <router-link to="/manage/admin/user" :class="currentRoute.path === '/manage/admin/user' ? 'nav-link active ps-4' : 'nav-link link-dark ps-4'">
                <i class="bi bi-people"></i> 
                用户管理
              </router-link>
            </li>
            <li>
              <router-link to="/manage/admin/menu" :class="currentRoute.path === '/manage/admin/menu' ? 'nav-link active ps-4' : 'nav-link link-dark ps-4'">
                <i class="bi bi-menu-button-wide-fill"></i>
                菜单管理
              </router-link>
            </li>
            <li>
              <router-link to="/manage/admin/category" :class="currentRoute.path === '/manage/admin/category' ? 'nav-link active ps-4' : 'nav-link link-dark ps-4'">
                <i class="bi bi-bookmarks"></i> 
                分类管理
              </router-link>
            </li>
            <router-link to="/manage/admin/announce" :class="currentRoute.path === '/manage/admin/announce' ? 'nav-link active ps-4' : 'nav-link link-dark ps-4'">
                <i class="bi bi-megaphone"></i> 
                通知公告
              </router-link>
            <li>
              <router-link to="/manage/admin/setting" :class="currentRoute.path === '/manage/admin/setting' ? 'nav-link active ps-4' : 'nav-link link-dark ps-4'">
                <i class="bi bi-gear"></i>
                设置
              </router-link>
            </li>
          </ul>
        </div>
      </li>
    </ul>
    <hr>
    <div class="dropdown">
      <a href="#" class="d-flex align-items-center link-dark text-decoration-none dropdown-toggle" id="dropdownUser2" data-bs-toggle="dropdown" aria-expanded="false">
        <img src="@/assets/404/astronaut.svg" alt="" width="32" height="32" class="rounded-circle me-2">
        <strong>用户</strong>
      </a>
      <ul class="dropdown-menu text-small shadow" aria-labelledby="dropdownUser2">
        <li><a class="dropdown-item" href="/manage/my">个人资料</a></li>
        <!-- <li><hr class="dropdown-divider"></li>
        <li><a class="dropdown-item" href="#">退出登录</a></li> -->
      </ul>
    </div>
  </div>
</template>

<script>
import { useRoute } from 'vue-router';
import { useUserInfoStore } from "@/stores/userInfo.js";
import { onMounted, ref } from 'vue';

export default {
  setup() {
    const currentRoute = useRoute(); // 获取当前路由对象
    console.log(currentRoute.path);
    const nowUser = ref({});
    const userInfoStore = useUserInfoStore();
    const getNowUser = () => {
      nowUser.value = userInfoStore.info;
      console.log("now", nowUser.value);
    }
    onMounted(() => {
      getNowUser();
    })
    return {
      currentRoute,
      nowUser
    };
  }
}
</script>

<style scoped>
/* 可根据需要添加更多自定义样式 */
.left-bar-container {
  
}

.collapse ul {
  margin-top: 10px;
}

.ps-4 {
  padding-left: 1.5rem !important;
}
.active{
  background-color: #de7622 !important;
}
.nav-white{
  color: white !important;
}
</style>
