<template>
  <div class="m-content-display">
    <div class="m-router-view">
    <router-view />
    </div>
    <BottomIcp></BottomIcp>
</div>

</template>
<script>
import BottomIcp from "@/components/front/BottomIcp.vue";
export default {
  components: {
    BottomIcp
  }
}
</script>
<style scoped>
.m-content-display {
  display: flex;
  flex-direction: column;
  min-height: 100%;
}
.m-router-view {
  flex: 1;
}

</style>
