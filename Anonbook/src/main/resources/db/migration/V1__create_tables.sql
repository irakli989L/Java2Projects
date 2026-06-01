CREATE TABLE posts (
    id BIGSERIAL PRIMARY KEY,
    content TEXT NOT NULL,
    image_name VARCHAR(255),
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE comments (
    id BIGSERIAL PRIMARY KEY,
    post_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_comment_post FOREIGN KEY(post_id)
        REFERENCES posts(id)
        ON DELETE CASCADE
);