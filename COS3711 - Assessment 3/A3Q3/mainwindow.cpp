#include "mainwindow.h"
#include <QVBoxLayout>
#include <QMessageBox>
#include <QPushButton>

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent), validate(this) {
    QWidget* centralWidget = new QWidget(this);
    setCentralWidget(centralWidget);

    rule1 = new QCheckBox("Rule 1: No spaces with minimum 5 characters", this);
    rule2 = new QCheckBox("Rule 2: 5 characters, 3rd character (a-f, 0-9, A-F)", this);
    rule3 = new QCheckBox("Rule 3: 4 to 6 digits, first digit not 0", this);
    inputPassword = new QLineEdit(this);
    inputPassword->setPlaceholderText("Enter password");

    QPushButton* validationButton = new QPushButton("Validate", this);

    QVBoxLayout* inputLayout = new QVBoxLayout(this);
    inputLayout->addWidget(rule1);
    inputLayout->addWidget(rule2);
    inputLayout->addWidget(rule3);
    inputLayout->addWidget(inputPassword);
    inputLayout->addWidget(validationButton);

    centralWidget->setLayout(inputLayout);

    connect(validationButton, &QPushButton::clicked, this, &MainWindow::validatePassword);
}

void MainWindow::validatePassword() {
    QString password = inputPassword->text();
    bool isValid = false;

    if (rule1->isChecked() && !rule2->isChecked() && !rule3->isChecked()) {
        isValid = validate.oneValidate(password);
        QMessageBox::information(this, "Validation Result", isValid ? QString("Password '%1' is VALID!").arg(password) : QString("Password '%1' is INVALID!").arg(password));

    } else if (rule2->isChecked() && !rule1->isChecked() && !rule3->isChecked()) {
        isValid = validate.twoValidate(password);
        QMessageBox::information(this, "Validation Result", isValid ? QString("Password '%1' is VALID!").arg(password) : QString("Password '%1' is INVALID!").arg(password));

    } else if (rule3->isChecked() && !rule1->isChecked() && !rule2->isChecked()) {
        isValid = validate.threeValidate(password);
        QMessageBox::information(this, "Validation Result", isValid ? QString("Password '%1' is VALID!").arg(password) : QString("Password '%1' is INVALID!").arg(password));

    } else {
        QMessageBox::information(this, "Error", "Only ONE RULE can be chosen at a time!");
    }

    inputPassword->clear();
}

MainWindow::~MainWindow() {}
