#include "book.h"

Book::Book(QObject *parent) : QObject(parent) {}

Book::Book(const QString &ti, const QStringList &au, const QString &is, const QDate &da, QObject *parent)
    : QObject(parent), title(ti), authors(au), isbn(is), publicationDate(da) {}

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
    publicationDate = da;
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
    return publicationDate;
}
