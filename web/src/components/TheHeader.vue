<template>
  <a-layout-header class="header">
    <div class="logo" />
    <a-menu
        theme="dark"
        mode="horizontal"
        :style="{ lineHeight: '64px' }"
    >
      <a-menu-item key="home">
        <router-link to = "/">Home</router-link>
      </a-menu-item>
      <a-menu-item key="/admin/ebook">
        <router-link to = "/admin/ebook">Ebook Lists</router-link>
      </a-menu-item>
      <a-menu-item key="/admin/category">
        <router-link to = "/admin/category">Category</router-link>
      </a-menu-item>
      <a-menu-item key="/about">
        <router-link to = '/about'>About</router-link>
      </a-menu-item>
    </a-menu>
  </a-layout-header>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'TheHeader',
  props: {
    msg: String,
  },
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
