#include "mainwindow.h"
#include "person.h"
#include "studentregistration.h"
#include "guestregistration.h"

#include <QVBoxLayout>
#include <QHBoxLayout>
#include <QMessageBox>
#include <QHeaderView>
#include <QLabel>
#include <QMessageBox>
#include <QHeaderView>

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent),
    registrationList(new RegistrationList(this)),
    tableWidget(new QTableWidget(this)),
    registerButton(new QPushButton("Register", this)),
    queryButton(new QPushButton("Query", this)),
    queryAffiliationButton(new QPushButton("Query by Affiliation", this)),
    queryFeesButton(new QPushButton("Query Total Fees", this)),
    nameEdit(new QLineEdit(this)),
    emailEdit(new QLineEdit(this)),
    affiliationEdit(new QLineEdit(this)),
    typeBox(new QComboBox(this)),
    queryEdit(new QLineEdit(this)),
    affiliationQueryEdit(new QLineEdit(this)),
    feesTypeBox(new QComboBox(this))
{
    ui();
    connects();
    table();
}

MainWindow::~MainWindow() {
}

//Setup for the GUI user interface.
void MainWindow::ui() {
    QWidget *mainWidget = new QWidget(this);
    QVBoxLayout *mainLayout = new QVBoxLayout(mainWidget);

    tableWidget->setColumnCount(5);
    tableWidget->setHorizontalHeaderLabels({"Name", "Email", "Fee", "Affiliation", "Date"});
    tableWidget->horizontalHeader()->setSectionResizeMode(QHeaderView::Stretch);

    QHBoxLayout *infoLayout = new QHBoxLayout;
    infoLayout->addWidget(new QLabel("Name:"));
    infoLayout->addWidget(nameEdit);
    infoLayout->addWidget(new QLabel("Email:"));
    infoLayout->addWidget(emailEdit);
    infoLayout->addWidget(new QLabel("Affiliation:"));
    infoLayout->addWidget(affiliationEdit);
    infoLayout->addWidget(new QLabel("Type:"));
    infoLayout->addWidget(typeBox);

    typeBox->addItem("Standard");
    typeBox->addItem("Student");
    typeBox->addItem("Guest");

    QHBoxLayout *queryLayout = new QHBoxLayout;
    queryLayout->addWidget(new QLabel("Name Query"));
    queryLayout->addWidget(queryEdit);
    queryLayout->addWidget(queryButton);
    queryLayout->setStretch(0, 1);
    queryLayout->setStretch(1, 2);
    queryLayout->setStretch(2, 2);

    QHBoxLayout *affiliationLayout = new QHBoxLayout;
    affiliationLayout->addWidget(new QLabel("Affiliation Query:"));
    affiliationLayout->addWidget(affiliationQueryEdit);
    affiliationLayout->addWidget(queryAffiliationButton);
    affiliationLayout->setStretch(0, 1);
    affiliationLayout->setStretch(1, 2);
    affiliationLayout->setStretch(2, 2);

    QHBoxLayout *feesLayout = new QHBoxLayout;
    feesLayout->addWidget(new QLabel("Total Fees:"));
    feesLayout->addWidget(feesTypeBox);
    feesLayout->addWidget(queryFeesButton);
    feesLayout->setStretch(0, 1);
    feesLayout->setStretch(1, 2);
    feesLayout->setStretch(2, 2);

    feesTypeBox->addItem("All");
    feesTypeBox->addItem("Registration");
    feesTypeBox->addItem("StudentRegistration");
    feesTypeBox->addItem("GuestRegistration");

    QHBoxLayout *buttonLayout = new QHBoxLayout;
    buttonLayout->addWidget(registerButton);

    mainLayout->addLayout(infoLayout);
    mainLayout->addLayout(buttonLayout);
    mainLayout->addLayout(queryLayout);
    mainLayout->addLayout(affiliationLayout);
    mainLayout->addLayout(feesLayout);
    mainLayout->addWidget(tableWidget);

    setCentralWidget(mainWidget);
}

