#include "registrationlistreader.h"
#include "registration.h"
#include "guestregistration.h"
#include "registrationlist.h"
#include "studentregistration.h"
#include <QFile>
#include <QMessageBox>

RegistrationListReader::RegistrationListReader(QObject *parent)
    : QObject(parent) {}

bool RegistrationListReader::readFile(const QString &fileName, RegistrationList *registrationList) {
    if (!registrationList) {
        return false;
    }

    QFile file(fileName);

    if (!file.open(QIODevice::ReadOnly | QIODevice::Text)) {
        QMessageBox::warning(nullptr, "File Error", "Unable to open file.");
        return false;
    }

    QXmlStreamReader xml(&file);

    while (!xml.atEnd() && !xml.hasError()) {
        QXmlStreamReader::TokenType token = xml.readNext();

        if (token == QXmlStreamReader::StartElement) {
            if (xml.name() == QLatin1String("registrationlist")) {
                // Read the registration list elements
                while (!(xml.tokenType() == QXmlStreamReader::EndElement && xml.name() == QLatin1String("registrationlist"))) {
                    if (xml.tokenType() == QXmlStreamReader::StartElement) {
                        if (xml.name() == QLatin1String("registration")) {
                            Registration *reg = parseRegistration(xml);
                            if (reg) {
                                registrationList->addRegistration(reg);
                            }
                        }
                    }
                    xml.readNext();
                }
            }
        }
    }

    if (xml.hasError()) {
        QMessageBox::warning(nullptr, "XML Error", "Error parsing XML.");
        return false;
    }

    xml.clear();
    file.close();
    return true;
}

Registration* RegistrationListReader::parseRegistration(QXmlStreamReader &xml) {
    QString type;
    Person *attendee = nullptr;
    QString qualification;

    if (xml.tokenType() != QXmlStreamReader::StartElement && xml.name() != QLatin1String("registration")) {
        return nullptr;
    }

    type = xml.attributes().value("type").toString();

    while (!(xml.tokenType() == QXmlStreamReader::EndElement && xml.name() == QLatin1String("registration"))) {
        if (xml.tokenType() == QXmlStreamReader::StartElement) {
            if (xml.name() == QLatin1String("attendee")) {
                attendee = parseAttendee(xml);
            } else if (xml.name() == QLatin1String("qualification")) {
                qualification = xml.readElementText();
            }
        }
        xml.readNext();
    }

    if (type == QLatin1String("Registration")) {
        return new Registration(attendee);
    } else if (type == QLatin1String("StudentRegistration")) {
        return new StudentRegistration(attendee, qualification);
    } else if (type == QLatin1String("GuestRegistration")) {
        return new GuestRegistration(attendee, qualification);
    } else {
        return nullptr;
    }
}

Person* RegistrationListReader::parseAttendee(QXmlStreamReader &xml) {
    QString name;
    QString affiliation;
    QString email;

    while (!(xml.tokenType() == QXmlStreamReader::EndElement && xml.name() == QLatin1String("attendee"))) {
        if (xml.tokenType() == QXmlStreamReader::StartElement) {
            if (xml.name() == QLatin1String("name")) {
                name = xml.readElementText();
            } else if (xml.name() == QLatin1String("affiliation")) {
                affiliation = xml.readElementText();
            } else if (xml.name() == QLatin1String("email")) {
                email = xml.readElementText();
            }
        }
        xml.readNext();
    }

    return new Person(name, affiliation, email);
}
