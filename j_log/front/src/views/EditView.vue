<script setup lang = "ts">
import { ref } from 'vue'
import axios from 'axios'
import constant from '@/stores/constant'
import { useRouter } from 'vue-router';

const props = defineProps({
    postId: {
        type: [Number, String],
        require: true
    }
})

const router = useRouter();

const post = ref({
    id: props.postId,
    title: "",
    content: ""
});

const edit = function () {
    axios.patch(constant.J_LOG_POST_API + `/${props.postId}`, {
        title: post.value.title,
        content: post.value.content
    }).then(() => {
        router.replace({ name: "read", params: { postId: props.postId } })
    })
}

axios.get(constant.J_LOG_POST_API + `/${props.postId}`).then((response) => {
    post.value = response.data;
})

</script>

<template>
    <div>
        <el-input v-model="post.title" placeholder="제목을 입력해주세요" />
    </div>
    <div>
        <div>
            <el-input v-model="post.content" type="textarea" rows="15"></el-input>
        </div>

        <div>
            <el-button tpye="warning" @click="edit()">글 수정완료</el-button>
        </div>
    </div>
</template>

<style></style>