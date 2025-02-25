#include <QApplication>
#include "bookinput.h"

int main(int argc, char *argv[]) {
    QApplication app(argc, argv);
    BookInput bookInput;
    bookInput.show();
    return app.exec();
}
