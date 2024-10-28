<template>
  <div class="page-container noto-serif-sc-font-family">
    <!-- 页面主体内容 -->
    <div v-if="bottomMenus.length > 0" class="content icp" style="max-width: 1100px; margin: auto;">
      友情链接：
      <a v-for="item in bottomMenus" :href="item.url" style="margin-right: 10px; text-decoration: none;">
        {{ item.title }} 
      </a>
    </div>
    
    <!-- 备案号部分 -->
    <div class="icp">
      CopyRight {{`© ${year} ${author} ` }}
      <a href="http://beian.miit.gov.cn/" target="_blank">
        {{ record }}
      </a>&nbsp;
      <a href="http://beian.miit.gov.cn/" target="_blank">
        <img src="@/assets/RecordIcon.png" alt="备案号" />
        {{ police }}
      </a>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import menuApi from '@/api/menu.js';
let year = new Date().getFullYear();        // 一般都是最新的一年
let author = '智浪星辰';						// 作者名
let record = '鲁ICP备2024107617号-1';		// 备案号
let police = '鲁公网安备37011202000109号';
const bottomMenus = ref([])
const getBottomMenu = async () => {
  try {
    const res = await menuApi.getMenuList(1);
    bottomMenus.value = res.data;
  } catch (error) {
    console.error(error);
  }
};
onMounted(() => {
  getBottomMenu();
});
</script>

<style>
.noto-serif-sc-font-family {
  font-family: "Noto Serif SC", serif;
  font-optical-sizing: auto;
  font-weight: 600;
  font-style: normal;
}
/* 页面根容器 */
.page-container {
  /* display: absolute; */
  bottom: 0;
}

/* 页面内容 */
.content {
  flex: 1; /* 让内容区域自动扩展，推到备案号组件 */
  /* 这里可以放置其他内容样式 */
}

/* 备案号样式 */
.icp {
  background-color: white;
  margin: 0;
  width: 100%;
  text-align: center;
  color: gray;
  padding: 5px 0;
}

.icp > a {
  color: gray;
  text-decoration: none;
}

.icp > a:hover {
  color: #de7622;
  text-decoration: none;
}
</style>
