import axios from "axios";

const api = axios.create({
    // prd
    // baseURL: 'http://digitalbooking-api.ctdprojetointegrador.com/'
    // dev
    baseURL: 'http://localhost:8080'
})


api.interceptors.request.use(async config => {
    const token = localStorage.getItem("@token")
    const img = localStorage.getItem("upImagem")
    if(token && !img){
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

export default api;