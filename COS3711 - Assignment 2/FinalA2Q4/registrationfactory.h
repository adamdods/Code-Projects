#ifndef REGISTRATIONFACTORY_H
#define REGISTRATIONFACTORY_H

#include "registration.h"
#include "studentregistration.h"
#include "guestregistration.h"
#include <QString>

class RegistrationFactory
{
private:
    RegistrationFactory();
    RegistrationFactory(const RegistrationFactory&) = delete;
    void operator=(const RegistrationFactory&) = delete;

public:
    static RegistrationFactory& getInstance();

    Registration* makeRegistration(const QString& type, Person* attendee, const QString& qualification = "", const QString& category = "");
};

#endif // REGISTRATIONFACTORY_H
