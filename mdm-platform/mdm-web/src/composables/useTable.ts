import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'

export function useTable<T = any>(apiFn: (params: any) => Promise<any>) {
  const loading = ref(false)
  const tableData = ref<T[]>([])
  const total = ref(0)
  const pagination = reactive({ pageNum: 1, pageSize: 20 })

  async function fetchData(extraParams: Record<string, any> = {}) {
    loading.value = true
    try {
      const res = await apiFn({ ...pagination, ...extraParams })
      tableData.value = res.data?.list || []
      total.value = res.data?.total || 0
    } catch (e: any) {
      ElMessage.error(e.message || '加载数据失败')
    } finally {
      loading.value = false
    }
  }

  function handlePageChange(page: number) {
    pagination.pageNum = page
  }

  function handleSizeChange(size: number) {
    pagination.pageSize = size
    pagination.pageNum = 1
  }

  return { loading, tableData, total, pagination, fetchData, handlePageChange, handleSizeChange }
}
