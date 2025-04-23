interface Environment {
  apiUrl: string;
}

const environment: Environment = {
  apiUrl: import.meta.env.VITE_ADS_APP_WEB_URL ?? "",
};

export default environment;
