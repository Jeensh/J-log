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
    <div class="title">
        <el-input v-model="post.title" />
    </div>

    <div class="mt-2">
        <div class="content">
            <el-input v-model="post.content" type="textarea" rows="15"></el-input>
        </div>
    </div>

    <div class="mt-2">
        <el-rowl>
            <el-col>
                <div class="d-flex justify-content-end">
                    <el-button tpye="warning" @click="edit()">수정완료</el-button>
                </div>
            </el-col>
        </el-rowl>
    </div>
</template>

<style scoped lang="scss">
</style>