//Connects buttons.
void MainWindow::connects() {
    connect(registerButton, &QPushButton::clicked, this, &MainWindow::registerAttendee);
    connect(queryButton, &QPushButton::clicked, this, &MainWindow::queryAttendee);
    connect(queryAffiliationButton, &QPushButton::clicked, this, &MainWindow::queryByAffiliation);
    connect(queryFeesButton, &QPushButton::clicked, this, &MainWindow::queryTotalFees);
}


//Updates the tabe with new regis
void MainWindow::table() {
    tableWidget->setRowCount(registrationList->getRegistrations().size());
    int row = 0;
    for (const auto &registration : registrationList->getRegistrations()) {
        tableWidget->setItem(row, 0, new QTableWidgetItem(registration->getAttendee()->getName()));
        tableWidget->setItem(row, 1, new QTableWidgetItem(registration->getAttendee()->getEmail()));
        tableWidget->setItem(row, 2, new QTableWidgetItem(QString::number(registration->calculateFee())));
        tableWidget->setItem(row, 3, new QTableWidgetItem(registration->getAttendee()->getAffiliation()));
        tableWidget->setItem(row, 4, new QTableWidgetItem(registration->getBookingDate().toString()));
        ++row;
    }
}

//Clears the input boxes for next input.
void MainWindow::clear() {
    nameEdit->clear();
    emailEdit->clear();
    affiliationEdit->clear();
    typeBox->setCurrentIndex(0);
    queryEdit->clear();
    affiliationQueryEdit->clear();
}

void MainWindow::registerAttendee() {
    QString name = nameEdit->text();
    QString email = emailEdit->text();
    QString affiliation = affiliationEdit->text();
    QString type = typeBox->currentText();

    if (name.isEmpty() || email.isEmpty() || affiliation.isEmpty()) {
        QMessageBox::warning(this, "Input Error", "Please fill in all fields.");
        return;
    }

    Person *attendee = new Person(name, affiliation, email);

    Registration *registration = nullptr;
    if (type == "Standard") {
        registration = new Registration(attendee);
    } else if (type == "Student") {
        registration = new StudentRegistration(attendee, "Student Qualification");
    } else if (type == "Guest") {
        registration = new GuestRegistration(attendee, "Guest Category");
    }

    if (registrationList->addRegistration(registration)) {
        table();
        clear();
    } else {
        QMessageBox::warning(this, "Registration Error", "This attendee is already registered.");
        delete registration;
    }
}

//Checks and displays if a person is registered by name.
void MainWindow::queryAttendee() {
    QString name = queryEdit->text();
    if (name.isEmpty()) {
        QMessageBox::warning(this, "Input Error", "Please enter a name to query.");
        return;
    }
    displayResults(name);
}

//Displays the number of registrations with same affiliation.
void MainWindow::queryByAffiliation() {
    QString affiliation = affiliationQueryEdit->text();
    if (affiliation.isEmpty()) {
        QMessageBox::warning(this, "Input Error", "Please enter an affiliation to query.");
        return;
    }

    int count = registrationList->totalRegistrations(affiliation);
    QMessageBox::information(this, "Query Result", QString("Number of attendees from %1: %2").arg(affiliation).arg(count));
}

//Displays the total fees for the type of registration.
void MainWindow::queryTotalFees() {
    QString type = feesTypeBox->currentText();
    double total = registrationList->totalFee(type);
    QMessageBox::information(this, "Query Result", QString("Total fees for %1: $%2").arg(type).arg(total));
}

//Displays the results of the query.
void MainWindow::displayResults(const QString &name) {
    QString result;
    for (const auto &registration : registrationList->getRegistrations()) {
        if (registration->getAttendee()->getName() == name) {
            result += registration->toString() + "\n";
        }
    }
    if (result.isEmpty()) {
        QMessageBox::information(this, "Query Result", "No attendee found with the name " + name);
    } else {
        QMessageBox::information(this, "Query Result", result);
    }
}
