import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';
import axios from 'axios';

axios.defaults.baseURL=process.env.VUE_APP_SERVER_URL


createApp(App).use(store).use(router).use(Antd).mount('#app');
console.log("env: " + process.env.NODE_ENV);
console.log("VUE_APP_SERVER_URL: " + process.env.VUE_APP_SERVER_URL);
