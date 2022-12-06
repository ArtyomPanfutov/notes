import AuthService from './AuthService';
import axios from 'axios';

class Axios {

    constructor() {
        this.instance = axios.create();
        this.instance.interceptors.request.use(config => {
            const accessToken = AuthService.getAccessToken();
            config.headers.post['Authorization'] = `Bearer ${accessToken}`;
            return config;
          });
    }
}

export default new Axios();