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

--------------------------------------------------------------------------------------------------

CREATE TABLE board (no number,
                    title varchar2(500) not null,
                    content varchar2(4000),
                    hit number default 0,
                    reg_date date not null,
                    user_no number,
                    primary key(no),
                    constraint board_fk foreign key (user_no) references users(no));

DELETE FROM board;

DROP SEQUENCE seq_board_no;

CREATE SEQUENCE seq_board_no INCREMENT by 1 START WITH 1;

SELECT  no as "게시물식별번호",
        title as "제목",
        content as "내용",
        hit as "조회수",
        reg_date as "등록일",
        user_no as "회원식별번호"
FROM    board;

INSERT INTO board VALUES(seq_board_no.NEXTVAL, 'test', 'test', default, sysdate, 1);

------------------------------------게시판 목록--------------------------------------------------------------

SELECT  b.no as "게시물 식별번호",
        b.title as "제목",
        u.name as "글쓴이",
        b.hit as "조회수",
        TO_CHAR(b.reg_date, 'yyyy-mm-dd hh:mi:ss') as "작성일"
FROM    board b, users u
WHERE   b.user_no = u.no
        and b.no = 1;

-----------------------------------게시글 작성---------------------------------------------------------------

SELECT  b.no,
        u.name as "작성자",
        b.hit as "조회수",
        TO_CHAR(b.reg_date, 'yyyy-mm-dd hh:mi:ss') as "작성일",
        b.title as "제목",
        b.content
FROM    board b, users u
WHERE   b.user_no = u.no;

UPDATE board SET title = '에러 발생2', content = 'test2' WHERE no = 2;
--------------------------------------------------------------------------------------------------

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

--------------------------------------------------------------------------------------------------

COMMIT;

ROLLBACK;
