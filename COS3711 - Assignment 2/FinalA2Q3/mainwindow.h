#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QTableWidget>
#include <QLineEdit>
#include <QComboBox>
#include <QPushButton>
#include <QFileDialog>
#include "registrationlist.h"

class MainWindow : public QMainWindow
{
    Q_OBJECT
private:
    void ui();
    void connects();
    void displayResults(const QString &name);
    void table();
    void clear();

    RegistrationList *registrationList;
    QTableWidget *tableWidget;
    QLineEdit *nameEdit;
    QLineEdit *emailEdit;
    QLineEdit *affiliationEdit;
    QComboBox *typeBox;
    QLineEdit *queryEdit;
    QLineEdit *affiliationQueryEdit;
    QComboBox *feesTypeBox;
    QPushButton *registerButton;
    QPushButton *queryButton;
    QPushButton *queryAffiliationButton;
    QPushButton *queryFeesButton;
    QPushButton *saveButton;
    QPushButton *loadButton;
public:
    MainWindow(QWidget *parent = nullptr);
    ~MainWindow();

private slots:
    void registerAttendee();
    void queryAttendee();
    void queryByAffiliation();
    void queryTotalFees();
    void saveRegistrationList();
    void loadRegistrationList();
};

#endif // MAINWINDOW_H
