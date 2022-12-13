<template>
    <div v-if="currentPatientHistory" class="edit-form py-3">
      <!-- <button class="btn btn-sm btn-info mt-2 mb-2" @click="getPatient(currentPatientHistory.patientId)">Show Patient info</button> -->
    
      <div class="shadow rounded p-3 mb-2 bg-light text-dark" v-if="patient != null">
        <h5> &nbsp;&nbsp; {{ patient.lastName }} {{ patient.firstName }}</h5>
        <p>Birthdate: {{ patient.birthDate | formatDate }} &nbsp;&nbsp;|&nbsp;&nbsp;</p>
        <p>Gender: {{ patient.gender }}</p>
        <p>Address: {{ patient.address }}</p>
        <p>Email: {{ patient.email }}</p>
        <p>Phone Number: {{ patient.phoneNumber }}</p>

     
    </div>
      

      <p class="headline">Edit Patient History</p>
  
      <v-form ref="form" lazy-validation>
        <v-text-field v-model="currentPatientHistory.patientId" :rules="[(v) => !!v || 'Patient ID is required']" label="patientId"
          required></v-text-field>
          <v-text-field v-model="currentPatientHistory.creationDate" :rules="[(v) => !!v || 'CreationDate is required']" label="creationDate"
          required></v-text-field>
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
      <p>Please click on a Patient...</p>
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
            console.log(response.data);
            this.message = "The Patient History was updated successfully!";
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
  