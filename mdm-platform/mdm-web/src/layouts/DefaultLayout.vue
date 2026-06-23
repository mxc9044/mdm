<template>
  <el-container class="layout-container">
    <el-aside :width="isCollapse ? '68px' : '240px'" class="layout-aside">
      <div class="aside-inner">
        <div class="logo-area">
          <div class="logo-badge">
            <svg viewBox="0 0 32 32" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M4 8L16 4L28 8V18C28 24 22 28 16 30C10 28 4 24 4 18V8Z" fill="url(#shield)" opacity="0.9"/>
              <path d="M12 16L15 19L21 13" stroke="#fff" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              <defs>
                <linearGradient id="shield" x1="4" y1="4" x2="28" y2="30">
                  <stop stop-color="#4299E1"/>
                  <stop offset="1" stop-color="#00D1B2"/>
                </linearGradient>
              </defs>
            </svg>
          </div>
          <transition name="fade">
            <div v-show="!isCollapse" class="logo-text-group">
              <span class="logo-text">HYD 主数据</span>
              <span class="logo-sub">MDM Platform</span>
            </div>
          </transition>
        </div>

        <div class="menu-section-label" v-show="!isCollapse">导航菜单</div>

        <el-scrollbar>
          <el-menu
            :default-active="activeMenu"
            :collapse="isCollapse"
            router
            background-color="transparent"
            text-color="rgba(255,255,255,0.55)"
            active-text-color="#fff"
            :collapse-transition="false"
            class="sidebar-menu"
          >
            <template v-for="route in menuRoutes" :key="route.path">
              <el-sub-menu v-if="route.children && route.children.length > 1" :index="route.path">
                <template #title>
                  <el-icon><component :is="route.meta?.icon" /></el-icon>
                  <span>{{ route.meta?.title }}</span>
                </template>
                <el-menu-item
                  v-for="child in route.children"
                  :key="child.path"
                  :index="resolvePath(route.path, child.path)"
                >
                  {{ child.meta?.title }}
                </el-menu-item>
              </el-sub-menu>
              <el-menu-item
                v-else-if="route.children?.length === 1"
                :index="resolvePath(route.path, route.children[0].path)"
              >
                <el-icon><component :is="route.meta?.icon" /></el-icon>
                <span>{{ route.meta?.title || route.children[0].meta?.title }}</span>
              </el-menu-item>
            </template>
          </el-menu>
        </el-scrollbar>

        <div class="aside-footer" v-show="!isCollapse">
          <div class="aside-footer__badge">
            <span class="dot"></span>
            <span>系统运行中</span>
          </div>
        </div>
      </div>
    </el-aside>

    <el-container>
      <el-header class="layout-header">
        <div class="header-left">
          <div class="collapse-btn" @click="isCollapse = !isCollapse">
            <el-icon :size="18">
              <Fold v-if="!isCollapse" />
              <Expand v-else />
            </el-icon>
          </div>
          <div class="header-divider"></div>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item v-for="item in breadcrumbs" :key="item.path">
              {{ item.meta?.title }}
            </el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <div class="header-right">
          <div class="header-time">{{ currentTime }}</div>
          <div class="header-divider"></div>
          <el-dropdown trigger="click">
            <div class="user-area">
              <div class="user-avatar">
                {{ (userStore.realName || userStore.username || 'U').charAt(0) }}
              </div>
              <div class="user-info">
                <span class="user-name">{{ userStore.realName || userStore.username }}</span>
                <span class="user-role">管理员</span>
              </div>
              <el-icon :size="12" color="#A0AEC0"><ArrowDown /></el-icon>
            </div>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="handleLogout">
                  <el-icon><SwitchButton /></el-icon>
                  退出登录
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>

      <el-main>
        <router-view v-slot="{ Component }">
          <transition name="fade-slide" mode="out-in">
            <component :is="Component" />
          </transition>
        </router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onBeforeUnmount } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { routes } from '@/router'
import { post } from '@/utils/request'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()
const isCollapse = ref(false)
const currentTime = ref('')

const activeMenu = computed(() => route.path)

const menuRoutes = computed(() => {
  return routes.filter(r => r.path !== '/login' && !r.meta?.hidden && r.children)
})

const breadcrumbs = computed(() => route.matched.filter(r => r.meta?.title))

function resolvePath(parent: string, child: string) {
  if (parent.endsWith('/')) return parent + child
  return parent + '/' + child
}

function updateTime() {
  const now = new Date()
  currentTime.value = now.toLocaleDateString('zh-CN', {
    month: '2-digit', day: '2-digit', weekday: 'short'
  }) + ' ' + now.toLocaleTimeString('zh-CN', {
    hour: '2-digit', minute: '2-digit'
  })
}

let timer: ReturnType<typeof setInterval>

onMounted(() => {
  updateTime()
  timer = setInterval(updateTime, 30000)
})

onBeforeUnmount(() => clearInterval(timer))

async function handleLogout() {
  try {
    await post('/v1/auth/logout')
  } catch {}
  userStore.logout()
  router.push('/login')
}
</script>

<style scoped lang="scss">
.layout-container {
  height: 100vh;
}

.layout-aside {
  background: #0F1B2D;
  transition: width 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  overflow: hidden;
  position: relative;
  z-index: 20;

  &::after {
    content: '';
    position: absolute;
    right: 0;
    top: 0;
    bottom: 0;
    width: 1px;
    background: linear-gradient(180deg, rgba(66, 153, 225, 0.15) 0%, rgba(0, 209, 178, 0.08) 100%);
  }
}

