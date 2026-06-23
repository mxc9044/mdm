<template>
  <div class="change-history-page">
    <div class="page-header">
      <h2>变更历史</h2>
    </div>
    <div class="search-bar">
      <el-input v-model="query.dataDomain" placeholder="数据域" clearable style="width: 140px" @clear="fetchData" />
      <el-input v-model="query.dataCode" placeholder="数据编码" clearable style="width: 160px" />
      <el-input v-model="query.changeType" placeholder="变更类型" clearable style="width: 140px" />
      <el-date-picker v-model="query.startTime" type="datetime" value-format="YYYY-MM-DD HH:mm:ss" placeholder="开始时间" style="width: 200px" />
      <el-date-picker v-model="query.endTime" type="datetime" value-format="YYYY-MM-DD HH:mm:ss" placeholder="结束时间" style="width: 200px" />
      <el-button type="primary" @click="fetchData">查询</el-button>
    </div>
    <div class="table-card">
      <el-table :data="tableData" v-loading="loading" stripe>
        <template #empty>
          <el-empty description="暂无变更历史数据" />
        </template>
        <el-table-column prop="dataDomain" label="数据域" width="110" />
        <el-table-column prop="dataCode" label="数据编码" width="140" />
        <el-table-column prop="changeType" label="变更类型" width="100" />
        <el-table-column prop="fieldName" label="字段名" width="120" />
        <el-table-column prop="oldValue" label="旧值" min-width="140" show-overflow-tooltip />
        <el-table-column prop="newValue" label="新值" min-width="140" show-overflow-tooltip />
        <el-table-column prop="version" label="版本" width="70" />
        <el-table-column prop="operator" label="操作人" width="100" />
        <el-table-column prop="operateTime" label="操作时间" width="170" />
        <el-table-column prop="source" label="来源" width="100" />
      </el-table>
      <div class="pagination-wrapper">
        <el-pagination v-model:current-page="pagination.pageNum" v-model:page-size="pagination.pageSize"
          :total="total" :page-sizes="[20, 50, 100]" layout="total, sizes, prev, pager, next" @current-change="fetchData" @size-change="fetchData" />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, onMounted } from 'vue'
import { useTable } from '@/composables/useTable'
import { auditApi } from '@/api/audit'

const query = reactive({
  dataDomain: '',
  dataCode: '',
  changeType: '',
  startTime: '',
  endTime: '',
})

const { loading, tableData, total, pagination, fetchData: baseFetch } = useTable(auditApi.changes)

function fetchData() { baseFetch(query) }

onMounted(() => fetchData())
</script>

