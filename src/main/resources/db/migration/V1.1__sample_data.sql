INSERT INTO student (email, username, first_name, last_name, created)
VALUES ('bradyzp@gmail.com', 'bradyzp', 'Zac', 'Brady', now());

INSERT INTO instructor (email, first_name, last_name, title, phone, office_location)
VALUES ('emaldon3@msudenver.edu', 'Edgar', 'Maldonado', 'Prof.', '303 615-0166', 'Admin 590E');

WITH maldonado AS (
  SELECT id
  FROM instructor
  WHERE email = 'emaldon3@msudenver.edu'
  LIMIT 1
)
INSERT
INTO office_hours_block (instructor_id, start_time, end_time, day_of_week)
VALUES ((SELECT id FROM maldonado), time '13:00' AT TIME ZONE 'MST', time '16:00' AT TIME ZONE 'MST', 'MONDAY');

INSERT INTO semester (year, start_date, end_date)
VALUES (2019, '2019-01-23', '2019-05-11');

WITH bradyzp AS (
  SELECT id
  FROM student
  WHERE email = 'bradyzp@gmail.com'
  LIMIT 1
)
INSERT
INTO course (semester_id, student_id, instructor_id, title, code, crn, building, room, start_time, end_time, is_current)
VALUES (1, (SELECT id FROM bradyzp), 1, 'Systems Analysis & Design II', 'CIS4050', 51231, 'Admin Building', '250A',
        time '14:00' AT TIME ZONE 'MST', time '15:15' AT TIME ZONE 'MST', true);