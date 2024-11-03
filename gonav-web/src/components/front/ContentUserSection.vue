<template>
  <div class="content-section-search-container">
    <form class="d-flex " role="search" style="width: 100%;" @submit="handleSearch">
      <input 
        ref="searchInput"
        class="form-control me-2" 
        type="search" 
        placeholder="按任意键开始搜索~~" 
        aria-label="Search" 
        style="border: 0px;box-shadow: 0 0 0 0rem;"
        >
      <button class="btn custom-search-btn" type="submit" >
        <svg t="1728041224119" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="5573" width="20" height="20"><path d="M351.1808 59.2896A435.2 435.2 0 0 1 805.376 715.264 460.8 460.8 0 0 1 351.1808 59.3408z" fill="#ffffff" p-id="5574" data-spm-anchor-id="a313x.search_index.0.i12.117c3a817iUd2m" class="selected"></path><path d="M754.3808 722.2272a358.4 358.4 0 1 0-267.8272 120.2176 51.2 51.2 0 0 1 0 102.4 460.8 460.8 0 1 1 365.1584-179.712l118.8864 121.2416c23.7568 24.2176 23.552 63.0272-0.4096 87.04l-0.4096 0.4096a61.184 61.184 0 0 1-86.9888-0.4608l-148.0192-150.9376a61.7984 61.7984 0 0 1 0.4096-86.9888l0.4096-0.4096c5.632-5.5808 11.9808-9.8304 18.7904-12.8z m-467.968-364.5952h409.6a51.2 51.2 0 1 1 0 102.4h-409.6a51.2 51.2 0 1 1 0-102.4z m0 204.8h256a51.2 51.2 0 0 1 0 102.4h-256a51.2 51.2 0 1 1 0-102.4z" fill="currentColor" p-id="5575"></path></svg>
      </button>
    </form>
    <br>
  </div>

  <!-- 顶级父分类展示 -->
  <div class="content-section-category-container">
    <span
      class="capsule"
      @click="nowCategory = null;nowHotOrAll = 'ALL'; getAllApp(); "
      :class="{ 'capsule-active': (!nowCategory && nowHotOrAll === 'ALL') }"
    >
      全部应用
    </span>
    <span
      class="capsule"
      @click="nowCategory = null;nowHotOrAll='HOT'; getHotApp(); "
      :class="{ 'capsule-active': (!nowCategory && nowHotOrAll === 'HOT') }"
    >
      热门应用
    </span>
    <!-- 遍历顶级父分类 -->
    <span
      v-for="category in topLevelCategories"
      :key="category.id"
      class="capsule"
      :class="{ 'capsule-active': (selectedCategory && selectedCategory.parentId === category.id) || (nowCategory && nowCategory.id === category.id) }"
      @click="selectCategory(category);"
      :title="category.description"
    >
      {{ category.name }}
    </span>
  </div>

  <!-- 当前选中的分类的子分类展示 -->
  <div v-if="selectedCategory" class="content-section-category-container" style="margin-top: 10px;">
    <RecursiveCategory
      :category="selectedCategory"
      :now-category="nowCategory"
      @select-category="selectCategory"
    />
  </div>
  <!-- APP -->
  <div class="row apps-container">
    <div v-for="app in appList" class=" col-md-3 col-lg-3 col-sm-4 col-4 app-container"  :title="app.description">
    <a :href="app.url" @click="clickApp(app.id);" style="text-decoration: none;">
      <div class="app-card d-flex align-items-center">
        <div class="app-logo">
          <!-- 条件渲染 Logo 或默认占位符 -->
          <img 
              v-if="app.iconUrl !== ''"
              :src="app.iconUrl" 
              alt="App Logo" 
              class="img-fluid  rounded-circle" 
            />
          <div v-else class="default-logo  rounded-circle">{{ app.name.charAt(0) }}</div>
        </div>
        <div class="app-info">
          <div class="app-title"> {{ app.name }} </div>
          <div class="app-category">
            <span style="background-color: aliceblue;">
              {{ app.categoryName }}
            </span>
            <span style="background-color: white;">
              &nbsp;
            </span>
            <span style="background-color: beige; float: right;">  
              {{ app.clickCount }}
            </span>
          </div>
          <div class="app-description"> {{ app.description }}</div>
        </div>
      </div>
    </a>
    </div>
  </div>
