<template>
  <TopBar></TopBar>
  <div class="container-fluid">
    <div class="row">
      <!-- 左侧导航栏：在大屏幕为横向布局，小屏幕为纵向布局 -->
      <LeftBar class="col-12 col-sm-1 d-block d-sm-flex" style="padding-right: 0;" :class="leftBarClass"></LeftBar>
      
      <!-- 主体内容区域 -->
      <div class="col-12 col-sm" style="margin-top: 66px;">
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted, onBeforeUnmount, computed } from 'vue';
import TopBar from '@/components/manage/TopBar.vue';
import LeftBar from '@/components/manage/LeftBar.vue';

export default {
  name: 'AdminIndex',
  components: {
    TopBar,
    LeftBar
  },
  setup() {
    const isSmallScreen = ref(window.innerWidth <= 576);  // Bootstrap sm 尺寸为 576px

    // 当窗口大小发生变化时更新 isSmallScreen
    const updateScreenSize = () => {
      isSmallScreen.value = window.innerWidth <= 576;
    };

    onMounted(() => {
      window.addEventListener('resize', updateScreenSize);
    });

    onBeforeUnmount(() => {
      window.removeEventListener('resize', updateScreenSize);
    });

    // 根据屏幕大小动态应用不同的样式
    const leftBarClass = computed(() => {
      return isSmallScreen.value ? 'min-h-100' : 'min-vh-100';
    });

    return {
      leftBarClass
    };
  }
}
</script>

<style scoped>
/* 在小屏幕下，确保 LeftBar 根据内容调整高度 */
@media (max-width: 576px) {
  .min-h-100 {
    width: 100% !important;
  }
}
</style>
