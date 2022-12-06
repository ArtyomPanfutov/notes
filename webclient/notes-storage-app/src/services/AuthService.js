import { useAuth0 } from "@auth0/auth0-react";

class AuthService {

    getAccessToken() {
        const { getAccessTokenSilently } = useAuth0();
        return getAccessTokenSilently();
    }
}

export default new AuthService();