#include "registration.h"

const double Registration::STANDARD_FEE = 100.0;

Registration::Registration(Person *attendee, QObject *parent)
    : QObject(parent), m_Attendee(attendee), m_bookingDate(QDate::currentDate()) {}

Registration::~Registration() {
    delete m_Attendee;
}

Person *Registration::getAttendee() const {
    return m_Attendee;
}

QDate Registration::getBookingDate() const {
    return m_bookingDate;
}

double Registration::calculateFee() const {
    return STANDARD_FEE;
}

QString Registration::toString() const {
    return m_Attendee->toString() + " | " + QString::number(calculateFee());
}
