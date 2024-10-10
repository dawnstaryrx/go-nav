<template>
  <TopBar style="opacity: 0.8; color: aliceblue;"></TopBar>
  <div class="login-container">
    <div class="card login-container-card noto-serif-sc-text">
      <div class="card-body">
        <div class="row login-container-card-type">
          <div style="text-align: center;font-size: 20px;font-weight: 900;">
            重置密码
          </div>
        </div>
        <form class="row g-3">
          <div class="col-md-12">
              <label for="validationDefaultUsername" required class="form-label">邮箱</label>
              <input type="text" class="form-control" placeholder="请输入邮箱" id="validationDefaultUsername" aria-describedby="inputGroupPrepend2" required>
          </div>
          <div class="col-md-12">
            <label for="validationDefault03" required class="form-label">验证码</label>
            <input type="password" class="form-control" placeholder="请输入验证码" id="validationDefault03" required>
          </div>
          <div class="col-md-12">
            <label for="validationDefault03" required class="form-label">新密码</label>
            <input type="password" class="form-control" placeholder="请输入新密码" id="validationDefault03" required>
          </div>
          <div class="col-md-12">
            <label for="validationDefault03" required class="form-label">确认密码</label>
            <input type="password" class="form-control" placeholder="请再次输入密码" id="validationDefault03" required>
          </div>
          <div class="col-12">
            <button class="btn btn-primary col-12" type="submit">重置</button>
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
import { ref } from "vue";
export default {
  components: {
    TopBar,
  },
  setup() {

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
