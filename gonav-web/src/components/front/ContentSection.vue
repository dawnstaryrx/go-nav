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
  <!-- 多级分类选择 -->
  <div class="content-section-category-container"  >
    <span class="capsule">
      全部工具
    </span>
    <span class="capsule">
      常用网站
    </span>
    <span class="capsule">
      AI工具
    </span>
    <span class="capsule">
      邮箱
    </span>
    <span class="capsule">
      全部工具
    </span>
    <span class="capsule">
      常用网站
    </span>
    <span class="capsule">
      AI工具
    </span>
    <span class="capsule">
      邮箱
    </span>
    <span class="capsule">
      全部工具
    </span>
    <span class="capsule">
      常用网站常用网站常用网站
    </span>
    <span class="capsule">
      AI工具
    </span>
    <span class="capsule">
      常用网站常用网站常用网站
    </span>
    <span class="capsule">
      AI工具
    </span>
  </div>
  <!-- 多级分类选择 -->
  <div class="content-section-category-container" style="margin-top: 10px;" >
    <span class="capsule">
      全部工具
    </span>
    <span class="capsule">
      常用网站
    </span>
    <span class="capsule">
      AI工具
    </span>
    <span class="capsule">
      邮箱
    </span>
    <span class="capsule">
      全部工具
    </span>
  </div>
  <div v-for="item in [1,2,3,4,5,8,9,1,4,5,6,7,8,1,2,3,4,5,8,9,1,4,5,6,7,8,1,2,3,4,5,8,9,1,4,5,6,7,8,1,2,3,4,5,8,9,1,4,5,6,7,8,1,2,3,4,5,8,9,1,4,5,6,7,8,1,2,3,4,5,8,9,1,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9]" class="content-section-card-container">
  333
  </div>

</template>

<script>
import { ref, watch, onMounted, onBeforeUnmount } from 'vue';

export default {
  name: 'SearchCenter',
  setup() {
    // 引用搜索输入框
    const searchInput = ref(null);

    // 初始化一级分类选项
    onMounted(() => {
      // 添加键盘事件监听
      window.addEventListener('keydown', handleKeydown);
    });
    onBeforeUnmount(() => {
      window.removeEventListener('keydown', handleKeydown);
    });

    // 处理表单提交
    const handleSearch = (event) => {
      event.preventDefault(); // 阻止表单默认提交行为

      const query = searchInput.value.value.trim();
      if (query) {
        console.log('搜索查询:', query);
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
    return {
      searchInput,
      handleSearch,
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
  max-width: 1080px;
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
  border: 1px solid ; /* 默认边框颜色 */
  border-radius: 999px; /* 使元素呈现胶囊形状 */
  background-color: white; /* 默认背景颜色 */
  color: #000; /* 默认文字颜色 */
  cursor: pointer; /* 鼠标悬停时显示为指针 */
  transition: background-color 0.3s ease, color 0.3s ease; /* 平滑过渡效果 */
}

.capsule:hover,
.capsule:active {
  background-color: #de7622; /* 悬浮和点击后的背景颜色 */
  color: white; /* 悬浮和点击后的文字颜色 */
}

.noto-serif-sc-font {
  font-family: "Noto Serif SC", system-ui;
  font-optical-sizing: auto;
  font-weight: 500;
  font-style: normal;
}
</style>

