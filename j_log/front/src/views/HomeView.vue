<script setup lang="ts">
import axios from "axios";
import constant from "@/stores/constant";
import { ref } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

const posts: any = ref([]);

axios.get(constant.J_LOG_POST_API + "?page=1&size=10").then(response => {
  response.data.forEach((post: any) => {
    posts.value.push(post);
  });
})

const moveToRead = function () {
  router.push({ name: "read" });
};

</script>

<template>
  <ul>
    <li v-for="post in posts" :key="post.id">
      <div>
        <router-link :to="{ name: 'read', params: { postId: post.id } }">{{ post.title }}</router-link>
      </div>
    </li>
  </ul>
</template>

<style></style>
