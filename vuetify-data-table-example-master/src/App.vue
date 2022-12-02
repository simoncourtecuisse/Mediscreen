<template>
  <v-app>
    <v-app-bar app dark>
      <div class="d-flex align-center mr-2">
        Mediscreen App
      </div>

      <v-btn to="/patient/all" text>
        Patients
      </v-btn>
      <v-btn to="/patientHistory/all" text>
        Patient Histories
      </v-btn>

      <v-btn to="/add" text>
        Add
      </v-btn>
    </v-app-bar>

    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script>
export default {
  name: "app",
};
</script>
