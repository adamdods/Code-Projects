#include "bookinput.h"
#include "book.h"
#include "bookwriter.h"
#include <QLabel>
#include <QVBoxLayout>
#include <QMessageBox>
#include <QStringList>

BookInput::BookInput(QWidget *parent) : QWidget(parent) {
    ui();
}

void BookInput::ui() {
    titleInput = new QLineEdit(this);
    authorsInput = new QTextEdit(this);
    isbnInput = new QLineEdit(this);
    dateInput = new QDateEdit(this);
    dateInput->setCalendarPopup(true);

    saveButton = new QPushButton("Save Book", this);
    connect(saveButton, &QPushButton::clicked, this, &BookInput::SaveButton);

    QVBoxLayout *inputLayout = new QVBoxLayout(this);
    inputLayout->addWidget(new QLabel("Title:"));
    inputLayout->addWidget(titleInput);
    inputLayout->addWidget(new QLabel("Authors:"));
    inputLayout->addWidget(authorsInput);
    inputLayout->addWidget(new QLabel("ISBN:"));
    inputLayout->addWidget(isbnInput);
    inputLayout->addWidget(new QLabel("Publication Date:"));
    inputLayout->addWidget(dateInput);
    inputLayout->addWidget(saveButton);

    setLayout(inputLayout);
}

void BookInput::SaveButton() {
    QString title = titleInput->text();
    QStringList authors = authorsInput->toPlainText().split(",", Qt::SkipEmptyParts);
    QString isbn = isbnInput->text();
    QDate date = dateInput->date();

    if (title.isEmpty() || authors.isEmpty() || isbn.isEmpty() || !date.isValid()) {
        QMessageBox::warning(this, "Error", "Fill in all Info.");
        return;
    }

    Book book(title, authors, isbn, date);
    if (BookWriter::saveBook(book, "BookInfo.txt")) {
        QMessageBox::information(this, "Saved", "Book Saved to build file as: BookInfo.");
        titleInput->clear();
        authorsInput->clear();
        isbnInput->clear();
        dateInput->setDate(QDate(2000, 1, 1));
    } else {
        QMessageBox::critical(this, "Error", "Saving Book Info Failed.");
    }
}

