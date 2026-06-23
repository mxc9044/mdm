import { get, post, put, patch } from '@/utils/request'

export const mappingApi = {
  page: (params: any) => get('/v1/sync/mappings', params),
  create: (data: any) => post('/v1/sync/mappings', data),
  update: (id: number, data: any) => put(`/v1/sync/mappings/${id}`, data),
  translate: (mdmCode: string, localSystem: string) => get('/v1/sync/mappings/translate', { mdmCode, localSystem }),
}

export const syncExceptionApi = {
  page: (params: any) => get('/v1/sync/exceptions', params),
  handle: (id: number, handleStatus: number, remark?: string) => patch(`/v1/sync/exceptions/${id}/handle`, null, { params: { handleStatus, remark } } as any),
}
