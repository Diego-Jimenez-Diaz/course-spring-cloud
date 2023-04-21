INSERT INTO users (username, password, enabled, first_name, last_name,email) VALUES ('diegojd', '1234', 1, 'Diego', 'Jimenez', 'diego@example.com');
INSERT INTO users (username, password, enabled, first_name, last_name,email) VALUES ('jimenezd', '0000', 1, 'D', 'J', 'jd@example.com');

INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

INSERT INTO users_to_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_to_roles (user_id, role_id) VALUES (2, 1);
INSERT INTO users_to_roles (user_id, role_id) VALUES (2, 2);