<template>
  <div class="markdown-container">
    <markdown-renderer :markdown-text="props.data"></markdown-renderer>
  </div>
</template>

<script setup>
import MarkdownRenderer from '../MarkdownRenderer.vue';
import { ref, computed } from 'vue'
const props = defineProps(['data'])

</script>
<style scoped>
.markdown-container {
  padding: 0 100px;
}
</style>