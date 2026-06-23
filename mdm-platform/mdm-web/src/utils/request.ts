import axios, { type AxiosRequestConfig, type AxiosResponse } from 'axios'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/stores/user'
import router from '@/router'

const service = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  timeout: 30000,
})

service.interceptors.request.use(
  (config) => {
    const userStore = useUserStore()
    if (userStore.token) {
      config.headers['Authorization'] = userStore.token
    }
    return config
  },
  (error) => Promise.reject(error)
)

service.interceptors.response.use(
  (response: AxiosResponse) => {
    const res = response.data
    if (res.code !== 200) {
      ElMessage.error(res.message || '请求失败')
      if (res.code === 401) {
        const userStore = useUserStore()
        userStore.logout()
        router.push('/login')
      }
      return Promise.reject(new Error(res.message || '请求失败'))
    }
    return res
  },
  (error) => {
    const message = error.response?.data?.message || error.message || '网络异常'
    ElMessage.error(message)
    return Promise.reject(error)
  }
)

export default service

export function get<T = any>(url: string, params?: any): Promise<T> {
  return service.get(url, { params }) as any
}

export function post<T = any>(url: string, data?: any): Promise<T> {
  return service.post(url, data) as any
}

export function put<T = any>(url: string, data?: any): Promise<T> {
  return service.put(url, data) as any
}

export function patch<T = any>(url: string, data?: any): Promise<T> {
  return service.patch(url, data) as any
}

export function del<T = any>(url: string): Promise<T> {
  return service.delete(url) as any
}
