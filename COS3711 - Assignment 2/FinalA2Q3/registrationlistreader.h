#ifndef REGISTRATIONLISTREADER_H
#define REGISTRATIONLISTREADER_H

#include <QObject>
#include <QList>
#include <QString>
#include <QXmlStreamReader>

class Registration;
class Person;
class RegistrationList;

class RegistrationListReader : public QObject {
    Q_OBJECT
private:
    Registration* parseRegistration(QXmlStreamReader &xml);
    Person* parseAttendee(QXmlStreamReader &xml);

public:
    explicit RegistrationListReader(QObject *parent = nullptr);
    bool readFromFile(const QString &fileName, RegistrationList *registrationList);
};

#endif // REGISTRATIONLISTREADER_H
