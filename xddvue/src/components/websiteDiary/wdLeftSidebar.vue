<template>
  <el-menu
      :default-active="firstEleId"
      class="custom-menu"
  >
    <el-sub-menu index="1">
      <template #title>
        <el-icon><svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024" data-v-ea893728=""><path fill="currentColor" d="M128 192v640h768V320H485.76L357.504 192zm-32-64h287.872l128.384 128H928a32 32 0 0 1 32 32v576a32 32 0 0 1-32 32H96a32 32 0 0 1-32-32V160a32 32 0 0 1 32-32"></path></svg></el-icon>
        <el-text>2024年4月</el-text>
      </template>
      <el-menu-item v-for="item in blogData['data']" :index="item.id" @click="getToDetails(item.id)">
        <el-icon><Document /></el-icon>
          <el-text class="abstract-sidebar" truncated>{{item.title}}</el-text>
      </el-menu-item>
    </el-sub-menu>
  </el-menu>
</template>

<script setup>
import { Document } from '@element-plus/icons-vue'
import { ref, onMounted } from 'vue'
import axios from 'axios'

const baseUrl = ref(import.meta.env.VITE_BASE_URL);
// 定义用于存储博客数据的 ref
const blogData = ref('')
const emit = defineEmits(['getData'])
const firstEleId = ref('')

// 在页面加载时发送 HTTP 请求获取博客数据
onMounted(async () => {
    try {
      const response = await axios.get(`${baseUrl.value}/website/blog`)
      blogData.value = response.data
      firstEleId.value = response.data['data'][0]['id']
      const response1 = await axios.get(`${baseUrl.value}/website/blog/detail?id=${firstEleId.value}`);
      emit('getData',response1.data.data);
    } catch (error) {
      console.error('Failed to fetch blog data:', error)
    }
})
const getToDetails = async (id) => {
  const response = await axios.get(`${baseUrl.value}/website/blog/detail?id=${id}`);
  emit('getData',response.data.data);
}
</script>
<style>
/*.el-menu {*/
/*  --el-menu-item-height: 100px;*/
/*}*/
/*.el-sub-menu .el-menu-item{*/
/*  height: 100px;*/
/*}*/
</style>