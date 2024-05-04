<template>
  <!-- 导航栏 -->
  <el-menu :default-active="activeIndex" mode="horizontal" :ellipsis="false" >
    <!-- 头像和名字 TODO: 这段应该是可以点击的,哪怕什么都不发生都应该把鼠标换成可点击的那个手的图标-->
      <div class="profile">
        <el-avatar :size="50" :src="avatarSrc"></el-avatar>
        <span class="name">{{ userName }}</span>
      </div>
    <div class="flex-grow" />
    <el-menu-item index="blog">网站开发日志</el-menu-item>
  </el-menu>
</template>

<script lang="ts" setup>
import { ref } from 'vue'

const activeIndex = ref('blog'); // 默认选中的导航菜单项
const userName = ref('小叮当的个人网站'); // 个人名称
const avatarSrc = ref('哆啦A梦-大笑.jpg'); // 头像地址

</script>

<style scoped>
.name {
  font-weight: bold;
  margin-left: 10px;
  font-size: 20px;
}
.profile {
  display: flex;
  align-items: center;
}
.flex-grow {
  flex-grow: 1;
}
</style>

