import { useState } from 'react';
import Login from './Login';
import GymList from './GymList';

function App() {
    // 현재 로컬 스토리지에 토큰이 있는지 확인하여 초기 상태 설정
    const [isLoggedIn, setIsLoggedIn] = useState(!!localStorage.getItem('token'));

    // 로그인 성공 시 실행될 함수
    const handleLoginSuccess = () => {
        setIsLoggedIn(true); // 상태를 true로 변경 -> 리액트가 화면을 즉시 다시 그림
    };

    // 로그아웃 함수 (참고용)
    const handleLogout = () => {
        localStorage.removeItem('token');
        setIsLoggedIn(false); // 상태를 false로 변경 -> 로그인 화면으로 돌아감
    };

    return (
        <div style={{ maxWidth: '600px', margin: '0 auto', textAlign: 'center', fontFamily: 'sans-serif' }}>
            <header style={{ padding: '20px', borderBottom: '1px solid #eee' }}>
                <h1>Gym Congestion Service</h1>
                {isLoggedIn && (
                    <button onClick={handleLogout} style={{ marginTop: '10px' }}>로그아웃</button>
                )}
            </header>

            <main>
                {/* 조건부 렌더링: 로그인 여부에 따라 다른 컴포넌트를 보여줌 */}
                {!isLoggedIn ? (
                    <Login onLoginSuccess={handleLoginSuccess} />
                ) : (
                    <GymList />
                )}
            </main>
        </div>
    );
}

export default App;