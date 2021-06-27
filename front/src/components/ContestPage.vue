<template>
  <div>
    <div v-if="!loading">
      <div class="w3-bar w3-teal w3-container w3-left-align w3-large">
        <a v-if="selectedId !== ''" v-on:click="show_review = true"
           class="w3-bar-item w3-button w3-hide-small w3-hover-white ">Открыть</a>
        <a v-if="selectedId !== '' && evaluateIndicatorPredicate(selectedId)" v-on:click="show_indicators_eval = true"
           class="w3-bar-item w3-button w3-hide-small w3-hover-white ">Оценка критериев</a>
        <a v-if="selectedId !== '' && evaluateProjectPredicate(selectedId) && !evaluateIndicatorPredicate(selectedId)"
           v-on:click="show_projects_eval = true"
           class="w3-bar-item w3-button w3-hide-small w3-hover-white ">Оценка проектов</a>

      </div>

      <modal v-if="show_review" @close="closeModals" :title="'Конкурс'" :url="url" :id="selectedId"
             :fields="review_config" :read-only="true"
             class="w3-animate-opacity"></modal>
      <indicators_evaluation v-if="show_indicators_eval" @close="closeModals" :id="selectedId"></indicators_evaluation>
      <projects_evaluation v-if="show_projects_eval" @close="closeModals" :id="selectedId"></projects_evaluation>

      <table class="w3-table-all w3-animate-opacity">
        <thead>
        <tr class="w3-border">
          <th><a v-on:click="getPage({sort:'name'})">Название</a></th>
        </tr>
        </thead>
        <tbody>
        <tr v-on:click="selectedId=item.id" :class="{'w3-teal':selectedId === item.id}" class="w3-hover-teal w3-border"
            v-for="item in items" :key="item.id">
          <td>{{ item.name }}</td>
        </tr>
        </tbody>
      </table>

      <div class="w3-bar w3-center w3-margin-top w3-animate-opacity">
        <a class="w3-button w3-hover-teal" v-if="thisPage.page > 1" v-on:click="getPage({page : 1})"> 1 </a>
        <span v-if="thisPage.page > 2">...</span>
        <a class="w3-button w3-hover-teal" v-if="thisPage.page > 0"
           v-on:click="getPage({page : thisPage.page - 1})">{{ thisPage.page }}</a>
        <a class="w3-button w3-teal w3-border">{{ thisPage.page + 1 }}</a>
        <a class="w3-button w3-hover-teal" v-if="thisPage.page < total - 1"
           v-on:click="getPage({page : thisPage.page + 1})">{{ thisPage.page + 2 }}</a>
        <span v-if="thisPage.page < total - 3">...</span>
        <a class="w3-button w3-hover-teal" v-if="thisPage.page < total - 2" v-on:click="getPage({page: total - 1})"> {{
            total
          }}</a>
      </div>
    </div>
    <div v-if="loading" class="w3-container loader w3-display-middle"></div>
  </div>
</template>

<script>

import {auth, axios} from "@/_helpers";
import CrudModal from "./modal/CrudModal";
import {contestsAdminListConfig} from "../_page-configs/contests_admin";
import IndicatorsEvaluationModal from "./modal/IndicatorsEvaluationModal";
import ProjectsEvaluationModal from "./modal/ProjectsEvaluationModal";

export default {
  name: 'ContestPage',
  components:{
    modal: CrudModal,
    indicators_evaluation: IndicatorsEvaluationModal,
    projects_evaluation:ProjectsEvaluationModal
  },
  data() {
    return {
      url: '/contest',
      loading: false,
      thisPage: {
        page: 0,
        size: 10,
        sort: 'id',
        dir: 'asc'
      },
      total: '',
      auth: auth,
      items: [],
      selectedId: '',
      show_review: false,
      show_indicators_eval: false,
      show_projects_eval: false,
      review_config: contestsAdminListConfig.fields
    }
  },
  created() {
    this.updateData()
  },
  methods: {
    getPage({page, size, sort}) {

      if (page !== undefined) this.thisPage.page = page
      if (size !== undefined) this.thisPage.size = size
      if (sort !== undefined) {
        if (sort === this.thisPage.sort) if (this.thisPage.dir === 'asc') this.thisPage.dir = 'desc'
        else this.thisPage.dir = 'asc'
        this.thisPage.sort = sort
      }
      this.updateData()
    },
    updateData() {
      this.loading = true
      axios.get(this.url, {
        params: {
          page: this.thisPage.page,
          size: this.thisPage.size,
          sort: this.thisPage.sort + ',' + this.thisPage.dir
        }
      }).then(response => {
        this.items = response.data.content
        this.total = response.data.totalPages
      }).finally(() => this.loading = false)
    },
    closeModals() {
      this.show_review = false
      this.show_indicators_eval = false
      this.show_projects_eval = false
    },
    evaluateIndicatorPredicate(id){
      return !(this.items.find(item => item['id'] === id)['indicatorEvaluationList'].length)
    },
    evaluateProjectPredicate(id){
      return !(this.items.find(item => item['id'] === id)['projectEvaluationList'].length)
    }
  }
};
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