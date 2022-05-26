<template>
  <a-layout>
<!--    Msg 有什么作用呢-->
<!--    <TheHeader msg="Welcome to Your Vue.js + TypeScript App"></TheHeader>-->
    <TheHeader></TheHeader>
    <router-view/>
    <TheFooter></TheFooter>
  </a-layout>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import TheHeader from '@/components/TheHeader.vue'; // @ is an alias to /src
import TheFooter from '@/components/TheFooter.vue'; // @ is an alias to /src

export default defineComponent({
  name: 'App',
  components: {
    TheHeader,
    TheFooter
  },
});
</script>