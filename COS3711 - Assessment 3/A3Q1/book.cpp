#include "book.h"

Book::Book() {}

Book::Book(const QString &ti, const QStringList &au, const QString &is, const QDate &da)
    : title(ti), authors(au), isbn(is), Date(da) {}

void Book::setTitle(const QString &ti) {
    title = ti;
}

void Book::setAuthors(const QStringList &au) {
    authors = au;
}

void Book::setIsbn(const QString &is) {
    isbn = is;
}

void Book::setDate(const QDate &da) {
    Date = da;
}

QString Book::getTitle() const {
    return title;
}

QStringList Book::getAuthors() const {
    return authors;
}

QString Book::getIsbn() const {
    return isbn;
}

QDate Book::getDate() const {
    return Date;
}
