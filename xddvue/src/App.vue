<template>
  <el-header>
    <TopBar></TopBar>
  </el-header>
  <el-container>
    <el-aside>
      <router-view name="LeftSidebar"></router-view>
    </el-aside>
    <el-main>
      <router-view name="MainContent"></router-view>
    </el-main>
  </el-container>
</template>
<script setup>
import TopBar from "./components/common/TopBar.vue";
</script>