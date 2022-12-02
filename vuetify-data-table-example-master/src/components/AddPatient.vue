<template>
  <div class="submit-form mt-3 mx-auto">
    <p class="headline">Add Patient</p>

    <div v-if="!submitted">
      <v-form ref="form" lazy-validation>
        <v-text-field v-model="patient.lastName" :rules="[(v) => !!v || 'Last Name is required']" label="LastName"
          required></v-text-field>
        <v-text-field v-model="patient.firstName" :rules="[(v) => !!v || 'First Name is required']" label="FirstName"
          required></v-text-field>
        <!-- <v-text-field v-model="patient.birthdate" :rules="[(v) => !!v || 'Birhtdate is required']" label="Birhtdate"
          required></v-text-field> -->
          <v-menu
            
            v-model="fromDateMenu"
            :close-on-content-click="false"
            :nudge-right="40"
            transition="scale-transition"
            offset-y 
            max-width="290px"
            min-width="290px"
          >
            <template v-slot:activator="{ on }">
              <v-text-field
                label="Brithdate"
                readonly
                :value="patient.birthdate"
                :rules="[(v) => !!v || 'Birhtdate is required']"
                v-on="on"
              ></v-text-field>
            </template>
            <v-date-picker
              locale="en-in"
              v-model="patient.birthdate"
              no-title
              @input="fromDateMenu = false"
              :min="minDate"
            ></v-date-picker>
          </v-menu>
        <v-select v-model="patient.gender" :rules="[(v) => !!v || 'Gender is required']" label="Gender" :items="['MALE', 'FEMALE']" required>
        </v-select>
        <v-text-field v-model="patient.address" :rules="[(v) => !!v || 'Address is required']" label="Address" required>
        </v-text-field>
        <v-text-field v-model="patient.email" :rules="[(v) => !!v || 'Email is required']" label="Email" required>
        </v-text-field>
        <v-text-field v-model="patient.phoneNumber" :rules="[(v) => !!v || 'Phone Number is required']"
          label="PhoneNumber" required></v-text-field>
      </v-form>

      <v-btn color="primary" class="mt-3" @click="savePatient">Submit</v-btn>
    </div>

    <div v-else>
      <v-card class="mx-auto">
        <v-card-title>
          Submitted successfully!
        </v-card-title>

        <v-card-subtitle>
          Click the button to add new Patient.
        </v-card-subtitle>

        <v-card-actions>
          <v-btn color="success" @click="newPatient">Add</v-btn>
        </v-card-actions>
      </v-card>
    </div>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        fromDateMenu: false,
        fromDateVal: null,  

        minDate: "2019/07/04",
        maxDate: "2019/08/30",
      };
    },
    computed: {
      fromDateDisp() {
        return this.fromDateVal;
        
        // format date, apply validations, etc. Example below.
        // return this.fromDateVal ? this.formatDate(this.fromDateVal) : "";
      },
    },
  };
</script>
<script>
import PatientService from "../services/PatientService";

export default {
  name: "add-patient",
  data() {
    return {
      patient: {
        id: null,
        firstName: "",
        lastName: "",
        birthdate: "",
        gender: null,
        address: "",
        email: "",
        phoneNumber: ""
      },
      submitted: false,
    };
  },
  methods: {
    savePatient() {
      var data = {
        firstName: this.patient.firstName,
        lastName: this.patient.lastName,
        birthdate: this.patient.birthdate,
        gender: this.patient.gender,
        address: this.patient.address,
        email: this.patient.email,
        phoneNumber: this.patient.phoneNumber
      };
  

      PatientService.create(data)
        .then((response) => {
          this.patient.id = response.data.id;
          console.log(response.data);
          this.submitted = true;
        })
        .catch((e) => {
          console.log(e);
        });
    },

    newPatient() {
      this.submitted = false;
      this.patient = {};
    },
  },
};
</script>

<style>
.submit-form {
  max-width: 300px;
}
</style>
