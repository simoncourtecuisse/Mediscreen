<template>
  <div v-if="currentPatient" class="edit-form py-3">
    <p class="headline">Edit Patient</p>

    <v-form ref="form" lazy-validation>
      <v-text-field v-model="currentPatient.lastName" :rules="[(v) => !!v || 'Last Name is required']" label="LastName"
        required></v-text-field>
      <v-text-field v-model="currentPatient.firstName" :rules="[(v) => !!v || 'First Name is required']" label="FirstName"
        required></v-text-field>
      <v-text-field v-model="currentPatient.birthdate" :rules="[(v) => !!v || 'Birhtdate is required']" label="Birhtdate"
        required></v-text-field>
      <v-text-field v-model="currentPatient.gender" :rules="[(v) => !!v || 'Gender is required']" label="Gender" required>
      </v-text-field>
      <v-text-field v-model="currentPatient.address" :rules="[(v) => !!v || 'Address is required']" label="Address" required>
      </v-text-field>
      <v-text-field v-model="currentPatient.email" :rules="[(v) => !!v || 'Email is required']" label="Email" required>
      </v-text-field>
      <v-text-field v-model="currentPatient.phoneNumber" :rules="[(v) => !!v || 'Phone Number is required']"
        label="PhoneNumber" required></v-text-field>

      <v-divider class="my-5"></v-divider>

      <v-btn color="error" small class="mr-2" @click="deletePatient">
        Delete
      </v-btn>

      <v-btn color="success" small @click="updatePatient">
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

export default {
  name: "patient-details",
  data() {
    return {
      currentPatient: null,
      message: "",
    };
  },
  methods: {
    getPatient(id) {
      PatientService.get(id)
        .then((response) => {
          this.currentPatient = response.data;
          console.log(response.data);
        })
        .catch((e) => {
          console.log(e);
        });
    },

    updatePatient() {
      PatientService.update(this.currentPatient.id, this.currentPatient)
        .then((response) => {
          console.log(response.data);
          this.message = "The Patient was updated successfully!";
          this.$router.push({ name: "patientProfil" });
        })
        .catch((e) => {
          console.log(e);
        });
    },

    deletePatient() {
      PatientService.delete(this.currentPatient.id)
        .then((response) => {
          console.log(response.data);
          this.$router.push({ name: "patients" });
        })
        .catch((e) => {
          console.log(e);
        });
    },
  },
  mounted() {
    this.message = "";
    this.getPatient(this.$route.params.id);
  },
};
</script>

<style>
.edit-form {
  max-width: 300px;
  margin: auto;
}
</style>
