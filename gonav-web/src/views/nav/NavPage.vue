<template>
  <TopBar></TopBar>
  <div class="home-container">
    <ContentUserSection :username="username"></ContentUserSection>
  </div>
</template>
<script>
import HelloWorld from "@/components/HelloWorld.vue";
import TopBar from "@/components/front/TopBar.vue";
import ContentUserSection from "@/components/front/ContentUserSection.vue";
export default {
  components: {
    HelloWorld,
    TopBar,
    ContentUserSection
  },
  data() {
    return {
      userId: null, // 定义一个变量存储路由参数
    };
  },
  created() {
    // 在组件创建时获取路由参数id
    this.username = this.$route.params.username;
  }
}
</script>
<style scoped>
.home-container {
  margin: 0 auto;
  padding: 0;
  width: 100vw;
  margin-right: 0px;
}
</style>