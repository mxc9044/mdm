import { get } from '@/utils/request'

export const auditApi = {
  changes: (params: any) => get('/v1/audit/changes', params),
  changesByCode: (dataCode: string) => get(`/v1/audit/changes/${dataCode}`),
  operations: (params: any) => get('/v1/audit/operations', params),
}
