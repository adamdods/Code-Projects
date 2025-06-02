#ifndef BOOKWRITER_H
#define BOOKWRITER_H

#include <QObject>
#include <QString>
#include <QFile>
#include <QTextStream>
#include "book.h"

class BookWriter : public QObject {
    Q_OBJECT

public:
    BookWriter(QObject *parent = nullptr);

    static bool saveBook(const Book &book, const QString &name);
};

#endif // BOOKWRITER_H