</template>

<script>
import { ref, onMounted, onBeforeUnmount, computed } from 'vue';
import RecursiveCategory from './RecursiveCategory.vue'; // 引入递归组件
import categoryApi from '@/api/category';
import appApi from '@/api/app';
import {useRouter} from "vue-router";

export default {
  name: 'SearchCenter',
  components: {
    RecursiveCategory
  },
  props: {
    username: {
      type: String,
      required: true
    }
  },
  setup(props) {
    // 引用搜索输入框
    const searchInput = ref(null);
    // APP
    const appList = ref([]);
    const appListDTO = ref({
      username: props.username,
      categoryId: null
    });
    const nowHotOrAll = ref("ALL");
    // 初始化一级分类选项
    onMounted(() => {
      // 添加键盘事件监听
      window.addEventListener('keydown', handleKeydown);
    });
    onBeforeUnmount(() => {
      window.removeEventListener('keydown', handleKeydown);
    });

    // 处理表单提交
    const handleSearch = async (event) => {
      event.preventDefault(); // 阻止表单默认提交行为
      const query = searchInput.value.value.trim();
      if (query && query !== '') {
        const res = await appApi.searchAppByUsername(appListDTO.value.username, query)
        appList.value = res.data
        console.log('query', query)
        console.log(res.data)
        console.log('搜索查询:', query);
      } else if (query === '') {
        // 如果用户没有输入任何内容，则显示所有分类
        // await getAllCategory();
        getAllApp();
      } else {
        console.log('请输入搜索内容');
      }
    };

    const handleKeydown = (event) => {
      // 忽略修饰键（Ctrl, Alt, Meta, Shift）
      if (event.ctrlKey || event.altKey || event.metaKey || event.shiftKey) {
        return;
      }

      // 检查当前是否聚焦在输入框、textarea 或 contenteditable 元素
      const activeElement = document.activeElement;
      const isInputFocused =
        activeElement.tagName === 'INPUT' ||
        activeElement.tagName === 'TEXTAREA' ||
        activeElement.isContentEditable;
      if (isInputFocused) {
        return;
      }
      // 检查按下的键是否为可打印字符
      if (event.key.length === 1) {
        event.preventDefault(); // 防止其他默认行为
        searchInput.value.focus();
        searchInput.value.value = event.key;
      }
    };
    // ------------------------------------------
    // 所有分类数据
    const categories = ref([]);
    // 当前选中的父分类
    const selectedCategory = ref(null);
    // 当前选中分类ID
    const nowCategory = ref(null);

    // 顶级分类 (即 parentId 为 null 的分类)
    const topLevelCategories = computed(() => {
      return categories.value.filter(category => category.parentId === null);
    });

    // 选择分类时，将其id设为当前选中的分类id
    const selectCategory = (category) => {
      nowCategory.value = category;
      console.log('选中的分类ID:', nowCategory.value.id);
      // 如果没有子分类，保留选中状态
      if (category.children.length > 0) {
        selectedCategory.value = category; // 选择有子分类的分类
      } else {
        // 保持选中状态，不更改 selectedCategory
        selectedCategory.value = selectedCategory.value; // 保持当前分类
      }
      // console.log('选中的分类:', selectedCategory.value.id);
      if (nowCategory.value && selectedCategory.value) { // 检查两个值是否存在
        if( nowCategory.value.id != selectedCategory.value.id && nowCategory.value.parentId !== selectedCategory.value.id){
          selectedCategory.value = null;
        }
      }
      appListDTO.value.categoryId = nowCategory ? nowCategory.value.id : null;
      console.log('选中的分类id:', nowCategory.value.id, '更新app appListDTO.value.categoryId' , appListDTO.value.categoryId);
      getAppList();
    };

    // 从后端获取分类数据
    const fetchCategories = async () => {
      try {
        const res = await categoryApi.getCategoryByUsername(appListDTO.value.username, appListDTO.value.categoryId);
        console.log("从后端获取分类数据",res);
        categories.value = res.data;
      } catch (error) {
        console.error(error);
      }
    };

    // 组件挂载时加载分类数据
    onMounted(() => {
      fetchCategories();
    });
    // TODO 获取APP  区分登录未登录
    const getAppList = async () => {
      try {
        const res = await appApi.getAppByUsernameAndCategoryIdPublic(appListDTO.value.username, appListDTO.value.categoryId);
        console.log("获取APP",res);
        appList.value = res.data;
      } catch (error) {
        console.error(error);
      }
    };
    // 点击APP
    const clickApp = async (id) => {
      await appApi.clickApp(id);
    }
    const getAllApp = () => {
      appListDTO.value.categoryId = null
      selectCategory.value = null
      getAppList()
    }
    // 获取热门APP
    const getHotApp = async () => {
      const res = await appApi.getHotAppByUsername(appListDTO.value.username)
      console.log("获取热门APP",res)
      nowCategory.id = "hot"
      appList.value = res.data
    }

    getAppList();
    // ------------------------------------------------
    return {
      searchInput,
      handleSearch,
      topLevelCategories,
      selectedCategory,
      selectCategory,
      nowCategory,
      appList,
      getAppList,
      getAllApp,
      nowHotOrAll,
      clickApp,
      getHotApp
    };
  },
};
</script>

