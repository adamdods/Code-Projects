#ifndef BOOKINPUT_H
#define BOOKINPUT_H

#include <QWidget>
#include <QLineEdit>
#include <QTextEdit>
#include <QDateEdit>
#include <QPushButton>
#include <QVBoxLayout>


class BookInput : public QWidget {
    Q_OBJECT

public:
    BookInput(QWidget *parent = nullptr);

private:
    QLineEdit *titleInput;
    QTextEdit *authorsInput;
    QLineEdit *isbnInput;
    QDateEdit *dateInput;
    QPushButton *save;

    void ui();

private slots:
    void SaveButton();
};

#endif // BOOKINPUT_H
