<template>
  <div>
    <markdown-renderer :markdown-text="markdownString"></markdown-renderer>
  </div>
</template>

<script>
import MarkdownRenderer from '../MarkdownRenderer.vue';

export default {
  components: {
    MarkdownRenderer
  },
  data() {
    return {
      markdownString: `# 示例Markdown文本

这是一段 **加粗** 的文字，并且有 *斜体*。

\`\`\`javascript
function greet() {
  console.log('Hello, world!');
}
greet();
\`\`\`
`
    };
  }
};
</script>