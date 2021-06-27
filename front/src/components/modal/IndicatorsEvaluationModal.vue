<template>
  <div class="w3-modal w3-container" style="display: block;">
    <div class="w3-modal-content">
      <div v-if="!loading">
        <header class="w3-container w3-xxlarge w3-teal"><h2>{{ title }} {{ id }}</h2></header>
        <span v-on:click="close" class="w3-button w3-display-topright">&times;</span>
        <form class="w3-container w3-padding-bottom" @submit.prevent="submit">
          <div v-for="user in usersMap.keys()" :key="user">
            <div class="w3-card-4 w3-margin-top w3-margin-bottom">
              <div class="w3-teal w3-padding"><b>{{ user }}</b></div>
              <div class="w3-padding" v-for="item in usersMap.get(user)" :key="item.id">
                <evaluation :indicators-evaluation="item"></evaluation>
              </div>
            </div>
          </div>
          <button class="w3-button w3-block w3-teal w3-section w3-padding" type="submit">
            Оценить
          </button>
        </form>
      </div>
      <div v-else class="loader w3-display-middle"></div>
    </div>
  </div>
</template>

<script>
import {axios} from "@/_helpers";
import IndicatorsEvaluation from "../fragments/IndicatorsEvaluation";
import {auth} from "../../_helpers/auth";

export default {
  name: "IndicatorsEvaluationModal",
  components: {
    evaluation: IndicatorsEvaluation
  },
  data() {
    return {
      items: {},
      loading: false,
      auth: auth,
      title: 'Оценка критериев конкурса №',
      url: "/indicators_evaluation"
    }
  },
  props: {
    id: Number
  },
  created() {
    if (this.id) this.get()
  },
  watch: {
    id: {
      handler() {
        if (this.id) this.get()
      }
    }
  },
  methods: {
    setDataByKey(key, data) {
      this.items[key] = data;
    },
    close() {
      this.$emit('close')
    },
    submit() {
      this.put()
    },
    get() {
      this.loading = true
      axios.get(this.url, {
        params: {
          contest_id: this.id,
        }
      }).then(response => {
        this.items = response.data
      }).finally(() => this.loading = false)
    },
    put() {
      this.loading = true
      let data = this.items

      axios.put(this.url,
          data, {
            headers: {'Content-Type': 'application/json'},
          }).then(() => this.close())
          .finally(() => this.loading = false)
    },
  },
  computed: {
    usersMap() {
      const map = new Map();
      this.items.forEach(item => {
        const user = item["user"]["id"] + ': ' + item["user"]["secondName"] + " " + item["user"]["firstName"]
        const collection = map.get(user)
        if (!collection)
          map.set(user, [item])
        else
          collection.push(item)
      })
      return map
    }
  }
}
</script>

<style scoped>
.loader {
  border: 16px solid #f3f3f3; /* Light grey */
  border-top: 16px solid teal; /* Blue */
  border-radius: 50%;
  width: 120px;
  height: 120px;
  animation: spin 0.3s linear infinite;
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}
</style>