#include "studentregistration.h"

StudentRegistration::StudentRegistration(Person *attendee, const QString &qualification, QObject *parent)
    : Registration(attendee, parent), m_Qualification(qualification) {}

double StudentRegistration::calculateFee() const {
    return STANDARD_FEE / 2;
}

QString StudentRegistration::toString() const {
    return Registration::toString() + " (Student, " + m_Qualification + ")";
}
