#include "person.h"

Person::Person(const QString &name, const QString &affiliation, const QString &email)
    : m_Name(name), m_Affiliation(affiliation), m_Email(email) {}

QString Person::getName() const {
    return m_Name;
}

QString Person::getAffiliation() const {
    return m_Affiliation;
}

QString Person::getEmail() const {
    return m_Email;
}

QString Person::toString() const {
    return m_Name + " (" + m_Affiliation + ", " + m_Email + ")";
}
