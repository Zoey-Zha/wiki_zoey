<template>
  <a-layout-footer class = "fixed-bottom" style="text-align: center">
<!--    footer{height:100px;margin-top:-100px;background-color: #ffc0cb;}-->
    Wiki Design <span v-show="user.id">Welcome {{user.name}}</span> ©2022 Created by Zoey ZHA
  </a-layout-footer>
</template>

<script lang="ts">
import { defineComponent, computed, onMounted } from 'vue';
import store from "@/store";
import {Tool} from "@/util/tool";
import { notification } from 'ant-design-vue';

export default defineComponent({
  name: 'TheFooter',
  setup() {
    const user = computed(() => store.state.user);

    let websocket: any;
    let token: any;
    const onOpen = () => {
      console.log('WebSocket连接成功，状态码：', websocket.readyState)
    };
    const onMessage = (event: any) => {
      console.log('WebSocket收到消息：', event.data);
      notification['info']({
        message: '收到消息',
        description: event.data,
      });
    };
    const onError = () => {
      console.log('WebSocket连接错误，状态码：', websocket.readyState)
    };
    const onClose = () => {
      console.log('WebSocket连接关闭，状态码：', websocket.readyState)
    };
    const initWebSocket = () => {
      // 连接成功
      websocket.onopen = onOpen;
      // 收到消息的回调
      websocket.onmessage = onMessage;
      // 连接错误
      websocket.onerror = onError;
      // 连接关闭的回调
      websocket.onclose = onClose;
    };

    onMounted(() => {
      // WebSocket
      if ('WebSocket' in window) {
        token = Tool.uuid(10);
        // 连接地址：ws://127.0.0.1:8880/ws/xxx
        websocket = new WebSocket('ws://process.env.VUE_APP_WS_SERVER/ws/' + token);
        initWebSocket()

        // 关闭
        // websocket.close();
      } else {
        alert('当前浏览器 不支持')
      }
    });

    return {
      user
    }
  }
});
</script>

<style>
#components-layout-demo-top-side-2 .logo {
  float: left;
  width: 120px;
  height: 31px;
  margin: 16px 24px 16px 0;
  background: rgba(255, 255, 255, 0.3);
}

.ant-row-rtl #components-layout-demo-top-side-2 .logo {
  float: right;
  margin: 16px 0 16px 24px;
}

.site-layout-background {
  background: #fff;
}
.fixed-bottom
{position: fixed;bottom: 0;width:100%;}
</style>
