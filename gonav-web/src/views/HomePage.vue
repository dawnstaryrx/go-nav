<template>
  <TopBar></TopBar>
  <div class="home-container">
    <ContentSection></ContentSection>
    <!-- <HelloWorld></HelloWorld>  -->
  </div>
</template>
<script>
import HelloWorld from "@/components/HelloWorld.vue";
import TopBar from "@/components/front/TopBar.vue";
import ContentSection from "@/components/front/ContentSection.vue";
export default {
  components: {
    HelloWorld,
    TopBar,
    ContentSection
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