<template>
  <el-upload
      class="upload-demo"
      drag
      :action="`${uploadUrl}/upload/markdown`"
      multiple
  >
    <el-icon class="el-icon--upload"><upload-filled /></el-icon>
    <div class="el-upload__text">
      拖拽或 <em>点击</em>上传
    </div>
    <template #tip>
      <div class="el-upload__tip">
        zip文件
      </div>
    </template>
  </el-upload>
</template>

<script setup >
import { UploadFilled } from '@element-plus/icons-vue'
import { ref } from 'vue'

const uploadUrl = ref(import.meta.env.VITE_BASE_URL);
</script>
