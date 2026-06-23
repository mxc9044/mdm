import { get, post, put } from '@/utils/request'

export const drugApi = {
  page: (params: any) => get('/v1/supply/drugs', params),
  detail: (drugCode: string) => get(`/v1/supply/drugs/${drugCode}`),
  search: (keyword: string) => get('/v1/supply/drugs/search', { keyword }),
  create: (data: any) => post('/v1/supply/drugs', data),
  update: (drugCode: string, data: any) => put(`/v1/supply/drugs/${drugCode}`, data),
}

export const materialApi = {
  page: (params: any) => get('/v1/supply/materials', params),
  detail: (code: string) => get(`/v1/supply/materials/${code}`),
  create: (data: any) => post('/v1/supply/materials', data),
  update: (code: string, data: any) => put(`/v1/supply/materials/${code}`, data),
}

export const supplyItemApi = {
  page: (params: any) => get('/v1/supply/supply-items', params),
  detail: (code: string) => get(`/v1/supply/supply-items/${code}`),
  create: (data: any) => post('/v1/supply/supply-items', data),
  update: (code: string, data: any) => put(`/v1/supply/supply-items/${code}`, data),
}
