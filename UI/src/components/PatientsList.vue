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
        <v-card-title>Patients</v-card-title>

        <v-data-table
          :headers="headers"
          :items="patients"
          disable-pagination
          :hide-default-footer="true"
        >
          <template v-slot:[`item.actions`]="{ item }">
            <v-icon small class="mr-2" @click="getPatient(item.id)">mdi-account</v-icon>
            <v-icon small class="mr-2" @click="editPatient(item.id)">mdi-pencil</v-icon>
            <v-icon small @click="deletePatient(item.id)">mdi-delete</v-icon>
          </template>
        </v-data-table>

        <v-card-actions v-if="patients.length > 0">
          <!-- <v-btn small color="error" @click="removeAllPatients">
            Remove All
          </v-btn> -->
        </v-card-actions>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import PatientService from "../services/PatientService";
export default {
  name: "patients-list",
  data() {
    return {
      patients: [],
      lastName: "",
      headers: [
        { text: "Last Name", align: "start", sortable: true, value: "lastName" },
        { text: "First Name", sortable: false, value: "firstName" },
        { text: "Birthdate", sortable: true, value: "birthdate" },
        { text: "Gender", sortable: true, value: "gender" },
        { text: "Address", sortable: true, value: "address" },
        { text: "Email", sortable: false, value: "email" },
        { text: "Phone Number", sortable: false, value: "phoneNumber" },
        { text: "Actions", value: "actions", sortable: false },
      ],
    };
  },
  methods: {
    retrievePatients() {
      PatientService.getAll()
        .then((response) => {
          this.patients = response.data.map(this.getDisplayPatient);
          console.log(response.data);
        })
        .catch((e) => {
          console.log(e);
        });
    },

    refreshList() {
      this.retrievePatients();
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

    searchLastName() {
      PatientService.findByLastName(this.lastName)
        .then((response) => {
          this.patients = response.data.map(this.getDisplayPatient);
          console.log(response.data);
        })
        .catch((e) => {
          console.log(e);
        });
    },

    getPatient(id) {
      this.$router.push({ name: "patientProfil", params: { id: id } });
    },

    // getPatientHistory(id) {
    //     PatientHistoryService.getPatientHistory(this.$router.push({ name: "patientProfil", params: { patientId: id } }))
    //       .then((response) => {
    //         this.patientHistory = response.data.map(this.getDisplayPatientHistory);
    //         console.log(response.data);
    //       })
    //       .catch((e) => {
    //         console.log(e);
    //       });
    //   },
      
    editPatient(id) {
      this.$router.push({ name: "patient-details", params: { id: id } });
    },

    deletePatient(id) {
      PatientService.delete(id)
        .then(() => {
          this.refreshList();
        })
        .catch((e) => {
          console.log(e);
        });
    },

    getDisplayPatient(patient) {
      return {
        id: patient.id,
        lastName: patient.lastName.length > 30 ? patient.lastName.substr(0, 30) + "..." : patient.lastName,
        firstName: patient.firstName.length > 30 ? patient.firstName.substr(0, 30) + "..." : patient.firstName,
        birthdate: patient.birthdate.length > 30 ? patient.birthdate.substr(0, 30) + "..." : patient.birthdate,
        gender: patient.gender.length > 30 ? patient.gender.substr(0, 30) + "..." : patient.gender,
        address: patient.address.length > 30 ? patient.address.substr(0, 30) + "..." : patient.address,
        email: patient.email.length > 30 ? patient.email.substr(0, 30) + "..." : patient.email,
        phoneNumber: patient.phoneNumber.length > 30 ? patient.phoneNumber.substr(0, 30) + "..." : patient.phoneNumber,
      };
    },
  },
  mounted() {
    this.retrievePatients();
  },
};
</script>

<style>
.list {
  max-width: 1240px;
}
</style>


