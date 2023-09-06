<script setup lang="ts">
import Header from "./components/Header.vue"
import Content from './components/Content.vue';
</script>

<template>
  <div class="container">
    <Header></Header>
    <Content></Content>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  flex-direction: column; /* 컴포넌트를 세로로 배치합니다. */
  height: 100vh; /* 화면 전체 높이로 설정합니다. */
}

body {
  margin: 0;
  width: 100%;
  font-family: "Pretendard Variable", Pretendard, -apple-system,
    BlinkMacSystemFont, system-ui, Roboto, "Helvetica Neue",
    "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR",
    "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji",
    "Segoe UI Symbol", sans-serif;
}
</style>