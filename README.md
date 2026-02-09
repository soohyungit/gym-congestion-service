🏋️ 헬스장 실시간 혼잡도 관리 시스템
스프링 부트와 MySQL을 활용한 실시간 헬스장 인원 관리 및 JWT 기반 인증 서비스입니다.

🛠 Tech Stack
Backend: Java 17, Spring Boot, Spring Data JPA, Spring Security

Authentication: JWT (JSON Web Token), BCrypt Password Hashing

Database: MySQL 8.0 (Docker 기반)

Frontend: HTML5, CSS3, JavaScript (Fetch API) → React 전환 예정

✨ 주요 기능

1. 실시간 혼잡도 및 인원 관리
   실시간 혼잡도 계산: 현재 인원/최대 정원을 계산하여 '여유', '보통', '혼잡' 상태 시각화

입/퇴장 시스템: 유저별 입퇴장 기록(VisitLog) 관리 및 실시간 인원 반영

2. 보안 및 사용자 인증 (Updated)
   BCrypt 암호화: 사용자 비밀번호를 단방향 해시 함수로 암호화하여 DB 보안 강화

JWT 기반 Stateless 인증: 서버가 사용자의 상태를 기억하지 않고 토큰 검증만으로 권한을 확인하여 서버 확장성 확보

JwtFilter 검문소: 모든 API 요청 헤더의 Authorization 토큰을 검사하여 허가된 사용자만 접근 가능하도록 제어
🔍 트러블슈팅 경험
🚀 1. 비즈니스 로직 및 예외 처리
Off-by-one Error 해결: 인원 증가와 정원 확인 로직의 순서를 조정하여 경계값(정원 마지막 인원) 입실 오류 해결

전역 예외 처리: GlobalExceptionHandler를 통해 500 에러 대신 비즈니스 의미가 담긴 커스텀 에러 메시지 응답 구현

🚀 2. 동시성 제어와 Lock Timeout
문제: 비관적 락(Pessimistic Lock) 적용 후 테스트 중 후행 요청에서 PessimisticLockException 발생

원인: 선행 트랜잭션이 자원을 점유한 상태에서 후행 요청이 DB의 Lock Wait Timeout을 초과함

배운 점: 실제 서비스 환경에서 자원 점유 로직의 최소화와 적절한 타임아웃 설정의 중요성을 이해함

🚀 3. JWT 보안 표준 준수 (WeakKeyException)
문제: 로그인 API 호출 시 WeakKeyException 발생

원인: HS256 알고리즘 사용 시 보안 표준(RFC 7518)에 따른 최소 256비트(32바이트) 이상의 키 길이가 충족되지 않음

해결: 비밀키를 32자 이상의 복잡한 문자열로 변경하고, 바이트 배열 기반의 안전한 SecretKey 객체를 생성하여 해결

🚀 4. JWT 인코딩 이슈 해결 (DecodingException)
문제: 비밀키 내 특수문자(-)로 인해 DecodingException 발생

원인: 라이브러리가 문자열을 해석하는 과정에서 Base64 디코딩 규칙에 어긋나는 문자를 인지함

해결: Keys.hmacShaKeyFor()와 StandardCharsets.UTF_8을 활용하여 문자열을 바이트 배열로 직접 전달하는 표준 방식으로 수정
