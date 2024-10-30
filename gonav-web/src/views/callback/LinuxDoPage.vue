<!-- src/views/OAuthCallback.vue -->
<template>
  <div class="container">
    <h1>正在处理您的请求，请稍候。</h1>
  </div>
</template>

<script>
import { onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import userApi from '@/api/user' // 导入 userApi
import { useTokenStore } from '@/stores/token.js'
import { useUserInfoStore } from "@/stores/userInfo";

export default {
  name: 'OAuthCallback',
  setup() {
    const router = useRouter()
    const route = useRoute()
    const tokenStore = useTokenStore()

    const getNowUser = async () => {
      const res = await userApi.getCurrentUser();
      return res.data;
    };

    onMounted(async () => {
      const urlParams = new URLSearchParams(route.query)
      const code = urlParams.get('code')

      if (code) {
        try {
          // 发送POST请求到后端服务器
          const res = await userApi.loginLinuxDo(code)
          const tokenData = {
            token: '',
            refreshToken: ''
          }
          tokenData.token = res.data.token
          tokenData.refreshToken = res.data.refreshToken
          console.log(tokenData)
          if (tokenData.token != '') {
            // 存储token
            tokenStore.setToken(tokenData)

            // 如果需要存储用户信息
            const nowUser = await getNowUser()
            const userInfoStore = useUserInfoStore()
            userInfoStore.setInfo(nowUser)
            // 重定向到首页并携带欢迎消息
            router.push("/")
          } else {
            // 处理没有token的情况
            console.error('No token received')
            router.push({ name: 'login', query: { error: '认证失败，请重试。' } })
          }
        } catch (error) {
          console.error('Error during OAuth callback processing:', error)
          router.push({ name: 'login', query: { error: '认证过程中发生错误，请重试。' } })
        }
      } else {
        console.error('No code found in URL')
        router.push({ name: 'login', query: { error: '缺少授权码，请重试。' } })
      }
    })

    return {}
  },
}
</script>

<style scoped>
.container {
  font-family: Arial, sans-serif;
  /* background-color: #f4f4f4; */
  margin: 0;
  padding: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}
h1 {
  color: #333;
}
p {
  line-height: 1.6;
}
</style>
