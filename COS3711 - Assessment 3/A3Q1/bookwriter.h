#ifndef BOOKWRITER_H
#define BOOKWRITER_H

#include "book.h"
#include <QString>
#include <QFile>
#include <QTextStream>

class BookWriter {
public:
    static bool saveBook(const Book &book, const QString &name);
};

#endif // BOOKWRITER_H
