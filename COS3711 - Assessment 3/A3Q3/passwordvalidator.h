#ifndef PASSWORDVALIDATOR_H
#define PASSWORDVALIDATOR_H

#include <QObject>
#include <QString>

class PasswordValidator : public QObject {
    Q_OBJECT

public:
    explicit PasswordValidator(QObject* parent = nullptr);

    bool oneValidate(const QString& password);
    bool twoValidate(const QString& password);
    bool threeValidate(const QString& password);
};

#endif // PASSWORDVALIDATOR_H
