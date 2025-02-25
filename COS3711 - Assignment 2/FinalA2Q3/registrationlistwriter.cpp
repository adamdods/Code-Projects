#include "registrationlistwriter.h"

RegistrationListWriter::RegistrationListWriter(QObject *parent)
    : QObject(parent) {}

bool RegistrationListWriter::write(const QString &fileName, RegistrationList *registrationList) {
    QFile file(fileName);
    if (!file.open(QIODevice::WriteOnly | QIODevice::Text)) {
        return false;
    }

    QDomElement root = document.createElement("registrationlist");
    document.appendChild(root);

    const QList<Registration*> &registrations = registrationList->getRegistrations();
    for (Registration *registration : registrations) {
        QDomElement regElement = makeReg(document, registration);
        root.appendChild(regElement);
    }

    QTextStream stream(&file);
    stream << document.toString();
    file.close();

    return true;
}

QDomElement RegistrationListWriter::makeReg(QDomDocument &doc, Registration *registration) {
    QDomElement regElement = doc.createElement("registration");
    regElement.setAttribute("type", registration->metaObject()->className());

    QDomElement attendeeElement = makeAttend(doc, registration->getAttendee());
    regElement.appendChild(attendeeElement);

    QDomElement bookingDateElement = doc.createElement("bookingdate");
    bookingDateElement.appendChild(doc.createTextNode(registration->getBookingDate().toString(Qt::ISODate)));
    regElement.appendChild(bookingDateElement);

    QDomElement feeElement = doc.createElement("registrationfee");
    feeElement.appendChild(doc.createTextNode(QString::number(registration->calculateFee())));
    regElement.appendChild(feeElement);

    return regElement;
}

QDomElement RegistrationListWriter::makeAttend(QDomDocument &doc, Person *attendee) {
    QDomElement attendeeElement = doc.createElement("attendee");

    QDomElement nameElement = doc.createElement("name");
    nameElement.appendChild(doc.createTextNode(attendee->getName()));
    attendeeElement.appendChild(nameElement);

    QDomElement affiliationElement = doc.createElement("affiliation");
    affiliationElement.appendChild(doc.createTextNode(attendee->getAffiliation()));
    attendeeElement.appendChild(affiliationElement);

    QDomElement emailElement = doc.createElement("email");
    emailElement.appendChild(doc.createTextNode(attendee->getEmail()));
    attendeeElement.appendChild(emailElement);

    return attendeeElement;
}
