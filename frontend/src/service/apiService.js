import axios from 'axios'

export const getAllUser = () =>
    axios.get("http://localhost:8080/api/tomze/user")
        .then(response => response.data)
        .then(response => console.log(response))

export const createUser = (credentials) =>
    axios.post("http://localhost:8080/api/tomze", credentials)
        .then(response => console.log(response))


