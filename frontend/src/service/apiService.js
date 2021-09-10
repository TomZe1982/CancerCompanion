import axios from 'axios'

const headers = token => ({
    headers: {
        Authorization: `Bearer ${token}`,
    },
})


//Routes with token needed

export const getAllUser = (token) =>
    axios.get("/api/tomze/user", headers(token))
        .then(response => response.data)
        .then(response => console.log(response))

export const getLoggedInUser =(token) =>
    axios.get("api/auth/me", headers(token))
        .then(response => response.data)
//Route without token needed

export const getToken = credentials =>
    axios
        .post('/auth/access_token', credentials)
        .then(response => response.data)
        .then(dto => dto.token)


export const createUser = credentials =>
    axios.post("/api/tomze/register", credentials)
        .then(response => console.log(response))




