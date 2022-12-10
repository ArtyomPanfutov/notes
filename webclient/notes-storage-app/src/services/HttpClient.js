import axios from "axios";

const httpClient = axios.create();

export const addAccessTokenInterceptor = (getAccessTokenSilently) => {
  httpClient.interceptors.request.use(async (config) => {
    const token = await getAccessTokenSilently();
    config.headers.Authorization = `Bearer ${token}`;
    return config;
  });
};

export default httpClient;