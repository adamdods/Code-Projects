#include "bookwriter.h"
#include <QMetaProperty>
#include <QMetaObject>

BookWriter::BookWriter(QObject *parent) : QObject(parent) {}


bool BookWriter::saveBook(const Book &book, const QString &name) {
    QFile file(name);
    if (!file.open(QIODevice::WriteOnly | QIODevice::Text)) {
        return false;
    }

    QTextStream write(&file);
    const QMetaObject *metaObject = book.metaObject();
    for (int i = 0; i < metaObject->propertyCount(); ++i) {
        QMetaProperty property = metaObject->property(i);
        QString propertyName = property.name();
        QVariant value = book.property(propertyName.toUtf8());
        if (value.canConvert<QStringList>()) {
            QStringList list = value.toStringList();
            write << propertyName << ": " << list.join(", ") << "\n";
        } else {
            write << propertyName << ": " << value.toString() << "\n";
        }
    }
    file.close();
    return true;
}
