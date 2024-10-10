<template>
  <TopBar style="opacity: 0.8; color: aliceblue;"></TopBar>
  <div class="login-container">
    <div class="card login-container-card noto-serif-sc-text">
      <div class="card-body">
        <div class="row login-container-card-type">
          <div style="text-align: center;font-size: 20px;font-weight: 900; color: #de7622;">
            重置密码
          </div>
        </div>
        <form @submit.prevent="handleResetPwd" class="row g-3">
          <div class="col-md-12">
            <label for="validationEmail" class="form-label">邮箱</label>
            <input 
              v-model="resetPwdData.email" 
              type="email" 
              class="form-control" 
              placeholder="请输入邮箱" 
              id="validationEmail" 
              required
            >
          </div>
          <label for="validationCode" class="form-label">验证码</label>
          <div class="input-group" style="margin: 0px;">
            <input 
              v-model="resetPwdData.code" 
              type="text" 
              class="form-control" 
              placeholder="请输入验证码" 
              id="validationCode" 
              required
            >
            <span 
              :class="['input-group-text', { disabled: isSending }]" 
              @click="sendCode" 
              style="cursor: pointer;"
            >
              {{ sendButtonText }}
            </span>
          </div>
          <div class="col-md-12">
            <label for="validationPwd" class="form-label">密码</label>
            <input 
              v-model="resetPwdData.password" 
              type="password" 
              class="form-control" 
              placeholder="请输入密码" 
              id="validationPwd" 
              required
            >
          </div>
          <div class="col-md-12">
            <label for="validationRePwd" class="form-label">确认密码</label>
            <input 
              v-model="resetPwdData.rePassword" 
              type="password" 
              class="form-control" 
              placeholder="请再次输入密码" 
              id="validationRePwd" 
              required
            >
          </div>
          <div class="col-12">
            <button class="btn btn-primary col-12" type="submit">提交</button>
          </div>
        </form>
        
        <div class="row">
          <span class="col text-start">
            <router-link to="/login" style="text-decoration: none;">
              去登录！
            </router-link>
          </span>
        </div>
        
      </div>
    </div>
  </div>
</template>

<script>
import TopBar from "@/components/front/TopBar.vue";
import alertUtil from "@/utils/alert";
import userApi from "@/api/user";
import { ref, computed } from "vue";

export default {
  components: {
    TopBar,
  },
  setup() {
    const resetPwdData = ref({
      email: '',
      code: '',
      password: '',
      rePassword: ''
    });

    const isSending = ref(false);
    const countdown = ref(60);
    const timer = ref(null);

    const sendButtonText = computed(() => {
      return isSending.value ? `${countdown.value}s 后重试` : '发送';
    });

    const validateEmail = (email) => {
      const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      return re.test(email);
    };

    const sendCode = async () => {
      if (isSending.value) return;
      if (!validateEmail(resetPwdData.value.email)) {
        alertUtil.message('请输入有效的邮箱地址', 'danger');
        return;
      }

      isSending.value = true;

      try {
        // const res = await sendCodeService(registerData.value.email, 1);
        const res = await userApi.sendCode(resetPwdData.value.email, 3);
        console.log(res);
        alertUtil.message('验证码已发送', 'success');
        // 开始倒计时
        timer.value = setInterval(() => {
          if (countdown.value > 0) {
            countdown.value--;
          } else {
            clearInterval(timer.value);
            isSending.value = false;
            countdown.value = 60;
          }
        }, 1000);
      } catch (error) {
        alertUtil.message('发送验证码失败，请稍后再试', 'danger');
        isSending.value = false;
      }
    };

    const handleResetPwd = async () => {
      // 处理注册逻辑
      if (!validateEmail(resetPwdData.value.email)) {
        alertUtil.message('请输入有效的邮箱地址', 'danger');
        return;
      }

      if (resetPwdData.value.password !== resetPwdData.value.rePassword) {
        alertUtil.message('两次密码输入不一致', 'danger');
        return;
      }

      // 发送注册请求
      const res = await userApi.resetPwd(resetPwdData.value);
      if(res.code === 0){
        alertUtil.message('重置密码成功！');
      }
    };

    return {
      resetPwdData,
      sendCode,
      isSending,
      sendButtonText,
      handleResetPwd
    };
  },
};
</script>

<style scoped>
.login-type{
  cursor: pointer;
  text-align: center;
  font-size: 20px;
  font-weight: 900;
}
.login-type-active{
  cursor: pointer;
  text-align: center;
  font-size: 20px;
  font-weight: 900;
  color: #de7622;
}
.login-container {
  background-image: url("@/assets/hk-bg.png");
  background-size: cover; /* 让背景图片铺满容器 */
  background-position: center; /* 背景图片居中 */
  background-repeat: no-repeat; /* 防止背景图片重复 */
  min-height: 100vh; /* 设置容器高度为视口高度，确保图片铺满整个屏幕 */
  /* 使用 Flexbox 来居中内容 */
  display: flex; 
  /* 垂直居中 */
  align-items: center; 
  /* 水平居中 */
  justify-content: center; 
}
.login-container-card{
  opacity: 0.8;
}
.noto-serif-sc-text {
  font-family: "Noto Serif SC", serif;
  font-optical-sizing: auto;
  font-weight: 700;
  font-style: normal;
}
/* 自定义媒体查询以调整卡片宽度 */
@media (min-width: 576px) { /* 小型设备（平板手机） */
  .login-container-card {
    max-width: 450px;
  }
}

@media (min-width: 768px) { /* 中型设备（平板） */
  .login-container-card {
    max-width: 460px;
  }
}

@media (min-width: 992px) { /* 大型设备（桌面） */
  .login-container-card {
    max-width: 480px;
  }
}

@media (min-width: 1200px) { /* 超大型设备（大型桌面） */
  .login-container-card {
    max-width: 500px;
  }
}

/* 按钮全宽 */
.btn-primary.w-100 {
  padding: 10px;
  font-size: 18px;
}

/* 输入框样式调整（可选） */
.form-control {
  border-radius: 5px;
}
.line {
  display: table;
  width: 100%;
  white-space: nowrap;
  border-spacing: .2rem 0;
  color: #ccc;
}

.line:before,
.line:after {
  display: table-cell;
  content: '';
  width: 50%;
  background: -webkit-linear-gradient(#eee, #eee) repeat-x left center;
  background: linear-gradient(#eee, #eee) repeat-x left center;
  background-size: 0.1rem 0.1rem;
}

/* 禁用状态样式 */
.input-group-text.disabled {
  background-color: #ccc;
  cursor: not-allowed;
}
</style>
