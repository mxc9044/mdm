const dictMap: Record<string, Record<number, string>> = {
  orgType: { 1: '集团总部', 2: '总部子公司', 3: '医院', 4: '护理院', 5: '康复医院', 6: '社区项目' },
  deptType: { 1: '临床科室', 2: '医技科室', 3: '行政科室' },
  empStatus: { 1: '在职', 2: '离职', 3: '停职' },
  commonStatus: { 1: '启用', 2: '停用', 3: '注销' },
  drugCategory: { 1: '西药', 2: '中成药', 3: '中草药', 4: '生物制品' },
  storageCondition: { 1: '常温', 2: '冷藏', 3: '冷冻' },
  materialCategory: { 1: '高值耗材', 2: '低值耗材', 3: '试剂' },
  supplyItemCategory: { 1: '办公用品', 2: '后勤物资', 3: '维修配件', 4: '其他' },
  treatmentCategory: { 1: '检查', 2: '检验', 3: '治疗', 4: '手术', 5: '护理', 6: '其他' },
  chargeCategory: { 1: '甲类', 2: '乙类', 3: '丙类' },
  orderType: { 1: '长期医嘱', 2: '临时医嘱' },
  orderCategory: { 1: '药品医嘱', 2: '检查医嘱', 3: '手术医嘱', 4: '护理医嘱' },
  deviceCategory: { 1: '医疗设备', 2: '信息设备', 3: '后勤设备' },
  deviceStatus: { 1: '在用', 2: '停用', 3: '报废', 4: '维修中' },
  stdType: { 1: '临床路径', 2: '诊疗指南', 3: '操作规程' },
  stdStatus: { 1: '草稿', 2: '生效', 3: '废止' },
  practiceType: { 1: '医师', 2: '护士', 3: '药师', 4: '技师' },
  positionType: { 1: '主岗', 2: '兼职', 3: '多点执业', 4: '进修' },
  gender: { 1: '男', 2: '女' },
  handleStatus: { 0: '待处理', 1: '已处理', 2: '已忽略' },
}

export function useDict() {
  function getLabel(dictName: string, value: number | undefined | null): string {
    if (value == null) return '-'
    return dictMap[dictName]?.[value] || String(value)
  }

  function getOptions(dictName: string) {
    const dict = dictMap[dictName]
    if (!dict) return []
    return Object.entries(dict).map(([k, v]) => ({ value: Number(k), label: v }))
  }

  return { getLabel, getOptions }
}
