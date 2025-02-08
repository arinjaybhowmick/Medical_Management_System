CREATE OR REPLACE TRIGGER patient_insert_trigger
BEFORE INSERT ON patient
FOR EACH ROW
BEGIN
    IF :NEW.birth_year >= EXTRACT(YEAR FROM SYSDATE) THEN
        RAISE_APPLICATION_ERROR(-20001, 'The attribute must be less than the current date.');
    END IF;
END;
/

CREATE OR REPLACE TRIGGER doctor_insert_trigger
BEFORE INSERT ON doctor
FOR EACH ROW
BEGIN
    IF :NEW.experience_start >= EXTRACT(YEAR FROM SYSDATE) THEN
        RAISE_APPLICATION_ERROR(-20001, 'The attribute must be less than the current date.');
    END IF;
END;
/

ALTER TABLE PATIENT 
ADD CONSTRAINT PATIENT_EMAIL_CHECK
CHECK (REGEXP_LIKE(email, '[a-zA-Z0-9._%-]+@[a-zA-Z0-9._%-]+\.[a-zA-Z]{2,4}'));

ALTER TABLE DOCTOR 
ADD CONSTRAINT DOCTOR_EMAIL_CHECK
CHECK (REGEXP_LIKE(email, '[a-zA-Z0-9._%-]+@[a-zA-Z0-9._%-]+\.[a-zA-Z]{2,4}'));

CREATE INDEX doctor_name_index
ON doctor(name);
CREATE INDEX specialization_name_index
ON specialization(name);
CREATE INDEX doctor_fees_index
ON doctor(fees);
CREATE INDEX doctor_rating_index
ON doctor(rating);
CREATE BITMAP INDEX doctor_gender_index
ON doctor(gender);
CREATE BITMAP INDEX doctor_status_index
ON doctor(status);
CREATE INDEX appointment_date_index
ON appointment(app_date);
CREATE BITMAP INDEX appointment_status_index
ON appointment(app_status);
CREATE BITMAP INDEX appointment_slot_index
ON appointment(slot);
CREATE BITMAP INDEX user_username_password_index
ON users(user_name,password);


INSERT INTO "MMS_DB"."ROLE" (ID, ROLE_NAME) VALUES ('1', 'ROLE_ADMIN');
INSERT INTO "MMS_DB"."ROLE" (ID, ROLE_NAME) VALUES ('2', 'ROLE_PATIENT');
INSERT INTO "MMS_DB"."ROLE" (ID, ROLE_NAME) VALUES ('3', 'ROLE_DOCTOR');

-- To create admin user with encrypted password admin
INSERT INTO "MMS_DB"."USERS" (ID, ROLE_ID, USER_NAME, PASSWORD) VALUES (USER_SEQ.nextval, 1, 'admin', '$2a$10$EVGHAVvhkS3U9N8uyI.vleh1j.N5XmKIuSGXqM.gu.blcAv66g6PS');

COMMIT;