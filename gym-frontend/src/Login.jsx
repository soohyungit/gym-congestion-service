import { useState } from 'react';
import api from './api/axios';

// props로 onLoginSuccess 함수를 받아옴
function Login({ onLoginSuccess }) {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const response = await api.post('/api/users/login', { email, password });
            const token = response.data; // 서버에서 준 JWT 토큰

            localStorage.setItem('token', token); // 브라우저에 저장

            alert('로그인 성공!');

            // ⭐ 핵심: 로그인 성공을 부모 컴포넌트에 알림
            onLoginSuccess();
        } catch (error) {
            alert('로그인 실패: ' + (error.response?.data || error.message));
        }
    };

    return (
        <div style={{ padding: '20px', border: '1px solid #ccc', marginTop: '20px' }}>
            <h2>헬스장 서비스 로그인</h2>
            <form onSubmit={handleLogin}>
                <input
                    type="email"
                    placeholder="이메일"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    style={{ marginBottom: '10px', padding: '5px' }}
                /><br/>
                <input
                    type="password"
                    placeholder="비밀번호"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    style={{ marginBottom: '10px', padding: '5px' }}
                /><br/>
                <button type="submit" style={{ padding: '5px 15px', cursor: 'pointer' }}>로그인</button>
            </form>
        </div>
    );
}

export default Login;