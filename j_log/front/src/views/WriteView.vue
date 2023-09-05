<script setup lang = "ts">
import { ref } from 'vue'
import axios from 'axios'
import constant from '@/stores/constant'
import { useRouter } from 'vue-router';

const title = ref("");
const content = ref("");

const router = useRouter();

const write = function() {
    axios.post(constant.J_LOG_POST_API, {
        title: title.value,
        content: content.value
    })
    .then(() => {
        router.replace({name: "home"})
    })
}
</script>

<template>
    <div>
        <el-input v-model="title" placeholder="제목을 입력해주세요" />
    </div>
    <div>
        <div>
            <el-input v-model="content" type="textarea" rows ="15"></el-input>
        </div>

        <div>
            <el-button tpye="primary" @click="write()">글 작성완료</el-button>
        </div>
    </div>
</template>

<style>
</style>