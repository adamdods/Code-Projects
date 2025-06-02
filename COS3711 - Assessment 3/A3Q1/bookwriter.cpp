#include "bookwriter.h"

bool BookWriter::saveBook(const Book &book, const QString &name) {
    QFile file(name);
    if (!file.open(QIODevice::WriteOnly | QIODevice::Text)) {
        return false;
    }

    QTextStream write(&file);
    write << "Title: " << book.getTitle() << "\n";
    write << "Authors: " << book.getAuthors().join(", ") << "\n";
    write << "ISBN: " << book.getIsbn() << "\n";
    write << "Publication Date: " << book.getDate().toString(Qt::ISODate) << "\n";

    file.close();
    return true;
}
