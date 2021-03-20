-- ***** INSERTS ***** --
INSERT INTO users (name, email) values
	('name0', 'example0@email'),
	('name1', 'example1@email'),
	('name2', 'example2@email'),
	('name3', 'example3@email'),
	('name4', 'example4@email');

select * from users;


INSERT INTO movies (title, releaseYear) values
	('movie01', '2000'), -- 1
	('movie01', '2001'), -- 2
	('movie02', '2002'), -- 3
	('movie03', '2003'), -- 4
	('movie04', '2001'), -- 5
	('movie05', '2002'), -- 6
	('movie06', '2003'), -- 7

	('movie07', '2000'), -- 8
	('movie08', '2001'), -- 9
	('movie09', '2002'), -- 10
	('movie10', '2003'), -- 11
	('movie11', '2001'), -- 12
	('movie12', '2002'), -- 13
	('movie13', '2003'), -- 14
	('movie14', '2002'), -- 15
	('movie15', '2003'); -- 16

select * from movies;

-- without user
INSERT INTO reviews (mid, summary, review, rating) values
	(1, 'summ00', 'review 00', 5),--1
	(1, 'summ01', 'review 01', 1),--2
	(1, 'summ02', 'review 02', 2),--3
	(1, 'summ03', 'review 03', 3),--4
	(1, 'summ04', 'review 04', 3),--5
	(1, 'summ05', 'review 05', 5),--6
	(1, 'summ06', 'review 06', 5);--7

UPDATE movies SET rating1 = rating1 + 1 where id = 1;
UPDATE movies SET rating2 = rating2 + 1 where id = 1;
UPDATE movies SET rating3 = rating3 + 2 where id = 1;
UPDATE movies SET rating5 = rating5 + 3 where id = 1;

INSERT INTO reviews (mid, summary, review, rating) values
	(2, 'summ07', 'review 07', 1),--8
	(2, 'summ08', 'review 08', 2),--9
	(2, 'summ09', 'review 09', 3),--10
	(2, 'summ10', 'review 10', 3);--11

UPDATE movies SET rating1 = rating1 + 1 where id = 2;
UPDATE movies SET rating2 = rating2 + 1 where id = 2;
UPDATE movies SET rating3 = rating3 + 2 where id = 2;

INSERT INTO reviews (mid, summary, review, rating) values
	(3, 'summ11', 'review 11', 4),--12
	(3, 'summ12', 'review 12', 5),--13
	(3, 'summ13', 'review 13', 3),--14
	(3, 'summ14', 'review 14', 3);--15

UPDATE movies SET rating3 = rating3 + 2 where id = 3;
UPDATE movies SET rating4 = rating4 + 1 where id = 3;
UPDATE movies SET rating5 = rating5 + 1 where id = 3;


-- with user
INSERT INTO reviews (mid, uid, summary, review, rating) values
	(1, 1, 'summ13', 'review 13', 3),--16
	(2, 1, 'summ14', 'review 14', 2),--17
	(3, 1, 'summ15', 'review 15', 3);--18

UPDATE movies SET rating3 = rating3 + 1 where id = 1;
UPDATE movies SET rating2 = rating2 + 1 where id = 2;
UPDATE movies SET rating3 = rating3 + 1 where id = 3;

INSERT INTO reviews (mid, uid, summary, review, rating) values
	(3, 2, 'summ16', 'review 16',5),--19
	(3, 2, 'summ17', 'review 17',5),--20
	(1, 2, 'summ18', 'review 18',5);--21

UPDATE movies SET rating5 = rating5 + 2 where id = 3;
UPDATE movies SET rating5 = rating5 + 1 where id = 1;
