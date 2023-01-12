// import http from "../http-common";
import axios from "axios";

const REPORT_BASE_URL = "http://localhost:8083";

class ReportService {
  getDiabetesAssessmentForAPatient(patientId) {
    return axios.post(REPORT_BASE_URL + `/assess/${patientId}`);
  }

  // findByLastName(lastName) {
  //   return axios.post(`/assess/${lastName}`);
  // }
}

export default new ReportService();