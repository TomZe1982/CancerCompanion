import axios from 'axios'

export const createInbox = (email) =>
    axios.post("https://api.mailslurp.com/createInbox?apiKey=65a0ad567085d3c08b04d159789ee0b8c185df4d0e7aaa42b9fb7ef514a8a656", email)
        .then((response) => response.data)


export const sendEmail = (email) =>
    axios.post("https://api.mailslurp.com/sendEmail?apiKey=65a0ad567085d3c08b04d159789ee0b8c185df4d0e7aaa42b9fb7ef514a8a656", email)
        .then((response) => response.data)

const headers = token => ({
    headers: {
        Authorization: `Bearer ${token}`,
    },
})

//Route without token needed

export const getToken = credentials =>
    axios
        .post('/auth/access_token', credentials)
        .then(response => response.data)
        .then(dto => dto.token)


export const createUser = credentials =>
    axios.post("/api/tomze/register", credentials)
        .then(response => response.data)



//Routes with token needed

export const getAllUser = (token) =>
    axios.get("/api/tomze/user", headers(token))
        .then(response => response.data)

export const getUser =(userName, token) =>
    axios.get("api/tomze/user/"+userName, headers(token))
        .then(response => response.data)

export const updateUser = (credentials, token) =>
    axios.put("api/tomze/user/update/"+credentials.userName, credentials, headers(token))

export const getVideoList = (token) =>
    axios.get("api/tomze/videolist", headers(token))
        .then(response => response.data)

export const getNewVideo = (videoId, token) =>
    axios.get("api/tomze/videos/"+videoId, headers(token))

export const deleteVideo = (videoId, token) =>
    axios.delete("api/tomze/videos/"+videoId, headers(token))

export const deleteUser = (userName, token) =>
    axios.delete("api/tomze/user/delete/"+userName, headers(token) )

export const resetPassword = (userName, token) =>
    axios.put("/api/tomze/user/resetpassword/"+userName, userName, headers(token) )
        .then(response => response.data)






