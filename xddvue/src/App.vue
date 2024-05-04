<template>
  <el-header>
    <TopBar></TopBar>
  </el-header>
  <el-container>
    <el-aside>
      <router-view name="LeftSidebar" @get-data="getData"></router-view>
    </el-aside>
    <el-main>
      <router-view name="MainContent" :data="data_receive"></router-view>
    </el-main>
  </el-container>
</template>
<script setup>
import TopBar from "./components/common/TopBar.vue";
import { ref, onMounted } from 'vue'

const data_receive = ref('')
const getData = (data)=>{
  data_receive.value = data
}
</script>