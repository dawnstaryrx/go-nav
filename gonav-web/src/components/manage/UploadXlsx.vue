<template>
  <div class="row">
    <div class="col">
      <input
        type="file"
        @change="handleFileChange"
        accept=".xlsx"
        class="form-control"
        :disabled="loading"
      />
      <small class="form-text text-muted">上传xlsx文件</small>
    </div>
    <div class="col-auto">
      <button 
        class="btn btn-success" 
        @click="uploadFile"
        :disabled="!selectedFile || loading"
      >
        上传数据
      </button>
    </div>
    <div class="col-auto">
      <button 
        class="btn btn-primary" 
        @click="downloadFile"
        :disabled="loading"
      >
        下载模板
      </button>
    </div>
    <div class="col-auto">
      <button 
        class="btn btn-outline-secondary" 
        @click="downloadMyData"
        :disabled="loading"
      >
        下载本人数据
      </button>
    </div>

    <!-- 加载时展示转圈 -->
    <div v-if="loading" class="overlay">
      <div class="spinner-border" role="status">
        <span class="visually-hidden">加载中...</span>
      </div>
    </div>
  </div>
</template>

<script>
import alertUtil from '@/utils/alert.js';
import request from '@/utils/request.js';
import { useTokenStore } from "@/stores/token.js";
import appApi from '@/api/app';

const tokenStore = useTokenStore();

export default {
  data() {
    return {
      selectedFile: null,  // 用于存储选择的文件
      loading: false       // 用于控制加载状态
    };
  },
  methods: {
    handleFileChange(event) {
      const file = event.target.files[0];
      if (this.beforeUpload(file)) {
        this.selectedFile = file;
      } else {
        this.selectedFile = null;  // 清空选择的文件
      }
    },
    beforeUpload(file) {
      const isXlsx = file.type === 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet';
      if (!isXlsx) {
        alertUtil.message('请上传正确的 Excel (.xlsx) 文件', 'danger');
        return false;
      }
      return true;
    },
    uploadFile() {
      if (!this.selectedFile) return;

      this.loading = true;  // 开始加载
      const formData = new FormData();
      formData.append('file', this.selectedFile);

      request.put('/user/apps/upload/xlsx', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
          'Authorization': tokenStore.token.token
        }
      })
      .then(response => {
        this.handleSuccess(response.data);
      })
      .catch(error => {
        this.handleError(error);
      })
      .finally(() => {
        this.loading = false;  // 结束加载
      });
    },
    handleSuccess(response) {
      alertUtil.message('数据上传成功，将自动刷新页面！');
      setTimeout(() => {
        location.reload();
      }, 1000); // 1000 毫秒 = 1 秒
    },
    handleError(error) {
      alertUtil.message('数据上传失败' + error, 'danger');
    },
    downloadFile() {
      const fileUrl = '/GoNavModel.xlsx';
      const a = document.createElement('a');
      a.href = fileUrl;
      a.download = 'GoNavModel.xlsx';
      a.click();
    },
    async downloadMyData() {
      // 下载数据
      try {
        this.loading = true;  // 开启加载状态

        // 发起下载请求
        const res = await appApi.downLoadApps({ responseType: 'blob' });
        console.log('下载文件响应：', res);
        const contentType = res.headers['content-type'];

        // 检查返回的数据类型
        if (!contentType || contentType !== 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet') {
          alertUtil.showAlert('服务器返回的不是有效的 Excel 文件。');
          return;
        }
        // 下载文件
        this.downloadMyFile(res.data);

      } catch (error) {
        console.error('文件下载出错：', error);
        alertUtil.showAlert('下载文件时发生错误，请稍后重试。');
      } finally {
        this.loading = false;  // 关闭加载状态
      }
    },

    // 下载文件函数
    downloadMyFile(data) {
      const blob = data instanceof Blob ? data : new Blob([data], { type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" });
      const link = document.createElement('a');
      link.href = URL.createObjectURL(blob);
      link.download = 'apps.xlsx';  // 设置下载文件名
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    }
  }
};
</script>

<style scoped>
.form-control {
  margin-right: 10px;
}

.overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(255, 255, 255, 0.8);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;  /* 确保在最上面 */
}
</style>
