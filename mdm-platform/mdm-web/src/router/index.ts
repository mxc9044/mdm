import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router'
import { useUserStore } from '@/stores/user'

const Layout = () => import('@/layouts/DefaultLayout.vue')

const routes: RouteRecordRaw[] = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/index.vue'),
    meta: { title: '登录', hidden: true },
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/views/dashboard/index.vue'),
        meta: { title: '工作台', icon: 'Odometer' },
      },
    ],
  },
  {
    path: '/org',
    component: Layout,
    redirect: '/org/orgs',
    meta: { title: '组织人事', icon: 'OfficeBuilding' },
    children: [
      { path: 'orgs', name: 'OrgList', component: () => import('@/views/organization/org/index.vue'), meta: { title: '机构管理' } },
      { path: 'depts', name: 'DeptList', component: () => import('@/views/organization/dept/index.vue'), meta: { title: '科室管理' } },
      { path: 'employees', name: 'EmployeeList', component: () => import('@/views/organization/employee/index.vue'), meta: { title: '员工管理' } },
    ],
  },
  {
    path: '/supply',
    component: Layout,
    redirect: '/supply/drugs',
    meta: { title: '供应链', icon: 'Box' },
    children: [
      { path: 'drugs', name: 'DrugList', component: () => import('@/views/supply/drug/index.vue'), meta: { title: '药品管理' } },
      { path: 'materials', name: 'MaterialList', component: () => import('@/views/supply/material/index.vue'), meta: { title: '耗材管理' } },
      { path: 'supply-items', name: 'SupplyItemList', component: () => import('@/views/supply/supply-item/index.vue'), meta: { title: '物资管理' } },
    ],
  },
  {
    path: '/clinical',
    component: Layout,
    redirect: '/clinical/treatment-items',
    meta: { title: '临床业务', icon: 'FirstAidKit' },
    children: [
      { path: 'treatment-items', name: 'TreatmentItemList', component: () => import('@/views/clinical/treatment-item/index.vue'), meta: { title: '诊疗项目' } },
      { path: 'orders', name: 'OrderList', component: () => import('@/views/clinical/order/index.vue'), meta: { title: '医嘱管理' } },
      { path: 'standards', name: 'StandardList', component: () => import('@/views/clinical/standard/index.vue'), meta: { title: '规范管理' } },
    ],
  },
  {
    path: '/facility',
    component: Layout,
    redirect: '/facility/devices',
    meta: { title: '设备管理', icon: 'Monitor' },
    children: [
      { path: 'devices', name: 'DeviceList', component: () => import('@/views/facility/device/index.vue'), meta: { title: '设备管理' } },
    ],
  },
  {
    path: '/sync',
    component: Layout,
    redirect: '/sync/mapping',
    meta: { title: '数据同步', icon: 'Refresh' },
    children: [
      { path: 'mapping', name: 'MappingList', component: () => import('@/views/sync/mapping/index.vue'), meta: { title: '对照映射' } },
      { path: 'exceptions', name: 'SyncExceptions', component: () => import('@/views/sync/exception/index.vue'), meta: { title: '校验异常' } },
    ],
  },
  {
    path: '/quality',
    component: Layout,
    redirect: '/quality/report',
    meta: { title: '数据质量', icon: 'DataAnalysis' },
    children: [
      { path: 'report', name: 'QualityReport', component: () => import('@/views/quality/report/index.vue'), meta: { title: '质量报表' } },
      { path: 'dashboard', name: 'QualityDashboard', component: () => import('@/views/dashboard/index.vue'), meta: { title: '质量驾驶舱' } },
    ],
  },
  {
    path: '/audit',
    component: Layout,
    redirect: '/audit/changes',
    meta: { title: '审计管理', icon: 'Document' },
    children: [
      { path: 'changes', name: 'ChangeHistory', component: () => import('@/views/audit/change-history/index.vue'), meta: { title: '变更历史' } },
      { path: 'operations', name: 'OperationLog', component: () => import('@/views/audit/operation-log/index.vue'), meta: { title: '操作日志' } },
    ],
  },
  {
    path: '/system',
    component: Layout,
    redirect: '/system/users',
    meta: { title: '系统管理', icon: 'Setting' },
    children: [
      { path: 'users', name: 'UserList', component: () => import('@/views/system/user/index.vue'), meta: { title: '用户管理' } },
      { path: 'roles', name: 'RoleList', component: () => import('@/views/system/role/index.vue'), meta: { title: '角色管理' } },
      { path: 'api-keys', name: 'ApiKeyList', component: () => import('@/views/system/api-key/index.vue'), meta: { title: 'API Key' } },
    ],
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

router.beforeEach((to, from, next) => {
  document.title = `${to.meta.title || ''} - 主数据平台`
  const userStore = useUserStore()
  if (to.path !== '/login' && !userStore.token) {
    next('/login')
  } else {
    next()
  }
})

export default router
export { routes }
