import axios from "axios";
import environment from "../enviroments";

const apiClient = axios.create({
  baseURL: environment.apiUrl,
  headers: {
    "Content-Type": "application/json",
    Accept: "application/json",
  },
});

console.log({
  baseURL: environment.apiUrl,
  headers: {
    "Content-Type": "application/json",
    Accept: "application/json",
  },
});

export default apiClient;
