INSERT INTO student (email, username, first_name, last_name, created)
VALUES ('bradyzp@gmail.com', 'bradyzp', 'Zac', 'Brady', now());

INSERT INTO student (email, username, first_name, last_name, created)
VALUES ('ssautel@msudenver.edu', 'ssautel', 'Stephen', 'Sautel', now());


INSERT INTO instructor (email, first_name, last_name, title, phone, office_location)
VALUES ('emaldon3@msudenver.edu', 'Edgar', 'Maldonado', 'Prof.', '303 615-0166', 'Admin 590E');

INSERT INTO office_hours_block (instructor_id, start_time, end_time, day_of_week)
VALUES (1, time '13:00' AT TIME ZONE 'MST', time '16:00' AT TIME ZONE 'MST', 'MONDAY');
INSERT INTO office_hours_block (instructor_id, start_time, end_time, day_of_week)
VALUES (1, time '13:00' AT TIME ZONE 'MST', time '16:00' AT TIME ZONE 'MST', 'WEDNESDAY');

INSERT INTO semester (year, start_date, end_date)
VALUES (2019, '2019-01-23', '2019-05-11');

INSERT INTO course (semester_id, student_id, instructor_id, title, code, crn, building, room, start_time, end_time,
                    is_current)
VALUES (1, 1, 1, 'Systems Analysis & Design II', 'CIS4050', 51231, 'Admin Building', '250A',
        time '14:00' AT TIME ZONE 'MST', time '15:15' AT TIME ZONE 'MST', true);


INSERT INTO course (semester_id, student_id, instructor_id, title, code, crn, building, room, start_time, end_time,
                    is_current)
VALUES (1, 1, 1, 'Social Media Analysis', 'CIS4120', 51123, 'Admin Building', '155', time '11:00' AT TIME ZONE 'MST',
        time '12:15' AT TIME ZONE 'MST', true);


INSERT INTO course (semester_id, student_id, instructor_id, title, code, crn, building, room, start_time, end_time,
                    is_current)
VALUES (1, 2, 1, 'Social Media Analysis', 'CIS4120', 51123, 'Admin Building', '155', time '11:00' AT TIME ZONE 'MST',
        time '12:15' AT TIME ZONE 'MST', true);

-- INSERT INTO course_owner (course_id, student_id, created)
-- VALUES (1, 1, now());
--
-- INSERT INTO course_owner (course_id, student_id, created)
-- VALUES (2, 1, now());
--
-- INSERT INTO course_owner (course_id, student_id, created)
-- VALUES (3, 2, now());

