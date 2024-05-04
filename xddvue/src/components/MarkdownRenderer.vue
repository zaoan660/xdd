<template>
  <div v-html="renderedMarkdown" class="markdown"></div>
</template>

<script setup>
import MarkdownIt from 'markdown-it';
// import 'github-markdown-css'
import hljs from "highlight.js";
import 'highlight.js/styles/github.css';
import { onMounted,computed,onUpdated,onBeforeUpdate } from 'vue'
import Clipboard from 'clipboard'

const props = defineProps(['markdownText'])
const md = new MarkdownIt({
  html: true,
  linkify: true,
  typographer: true,
  highlight: function (str, lang) {
    const codeIndex = parseInt(Date.now()) + Math.floor(Math.random() * 10000000)
    // 复制功能主要使用的是 clipboard.js
    let html = ""
    let svg_ = `<svg aria-hidden="true" height="16" viewBox="0 0 16 16" version="1.1" width="16" data-view-component="true" class="svg-icon octicon-copy">
    <path d="M0 6.75C0 5.784.784 5 1.75 5h1.5a.75.75 0 0 1 0 1.5h-1.5a.25.25 0 0 0-.25.25v7.5c0 .138.112.25.25.25h7.5a.25.25 0 0 0 .25-.25v-1.5a.75.75 0 0 1 1.5 0v1.5A1.75 1.75 0 0 1 9.25 16h-7.5A1.75 1.75 0 0 1 0 14.25Z"></path><path d="M5 1.75C5 .784 5.784 0 6.75 0h7.5C15.216 0 16 .784 16 1.75v7.5A1.75 1.75 0 0 1 14.25 11h-7.5A1.75 1.75 0 0 1 5 9.25Zm1.75-.25a.25.25 0 0 0-.25.25v7.5c0 .138.112.25.25.25h7.5a.25.25 0 0 0 .25-.25v-7.5a.25.25 0 0 0-.25-.25Z"></path>
</svg><svg aria-hidden="true" height="16" viewBox="0 0 16 16" version="1.1" width="16" data-view-component="true" class="svg-icon octicon-check d-none">
    <path d="M13.78 4.22a.75.75 0 0 1 0 1.06l-7.25 7.25a.75.75 0 0 1-1.06 0L2.22 9.28a.751.751 0 0 1 .018-1.042.751.751 0 0 1 1.042-.018L6 10.94l6.72-6.72a.75.75 0 0 1 1.06 0Z"></path>
</svg>`
    let clipboardButton = `<div class="copy-btn" type="button" data-clipboard-action="copy" data-clipboard-target="#copy${codeIndex}">${svg_}</div>`
    if (lang && hljs.getLanguage(lang)) {
      try {
        const preCode = hljs.highlight(lang, str, true).value
        html = html + preCode
        return `<pre class="hljs"><code id="copy${codeIndex}">${html}</code><div class="copy-container">${clipboardButton}</div></pre>`
      } catch (error) {
        console.log(error)
      }
    }
    const preCode = md.utils.escapeHtml(str)
    html = html + preCode
    return `<pre class="hljs"><code class="hljs-code" id="copy${codeIndex}">${html}</code>${clipboardButton}</pre>`
  }
})
const renderedMarkdown = computed(()=>md.render(props.markdownText))
onMounted(()=>{
  const clipboard = new Clipboard('.copy-btn')
  clipboard.on('success', (e) => {
    e.clearSelection();
    console.log("复制成功")
  })
  clipboard.on('error', (e) => {
    e.clearSelection();
    console.error('复制成功失败')
  })
})
function toggleSvg(event) {
  const element = event.currentTarget;
  var octicon_copy = element.querySelector(".octicon-copy")
  octicon_copy.classList.add('d-none');
  var octicon_check = element.querySelector(".octicon-check")
  octicon_check.classList.remove('d-none');
  setTimeout(function() {
    octicon_copy.classList.remove('d-none');
    octicon_check.classList.add('d-none')
  }, 1000);
}
onUpdated(()=>{
  document.querySelectorAll('.copy-btn').forEach((element)=>{
    element.addEventListener('click', toggleSvg)
  })
})
onBeforeUpdate(()=>{
  document.querySelectorAll('.copy-btn').forEach((element)=>{
    element.removeEventListener('click', toggleSvg);
  })
})
</script>

<style>
.markdown {
  line-height: 1.7; /* 设置行高 */
  margin-bottom: 1.2em; /* 设置段落间距 */
}
.markdown h1,
.markdown h2 {
  border-bottom: 1px solid rgba(60, 60, 60, 0.12);
}
.markdown p img{
  max-width: 100%;
  max-height: 100%;
}
.markdown a{
  color: #0969da;
}
.markdown a:visited{
  color: #0969da;
}
.markdown p code{
  background-color: #f1f1f1;
  padding: .2em .4em;
  border-radius: 4px;
  color: #476582;
  font-size: 14px;
  white-space: nowrap;
}

pre.hljs {
  background-color: #f6f8fa;
  border-radius: 5px;
  font-size: 14px;
  line-height: 1.45;
  display: flex;
  justify-content: space-between;
}
.hljs-code{
  padding: 14px 8px 14px 24px;
  color:#1f2328;
}

pre.hljs code {
  overflow: auto;
}
pre.hljs .copy-btn {
  cursor: pointer;
  display: flex;
  width: 1.75rem;
  height: 1.75rem;
  margin: 0.5rem;
  align-items: center;
  justify-content: center;
}
pre.hljs .copy-btn:hover {
  background-color: #eef1f4;
}
.d-none{
  display: none
}
pre.hljs code::-webkit-scrollbar {
  z-index: 11;
  width: 6px;
}

pre.hljs code::-webkit-scrollbar:horizontal {
  height: 6px;
}

pre.hljs code::-webkit-scrollbar-thumb {
  border-radius: 5px;
  width: 6px;
  background: rgba(0, 0, 0, 0.2);
}
</style>
<!--<style>-->
<!--.markdown-body {-->
<!--  box-sizing: border-box;-->
<!--  min-width: 200px;-->
<!--  max-width: 980px;-->
<!--  margin: 0 auto;-->
<!--  padding: 45px;-->
<!--}-->

<!--@media (max-width: 767px) {-->
<!--  .markdown-body {-->
<!--    padding: 15px;-->
<!--  }-->
<!--}-->
<!--</style>-->