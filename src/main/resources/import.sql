
INSERT INTO status (id, description) VALUES (1, 'New');
INSERT INTO status (id, description) VALUES (2, 'Suggested');
INSERT INTO status (id, description) VALUES (3, 'Rejected');
INSERT INTO status (id, description) VALUES (4, 'Fulfilled');

INSERT INTO appuser (id, username, password, enabled, TOKEN_EXPIRED) VALUES (1, 'admin', '$2y$12$svw6XfWfeK38mB9CVBUYsOWmY.jWP1sXBVIKnIlhVeOKPYGZwSCyq', 1, 0);
INSERT INTO appuser (id, username, password, enabled, TOKEN_EXPIRED) VALUES (2, 'user1', '$2y$12$svw6XfWfeK38mB9CVBUYsOWmY.jWP1sXBVIKnIlhVeOKPYGZwSCyq', 1, 0);
INSERT INTO appuser (id, username, password, enabled, TOKEN_EXPIRED) VALUES (3, 'user2', '$2y$12$svw6XfWfeK38mB9CVBUYsOWmY.jWP1sXBVIKnIlhVeOKPYGZwSCyq', 1, 0);

INSERT INTO role (id, description, name) VALUES (1, 'Admin role', 'ADMIN');
INSERT INTO role (id, description, name) VALUES (2, 'User role', 'USER');
INSERT INTO role (id, description, name) VALUES (3, 'Moderator role', 'OPERATIONAL');

INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (1, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (3, 2);

