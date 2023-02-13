USE mediscreenapp;

INSERT INTO patients (id, last_name, first_name, address, email, birthdate, gender, phone_number)
VALUES (1, 'Ferguson', 'Lucas', '2 Warren Street', 'fergusonl@example.com', '1968-06-22', 'MALE', '387-866-1399'),
       (2, 'Rees', 'Pippa', '745 West Valley Farms Drive', 'reesp@example.com', '1952-09-27', 'FEMALE', '628-423-0993'),
       (3, 'Arnold', 'Edward', '599 East Garden Ave', 'arnolde@example.com', '1952-11-11', 'MALE', '123-727-2779'),
	   (4, 'Sharp', 'Anthony', '894 Hall Street', 'sharpa@example.com', '1946-11-26', 'MALE', '451-761-8383'),
       (5, 'Ince', 'Wendy', '4 Southampton Road', 'incew@example.com', '1958-06-29', 'FEMALE', '802-911-9975'),
       (6, 'Ross', 'Tracey', '40 Sulphur Springs Dr', 'rosst@example.com', '1949-12-07', 'FEMALE', '131-396-5049'),
       (7, 'Wilson', 'Claire', '12 Cobblestone St', 'wilsonc@example.com', '1966-12-31', 'FEMALE', '300-452-1091'),
       (8, 'Buckland', 'Max', '193 Vale St', 'bucklandm@example.com', '1945-06-24', 'MALE', '833-534-0864'),
       (9, 'Clark', 'Nathalie', '12 Beechwood Road', 'clarkn@example.com', '1964-06-18', 'FEMALE', '241-467-9197'),
       (10, 'Bailey', 'Piers', '1202 Bumble Dr', 'baileyp@example.com', '1959-06-28', 'MALE', '747-815-0557');

UPDATE patients_sequence
SET next_val = '11';