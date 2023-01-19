<template>
    <div v-if="currentPatientHistory" class="edit-form py-3">
          

      <p class="headline">Edit Patient History</p>
  
      <v-form ref="form" lazy-validation>
        <v-text-field v-model="currentPatientHistory.patientId" :rules="[(v) => !!v || 'Patient ID is required']" label="patientId"
          required></v-text-field>
          <!-- <v-text-field v-model="currentPatientHistory.creationDate" :rules="[(v) => !!v || 'CreationDate is required']" label="creationDate"
          required></v-text-field> -->
          <v-text-field v-model="currentPatientHistory.observation" :rules="[(v) => !!v || 'Observation is required']" label="observation"
          required></v-text-field>
        <v-divider class="my-5"></v-divider>
  
        <v-btn color="error" small class="mr-2" @click="deletePatientHistory">
          Delete
        </v-btn>
  
        <v-btn color="success" small @click="updatePatientHistory">
          Update
        </v-btn>
      </v-form>
  
      <p class="mt-3">{{ message }}</p>
    </div>
  
    <div v-else>
      <p>Please click on a Patient History...</p>
    </div>
  </template>
  
  <script>
  import PatientService from "../services/PatientService";
  import PatientHistoryService from "../services/PatientHistoryService";
  
  export default {
    name: "patientHistory-details",
    data() {
      return {
        currentPatientHistory: null,
        message: "",
      };
    },
    methods: {
      getPatientHistoryById(id) {
        PatientHistoryService.getPatientHistoryById(id)
          .then((response) => {
            console.log(response);
            this.currentPatientHistory = response.data;
            console.log(response.data);
          })
          .catch((e) => {
            console.log(e);
          });
      },

      getPatient(patientId) {
        PatientService.get(patientId)
        .then(response => {
          this.patient = response.data;
          console.log(response.data);
        })
        .catch(e => {
          console.log(e.response);
        });
    },
  
      updatePatientHistory() {
        PatientHistoryService.updatePatientHistory(this.currentPatientHistory.id, this.currentPatientHistory)
          .then((response) => {
            console.log(this.currentPatientHistory.patientId);
            console.log(response.data);
            this.message = "The Patient History was updated successfully!";
            this.$router.push({ name: "patientProfil", params: { id: this.currentPatientHistory.patientId } });
          })
          .catch((e) => {
            console.log(e);
          });
      },
  
      deletePatientHistory() {
        PatientHistoryService.deletePatientHistory(this.currentPatientHistory.id)
          .then((response) => {
            console.log(response.data);
            this.$router.push({ name: "patientHistories" });
          })
          .catch((e) => {
            console.log(e);
          });
      },
    },
    mounted() {
      this.message = "";
      this.getPatientHistoryById(this.$route.params.id);
    },
  };
  </script>
  
  <style>
  .edit-form {
    max-width: 300px;
    margin: auto;
  }
  </style>
  