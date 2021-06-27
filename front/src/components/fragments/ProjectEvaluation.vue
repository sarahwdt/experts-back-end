<template>
  <div >
    <table class="w3-table-all w3-panel" v-if="value.length">
      <tbody>
      <tr v-for="item in value" :key="item['id']">
        <td>{{item['project']['name']}}</td>
        <td>{{item['evaluation']}}</td>
      </tr>
      </tbody>
    </table>
    <div v-else class="w3-panel">Этап оценки проектов не закончен</div>
  </div>
</template>

<script>

export default {
  name: "IndicatorEvaluation",
  props: ['value'],
}
</script>

<style scoped>

</style>