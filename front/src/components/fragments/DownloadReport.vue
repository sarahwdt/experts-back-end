<template>
    <a class="w3-block w3-button w3-margin-bottom w3-teal" :href="url" v-text="label"
       @click.prevent="download">

    </a>
</template>

<script>

import {axios} from "../../_helpers/axios";

export default {
  name: "DownloadReport",
  props: ['value'],
  methods:{
    download(){
      axios.get("/contest/" + this.value + "/report", { responseType: 'blob' })
          .then(response => {
            const blob = new Blob([response.data], { type: 'application/pdf' })
            const link = document.createElement('a')
            link.href = URL.createObjectURL(blob)
            link.download = 'contest_'+this.value
            link.click()
            URL.revokeObjectURL(link.href)
          })
    }
  },
  computed:{
    url: function(){
      return "api/contest/" + this.value + "/report"
    },
    label: function (){
      return "Скачать отчет"
    }
  }
}
</script>

<style scoped>

</style>