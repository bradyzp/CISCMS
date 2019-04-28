CREATE VIEW v_current_courses AS
SELECT c.id "course_id", c.student_id
FROM course c
         JOIN (SELECT id "sid", owner_id FROM semester s WHERE s.is_current = TRUE) AS s ON c.semester_id = s.sid;

