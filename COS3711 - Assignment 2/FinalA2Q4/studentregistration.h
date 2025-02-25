#ifndef STUDENTREGISTRATION_H
#define STUDENTREGISTRATION_H

#include "registration.h"

class StudentRegistration : public Registration
{
    Q_OBJECT
private:
    QString m_qualification;

public:
    StudentRegistration(Person *attendee, const QString &qualification, QObject *parent = nullptr);

    double calculateFee() const override;
    QString toString() const;
};

#endif // STUDENTREGISTRATION_H
