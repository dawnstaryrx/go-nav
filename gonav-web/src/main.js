createApp(App).mount('#app')
import { createApp } from 'vue';
import App from './App.vue';
import router from './router/index';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap';
import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
// vue-i18n 需要命名导入
// import { createI18n } from 'vue-i18n'
// import i18n from './language/i18n'

const pinia = createPinia()
const app = createApp(App);
pinia.use(piniaPluginPersistedstate)
app.use(router);
app.use(pinia);
// app.use(i18n);
app.mount('#app');