.aside-inner {
  display: flex;
  flex-direction: column;
  height: 100%;
  position: relative;
  overflow: hidden;

  &::before {
    content: '';
    position: absolute;
    top: -50%;
    right: -50%;
    width: 200%;
    height: 200%;
    background: radial-gradient(ellipse at 80% 20%, rgba(66, 153, 225, 0.04) 0%, transparent 60%);
    pointer-events: none;
  }
}

.logo-area {
  height: 64px;
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 0 20px;
  position: relative;

  &::after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 20px;
    right: 20px;
    height: 1px;
    background: linear-gradient(90deg, rgba(66, 153, 225, 0.2) 0%, transparent 100%);
  }
}

.logo-badge {
  width: 36px;
  height: 36px;
  min-width: 36px;
  display: flex;
  align-items: center;
  justify-content: center;
  filter: drop-shadow(0 2px 8px rgba(66, 153, 225, 0.3));

  svg {
    width: 28px;
    height: 28px;
  }
}

.logo-text-group {
  display: flex;
  flex-direction: column;
  gap: 1px;
}

.logo-text {
  color: #fff;
  font-size: 16px;
  font-weight: 700;
  white-space: nowrap;
  letter-spacing: 0.5px;
}

.logo-sub {
  color: rgba(255, 255, 255, 0.35);
  font-size: 10px;
  font-weight: 500;
  letter-spacing: 1.5px;
  text-transform: uppercase;
}

.menu-section-label {
  padding: 20px 24px 8px;
  font-size: 10px;
  font-weight: 600;
  color: rgba(255, 255, 255, 0.25);
  letter-spacing: 2px;
  text-transform: uppercase;
}

.sidebar-menu {
  border-right: none !important;

  :deep(.el-menu-item),
  :deep(.el-sub-menu__title) {
    height: 42px;
    line-height: 42px;
    margin: 2px 10px;
    border-radius: 8px;
    transition: all 0.2s ease;
    font-size: 13.5px;

    &:hover {
      background: rgba(255, 255, 255, 0.06) !important;
      color: rgba(255, 255, 255, 0.9) !important;
    }
  }

  :deep(.el-menu-item.is-active) {
    background: linear-gradient(135deg, rgba(66, 153, 225, 0.18) 0%, rgba(0, 209, 178, 0.08) 100%) !important;
    color: #fff !important;
    font-weight: 600;
    position: relative;
    box-shadow: 0 2px 8px rgba(66, 153, 225, 0.1);

    &::before {
      content: '';
      position: absolute;
      left: 0;
      top: 50%;
      transform: translateY(-50%);
      width: 3px;
      height: 18px;
      background: linear-gradient(180deg, #4299E1 0%, #00D1B2 100%);
      border-radius: 0 3px 3px 0;
    }
  }

  :deep(.el-sub-menu.is-opened > .el-sub-menu__title) {
    color: rgba(255, 255, 255, 0.85) !important;
  }

  :deep(.el-sub-menu .el-menu) {
    background: transparent !important;
  }

  :deep(.el-sub-menu .el-menu .el-menu-item) {
    padding-left: 52px !important;
    height: 38px;
    line-height: 38px;
    font-size: 13px;
  }
}

.aside-footer {
  padding: 16px 20px;
  border-top: 1px solid rgba(255, 255, 255, 0.06);

  &__badge {
    display: flex;
    align-items: center;
    gap: 8px;
    font-size: 11px;
    color: rgba(255, 255, 255, 0.35);

    .dot {
      width: 6px;
      height: 6px;
      border-radius: 50%;
      background: #48BB78;
      box-shadow: 0 0 6px rgba(72, 187, 120, 0.6);
      animation: pulse-dot 2s ease-in-out infinite;
    }
  }
}

@keyframes pulse-dot {
  0%, 100% { opacity: 1; }
  50% { opacity: 0.5; }
}

.layout-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: #fff;
  box-shadow: 0 1px 0 #EDF2F7;
  padding: 0 24px;
  height: 60px;
  z-index: 10;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 16px;
}

.header-divider {
  width: 1px;
  height: 20px;
  background: #E2E8F0;
}

.collapse-btn {
  width: 36px;
  height: 36px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 8px;
  cursor: pointer;
  color: #A0AEC0;
  transition: all 0.2s ease;

  &:hover {
    background: #EBF4FF;
    color: #2B6CB0;
  }
}

.header-right {
  display: flex;
  align-items: center;
  gap: 16px;
}

.header-time {
  font-size: 12px;
  color: #A0AEC0;
  font-weight: 500;
  font-variant-numeric: tabular-nums;
}

.user-area {
  display: flex;
  align-items: center;
  gap: 10px;
  cursor: pointer;
  padding: 6px 10px;
  border-radius: 10px;
  transition: all 0.2s ease;

  &:hover {
    background: #F7FAFC;
  }
}

.user-avatar {
  width: 34px;
  height: 34px;
  border-radius: 10px;
  background: linear-gradient(135deg, #2B6CB0 0%, #4299E1 100%);
  color: #fff;
  font-size: 14px;
  font-weight: 700;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 2px 6px rgba(43, 108, 176, 0.25);
}

.user-info {
  display: flex;
  flex-direction: column;
  gap: 1px;
}

.user-name {
  font-size: 13px;
  color: #1A202C;
  font-weight: 600;
  line-height: 1.2;
}

.user-role {
  font-size: 11px;
  color: #A0AEC0;
  line-height: 1.2;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
