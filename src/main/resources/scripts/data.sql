insert into roles (name) values ('ROLE_ADMIN'), ('ROLE_USER');
insert into users (first_name, last_name, email, password) values ('John', 'Smith', 'john_smith@mail.ru',
                                                        '$2a$10$2Qzbj1CWp.PDMXBbx8SCmOo0cuXyL0yYNsZNigUlGEfneH5dLysWu');
insert into users_roles values (1, 1);