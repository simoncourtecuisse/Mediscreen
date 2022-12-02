import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

export default new Router({
  mode: "history",
  routes: [
    {
      path: "/",
      alias: "/patient/all",
      name: "patients",
      component: () => import("./components/PatientsList")
    },
    {
      path: "/patient/:id",
      name: "patient-details",
      component: () => import("./components/Patient")
    },
    {
      path: "/add",
      name: "add",
      component: () => import("./components/AddPatient")
    },
    {
      path: "/patientHistory/all",
      name: "patientHistories",
      component: () =>
          import ("./components/PatientHistoryList")
  },
  {
      path: "/patientHistory/patient/:id",
      name: "patient-patientHistory",
      component: () =>
          import ("./components/Patient-PatientHistory")
  },
  {
      path: "/patientHistory/patient/:patientId/add",
      name: "add-patientHistory",
      component: () =>
          import ("./components/PatientHistory")
  },
  {
      path: "/patientHistory/:id",
      name: "patientHistory-details",
      component: () =>
          import ("./components/PatientHistory")
  }
  ]
});
