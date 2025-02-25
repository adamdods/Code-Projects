#ifndef GUESTREGISTRATION_H
#define GUESTREGISTRATION_H

#include "registration.h"

class GuestRegistration : public Registration
{
    Q_OBJECT
public:
    GuestRegistration(Person *attendee, const QString &category, QObject *parent = nullptr);

    double calculateFee() const override;
    QString toString() const;

private:
    QString m_Category;
};

#endif // GUESTREGISTRATION_H
