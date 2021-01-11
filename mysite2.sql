CREATE TABLE users (no NUMBER,
                    id VARCHAR2(20) UNIQUE NOT NULL,
                    password VARCHAR2(20) NOT NULL,
                    name VARCHAR2(20),
                    gender VARCHAR2(10),
                    PRIMARY KEY (no));

DELETE FROM users;

DROP SEQUENCE seq_no;

CREATE SEQUENCE seq_no INCREMENT by 1 START WITH 1;

SELECT  no as "회원식별번호",
        id as "아이디",
        password as "패스워드",
        name as "이름",
        gender as "성별"
FROM    users;

INSERT INTO users VALUES(SEQ_NO.nextval, 'whjkh123', '1234', '조경환', '남성');

DELETE FROM guestbook;

DROP SEQUENCE seq_no;

CREATE SEQUENCE seq_no INCREMENT by 1 START WITH 1;

SELECT  no as "식별번호",
        name as "이름",
        password as "비밀번호",
        content as "본문",
        TO_CHAR(reg_date, 'yyyy-mm-dd hh:mi:ss') as "date"
FROM    guestbook
ORDER BY no asc;

COMMIT;

ROLLBACK;