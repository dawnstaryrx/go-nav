import {defineStore} from "pinia";
import {ref} from "vue";

export const useTokenStore = defineStore('token',
  () => {
    const token = ref({
      token: '',
      refreshToken: ''
    })
    const setToken = (newToken) => {
      token.value = newToken
    }
    const removeToken = () => {
      token.value = {
        token: '',
        refreshToken: ''
      }; 
    }
    return {
      token, setToken, removeToken
    }
  },
  {
    persist: true
  }
);