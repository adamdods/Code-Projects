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

    void setName(const QString &name);
    void setAffiliation(const QString &affiliation);
    void setEmail(const QString &email);
};

#endif // PERSON_H
