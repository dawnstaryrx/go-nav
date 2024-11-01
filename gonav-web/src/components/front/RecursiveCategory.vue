<template>
  <div class="content-section-category-container" style="margin-top: 10px;">
    <!-- 当前分类的直接子分类 -->
    <span
      v-for="subCategory in category.children"
      :key="subCategory.id"
      class="capsule"
      @click="selectCategory(subCategory)"
      :class="{ 'capsule-active': nowCategory && nowCategory.id === subCategory.id }"
      :title="subCategory.description"
    >
      {{ subCategory.name }}
    </span>
  </div>
</template>

<script>
export default {
  name: 'RecursiveCategory',
  props: {
    category: {
      type: Object,
      required: true
    },
    nowCategory: {  // 添加 nowCategory prop
      type: Object,
      required: false
    }
  },
  methods: {
    selectCategory(category) {
      // 只传递当前点击的分类到父组件
      this.$emit('select-category', category);
      console.log('Selected category:', category);
    }
  }
};
</script>

<style scoped>
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
.content-section-category-container {
  max-width: 1080px;
  margin: auto;
}
</style>
