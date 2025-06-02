#ifndef BOOK_H
#define BOOK_H

#include <QObject>
#include <QString>
#include <QStringList>
#include <QDate>

class Book : public QObject {
    Q_OBJECT
    Q_PROPERTY(QString title READ getTitle WRITE setTitle)
    Q_PROPERTY(QStringList authors READ getAuthors WRITE setAuthors)
    Q_PROPERTY(QString isbn READ getIsbn WRITE setIsbn)
    Q_PROPERTY(QDate date READ getDate WRITE setDate)

public:
    Book(QObject *parent = nullptr);
    Book(const QString &ti, const QStringList &au, const QString &is, const QDate &da, QObject *parent = nullptr);

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
    QDate publicationDate;
};

#endif // BOOK_H
