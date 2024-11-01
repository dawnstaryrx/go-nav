<template>
  <h2>通知公告（展示在首页）</h2>
  <div style="max-width: 1000px;">
    <!-- //  -->
    <QuillEditor contentType="html" v-model:content="announceDTO.valueText" :options="editorOption" theme="snow"  style="min-width: 500px;" />
    <button class="btn btn-primary" style="float: right; margin-top: 20px;" @click="update()"> 提交 </button>
  </div>
</template>
<script>
import { QuillEditor } from '@vueup/vue-quill'; // 引入 QuillEditor 组件
import Quill from 'quill';
import settingApi from '@/api/setting';
import { ref, onMounted, computed } from 'vue';
import alertUtil from "@/utils/alert";
import ImageResize from 'quill-image-resize'; //导入插件
// 注册
Quill.register('modules/imageResize', ImageResize);
export default {
  name: 'AnnouncePage',
  setup() {
    // 设置字体 
    let Font = Quill.import('attributors/style/font') //引入这个后会把样式写在style上
    let fonts = [false, 'SimSun', 'SimHei','Microsoft-YaHei','KaiTi','FangSong','Arial']
    Font.whitelist = fonts //将字体加入到白名单
    Quill.register(Font, true)
    // 富文本功能配置
    const  editorOption = {
      modules:{
        imageResize: {}, // 图像缩放
        toolbar:[
          ['bold', 'italic', 'underline', 'strike'],    //加粗，斜体，下划线，删除线
          ['blockquote', 'code-block'],     //引用，代码块
          [{ 'header': 1 }, { 'header': 2 }],        // 标题，键值对的形式；1、2表示字体大小
          [{ 'list': 'ordered'}, { 'list': 'bullet' }],     //列表
          [{ 'script': 'sub'}, { 'script': 'super' }],   // 上下标
          [{ 'indent': '-1'}, { 'indent': '+1' }],     // 缩进
          [{ 'direction': 'rtl' }],             // 文本方向
          [{ 'size': ['small', false, 'large', 'huge'] }], // 字体大小
          [{ 'header': [1, 2, 3, 4, 5, 6, false] }],     //几级标题
          [{ 'color': [] }, { 'background': [] }],     // 字体颜色，字体背景颜色
          [{ 'font': ['SimSun', 'SimHei','Microsoft-YaHei','KaiTi','FangSong','Arial']  }],     //字体
          [{ 'align': [] }],    //对齐方式
          ['clean'],    //清除字体样式
          ['image','video']    //上传图片、上传视频
        ],
      }
    }
    // 获取数据
    const announceDTO = ref({
      // id: null,
      key: 'announce',
      valueVarchar: '',
      valueText: ''
    })
    const getAnnounce = async () => {
      const res = await settingApi.getSetting('announce');
      announceDTO.value = res.data;
      console.log(announceDTO.value)
    }
    // 更新数据
    const update = async () => {
      const res = await settingApi.updateSetting(announceDTO.value);
      if (res.code === 0) {
        alertUtil.message('更新成功', 'success');
      } else {
        alertUtil.message('更新失败', 'danger');
      }
    }
    onMounted(() => {
      getAnnounce();
    })
    return {
      Quill,
      editorOption,
      announceDTO,
      update
    }
  }
}
</script>
<style scoped>
.quill-editor >>> .ql-container {
  min-height: 300px;
}
.quill-editor >>> .ql-picker.ql-font .ql-picker-label[data-value=SimSun]::before,
.quill-editor >>> .ql-picker.ql-font .ql-picker-item[data-value=SimSun]::before {
  content: "宋体";
  font-family: "SimSun"!important;
}
.quill-editor >>> .ql-picker.ql-font .ql-picker-label[data-value=SimHei]::before,
.quill-editor >>> .ql-picker.ql-font .ql-picker-item[data-value=SimHei]::before {
  content: "黑体";
  font-family: "SimHei";
}
.quill-editor >>> .ql-picker.ql-font .ql-picker-label[data-value=Microsoft-YaHei]::before,
.quill-editor >>> .ql-picker.ql-font .ql-picker-item[data-value=Microsoft-YaHei]::before {
  content: "微软雅黑";
  font-family: "Microsoft YaHei";
}
.quill-editor >>> .ql-picker.ql-font .ql-picker-label[data-value=KaiTi]::before,
.quill-editor >>> .ql-picker.ql-font .ql-picker-item[data-value=KaiTi]::before {
  content: "楷体";
  font-family: "KaiTi"!important;
}
.quill-editor >>> .ql-picker.ql-font .ql-picker-label[data-value=FangSong]::before,
.quill-editor >>> .ql-picker.ql-font .ql-picker-item[data-value=FangSong]::before {
  content: "仿宋";
  font-family: "FangSong";
}
</style>