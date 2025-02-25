#ifndef REGISTRATIONLISTREADER_H
#define REGISTRATIONLISTREADER_H

#include <QObject>
#include <QList>
#include <QString>
#include <QXmlStreamReader>

class Registration;
class Person;
class RegistrationList;  // Forward declaration

class RegistrationListReader : public QObject {
    Q_OBJECT
private:
    Registration* parseRegistration(QXmlStreamReader &xml);
    Person* parseAttendee(QXmlStreamReader &xml);

public:
    explicit RegistrationListReader(QObject *parent = nullptr);
    bool readFile(const QString &fileName, RegistrationList *registrationList);
};

#endif // REGISTRATIONLISTREADER_H
