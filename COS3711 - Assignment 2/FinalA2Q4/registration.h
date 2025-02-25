#ifndef REGISTRATION_H
#define REGISTRATION_H

#include <QObject>
#include <QDate>
#include "person.h"

class Registration : public QObject
{
    Q_OBJECT
private:
    Person *m_Attendee;
    QDate m_BookingDate;

public:
    static const double STANDARD_FEE;

    Registration(Person *attendee, QObject *parent = nullptr);
    virtual ~Registration();

    Person *getAttendee() const;
    QDate getBookingDate() const;
    virtual double calculateFee() const;
    QString toString() const;
};

#endif // REGISTRATION_H
