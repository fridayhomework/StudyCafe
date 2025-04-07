CREATE TABLE Member(-- 사용자 테이블(학생 정보)
	id BIGINT PRIMARY KEY, -- 학생 ID 저장
	password VARCHAR(100) UNIQUE NOT NULL, --고유한 학생 비밀번호 저장(NULL 비허용)
	userName VARCHAR(100) NOT NULL, -- 학생 이름 저장
	phoneNumber VARCHAR(11) NOT NULL, -- 학생 전화번호 입력
	created_at TIMESTAMP DEFAULT NOW() -- 행 생성 시간 자동 입력
);

CREATE TABLE seats( -- 좌석 테이블
	seatNo INT UNIQUE NOT NULL, -- 좌석번호 저장
	seatName VARCHAR(100) NOT NULL, -- 좌석에 앉아있는 학생 이름
	seatStatus BOOLEAN DEFAULT FALSE, -- 사용 여부 확인 기본값 false(사용중이지 않음)
	isWating BOOLEAN DEFAULT FALSE, -- 좌석 사용 가능 여부(관리자 관할)
	current_user_id BIGINT REFERENCES Member(id) ON DELETE SET NULL -- 학생 ID 참조 외래 키. id를 참조하여 학생이 삭제되면 NULL로 설정
);

CREATE TABLE action_logs( -- 행동 분석 테이블
	action_id SERIAL PRIMARY KEY, -- 행동 고유 식별자 제공
	id BIGINT NOT NULL, 
	action_data JSONB NOT NULL, -- JSON 형식으로 여러 행동 저장
	created_at TIMESTAMP DEFAULT NOW() --생성 시간 반환
	duration INTERVAL DEFAULT '00:00:00', --행동 지속 시간
	action_type VARCHAR(100); --행동 유형 저장
	-- id VARCHAR(100); -> 이건 뭐지..?
); --user_id 수정 필요

CREATE TABLE action_total( -- 총 행동별 시간
	total_id SERIAL PRIMARY KEY, -- 행동별 시간 ID
	id BIGINT NOT NULL REFERENCES Member(id) ON DELETE CASCADE,
	action_type VARCHAR(100) NOT NULL, --행동 유형
	total_time INTERVAL NOT NULL DEFAULT '00:00:00', --총 행동 시간
	total_date DATE NOT NULL, -- 기준 날짜
	created_at TIMESTAMP DEFAULT NOW() -- 생성 시간
);

CREATE TABLE goal( -- 학습 목표 테이블
	goal_id SERIAL PRIMARY KEY, -- 목표 고유 식별자 제공
	id BIGINT REFERENCES Member(id) ON DELETE CASCADE, -- users에서 사용자가 삭제되면 goal에서도 삭제
	goal INTERVAL NOT NULL, -- 목표 시간
	created_at TIMESTAMP DEFAULT NOW() --생성 시간 반환
	
);

CREATE TABLE goal_success( -- 목표 성공 여부 테이블
	success_id SERIAL PRIMARY KEY, -- 목표 고유 식별자 제공
	id BIGINT REFERENCES Member(id) ON DELETE CASCADE,
	date DATE NOT NULL, -- 날짜
	study INTERVAL NOT NULL, -- 실제 공부 시간
	goal INTERVAL NOT NULL, -- 목표 공부 시간
	created_at TIMESTAMP DEFAULT NOW() --생성 시간
);

CREATE TABLE image( -- 학생 이미지 저장 테이블
	image_id SERIAL PRIMARY KEY, --이미지 고유 식별자 제공
	id BIGINT REFERENCES Member(id) ON DELETE CASCADE, -- member에서 사용자가 삭제되면 image에서도 삭제
	image_data BYTEA NOT NULL, --이미지파일을 바이너리코드 파일로 변환
	created_at TIMESTAMP DEFAULT NOW() --생성 시간
);