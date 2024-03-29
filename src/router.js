import { createRouter, createWebHistory } from 'vue-router'
import wdMainContent from "./components/websiteDiary/wdMainContent.vue";
import wdLeftSidebar from "./components/websiteDiary/wdLeftSidebar.vue";

const router = createRouter({
    history: createWebHistory(),
    routes: [
        { path: '/', components: { MainContent: wdMainContent, LeftSidebar: wdLeftSidebar}},
    ],
})

export default router;