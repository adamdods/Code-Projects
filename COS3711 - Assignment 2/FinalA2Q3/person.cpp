#include "person.h"

Person::Person(const QString &name, const QString &affiliation, const QString &email)
    : m_Name(name), m_Affiliation(affiliation), m_Email(email) {}

QString Person::getName() const { return m_Name; }              // Getter implementation for name
QString Person::getAffiliation() const { return m_Affiliation; } // Getter implementation for affiliation
QString Person::getEmail() const { return m_Email; }            // Getter implementation for email
QString Person::toString() const {
    return m_Name + " | " + m_Affiliation + " | " + m_Email;  // Implement the toString method
}

void Person::setName(const QString &name) { m_Name = name; }           // Setter implementation for name
void Person::setAffiliation(const QString &affiliation) { m_Affiliation = affiliation; } // Setter implementation for affiliation
void Person::setEmail(const QString &email) { m_Email = email; }       // Setter implementation for email
