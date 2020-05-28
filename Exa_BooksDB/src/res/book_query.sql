SELECT          all_books_of_author.book_id AS "ID",
		all_books_of_author.title AS "title",
		all_books_of_author.total_pages AS "pages",
		all_books_of_author.rating AS "rating",
		COALESCE(all_books_of_author.isbn, '') AS "ISBN",
		all_books_of_author.published_date AS "published_date",
		p.name AS "publisher_name",
		g.genre AS "genre",
		a.first_name AS "firstname",
		a.middle_name AS "middlename",
		a.last_name AS "lastname"
FROM (
		SELECT DISTINCT b.book_id, b.title, b.total_pages, b.rating, b.isbn, b.published_date, b.publisher_id
		FROM books b
		INNER JOIN book_authors ba ON ba.book_id = b.book_id 
		INNER JOIN authors a1 ON a1.author_id = ba.author_id
		WHERE (UPPER(a1.first_name) LIKE UPPER(?)) OR (UPPER(a1.last_name) LIKE UPPER(?))
) all_books_of_author
		
INNER JOIN publishers p ON p.publisher_id = all_books_of_author.publisher_id
INNER JOIN book_genres bg ON bg.book_id = all_books_of_author.book_id
INNER JOIN genres g ON bg.genre_id = g.genre_id
INNER JOIN book_authors ba ON ba.book_id = all_books_of_author.book_id
INNER JOIN authors a ON ba.author_id = a.author_id

WHERE UPPER(all_books_of_author.title) LIKE UPPER(?) AND
UPPER(g.genre) LIKE UPPER(?) AND
UPPER(p.name) LIKE UPPER(?)
ORDER BY all_books_of_author.title;