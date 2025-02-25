#ifndef REGISTRATIONLISTWRITER_H
#define REGISTRATIONLISTWRITER_H

#include <QObject>
#include <QFile>
#include <QDomDocument>
#include "registrationlist.h"

class RegistrationListWriter : public QObject
{
    Q_OBJECT
private:
    QDomElement makeReg(QDomDocument &doc, Registration *registration);
    QDomElement makeAttend(QDomDocument &doc, Person *attendee);

    QDomDocument document;

public:
    explicit RegistrationListWriter(QObject *parent = nullptr);

    bool write(const QString &fileName, RegistrationList *registrationList);
};

#endif // REGISTRATIONLISTWRITER_H
