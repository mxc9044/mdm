import { post, get } from '@/utils/request'

export function login(data: { username: string; password: string }) {
  return post('/v1/auth/login', data)
}

export function logout() {
  return post('/v1/auth/logout')
}
