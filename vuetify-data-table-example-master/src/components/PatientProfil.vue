<template>
    <v-row align="center" class="list px-3 mx-auto">
  
      <v-col cols="12" sm="12">
        <v-card class="mx-auto" tile>
          <v-card-title>patientProfil</v-card-title>
            
          <v-data-table
            :headers="headers"
            :items="patientProfil"
            disable-pagination
            :hide-default-footer="true"
          >
         
          </v-data-table>
  
        
        </v-card>
      </v-col>
      <v-col cols="12" sm="12">
        <v-card class="mx-auto" tile>
          <v-card-title>Patient History</v-card-title>
  
          <v-data-table
            :headers="titles"
            :items="patientHistory"
            disable-pagination
            :hide-default-footer="true"
          >
            <template v-slot:[`item.actions`]="{ item }">
              <v-icon small class="mr-2" @click="editPatientHistory(item.id)">mdi-pencil</v-icon>
              <v-icon small @click="deletePatientHistory(item.id)">mdi-delete</v-icon>
            </template>
          </v-data-table>
  
          <!-- <v-card-actions v-if="patientHistory.length > 0">
          </v-card-actions> -->
        </v-card>
      </v-col>
    </v-row>
  </template>
  
  <script>
  import PatientService from "../services/PatientService";
  import PatientHistoryService from '../services/PatientHistoryService';
  export default {
    name: "patientProfil",
    data() {
      return {
        patientProfil: [],
        lastName: "",
        headers: [
          { text: "Last Name", align: "start", sortable: true, value: "lastName" },
          { text: "First Name", sortable: false, value: "firstName" },
          { text: "Birthdate", sortable: true, value: "birthdate" },
          { text: "Gender", sortable: true, value: "gender" },
          { text: "Address", sortable: true, value: "address" },
          { text: "Email", sortable: false, value: "email" },
          { text: "Phone Number", sortable: false, value: "phoneNumber" },
        ],
        patientHistory: [],
        titles: [
        //   { text: "PatientHistory Id", align: "start", sortable: false, value: "id" },
          { text: "Patient Id", sortable: true, value: "patientId" },
          { text: "Creation Date", sortable: true, value: "creationDate" },
          { text: "Obervations", sortable: false, value: "observation" },
          { text: "Actions", value: "actions", sortable: false },
        ],
      };
    },
    methods: {
        getPatient(id) {
      PatientService.get(id)
        .then((response) => {
          this.patientProfil = [this.getDisplayPatient(response.data)];
          console.log(response.data);
        })
        .catch((e) => {
          console.log(e);
        });
    },

    getPatientHistory(patientId) {
        PatientHistoryService.getPatientHistory(this.$router.push({ name: "patientProfil", params: { patientId: patientId } }))
          .then((response) => {
            this.patientHistory = response.data.map(this.getDisplayPatientHistory);
            console.log(response.data);
          })
          .catch((e) => {
            console.log(e);
          });
      },
      
       
      getDisplayPatient(patient) {
        return {
          id: patient.id,
          lastName: patient.lastName,
          firstName: patient.firstName,
          birthdate: patient.birthdate,
          gender: patient.gender,
          address: patient.address,
          email: patient.email,
          phoneNumber: patient.phoneNumber,
        };
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
        this.getPatient(this.$route.params.id);
        this.getPatientHistory();
        // this.getPatientHistory(this.$router.push({ name: "patientProfil", params: { patientId: patientId } }))
    },
  };

  

  </script>
  
  <style>
  .list {
    max-width: 1240px;
  }
  </style>
  
  
  