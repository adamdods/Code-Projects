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

    QDomElement namePart = doc.createElement("name");
    namePart.appendChild(doc.createTextNode(attendee->getName()));
    attendeeElement.appendChild(namePart);

    QDomElement affiliationPart = doc.createElement("affiliation");
    affiliationPart.appendChild(doc.createTextNode(attendee->getAffiliation()));
    attendeeElement.appendChild(affiliationPart);

    QDomElement emailPart = doc.createElement("email");
    emailPart.appendChild(doc.createTextNode(attendee->getEmail()));
    attendeeElement.appendChild(emailPart);

    return attendeeElement;
}
