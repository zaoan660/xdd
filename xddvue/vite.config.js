import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'
import Icons from 'unplugin-icons/vite'
import viteCompression from 'vite-plugin-compression'
import IconsResolver from 'unplugin-icons/resolver'


// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue(),
    AutoImport({
      resolvers: [ElementPlusResolver(),
        ],

    }),
    Components({
      resolvers: [ElementPlusResolver(),
       ],
    }),

    viteCompression({
      verbose: true,
      threshold: 10240,
      algorithm: 'gzip',
    })
  ],

})
