import { useEffect, useState } from 'react';
import api from './api/axios';
import { jwtDecode } from 'jwt-decode';

function GymList() {
    const [gyms, setGyms] = useState([]);

    // 1. 초기 데이터 및 새로고침 함수
    const fetchGyms = async () => {
        try {
            const response = await api.get('/api/gyms');
            setGyms(response.data);
        } catch (error) {
            console.error('데이터 로딩 실패:', error);
        }
    };

    useEffect(() => {
        fetchGyms();
    }, []);

    // 💡 혼잡도 상태와 색상을 결정하는 함수 (Gym.java 로직 동기화)
    const getStatusInfo = (current, max) => {
        if (!max || max === 0) return { text: '정보 없음', color: '#bfbfbf' };

        const ratio = (current / max) * 100;

        if (ratio <= 30) return { text: '여유', color: '#52c41a' }; // 30% 이하: 초록
        if (ratio <= 70) return { text: '보통', color: '#faad14' }; // 70% 이하: 주황
        return { text: '혼잡', color: '#ff4d4f' }; // 그 외: 빨강
    };

    // 2. 입장 처리
    const handleEnter = async (gymId) => {
        try {
            const token = localStorage.getItem('token');
            const decoded = jwtDecode(token);
            const currentUserId = decoded.id;

            await api.post('/api/gyms/check-in', {
                userId: currentUserId,
                gymId: gymId
            });
            alert("입장이 완료되었습니다!");
            fetchGyms();
        } catch (error) {
            alert("실패: " + (error.response?.data || "오류 발생"));
        }
    };

    // 3. 퇴장 처리
    const handleExit = async (gymId) => {
        try {
            const token = localStorage.getItem('token');
            const decoded = jwtDecode(token);
            const currentUserId = decoded.id;

            await api.post('/api/gyms/check-out', {
                userId: currentUserId,
                gymId: gymId
            });

            alert("퇴장이 완료되었습니다. 안녕히 가세요!");
            fetchGyms();
        } catch (error) {
            alert("퇴장 실패: " + (error.response?.data || "오류 발생"));
        }
    };

    return (
        <div style={{ padding: '20px' }}>
            <h2 style={{ marginBottom: '20px' }}>🏃 실시간 헬스장 혼잡도</h2>
            <div style={{ display: 'grid', gap: '15px' }}>
                {gyms.map((gym) => {
                    // 루프 내부에서 현재 헬스장의 상태 정보를 계산해
                    const status = getStatusInfo(gym.currentCount, gym.maxCapacity);

                    return (
                        <div key={gym.id} style={{
                            padding: '20px',
                            border: '1px solid #eee',
                            borderRadius: '12px',
                            boxShadow: '0 2px 8px rgba(0,0,0,0.05)',
                            textAlign: 'left',
                            backgroundColor: 'white'
                        }}>
                            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                                <h3 style={{ margin: 0, color: '#333' }}>{gym.name}</h3>
                                {/* 3단계 상태 배지 적용 */}
                                <span style={{
                                    padding: '4px 12px',
                                    borderRadius: '20px',
                                    fontSize: '0.85rem',
                                    fontWeight: 'bold',
                                    color: 'white',
                                    backgroundColor: status.color
                                }}>
                                    {status.text}
                                </span>
                            </div>

                            <p style={{ margin: '15px 0', color: '#666' }}>
                                현재 인원: <strong style={{ color: '#000' }}>{gym.currentCount}</strong> / {gym.maxCapacity}명
                            </p>

                            <div style={{ display: 'flex', gap: '10px' }}>
                                <button
                                    onClick={() => handleEnter(gym.id)}
                                    style={{ flex: 1, padding: '12px', backgroundColor: '#e6f7ff', border: '1px solid #91d5ff', borderRadius: '8px', cursor: 'pointer', fontWeight: 'bold' }}
                                >
                                    📥 입장하기
                                </button>
                                <button
                                    onClick={() => handleExit(gym.id)}
                                    style={{ flex: 1, padding: '12px', backgroundColor: '#fff1f0', border: '1px solid #ffa39e', borderRadius: '8px', cursor: 'pointer', fontWeight: 'bold' }}
                                >
                                    📤 퇴장하기
                                </button>
                            </div>
                        </div>
                    );
                })}
            </div>
        </div>
    );
}

export default GymList;