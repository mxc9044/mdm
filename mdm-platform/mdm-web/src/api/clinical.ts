import { get, post, put } from '@/utils/request'

export const treatmentApi = {
  page: (params: any) => get('/v1/clinical/treatment-items', params),
  detail: (code: string) => get(`/v1/clinical/treatment-items/${code}`),
  create: (data: any) => post('/v1/clinical/treatment-items', data),
  update: (code: string, data: any) => put(`/v1/clinical/treatment-items/${code}`, data),
}

export const orderApi = {
  page: (params: any) => get('/v1/clinical/orders', params),
  detail: (code: string) => get(`/v1/clinical/orders/${code}`),
  create: (data: any) => post('/v1/clinical/orders', data),
  update: (code: string, data: any) => put(`/v1/clinical/orders/${code}`, data),
}

export const standardApi = {
  page: (params: any) => get('/v1/clinical/standards', params),
  detail: (code: string) => get(`/v1/clinical/standards/${code}`),
  create: (data: any) => post('/v1/clinical/standards', data),
  update: (code: string, data: any) => put(`/v1/clinical/standards/${code}`, data),
}
