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

    QString getName() const;          // Getter for name
    QString getAffiliation() const;   // Getter for affiliation
    QString getEmail() const;         // Getter for email
    QString toString() const;  // Add this line

    void setName(const QString &name);           // Setter for name
    void setAffiliation(const QString &affiliation); // Setter for affiliation
    void setEmail(const QString &email);         // Setter for email
};

#endif // PERSON_H
