<template>
  <TopBar style="opacity: 0.8; color: aliceblue;"></TopBar>
  <div class="login-container">
    <div class="card login-container-card noto-serif-sc-text">
      <div class="card-body">
        <!-- <div style="text-align: center;">
          登录
        </div> -->
        <div class="row login-container-card-type">
          <div @click="changeLoginType(0)" :class="loginType === 0 ? 'col login-type-active' : 'col login-type'">
            密码登录
          </div>
          <div @click="changeLoginType(1)" :class="loginType === 1 ? 'col login-type-active' : 'col login-type'">
            免密登录
          </div>
        </div>

        <form v-if="loginType === 0" class="row g-3">
          <div class="col-md-12">
              <label for="validationDefaultUsername" required class="form-label">用户名/邮箱</label>
              <input type="text" class="form-control" placeholder="请输入用户名或邮箱" id="validationDefaultUsername" aria-describedby="inputGroupPrepend2" required>
          </div>
          <div class="col-md-12">
            <label for="validationDefault03" required class="form-label">密码</label>
            <input type="password" class="form-control" placeholder="请输入密码" id="validationDefault03" required>
          </div>
          <div class="col-12">
            <div class="form-check">
              <input class="form-check-input" type="checkbox" value="" id="invalidCheck2" required>
              <label class="form-check-label" for="invalidCheck2">
                记住我
              </label>
            </div>
          </div>
          <div class="col-12">
            <button class="btn btn-primary col-12" type="submit">登录</button>
          </div>
        </form>

        <form v-else class="row g-3">
          <div class="col-md-12">
              <label for="validationDefaultUsername" required class="form-label">邮箱</label>
              <input type="text" class="form-control" placeholder="请输入邮箱" id="validationDefaultUsername" aria-describedby="inputGroupPrepend2" required>
          </div>
          <div class="col-md-12">
            <label for="validationDefault03" required class="form-label">验证码</label>
            <input type="password" class="form-control" placeholder="请输入验证码" id="validationDefault03" required>
          </div>
          <div class="col-12">
            <div class="form-check">
              <input class="form-check-input" type="checkbox" value="" id="invalidCheck2" required>
              <label class="form-check-label" for="invalidCheck2">
                记住我
              </label>
            </div>
          </div>
          <div class="col-12">
            <button class="btn btn-primary col-12" type="submit">登录</button>
          </div>
        </form>


        <div class="line text-center">
          第三方登录
        </div>
        <div>
          <div class="col text-center">
            <img src="@/assets/linuxdo.png" alt="" height="30px">LINUX DO登录
          </div>
          <div class="col text-center">
            <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" viewBox="0 0 32 32" fill="none">
            <path fill-rule="evenodd" clip-rule="evenodd" d="M16 0C7.16 0 0 7.16 0 16C0 23.08 4.58 29.06 10.94 31.18C11.74 31.32 12.04 30.84 12.04 30.42C12.04 30.04 12.02 28.78 12.02 27.44C8 28.18 6.96 26.46 6.64 25.56C6.46 25.1 5.68 23.68 5 23.3C4.44 23 3.64 22.26 4.98 22.24C6.24 22.22 7.14 23.4 7.44 23.88C8.88 26.3 11.18 25.62 12.1 25.2C12.24 24.16 12.66 23.46 13.12 23.06C9.56 22.66 5.84 21.28 5.84 15.16C5.84 13.42 6.46 11.98 7.48 10.86C7.32 10.46 6.76 8.82 7.64 6.62C7.64 6.62 8.98 6.2 12.04 8.26C13.32 7.9 14.68 7.72 16.04 7.72C17.4 7.72 18.76 7.9 20.04 8.26C23.1 6.18 24.44 6.62 24.44 6.62C25.32 8.82 24.76 10.46 24.6 10.86C25.62 11.98 26.24 13.4 26.24 15.16C26.24 21.3 22.5 22.66 18.94 23.06C19.52 23.56 20.02 24.52 20.02 26.02C20.02 28.16 20 29.88 20 30.42C20 30.84 20.3 31.34 21.1 31.18C27.42 29.06 32 23.06 32 16C32 7.16 24.84 0 16 0V0Z" fill="#24292E"/>
            </svg>GitHub登录
          </div>
        </div>
        
        <div class="row">
          <span class="col text-start">
            <router-link to="/register" style="text-decoration: none;">
              没有账号，去注册！
            </router-link>
          </span>
          <span class="col text-end">
            <router-link to="/reset-pwd" style="text-decoration: none;">
              忘记密码？
            </router-link>
          </span>
        </div>
        
      </div>
    </div>
  </div>
</template>

<script>
import TopBar from "@/components/front/TopBar.vue";
import { ref } from "vue";
export default {
  components: {
    TopBar,
  },
  setup() {
    const loginType = ref(0);
    const changeLoginType = (val) => {
      loginType.value = val;
    };
    return {
      loginType,
      changeLoginType
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
</style>
