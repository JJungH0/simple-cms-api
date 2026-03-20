-- TODO data
-- example
-- insert into members (name, last_modified_date) values('bob', now());

insert into members (name, password, role)
values ('alice', 'qwer1234@', 'USER'),
       ('bob','asdf1234@','USER');

insert into contents (member_id,title, description, created_by, view_count)
values (1,'제목 예시1', '내용 예시1','alice',0),
       (2,'제목 예시2', '내용 예시2', 'bob',0),
       (1,'제목 예시3', '내용 예시3', 'alice',0),
       (2,'제목 예시4', '내용 예시4', 'bob',0),
       (1,'제목 예시5', '내용 예시5', 'alice',0),
       (2,'제목 예시6', '내용 예시6', 'bob',0),
       (1,'제목 예시7', '내용 예시7', 'alice',0),
       (2,'제목 예시8', '내용 예시8', 'bob',0),
       (1,'제목 예시9', '내용 예시9', 'alice',0),
       (2,'제목 예시10', '내용 예시10','bob',0);