<style scoped>
.content-section-search-container {
  width: 100%;
  max-width: 1080px;
  margin: auto;
  margin-top: 66px;
  margin-left: 8px;
  position: fixed;
  top: 20px;                      /* 垂直居中 */
  left: 50%;                     /* 水平居中 */
  transform: translate(-50%, -50%); /* 精确居中 */
  /* margin: auto; */
  background-color: rgba(255, 255, 255, 0.5); /* 半透明背景 */
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);   /* 添加阴影 */
  border-radius: 8px;            /* 圆角边框 */
  display: flex;
  align-items: center;
  justify-content: space-between;
  z-index: 999;                 /* 确保搜索框位于其他内容之上 */
}
@media (max-width: 768px) {
  .content-section-search-container {
    width: 90%;
  }
}
.content-section-category-container {
  max-width: 1050px;
  margin: auto;
  margin-top: 120px;
}
.content-section-card-container {
  max-width: 1100px;
  margin: auto;
  /* margin-top: 120px; */
}
/* 自定义按钮样式 */
.custom-search-btn {
  /* 基础样式 */
  width: 40px;
  height: 40px;
  border-radius: 100%;
  color: #000; /* 文字颜色 */
  background-color: #fff; /* 按钮背景颜色 */
  border: 0px solid #de7622; /* 按钮边框颜色 */
  transition: background-color 0.3s ease, border-color 0.3s ease, color 0.3s ease; /* 平滑过渡效果 */
}

/* 悬停状态样式 */
.custom-search-btn:hover {
  background-color: #de7622; /* 悬浮后的背景颜色 */
  border-color: #de7622; /* 悬浮后的边框颜色 */
  color: #fff; /* 悬浮后的文字颜色 */
}

/* 聚焦状态样式（可选） */
.custom-search-btn:focus {
  box-shadow: 0 0 0 0.1rem rgba(222, 118, 34, 0.5); /* 添加聚焦时的阴影效果 */
}

/* 禁用状态样式（可选） */
.custom-search-btn:disabled {
  background-color: #6c757d;
  border-color: #6c757d;
  color: #fff;
  cursor: not-allowed;
  opacity: 0.65;
}
/* #80766e */
.capsule {
  font-size: 12px;
  min-width: 75px;
  height: 30px;
  text-align: center;
  justify-content: center;
  align-items: center;
  margin-right: 7px;
  margin-bottom: 7px;
  display: inline-block; /* 使 span 可以设置宽高 */
  padding: 0px 12px; /* 内边距，调整胶囊的大小 */
  line-height: 30px;
  border: 1px solid #b5aa90; /* 默认边框颜色 */
  border-radius: 999px; /* 使元素呈现胶囊形状 */
  background-color: white; /* 默认背景颜色 */
  color: #000; /* 默认文字颜色 */
  cursor: pointer; /* 鼠标悬停时显示为指针 */
  transition: background-color 0.3s ease, color 0.3s ease; /* 平滑过渡效果 */
}

