# 🏋️ 헬스장 실시간 혼잡도 관리 시스템

스프링 부트와 순수 자바스크립트를 활용한 실시간 헬스장 인원 관리 및 혼잡도 시각화 서비스입니다.

## 🛠 Tech Stack

- **Backend**: Java 17, Spring Boot, Spring Data JPA
- **Database**: H2 (In-memory)
- **Frontend**: HTML5, CSS3, JavaScript (Fetch API)

## ✨ 주요 기능

- **실시간 혼잡도 계산**: 현재 인원/최대 정원을 계산하여 '여유', '보통', '혼잡' 상태 시각화
- **입/퇴장 시스템**: 유저별 입퇴장 기록(VisitLog) 관리 및 실시간 인원 반영
- **예외 처리**:
  - 정원 초과 시 입장 제한 (Custom Exception)
  - 입장 기록 없는 사용자 퇴장 차단 및 에러 메시지 반환

## 🔍 트러블슈팅 경험

- **Off-by-one Error 해결**: 인원 증가와 정원 확인 로직의 순서를 조정하여 경계값(정원 마지막 인원) 입실 오류 해결
- **전역 예외 처리**: `GlobalExceptionHandler`를 통해 500 에러 대신 의미 있는 비즈니스 에러 메시지 응답 구현

### 🚀 트러블슈팅: 동시성 제어와 Lock Timeout

- **문제**: 비관적 락(Pessimistic Lock) 적용 후 테스트를 위해 `Thread.sleep()`으로 대기 시간을 주었을 때, 후행 요청에서 500 에러(Internal Server Error) 발생.
- **원인 분석**:
  - 첫 번째 요청이 자물쇠(Lock)를 쥐고 있는 상태에서 두 번째 요청이 대기함.
  - DB의 설정된 **Lock Wait Timeout** 시간을 초과하여 대기하던 두 번째 요청이 강제로 종료됨 (PessimisticLockException).
- **해결 및 배운 점**:
  - 비관적 락이 실제로 데이터베이스 수준에서 다른 트랜잭션을 차단하고 있음을 확인.
  - 실제 서비스 환경에서는 자원을 점유하는 로직을 최소화하고, 적절한 타임아웃 설정이 필수적임을 이해함.
