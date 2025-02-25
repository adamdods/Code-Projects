#ifndef REGISTRATIONLIST_H
#define REGISTRATIONLIST_H

#include <QObject>
#include <QList>
#include "registration.h"

class RegistrationList : public QObject
{
    Q_OBJECT
private:
    QList<Registration*> m_AttendeeList;

public:
    RegistrationList(QObject *parent = nullptr);
    ~RegistrationList();

    bool addRegistration(Registration *registration);
    bool isRegistered(const QString &name) const;
    double totalFee(const QString &type) const;
    int totalRegistrations(const QString &affiliation) const;

    const QList<Registration*>& getRegistrations() const;
};

#endif // REGISTRATIONLIST_H
