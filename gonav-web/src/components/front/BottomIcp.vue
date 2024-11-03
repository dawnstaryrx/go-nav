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
      <span v-if="author !== ''">
        CopyRight {{`© ${year} ${author} ` }}
      </span>
      
      <a v-if="record !== ''" href="http://beian.miit.gov.cn/" target="_blank">
        {{ record }}
      </a>&nbsp;

      <a v-if="police !== ''" href="https://beian.mps.gov.cn/#/query/webSearch" target="_blank">
        <img src="@/assets/RecordIcon.png" alt="备案号" />
        {{ police }}
      </a>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import menuApi from '@/api/menu.js';
import settingApi from '@/api/setting.js';

export default {
  name: 'BottomIcp',
  setup() {
    const year = new Date().getFullYear();        // 一般都是最新的一年
    const author = ref('');						// 作者名
    const record = ref('');		// 备案号
    const police = ref('');
    const bottomMenus = ref([])
    const getBottomMenu = async () => {
      try {
        const res = await menuApi.getMenuList(1);
        bottomMenus.value = res.data;
      } catch (error) {
        console.error(error);
      }
    };
    const getBottomSetting = async () => {
      try {
        const res = await settingApi.getBottomSetting();
        console.log(res.data);
        author.value = res.data.companyName;
        record.value = res.data.icp;
        police.value = res.data.police;
      } catch (error) {
        console.error(error);
      }
    };
    getBottomSetting();
    onMounted(() => {
      getBottomMenu();
      getBottomSetting();
    });
    return {
      year,
      author,
      record,
      police,
      bottomMenus
    };
  },
};



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
