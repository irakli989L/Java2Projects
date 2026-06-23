ALTER TABLE items ADD COLUMN user_id BIGINT;

ALTER TABLE items ADD CONSTRAINT fk_items_users
    FOREIGN KEY (user_id)
    REFERENCES users(id)
    ON DELETE CASCADE;