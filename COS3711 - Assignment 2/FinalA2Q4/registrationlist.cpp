#include "registrationlist.h"

RegistrationList::RegistrationList(QObject *parent)
    : QObject(parent) {}

RegistrationList::~RegistrationList() {
    qDeleteAll(m_AttendeeList);
}

bool RegistrationList::addRegistration(Registration *registration) {
    for (const auto &reg : m_AttendeeList) {
        if (reg->getAttendee()->getEmail() == registration->getAttendee()->getEmail() &&
            reg->getAttendee()->getName() == registration->getAttendee()->getName()) {
            return false;
        }
    }
    m_AttendeeList.append(registration);
    return true;
}

bool RegistrationList::isRegistered(const QString &name) const {
    for (const auto &reg : m_AttendeeList) {
        if (reg->getAttendee()->getName() == name) {
            return true;
        }
    }
    return false;
}

double RegistrationList::totalFee(const QString &type) const {
    double total = 0.0;
    for (const auto &reg : m_AttendeeList) {
        if (type == "All" || reg->metaObject()->className() == type) {
            total += reg->calculateFee();
        }
    }
    return total;
}

int RegistrationList::totalRegistrations(const QString &affiliation) const {
    int count = 0;
    for (const auto &reg : m_AttendeeList) {
        if (reg->getAttendee()->getAffiliation() == affiliation) {
            count++;
        }
    }
    return count;
}

const QList<Registration*>& RegistrationList::getRegistrations() const {
    return m_AttendeeList;
}
