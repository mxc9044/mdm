import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('mdm_token') || '')
  const username = ref(localStorage.getItem('mdm_username') || '')
  const realName = ref(localStorage.getItem('mdm_realName') || '')
  const roles = ref<string[]>(JSON.parse(localStorage.getItem('mdm_roles') || '[]'))

  function setLoginInfo(data: { token: string; username: string; realName: string; roles: string[] }) {
    token.value = data.token
    username.value = data.username
    realName.value = data.realName
    roles.value = data.roles
    localStorage.setItem('mdm_token', data.token)
    localStorage.setItem('mdm_username', data.username)
    localStorage.setItem('mdm_realName', data.realName)
    localStorage.setItem('mdm_roles', JSON.stringify(data.roles))
  }

  function logout() {
    token.value = ''
    username.value = ''
    realName.value = ''
    roles.value = []
    localStorage.removeItem('mdm_token')
    localStorage.removeItem('mdm_username')
    localStorage.removeItem('mdm_realName')
    localStorage.removeItem('mdm_roles')
  }

  return { token, username, realName, roles, setLoginInfo, logout }
})
