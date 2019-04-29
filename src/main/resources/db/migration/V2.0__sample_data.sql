INSERT INTO student (id, email, username, first_name, last_name, created)
VALUES (1, 'bradyzp@gmail.com', 'bradyzp', 'Zac', 'Brady', now());

INSERT INTO instructor (id, email, owner_id, first_name, last_name, title, phone, office_location)
VALUES (1, 'emaldon3@msudenver.edu', 1, 'Edgar', 'Maldonado', 'Prof.', '303 615-0166', 'Admin 590E');

INSERT INTO office_hours_block (instructor_id, start_time, end_time, day_of_week)
VALUES (1, time '13:00' AT TIME ZONE 'MST', time '16:00' AT TIME ZONE 'MST', 'MONDAY');
INSERT INTO office_hours_block (instructor_id, start_time, end_time, day_of_week)
VALUES (1, time '13:00' AT TIME ZONE 'MST', time '16:00' AT TIME ZONE 'MST', 'WEDNESDAY');

INSERT INTO semester (id, owner_id, year, season, start_date, end_date)
VALUES (1, 1, 2019, 1, '2019-01-23', '2019-05-11');

INSERT INTO course (semester_id, student_id, instructor_id, title, code, crn, building, room, start_time, end_time,
                    is_current)
VALUES (1, 1, 1, 'Systems Analysis & Design II', 'CIS4050', 51231, 'Admin Building', '250A',
        time '14:00' AT TIME ZONE 'MST', time '15:15' AT TIME ZONE 'MST', true);

ALTER SEQUENCE student_id_seq START 1001;
ALTER SEQUENCE instructor_id_seq START 1001;
ALTER SEQUENCE semester_id_seq START 1001;


-- INSERT INTO course (semester_id, student_id, instructor_id, title, code, crn, building, room, start_time, end_time,
--                     is_current)
-- VALUES (1, 1, 1, 'Social Media Analysis', 'CIS4120', 51123, 'Admin Building', '155', time '11:00' AT TIME ZONE 'MST',
--         time '12:15' AT TIME ZONE 'MST', true);

