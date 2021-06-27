<template>
  <div class="w3-row w3-container">
    <div class=""><div class="w3-col s8 w3-padding"
    >{{ indicatorsEvaluation.first.name }}/{{ indicatorsEvaluation.second.name }}</div></div>
    <div class="w3-col s4 w3-centered">
        <span class="w3-button w3-border w3-round-xxlarge" style="margin-outside: 5px; margin-top: 3px"
              v-on:click="setEvaluation(1.5)" :class="{'w3-green':isSelected(1.5)}"
        >&gt;</span>
      <span class="w3-button w3-border w3-round-xxlarge" style="margin-outside: 5px; margin-top: 3px"
            v-on:click="setEvaluation(1.0)" :class="{'w3-green':isSelected(1.0)}"
      >=</span>
      <span class="w3-button w3-border w3-round-xxlarge" style="margin-outside: 5px; margin-top: 3px"
            v-on:click="setEvaluation(0.5)" :class="{'w3-green':isSelected(0.5)}"
      >&lt;</span>
    </div>
  </div>
</template>

<script>

export default {
  name: "IndicatorsEvaluation",
  data() {
    return {
      item: this.indicatorsEvaluation
    }
  },
  props: {
    indicatorsEvaluation: {
      type: Object,
      default: () => {
      }
    }
  },
  methods: {
    isSelected(float) {
      return this.item.evaluation === float
    },
    setEvaluation(float) {
      this.item.evaluation = float
      this.returnData(this.item)
    },
    returnData(data) {
      this.$emit('input', data);
    }
  },
}
</script>

<style scoped>

</style>