// import http from "../http-common";
import axios from "axios";

const PATIENTHISTORY_BASE_URL = "http://localhost:8082";

class PatientHistoryService {
  getPatientHistories() {
    return axios.get(PATIENTHISTORY_BASE_URL + "/patientHistory/all");
  }

  getPatientHistory(patientId) {
    return axios.get(PATIENTHISTORY_BASE_URL + `/patientHistory/patient/${patientId}`);
  }

  getPatientHistoryById(id) {
    return axios.get(PATIENTHISTORY_BASE_URL + `/patientHistory/${id}`);
  }

  createPatientHistory(data) {
    return axios.post(PATIENTHISTORY_BASE_URL + `/patientHistory/patient/${patientId}/add`, data);
  }

  updatePatientHistory(id, data) {
    return axios.put(PATIENTHISTORY_BASE_URL + `/patientHistory/${id}`, data);
  }

  deletePatientHistory(id) {
    return axios.delete(PATIENTHISTORY_BASE_URL + `/patientHistory/${id}`);
  }

  findByLastName(lastName) {
    return axios.get(`/patient?title=${lastName}`);
  }
}

export default new PatientHistoryService();