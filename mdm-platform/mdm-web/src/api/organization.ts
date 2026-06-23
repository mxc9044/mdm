import { get, post, put, patch } from '@/utils/request'

export const orgApi = {
  page: (params: any) => get('/v1/org/orgs', params),
  tree: () => get('/v1/org/orgs/tree'),
  detail: (orgCode: string) => get(`/v1/org/orgs/${orgCode}`),
  create: (data: any) => post('/v1/org/orgs', data),
  update: (orgCode: string, data: any) => put(`/v1/org/orgs/${orgCode}`, data),
  updateStatus: (orgCode: string, status: number) => patch(`/v1/org/orgs/${orgCode}/status`, null, { params: { status } } as any),
}

export const deptApi = {
  page: (params: any) => get('/v1/org/depts', params),
  tree: (orgCode: string) => get(`/v1/org/depts/tree/${orgCode}`),
  detail: (deptCode: string) => get(`/v1/org/depts/${deptCode}`),
  create: (data: any) => post('/v1/org/depts', data),
  update: (deptCode: string, data: any) => put(`/v1/org/depts/${deptCode}`, data),
}

export const employeeApi = {
  page: (params: any) => get('/v1/org/employees', params),
  detail: (empCode: string) => get(`/v1/org/employees/${empCode}`),
  create: (data: any) => post('/v1/org/employees', data),
  update: (empCode: string, data: any) => put(`/v1/org/employees/${empCode}`, data),
  positions: (empCode: string) => get(`/v1/org/employees/${empCode}/positions`),
  addPosition: (empCode: string, data: any) => post(`/v1/org/employees/${empCode}/positions`, data),
}
