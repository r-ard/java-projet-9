USE `medilabo_administration`;

-- Insert a test user in database, with username 'TestUser' and password 'azerty123'.
INSERT INTO `users` VALUES (1, 'John', 'Doe', 'TestUser', '$2y$10$dMw0bMRmFQq8JhZOlho2u.k6aEnLHowatgA.hgtEsJD2L8qPQij9K');

USE `medilabo`;

-- Insert some test patients in database.
INSERT INTO `patients` VALUES
    (1, 'Alice', 'Smith', '1990-01-01', 'F', '123 Main St, Cityville', '123-456-7890'),
    (2, 'Bob', 'Johnson', '1985-05-15', 'M', '456 Elm St, Townsville', '987-654-3210'),
    (3, 'Charlie', 'Brown', '2000-10-20', 'M', NULL, NULL),
    (4, 'Diana', 'Prince', '1995-07-30', 'F', NULL, NULL),
    (5, 'Ethan', 'Hunt', '1988-12-12', 'M', NULL, NULL),
    (6, 'Fiona', 'Apple', '1992-03-03', 'F', NULL, NULL),
    (7, 'George', 'Costanza', '1975-08-08', 'M', NULL, NULL),
    (8, 'Hannah', 'Montana', '2002-11-11', 'F', NULL, NULL);