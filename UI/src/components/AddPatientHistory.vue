<template>
    <div class="submit-form mt-3 mx-auto">
      <p class="headline">Add Patient History</p>
  
      <div v-if="!submitted">
        <v-form ref="form" lazy-validation>
          <v-text-field v-model="patientHistory.observation" :rules="[(v) => !!v || 'Observation is required']" label="Observation"
            required></v-text-field>
        </v-form>
  
        <v-btn color="primary" class="mt-3" @click="savePatientHistory">Submit</v-btn>
      </div>
  
      <div v-else>
        <v-card class="mx-auto">    
          <v-card-title>
            Submitted successfully!
          </v-card-title>
  
          <v-card-subtitle>
            Click the button to add new Patient History.
          </v-card-subtitle>
  
          <v-card-actions>
            <v-btn color="success" @click="newPatientHistory">Add</v-btn>
          </v-card-actions>
        </v-card>
      </div>
    </div>
  </template>
  
  <script>
  import PatientHistoryService from "../services/PatientHistoryService";
  
  export default {
    name: "add-patientHistory",
    data() {
      return {
        patientHistory: {
          observation: ""
        },
        submitted: false,
      };
    },
    methods: {
      savePatientHistory() {
        var data = {  
          patientId: this.$route.params.patientId, 
          creationDate: this.patientHistory.creationDate,
          observation: this.patientHistory.observation
        }
        console.log(data);
    
        PatientHistoryService.createPatientHistory(this.$route.params.patientId, data)
          .then((response) => {
            console.log(this.$route.params.patientId)
            this.patientHistory.id = response.data.id;
            console.log(response.data);
            this.submitted = true;
          })  
          .catch((e) => {
            console.log(e);
          })
          ;
          this.$router.push({ name: "patientProfil", params: { id: this.$route.params.patientId } });
      },
  
      newPatientHistory() {
        this.submitted = false;
        this.patientHistory = {};
      },
    },
  };
  </script>
  
  <style>
  .submit-form {
    max-width: 300px;
  }
  </style>
  