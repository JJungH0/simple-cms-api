-- TODO data
-- example
-- insert into members (name, last_modified_date) values('bob', now());

insert into members (name, password, role)
values ('alice', 'qwer1234@', 'USER'),
       ('bob','asdf1234@','USER');

insert into contents (member_id,title, description, created_by, view_count)
values (1,'제목 예시1', '내용 예시1','alice',0),
       (2,'제목 예시2', '내용 예시2','bob',0);


