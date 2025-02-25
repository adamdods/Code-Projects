#ifndef PERSON_H
#define PERSON_H

#include <QString>

class Person
{
private:
    QString m_Name;
    QString m_Affiliation;
    QString m_Email;

public:
    Person(const QString &name, const QString &affiliation, const QString &email);
    QString getName() const;
    QString getAffiliation() const;
    QString getEmail() const;
    QString toString() const;
};

#endif // PERSON_H
