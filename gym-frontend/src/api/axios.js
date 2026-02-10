import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8080', // 우리 스프링 부트 서버 주소
});

// 토큰이 있다면 모든 요청 헤더에 자동으로 실어주는 설정 (나중에 쓸 거야!)
api.interceptors.request.use((config) => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

export default api;