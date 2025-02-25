#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QTableWidget>
#include <QPushButton>
#include <QLineEdit>
#include <QComboBox>
#include "registrationlist.h"

class MainWindow : public QMainWindow
{
    Q_OBJECT
private:
    void ui();
    void connects();
    void table();
    void clear();
    void displayResults(const QString &name);

    RegistrationList *registrationList;
    QTableWidget *tableWidget;
    QPushButton *registerButton;
    QPushButton *queryButton;
    QPushButton *queryAffiliationButton;
    QPushButton *queryFeesButton;
    QLineEdit *nameEdit;
    QLineEdit *emailEdit;
    QLineEdit *affiliationEdit;
    QComboBox *typeBox;
    QLineEdit *queryEdit;
    QLineEdit *affiliationQueryEdit;
    QComboBox *feesTypeBox;

public:
    MainWindow(QWidget *parent = nullptr);
    ~MainWindow();

private slots:
    void registerAttendee();
    void queryAttendee();
    void queryByAffiliation();
    void queryTotalFees();

};

#endif // MAINWINDOW_H
