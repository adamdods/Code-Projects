#include "guestregistration.h"

GuestRegistration::GuestRegistration(Person *attendee, const QString &category, QObject *parent)
    : Registration(attendee, parent), m_Category(category) {}

double GuestRegistration::calculateFee() const {
    return STANDARD_FEE * 0.1;
}

QString GuestRegistration::toString() const {
    return Registration::toString() + " (Guest, " + m_Category + ")";
}
