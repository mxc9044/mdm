<template>
  <div class="operation-log-page">
    <div class="page-header">
      <h2>操作日志</h2>
    </div>
    <div class="search-bar">
      <el-input v-model="query.module" placeholder="模块" clearable style="width: 140px" @clear="fetchData" />
      <el-input v-model="query.operator" placeholder="操作人" clearable style="width: 140px" />
      <el-date-picker v-model="query.startTime" type="datetime" value-format="YYYY-MM-DD HH:mm:ss" placeholder="开始时间" style="width: 200px" />
      <el-date-picker v-model="query.endTime" type="datetime" value-format="YYYY-MM-DD HH:mm:ss" placeholder="结束时间" style="width: 200px" />
      <el-button type="primary" @click="fetchData">查询</el-button>
    </div>
    <div class="table-card">
      <el-table :data="tableData" v-loading="loading" stripe>
        <template #empty>
          <el-empty description="暂无操作日志数据" />
        </template>
        <el-table-column prop="module" label="模块" width="120" />
        <el-table-column prop="operation" label="操作" width="120" />
        <el-table-column prop="method" label="方法" width="100" />
        <el-table-column prop="requestUrl" label="请求地址" min-width="220" show-overflow-tooltip />
        <el-table-column prop="responseCode" label="响应码" width="90" />
        <el-table-column prop="costTime" label="耗时(ms)" width="100" />
        <el-table-column prop="operator" label="操作人" width="100" />
        <el-table-column prop="operatorIp" label="操作IP" width="140" />
        <el-table-column prop="createTime" label="创建时间" width="170" />
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
  module: '',
  operator: '',
  startTime: '',
  endTime: '',
})

const { loading, tableData, total, pagination, fetchData: baseFetch } = useTable(auditApi.operations)

function fetchData() { baseFetch(query) }

onMounted(() => fetchData())
</script>

