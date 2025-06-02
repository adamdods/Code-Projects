#ifndef BOOK_H
#define BOOK_H

#include <QString>
#include <QStringList>
#include <QDate>

class Book {

public:
    Book();
    Book(const QString &ti, const QStringList &au, const QString &is, const QDate &da);

    void setTitle(const QString &ti);
    void setAuthors(const QStringList &au);
    void setIsbn(const QString &is);
    void setDate(const QDate &da);

    QString getTitle() const;
    QStringList getAuthors() const;
    QString getIsbn() const;
    QDate getDate() const;

private:
    QString title;
    QStringList authors;
    QString isbn;
    QDate Date;
};

#endif // BOOK_H
