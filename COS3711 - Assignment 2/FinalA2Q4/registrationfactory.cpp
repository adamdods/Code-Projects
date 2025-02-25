#include "registrationfactory.h"

RegistrationFactory::RegistrationFactory() {}

RegistrationFactory& RegistrationFactory::getInstance() {
    static RegistrationFactory instance;
    return instance;
}

Registration* RegistrationFactory::makeRegistration(const QString& type, Person* attendee, const QString& qualification, const QString& category) {
    if (type == "Standard") {
        return new Registration(attendee);
    } else if (type == "Student") {
        return new StudentRegistration(attendee, qualification);
    } else if (type == "Guest") {
        return new GuestRegistration(attendee, category);
    }
    return nullptr;
}
