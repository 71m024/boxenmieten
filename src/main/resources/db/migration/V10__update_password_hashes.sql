UPDATE USER
SET PASSWORD = '323d81f07d6f47427be14db748be7a612d169c5efe02711892ff9e1327afc4d5', SALT = "4B/QJ1T1PI3DJ6hIQ8s9f+E84z1IIImQ";

ALTER TABLE USER
MODIFY SALT VARCHAR(32);
