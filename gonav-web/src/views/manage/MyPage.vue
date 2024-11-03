<template>
  <h3 style="margin-top: 10px;">修改个人信息</h3>
  <div class="mb-3">
    <label for="username" class="form-label">用户名</label>
    <input v-model="nowUser.username" type="text" class="form-control" id="username" placeholder="请输入用户名...">
  </div>
  <div class="mb-3">
    <label for="nickname" class="form-label">昵称</label>
    <input v-model="nowUser.nickname" type="text" class="form-control" id="nickname" placeholder="请输入昵称...">
  </div>
  <div class="mb-3">
    <label for="email" class="form-label">邮箱</label>
    <input v-model="nowUser.email" type="text" class="form-control" id="email" placeholder="请输入邮箱..." readonly>
  </div>
  <button class="btn btn-primary" @click="updateUser()"> 修改 </button>
  <h3 style="margin-top: 50px;">修改密码</h3>
  请前往<a href="/reset-pwd" target="_blank">修改密码</a>
  <h3 style="margin-top: 50px;">绑定GitHub</h3>
  待开发
  <h3 style="margin-top: 50px;">绑定LinuxDo</h3>
  待开发
</template>
<script>
import { onMounted, ref } from 'vue'
import userApi from '@/api/user'
import alertUtil from "@/utils/alert";
export default {
  name: 'SettingPage',
  
  setup() {
    const nowUser = ref({
      id: '',
      username: '',
      email: '',
      phone: '',
      nickname: '',
      linuxdoOpenid: '',
      githubOpenid: '',
      createTime: '',
    })
    const getNowUser = async () => {
      const res = await userApi.getCurrentUser();
      nowUser.value.id = res.data.id
      nowUser.value.username = res.data.username
      nowUser.value.email = res.data.email
      nowUser.value.phone = res.data.phone
      nowUser.value.nickname = res.data.nickname
      nowUser.value.linuxdoOpenid = res.data.linuxdoOpenid
      nowUser.value.githubOpenid = res.data.githubOpenid
      nowUser.value.createTime = res.data.createTime
    };
    const updateUser = async () => {
      const res = await userApi.updateUser(nowUser.value)
      console.log(res)
      if(res.code == 0){
        alertUtil.message('修改成功，部分配置在重新登录后生效。', 'success')
      }
      // getNowUser()
    };
    onMounted(() => {
      getNowUser()
    })
    return {
      nowUser,
      getNowUser,
      updateUser
    }
  }
}
</script>