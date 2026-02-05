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
