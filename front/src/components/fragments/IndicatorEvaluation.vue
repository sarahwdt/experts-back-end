<template>
  <div >
    <table class="w3-table-all w3-panel" v-if="value.length">
      <thead>
      <tr>
        <th v-for="item in value" :key="item['id']">{{item['indicator']['name']}}</th>
      </tr>
      </thead>
      <tbody>
      <tr>
        <td v-for="item in value" :key="item['id']">{{item['evaluation']}}</td>
      </tr>
      </tbody>
    </table>
    <div v-else class="w3-panel">Этап оценки критериев не закончен</div>
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