.capsule:hover,
.capsule:active {
  background-color: #de7622; /* 悬浮和点击后的背景颜色 */
  border:1px solid  #de7622;
  color: white; /* 悬浮和点击后的文字颜色 */
}
.capsule-active{
  background-color: #de7622; /* 悬浮和点击后的背景颜色 */
  border:1px solid  #de7622;
  color: white; /* 悬浮和点击后的文字颜色 */
}

.noto-serif-sc-font {
  font-family: "Noto Serif SC", system-ui;
  font-optical-sizing: auto;
  font-weight: 500;
  font-style: normal;
}
/* APP 容器相关 */
.apps-container {
  max-width: 1080px;
  padding: 0;
  margin: auto;
}

.app-container {
  padding: 12px 12px 2px 12px;
}

/* APP 卡片样式 */
.app-card {
  display: flex;
  flex-direction: row;
  background-color: white;
  border-radius: 6px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  transition: box-shadow 0.3s ease-in-out, transform 0.3s ease;
  padding:10px 13px 10px 13px;
  border: 1px solid #f0f0f0;
}

.app-card:hover {
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.2);
  transform: translateY(-5px); /* 提升效果 */
}

.app-logo {
  flex: 1 1 20%;
  max-width: 20%;
  position: relative;
  width: 50px; /* 设置宽高确保是圆形 */
  height: 43px;
}

.app-logo img,
.default-logo {
  border-radius: 50%; /* 使图片和默认 Logo 都是圆形 */
  width: 100%;
  height: 100%;
  object-fit: cover; /* 确保图片内容适应圆形区域 */
}
.default-logo {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;
  border-radius: 50%;
  background-color: #de7622;
  color: white;
  font-weight: bold;
  font-size: 1.2rem;
}
.app-info {
  flex: 1 1 80%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding-left: 10px;
}

.app-title {
  font-weight: bold;
  font-size: 1.1rem;
  color: #000;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  line-height: 1.3; /* 行高可根据需求调整 */
  max-height: 1.3em; /* 2 行的最大高度 = 2 x 行高 */
}

.app-category {
  margin-top: 5px;
  font-size: 0.6rem;
  color: #666;
  width: fit-content;
  width: 100%;
}
.app-description {
    /* margin-top: 5px; */
    font-size: 0.9rem;
    color: #666;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;
    overflow: hidden;
    text-overflow: ellipsis;
    line-height: 1.3; /* 行高可根据需求调整 */
    max-height: 2.6em; /* 2 行的最大高度 = 2 x 行高 */
}
.content-section-search-container {
    width: 90%;
  }
@media (max-width: 768px) {
  .content-section-search-container {
    width: 90%;
  }
  .content-section-category-container {
    max-width: 90%;
    margin: auto;
    margin-top: 120px;
  }
}
/* 中等屏幕 sm 下的调整 */
@media (max-width: 991px) and (min-width: 576px) {
    .app-card {
        flex-direction: row;
    }
    .app-logo {
      max-width: 80%; /* 适当放大 Logo */
      margin-left: auto;
      margin-right: auto;
      height: 29px;
    }
    .app-info {
        justify-content: flex-start;
    }

    .app-description {
        display: none; /* 隐藏描述 */
    }

    .app-title {
        font-size: 1.2rem;
    }
}

/* 小屏幕下的调整 */
@media (max-width: 575px) {
    .app-card {
        flex-direction: column;
        text-align: center;
        border: 1px;
        box-shadow: 1 0px 0px rgba(0, 0, 0, 0.1);
    }

    .app-logo {
      margin-bottom: 10px;
      max-width: 40%; /* 适当放大 Logo */
      margin-left: auto;
      margin-right: auto;
    }

    .app-title {
        font-size: 1rem;
        margin-bottom: 5px;
    }

    .app-category {
        font-size: 0.9rem;
        display: none; /* 小屏幕下不展示分类名称 */
    }

    .app-description {
        display: none; /* 小屏幕下不展示描述 */
    }
    .app-info{
      padding: 0;
    }
}


</style>

