CREATE TABLE books (
	code VARCHAR(50) PRIMARY KEY,
	title VARCHAR(100) NOT NULL,
	author VARCHAR(100) NOT NULL
);

CREATE TABLE members (
	id SERIAL PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	email VARCHAR(100) UNIQUE NOT NULL,
	join_date DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE borrowings (
	book_code VARCHAR(50),
	member_id INT,
	borrow_date DATE NOT NULL DEFAULT CURRENT_DATE,
	return_date DATE,

	PRIMARY KEY(book_code, member_id, borrow_date),

	CONSTRAINT fk_book FOREIGN KEY(book_code) REFERENCES books(code),
	CONSTRAINT fk_member FOREIGN KEY(member_id) REFERENCES members(id)
);

CREATE INDEX idx_borrowings_book_code ON borrowings(book_code);
CREATE INDEX idx_borrowings_member_id ON borrowings(member_id);
CREATE INDEX idx_borrowings_return_date ON borrowings(return_date);