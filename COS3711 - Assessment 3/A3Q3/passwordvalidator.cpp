#include "passwordvalidator.h"
#include <QChar>

PasswordValidator::PasswordValidator(QObject *parent) : QObject(parent) {}

bool PasswordValidator::oneValidate(const QString& password) {
    return password.length() >= 5 && !password.contains(" ");
}

bool PasswordValidator::twoValidate(const QString& password) {
    if (password.length() != 5) { return false; }
    QChar char3 = password[2];
    return (char3 >= 'a' && char3 <= 'f') || (char3 >= 'A' && char3 <= 'F') || char3.isDigit();
}

bool PasswordValidator::threeValidate(const QString& password) {
    if (password.length() < 4 || password.length() > 6) { return false; }
    if (password[0] == '0') { return false; }
    return password.toInt() > 0;
}
