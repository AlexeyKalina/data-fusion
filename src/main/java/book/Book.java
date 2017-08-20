package book;

import object_model.Object;

class Book extends Object {

    private String isbn;
    private String title;
    private String author;
    private String publicationYear;
    private String publisher;

    Book(String isbn, String title, String author, String publicationYear, String publisher) {

        id = isbn;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.publisher = publisher;
    }

    String isbn() {
        return isbn;
    }
    String title() {
        return title;
    }
    String author() {
        return author;
    }
    String publicationYear() {
        return publicationYear;
    }
    String publisher() {
        return publisher;
    }
}
