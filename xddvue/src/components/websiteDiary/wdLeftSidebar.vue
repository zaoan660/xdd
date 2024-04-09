<template>
  <el-menu
      default-active="1-1"
      class="custom-menu"
  >
    <el-sub-menu index="1">
      <template #title>
        <el-icon><svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024" data-v-ea893728=""><path fill="currentColor" d="M128 192v640h768V320H485.76L357.504 192zm-32-64h287.872l128.384 128H928a32 32 0 0 1 32 32v576a32 32 0 0 1-32 32H96a32 32 0 0 1-32-32V160a32 32 0 0 1 32-32"></path></svg></el-icon>
        <el-text>2024年4月</el-text>
      </template>
      <el-menu-item index="1-1" class="custom-menu-item">
        <el-icon><Document /></el-icon>
          <el-text class="abstract-sidebar" truncated>我看vue里提到了SSR,即服务器端渲染,有着更好的SEO和更快的首屏加载速度,但步骤繁琐而且有一些限制.</el-text>
      </el-menu-item>
    </el-sub-menu>
  </el-menu>
</template>

<script lang="ts" setup>
import {
  Document,
  Menu as IconMenu,
  Location,
  Setting,
} from '@element-plus/icons-vue'
const handleOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
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