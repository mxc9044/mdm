import { get } from '@/utils/request'

export const qualityApi = {
  dashboard: () => get('/v1/quality/dashboard'),
  dailyReport: (date?: string) => get('/v1/quality/reports/daily', date ? { date } : {}),
  weeklyReport: () => get('/v1/quality/reports/weekly'),
}
