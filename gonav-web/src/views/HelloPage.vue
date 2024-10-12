<template>
    <TopBar></TopBar>
    <HelloWorld></HelloWorld>
    <!-- 页面主体内容 -->
    <div style="margin-top: 100px;">
      public: {{ hello.publicHello?.data }}
    </div>
    <div>
      user: {{ hello.userHello?.data }}
    </div>
    <div>
      admin: {{ hello.adminHello?.data }}
    </div>
    <div>
      default: {{ hello.defaultHello?.data }}
    </div>
    <div>
      {{ msg }}
    </div>
</template>
<script>
import HelloWorld from "@/components/HelloWorld.vue";
import TopBar from "@/components/front/TopBar.vue";
import BottomIcp from "@/components/front/BottomIcp.vue";
import { ref, onMounted } from "vue";
import testApi from "@/api/test";
export default {
  components: {
    HelloWorld,
    TopBar,
    BottomIcp
  },
  setup() {
    const msg = ref("Hello World");
    const hello = ref({
      defaultHello: null,
      publicHello: null,
      userHello: null,
      adminHello: null
    });

    const fetchHelloData = async () => {
      try {
        // 获取各个接口的数据
        hello.value.defaultHello = await testApi.default();
        console.log(hello.value.defaultHello);
        hello.value.publicHello = await testApi.public();
        hello.value.userHello = await testApi.user();
        hello.value.adminHello = await testApi.admin();
      } catch (error) {
        console.error("Error fetching hello data:", error);
      }
    };

    // 在组件挂载后调用 fetchHelloData
    onMounted(fetchHelloData);

    return {
      msg,
      hello
    };
  }
}
</script>
<style scoped>

</style>