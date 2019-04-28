-- Constraint naming should follow the convention:
-- {table_name}_{column_names}_{suffix}
-- Where suffix is one of (pkey, key, excl, idx, fkey, check)
CREATE SEQUENCE hibernate_sequence START WITH 1000;

CREATE TABLE student
(
    id         SERIAL PRIMARY KEY,
    email      VARCHAR(50) UNIQUE NOT NULL,
    username   VARCHAR(20) UNIQUE NOT NULL,
    first_name VARCHAR(30),
    last_name  VARCHAR(30),
    active     BOOLEAN   DEFAULT TRUE,
    created    TIMESTAMP DEFAULT now()
);

-- Index user-names of active students for optimized lookup
CREATE INDEX student_username_hash_index ON student USING HASH (username) WHERE active = TRUE;

CREATE TABLE instructor
(
    id              SERIAL PRIMARY KEY,
    owner_id        INTEGER,
    email           VARCHAR(50),
    title           VARCHAR(10),
    first_name      VARCHAR(30),
    last_name       VARCHAR(30),
    phone           VARCHAR(20),
    office_location VARCHAR(30),
    created         TIMESTAMP DEFAULT now(),
    CONSTRAINT instructor_owner_id_fkey FOREIGN KEY (owner_id) REFERENCES student (id)
);

CREATE TABLE office_hours_block
(
    id            SERIAL PRIMARY KEY,
    instructor_id INTEGER,
    start_time    TIME WITH TIME ZONE,
    end_time      TIME WITH TIME ZONE,
    day_of_week   VARCHAR(10),
    CONSTRAINT office_hours_block_instructor_id_fkey FOREIGN KEY (instructor_id) REFERENCES instructor (id)
);

CREATE TABLE semester
(
    id         SERIAL PRIMARY KEY,
    owner_id   INTEGER,
    year       INTEGER NOT NULL,
    season     INTEGER,
    start_date DATE,
    end_date   DATE,
    is_current BOOLEAN NOT NULL DEFAULT TRUE,
    calc_gpa   DECIMAL(4, 2),
    created    TIMESTAMP        DEFAULT now(),
    CONSTRAINT semester_owner_id_fkey FOREIGN KEY (owner_id) REFERENCES student (id),
    CONSTRAINT semester_end_date_after_start_date_check CHECK (end_date > start_date)
);

CREATE TABLE course_week_schedule
(
    id        SERIAL PRIMARY KEY,
    monday    BOOLEAN DEFAULT FALSE,
    tuesday   BOOLEAN DEFAULT FALSE,
    wednesday BOOLEAN DEFAULT FALSE,
    thursday  BOOLEAN DEFAULT FALSE,
    friday    BOOLEAN DEFAULT FALSE,
    saturday  BOOLEAN DEFAULT FALSE,
    sunday    BOOLEAN DEFAULT FALSE
);

CREATE TABLE course
(
    id               SERIAL PRIMARY KEY,
    semester_id      INTEGER,
    student_id       INTEGER     NOT NULL,
    instructor_id    INTEGER,
    weekschedule_id  INTEGER,
    title            VARCHAR(50) NOT NULL,
    code             VARCHAR(10),
    crn              INTEGER,
    is_online        BOOLEAN,
    building         VARCHAR(30),
    room             VARCHAR(10),
    start_time       TIME WITH TIME ZONE,
    end_time         TIME WITH TIME ZONE,
    is_current       BOOLEAN   DEFAULT TRUE,
    calculated_grade DECIMAL(5, 2),
    created          TIMESTAMP DEFAULT now(),
    modified         TIMESTAMP,
    CONSTRAINT course_semseter_id_fkey FOREIGN KEY (semester_id) REFERENCES semester (id),
    CONSTRAINT course_student_id_fkey FOREIGN KEY (student_id) REFERENCES student (id),
    CONSTRAINT course_instructor_id_fkey FOREIGN KEY (instructor_id) REFERENCES instructor (id),
    CONSTRAINT course_weekschedule_id_fkey FOREIGN KEY (weekschedule_id) REFERENCES course_week_schedule (id)
);

CREATE TABLE gradeable
(
    id        SERIAL PRIMARY KEY,
    owner_id  INTEGER     NOT NULL,
    course_id INTEGER     NOT NULL,
    title     VARCHAR(50) NOT NULL,
    weight    DECIMAL(5, 2),
    grade     DECIMAL(5, 2),
    due_date  DATE,
    due_time  TIME WITH TIME ZONE,
    optional  BOOLEAN     NOT NULL DEFAULT FALSE,
    created   TIMESTAMP            DEFAULT now(),
    modified  TIMESTAMP,
    CONSTRAINT gradeable_owner_id FOREIGN KEY (owner_id) REFERENCES student (id) ON DELETE CASCADE,
    CONSTRAINT gradeable_course_id_fkey FOREIGN KEY (course_id) REFERENCES course (id) ON DELETE CASCADE
);

CREATE TABLE document
(
    id           SERIAL PRIMARY KEY,
    owner_id     INTEGER       NOT NULL,
    title        VARCHAR(255)  NOT NULL,
    filename     VARCHAR(255)  NOT NULL,
    content_type VARCHAR(50)   NOT NULL,
    content_url  VARCHAR(1024) NOT NULL,
    content      bytea,
    created      TIMESTAMP DEFAULT now(),
    CONSTRAINT document_owner_id FOREIGN KEY (owner_id) REFERENCES student (id) ON DELETE CASCADE
);

CREATE TABLE course_document
(
    document_id   INTEGER,
    course_id     INTEGER,
    document_date DATE,
    created       TIMESTAMP DEFAULT now(),
    CONSTRAINT course_document_document_id_course_id_pkey PRIMARY KEY (document_id, course_id),
    CONSTRAINT course_document_document_id_fkey FOREIGN KEY (document_id) REFERENCES document (id),
    CONSTRAINT course_document_course_id_fkey FOREIGN KEY (course_id) REFERENCES course (id) ON DELETE CASCADE
);

CREATE TABLE course_syllabus
(
    id            SERIAL PRIMARY KEY,
    course_id     INTEGER,
    document_id   INTEGER,
    syllabus_date DATE,
    version       INTEGER,
    comment       VARCHAR(4096),
    created       TIMESTAMP DEFAULT now(),
    CONSTRAINT course_syllabus_course_id_fkey FOREIGN KEY (course_id) REFERENCES course (id) ON DELETE CASCADE,
    CONSTRAINT course_syllabus_document_id_fkey FOREIGN KEY (document_id) REFERENCES document (id)
);

CREATE TABLE gradeable_document
(
    document_id  INTEGER,
    gradeable_id INTEGER,
    comment      VARCHAR(4096),
    created      TIMESTAMP DEFAULT now(),
    CONSTRAINT gradeable_document_document_id_gradeable_id_pkey PRIMARY KEY (document_id, gradeable_id),
    CONSTRAINT gradeable_document_document_id_fkey FOREIGN KEY (document_id) REFERENCES document (id),
    CONSTRAINT gradeable_document_gradeable_id_fkey FOREIGN KEY (gradeable_id) REFERENCES gradeable (id)
);
