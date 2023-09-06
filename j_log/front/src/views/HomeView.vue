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
      <div class="title">
        <router-link :to="{ name: 'read', params: { postId: post.id } }">{{ post.title }}</router-link>
      </div>

      <div class="content">
        {{ post.content }}
      </div>

      <div class="sub d-flex">
        <div class="category">개발</div>
        <div class="regDate">2023-08-25</div>
      </div>
    </li>
  </ul>
</template>

<style scoped lang="scss">
ul {
  list-style: none;
  padding: 0;

  li {
    margin-bottom: 1.2rem;

    .title {
      a {
        font-size: 1.3rem;
        color: #8BDD42;
        text-decoration: none;
      }

      &:hover{
        text-decoration: underline;
        text-decoration-color: #C8EB6F;
      }
    }

    .content {
      font-size: 0.95rem;
      margin-top: 4px;
      color: #515351;
    }

    &:last-child {
      margin-bottom: 0;
    }

    .sub {
      margin-top: 0.4px;
      font-size: 0.6rem;

      .regDate{
        margin-left: 5px;
      }
    }
  }
}
</style>
