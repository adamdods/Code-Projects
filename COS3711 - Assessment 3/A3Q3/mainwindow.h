#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include "passwordvalidator.h"
#include <QCheckBox>
#include <QLineEdit>
#include <QString>

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    MainWindow(QWidget *parent = nullptr);
    ~MainWindow();

private:
    QCheckBox* rule1;
    QCheckBox* rule2;
    QCheckBox* rule3;
    QLineEdit* inputPassword;
    PasswordValidator validate;

private slots:
    void validatePassword();
};
#endif // MAINWINDOW_H
