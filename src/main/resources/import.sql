

INSERT INTO user (id, username, password, enabled, TOKEN_EXPIRED) VALUES (1, 'admin', '$2y$12$svw6XfWfeK38mB9CVBUYsOWmY.jWP1sXBVIKnIlhVeOKPYGZwSCyq', true, false);

INSERT INTO role (id, description, name) VALUES (1, 'Admin role', 'ADMIN');
INSERT INTO role (id, description, name) VALUES (2, 'User role', 'USER');
INSERT INTO role (id, description, name) VALUES (3, 'Moderator role', 'OPERATIONAL');

INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (1, 2);
