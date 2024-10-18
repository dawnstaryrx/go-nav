<template>
  <div class="m-content-display">
    <div class="m-router-view">
    <router-view />
    </div>
    <!-- 使用 v-if 根据条件决定是否显示 BottomIcp -->
    <BottomIcp v-if="!hideBottomIcp" />
</div>

</template>
<script>
import BottomIcp from "@/components/front/BottomIcp.vue";
export default {
  components: {
    BottomIcp
  },
  computed: {
    hideBottomIcp() {
      const route = this.$route;
      // 确保 this.$route 存在后再访问 path
      if (!route || !route.path) {
        return false;  // 如果没有路由信息，默认显示 BottomIcp
      }
      const path = route.path;
      // 检查当前路由是否是 /login, /register 或以 /admin 开头
      return path === '/login' || path === '/register' || path ==='/404' ||path === '/reset-pwd' || path.startsWith('/manage');
    }
  }
}
</script>
<style scoped>
html, body, #app {
  margin: 0;
  padding: 0;
  height: 100%;
  width: 100%;
  /* 禁止横向滚动 */
  overflow-x: hidden; 
  box-sizing: border-box;
}

*, *::before, *::after {
  box-sizing: inherit;
}
.alert {
    z-index: 1051 !important; /* 确保 z-index 覆盖其他样式 */
}
.m-content-display {
  display: flex;
  flex-direction: column;
  /* 使用视口高度 */
  min-height: 100vh; 
  /* 禁止横向滚动 */
  overflow-x: hidden; 
}
.m-router-view {
  flex: 1;
}

</style>
