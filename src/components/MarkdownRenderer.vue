<template>
  <div v-html="renderedMarkdown"></div>
</template>

<script>
import MarkdownIt from 'markdown-it';
import hljs from "highlight.js";
import 'highlight.js/styles/default.css';

export default {
  props: {
    markdownText: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      renderedMarkdown: ''
    };
  },
  mounted() {
    this.renderMarkdown();
  },
  watch: {
    markdownText() {
      this.renderMarkdown();
    }
  },
  methods: {
    renderMarkdown() {
      const md = new MarkdownIt({
        highlight: function (str, lang) {
          if (lang && hljs.getLanguage(lang)) {
            try {
              return '<pre class="hljs"><code>' + hljs.highlight(lang, str, true).value + '</code></pre>';
            } catch (__) {}
          }
          return '<pre class="hljs"><code>' + md.utils.escapeHtml(str) + '</code></pre>';
        }
      });

      this.renderedMarkdown = md.render(this.markdownText);
    }
  }
};
</script>

<style>
/* 根据你的需求自定义样式 */
.hljs {
  display: block;
  overflow-x: auto;
  padding: 0.5em;
  background: #f0f0f0;
  color: #333;
}
</style>
