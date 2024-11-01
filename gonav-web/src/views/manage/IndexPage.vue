<template>
  <div v-html="setting.valueText">

  </div>
</template>
<script>
import settingApi from '@/api/setting';
import { ref, onMounted, computed } from 'vue';
export default {
  name: 'AdminIndex',
  components: {
  },
  setup() {
    // 获取数据
    const setting = ref({
      key: 'announce',
      valueVarchar: '',
      valueText: ''
    });
    const getSetting = async () => {
      const res = await settingApi.getSetting(setting.value.key);
      setting.value = res.data;
    }
    onMounted(() => {
      getSetting();
    })
    return {
      setting
    }
  }
}
</script>