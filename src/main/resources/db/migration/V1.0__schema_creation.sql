CREATE TABLE student
(
  id         serial PRIMARY KEY,
  email      VARCHAR(50),
  username   VARCHAR(20),
  first_name VARCHAR(30),
  last_name  VARCHAR(30),
  created    TIMESTAMP
);

CREATE TABLE instructor
(
  id              SERIAL PRIMARY KEY,
  email           VARCHAR(50),
  title           VARCHAR(10),
  first_name      VARCHAR(30),
  last_name       VARCHAR(30),
  phone           VARCHAR(20),
  office_location VARCHAR(30)
);

CREATE TABLE office_hours_block
(
  id            SERIAL PRIMARY KEY,
  instructor_id INTEGER,
  start_time    TIME WITH TIME ZONE,
  end_time      TIME WITH TIME ZONE,
  day_of_week   VARCHAR(10),
  CONSTRAINT instructor_id_fk FOREIGN KEY (instructor_id) REFERENCES instructor (id)
);

CREATE TABLE semester
(
  id         SERIAL PRIMARY KEY,
  year       INTEGER,
  start_date DATE,
  end_date   DATE,
  CONSTRAINT end_date_after_start_date_check CHECK (end_date > start_date)
);

CREATE TABLE course
(
  id            SERIAL PRIMARY KEY,
  semester_id   INTEGER,
  student_id    INTEGER     NOT NULL,
  instructor_id INTEGER,
  title         VARCHAR(50) NOT NULL,
  code          VARCHAR(10),
  crn           INTEGER,
  building      VARCHAR(30),
  room          VARCHAR(10),
  start_time    TIME WITH TIME ZONE,
  end_time      TIME WITH TIME ZONE,
  is_current    BOOLEAN,
  created       TIMESTAMP,
  modified      TIMESTAMP,
  CONSTRAINT semseter_id_fk FOREIGN KEY (semester_id) REFERENCES semester (id),
  CONSTRAINT student_id_fk FOREIGN KEY (student_id) REFERENCES student (id),
  CONSTRAINT instructor_id_fk FOREIGN KEY (instructor_id) REFERENCES instructor (id)
);

CREATE TABLE document
(
  id           SERIAL PRIMARY KEY,
  title        VARCHAR(255),
  filename     VARCHAR(255),
  content_type VARCHAR(20),
  content      bytea
);

CREATE TABLE course_document
(
  document_id   INTEGER,
  course_id     INTEGER,
  document_date DATE,
  created       TIMESTAMP,
  CONSTRAINT document_id_course_id_pk PRIMARY KEY (document_id, course_id),
  CONSTRAINT document_id_fk FOREIGN KEY (document_id) REFERENCES document (id),
  CONSTRAINT course_id_fk FOREIGN KEY (course_id) REFERENCES course (id)
);

CREATE TABLE course_syllabus
(
  id            SERIAL PRIMARY KEY,
  course_id     INTEGER,
  document_id   INTEGER,
  syllabus_date DATE,
  comment       VARCHAR(4096),
  CONSTRAINT course_id_fk FOREIGN KEY (course_id) REFERENCES course (id),
  CONSTRAINT document_id_fk FOREIGN KEY (document_id) REFERENCES document (id)
);

CREATE TABLE gradeable
(
  id           SERIAL PRIMARY KEY,
  course_id    INTEGER,
  title        VARCHAR(50) NOT NULL,
  weight       DECIMAL(5, 2),
  grade        DECIMAL(5, 2),
  due_datetime DATE,
  optional     BOOLEAN,
  created      TIMESTAMP,
  modified     TIMESTAMP,
  CONSTRAINT course_id_fk FOREIGN KEY (course_id) REFERENCES course (id) ON DELETE CASCADE
);

CREATE TABLE gradeable_document
(
  document_id  INTEGER,
  gradeable_id INTEGER,
  created      TIMESTAMP,
  CONSTRAINT document_id_gradeable_id_pk PRIMARY KEY (document_id, gradeable_id),
  CONSTRAINT document_id_fk FOREIGN KEY (document_id) REFERENCES document (id),
  CONSTRAINT gradeable_id_fk FOREIGN KEY (gradeable_id) REFERENCES gradeable (id)
);