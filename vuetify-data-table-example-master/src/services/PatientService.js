// import http from "../http-common";
import axios from "axios";

const PATIENT_BASE_URL = "http://localhost:8081";

class PatientService {
  getAll() {
    return axios.get(PATIENT_BASE_URL + "/patient/all");
  }

  get(id) {
    return axios.get(PATIENT_BASE_URL + `/patient/${id}`);
  }

  create(data) {
    return axios.post(PATIENT_BASE_URL + "/patient/add", data);
  }

  update(id, data) {
    return axios.put(PATIENT_BASE_URL + `/patient/${id}`, data);
  }

  delete(id) {
    return axios.delete(PATIENT_BASE_URL + `/patient/${id}`);
  }

  deleteAll() {
    return axios.delete(PATIENT_BASE_URL + `/patient/all`);
  }

  findByLastName(lastName) {
    return axios.get(PATIENT_BASE_URL + `/patient?title=${lastName}`);
  }
}

export default new PatientService();