<template>
    <v-row align="center" class="list px-3 mx-auto">
      <v-col cols="12" md="8">
        <v-text-field v-model="lastName" label="Search by Last Name"></v-text-field>
      </v-col>
  
      <v-col cols="12" md="4">
        <v-btn small @click="searchLastName">
          Search
        </v-btn>
      </v-col>
  
      <v-col cols="12" sm="12">
        <v-card class="mx-auto" tile>
          <v-card-title>Patient Histories</v-card-title>
  
          <v-data-table
            :headers="headers"
            :items="patientHistory"
            disable-pagination
            :hide-default-footer="true"
          >
            <template v-slot:[`item.actions`]="{ item }">
              <v-icon small class="mr-2" @click="editPatientHistory(item.id)">mdi-pencil</v-icon>
            </template>
          </v-data-table>
  
          <v-card-actions v-if="patientHistory.length > 0">
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </template>
  
  <script>
//   import PatientService from "../services/PatientHistoryService";
import PatientHistoryService from '../services/PatientHistoryService';
  export default {
    name: "patientHistories-list",
    data() {
      return {
        patientHistory: [],
        lastName: "",
        headers: [
        //   { text: "PatientHistory Id", align: "start", sortable: false, value: "id" },
          { text: "Patient Id", sortable: true, value: "patientId" },
          { text: "Creation Date", sortable: true, value: "creationDate" },
          { text: "Obervations", sortable: false, value: "observation" },
          { text: "Actions", value: "actions", sortable: false },
        ],
      };
    },
    methods: {
      retrievePatientHistories() {
        PatientHistoryService.getPatientHistories()
          .then((response) => {
            this.patientHistory = response.data.map(this.getDisplayPatientHistory);
            console.log(response.data);
          })
          .catch((e) => {
            console.log(e);
          });
      },
  
      refreshList() {
        this.retrievePatientHistories();
      },
  
      // removeAllPatients() {
      //   PatientService.deleteAll()
      //     .then((response) => {
      //       console.log(response.data);
      //       this.refreshList();
      //     })
      //     .catch((e) => {
      //       console.log(e);
      //     });
      // },
      
 
      editPatientHistory(id) {
      this.$router.push({ name: "patientHistory-details", params: { id: id } });
    },

      
      searchLastName() {
        PatientHistoryService.findByLastName(this.lastName)
          .then((response) => {
            this.patientHistory = response.data.map(this.getDisplayPatientHistory);
            console.log(response.data);
          })
          .catch((e) => {
            console.log(e);
          });
      },
    
      deletePatientHistory(id) {
        PatientHistoryService.deletePatientHistory(id)
          .then(() => {
            this.refreshList();
          })
          .catch((e) => {
            console.log(e);
          });
      },
  
      getDisplayPatientHistory(patientHistory) {
        return {
          id: patientHistory.id,
          patientId: patientHistory.patientId,
          creationDate: patientHistory.creationDate,
          observation: patientHistory.observation,
        };
      },
    },
    mounted() {
      this.retrievePatientHistories();
    },
  };
  </script>
  
  <style>
 .list {
  max-width: 1240px;
}
  </style>
  
  
  