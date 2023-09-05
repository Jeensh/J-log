<script setup lang="ts">
import axios from "axios";
import constant from "@/stores/constant";
import { ref } from "vue";

const posts : any = ref([]);

axios.get(constant.J_LOG_POST_API + "?page=1&size=10").then(response => {
  response.data.forEach((post: any) => {
    posts.value.push(post);
  });
})

</script>

<template>
  <ul>
    <li v-for="post in posts" :key="post.id">
      <div>{{ post.title }}</div>
      <div>{{ post.content }}</div>
    </li>
  </ul>
</template>

<style></style>
