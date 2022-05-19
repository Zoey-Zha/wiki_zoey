<template>
  <a-layout-footer style="text-align: center">
    Wiki Design <span v-show="user.id">Welcome {{user.name}}</span> ©2022 Created by Zoey ZHA
  </a-layout-footer>
</template>

<script lang="ts">
import { defineComponent, computed } from 'vue';
import store from "@/store";

export default defineComponent({
  name: 'TheFooter',
  setup() {
    const user = computed(() => store.state.user);
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
</style>
