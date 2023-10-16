ALTER TABLE user
    ADD password VARCHAR(255) NULL;

ALTER TABLE user
    DROP COLUMN enc_password;