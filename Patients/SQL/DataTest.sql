USE mediscreenapp;

INSERT INTO address (id, street, city, zip_code, state, country)
VALUES (1, 'Downtown street 140', 'New York', '232625', 'New York', 'USA'),
       (2, 'Uptown street 140', 'New York', '232625', 'New York', 'USA');

INSERT INTO patients (id, first_name, last_name, email, birthdate, gender, phone_number, address_id)
VALUES (1, 'Simon', 'Courtecuisse', 'simon.courtecuisse@example.com', '1989/03/31', 'MALE', '0647536617','1'),
       (2, 'Simon', 'Soulie', 'simon.soulie@example.com', '1989/03/31', 'MALE', '0647536617','2');

UPDATE patients_sequence
SET next_val = '3';

UPDATE address_sequence
SET next_val = '3';
