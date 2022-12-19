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
      path: "/patient/:id",
      name: "patientProfil",
      component: () => import("./components/PatientProfil")
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
      path: "/patientHistory/patient/:patientId",
      name: "patientProfil",
      component: () =>
          import ("./components/PatientProfil")
  },
  {
      path: "/patientHistory/patient/:patientId/add",
      name: "add-patientHistory",
      component: () =>
          import ("./components/AddPatientHistory")
  },
  {
      path: "/patientHistory/:id",
      name: "patientHistory-details",
      component: () =>
          import ("./components/PatientHistory")
  }
  ]
});
