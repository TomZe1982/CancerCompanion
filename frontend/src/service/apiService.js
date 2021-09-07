import axios from 'axios'

export const getUser = (credentials) =>
    axios.get("api/tomze/{userName}", credentials)
        .then(response => response.data)