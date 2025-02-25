#include "mainwindow.h"
#include "person.h"
#include "studentregistration.h"
#include "guestregistration.h"
#include "registrationlistwriter.h" // Include the new class
#include "registrationlistreader.h"

#include <QVBoxLayout>
#include <QHBoxLayout>
#include <QLabel>
#include <QMessageBox>
#include <QHeaderView>
#include <QFileDialog> // For file dialogs

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
    feesTypeBox(new QComboBox(this)),
    saveButton(new QPushButton("Save Registration List", this)),
    loadButton(new QPushButton("Load Registration List", this))
{
    ui();
    connects();
    table();
}

MainWindow::~MainWindow() {
}

void MainWindow::ui() {
    QWidget *mainWidget = new QWidget(this);
    QVBoxLayout *mainLayout = new QVBoxLayout(mainWidget);

    tableWidget->setColumnCount(5);
    tableWidget->setHorizontalHeaderLabels({"Name", "Email", "Fee", "Affiliation", "Date"});
    tableWidget->horizontalHeader()->setSectionResizeMode(QHeaderView::Stretch);

    QHBoxLayout *formLayout = new QHBoxLayout;
    formLayout->addWidget(new QLabel("Name:"));
    formLayout->addWidget(nameEdit);
    formLayout->addWidget(new QLabel("Email:"));
    formLayout->addWidget(emailEdit);
    formLayout->addWidget(new QLabel("Affiliation:"));
    formLayout->addWidget(affiliationEdit);
    formLayout->addWidget(new QLabel("Type:"));
    formLayout->addWidget(typeBox);

    typeBox->addItem("Standard");
    typeBox->addItem("Student");
    typeBox->addItem("Guest");

    QHBoxLayout *queryLayout = new QHBoxLayout;
    queryLayout->addWidget(new QLabel("Query Name:"));
    queryLayout->addWidget(queryEdit);
    queryLayout->addWidget(queryButton);
    queryLayout->setStretch(0, 1);
    queryLayout->setStretch(1, 2);
    queryLayout->setStretch(2, 2);

    QHBoxLayout *affiliationLayout = new QHBoxLayout;
    affiliationLayout->addWidget(new QLabel("Query Affiliation:"));
    affiliationLayout->addWidget(affiliationQueryEdit);
    affiliationLayout->addWidget(queryAffiliationButton);
    affiliationLayout->setStretch(0, 1);
    affiliationLayout->setStretch(1, 2);
    affiliationLayout->setStretch(2, 2);

    QHBoxLayout *feesLayout = new QHBoxLayout;
    feesLayout->addWidget(new QLabel("Total Fees by Type:"));
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
    buttonLayout->addWidget(saveButton);
    buttonLayout->addWidget(loadButton);

    mainLayout->addLayout(formLayout);
    mainLayout->addLayout(buttonLayout);
    mainLayout->addLayout(queryLayout);
    mainLayout->addLayout(affiliationLayout);
    mainLayout->addLayout(feesLayout);
    mainLayout->addWidget(tableWidget);

    setCentralWidget(mainWidget);
}

void MainWindow::connects() {
    connect(registerButton, &QPushButton::clicked, this, &MainWindow::registerAttendee);
    connect(queryButton, &QPushButton::clicked, this, &MainWindow::queryAttendee);
    connect(queryAffiliationButton, &QPushButton::clicked, this, &MainWindow::queryByAffiliation);
    connect(queryFeesButton, &QPushButton::clicked, this, &MainWindow::queryTotalFees);
    connect(saveButton, &QPushButton::clicked, this, &MainWindow::saveRegistrationList);
    connect(loadButton, &QPushButton::clicked, this, &MainWindow::loadRegistrationList);
}

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

void MainWindow::queryAttendee() {
    QString name = queryEdit->text();
    if (name.isEmpty()) {
        QMessageBox::warning(this, "Input Error", "Please enter a name to query.");
        return;
    }
    displayResults(name);
}

void MainWindow::queryByAffiliation() {
    QString affiliation = affiliationQueryEdit->text();
    if (affiliation.isEmpty()) {
        QMessageBox::warning(this, "Input Error", "Please enter an affiliation to query.");
        return;
    }

    int count = registrationList->totalRegistrations(affiliation);
    QMessageBox::information(this, "Query Result", QString("Number of attendees from %1: %2").arg(affiliation).arg(count));
}

void MainWindow::queryTotalFees() {
    QString type = feesTypeBox->currentText();
    double total = registrationList->totalFee(type);
    QMessageBox::information(this, "Query Result", QString("Total fees for %1: $%2").arg(type).arg(total));
}

void MainWindow::saveRegistrationList() {
    QString fileName = QFileDialog::getSaveFileName(this, "Save Registration List", QDir::homePath(), "XML Files (*.xml)");
    if (fileName.isEmpty()) {
        return; // User canceled or no file selected
    }

    RegistrationListWriter writer;
    if (writer.write(fileName, registrationList)) {
        QMessageBox::information(this, "Save Successful", "Registration list saved successfully.");
    } else {
        QMessageBox::warning(this, "Save Error", "Failed to save registration list.");
    }
}

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

void MainWindow::loadRegistrationList() {
    QString fileName = QFileDialog::getOpenFileName(this, "Open Registration List", QDir::homePath(), "XML Files (*.xml)");
    if (fileName.isEmpty()) {
        return; // User canceled or no file selected
    }

    RegistrationListReader reader;
    if (reader.readFromFile(fileName, registrationList)) {
        table();
        QMessageBox::information(this, "Load Successful", "Registration list loaded successfully.");
    } else {
        QMessageBox::warning(this, "Load Error", "Failed to load registration list.");
    }
}
