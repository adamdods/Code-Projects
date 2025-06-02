#include "mainwindow.h"
#include "primefinder.h"
#include <QVBoxLayout>
#include <QHBoxLayout>
#include <QLabel>
#include <QCloseEvent>

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent), activeThreads(0)
{
    ui();
}

void MainWindow::ui() {
    QWidget *widget = new QWidget(this);
    QVBoxLayout *inputLayout = new QVBoxLayout(widget);

    startInfo = new QLineEdit(this);
    endInfo = new QLineEdit(this);

    threadInfo = new QSpinBox(this);
    threadInfo->setMinimum(1);
    threadInfo->setMaximum(4);

    startButton = new QPushButton("Start", this);

    inputLayout->addWidget(new QLabel("Start Number"));
    inputLayout->addWidget(startInfo);
    inputLayout->addWidget(new QLabel("End Number"));
    inputLayout->addWidget(endInfo);
    inputLayout->addWidget(new QLabel("Number of Threads to use?"));
    inputLayout->addWidget(threadInfo);
    inputLayout->addWidget(startButton);

    QHBoxLayout *headTitle = new QHBoxLayout();
    headTitle->addWidget(new QLabel("Thread 1"));
    headTitle->addWidget(new QLabel("Thread 2"));
    headTitle->addWidget(new QLabel("Thread 3"));
    headTitle->addWidget(new QLabel("Thread 4"));
    inputLayout->addLayout(headTitle);

    QHBoxLayout *answerLayout = new QHBoxLayout();
    for (int i = 0; i < 4; ++i) {
        QTextEdit *output = new QTextEdit(this);
        output->setReadOnly(true);
        outputVec.push_back(output);
        answerLayout->addWidget(output);
    }
    inputLayout->addLayout(answerLayout);
    setCentralWidget(widget);
    connect(startButton, &QPushButton::clicked, this, &MainWindow::clickStart);
}

void MainWindow::clickStart() {
    int startnum = startInfo->text().toInt();
    int endnum = endInfo->text().toInt();
    int numThreads = threadInfo->value();
    threadStop();
    clearInfo();
    int rangePerThread = (endnum - startnum + 1) / numThreads;
    int startCurrent = startnum;

    activeThreads = numThreads;

    for (int i = 0; i < numThreads; ++i) {
        int endCurrent = (i == numThreads - 1) ? endnum : startCurrent + rangePerThread - 1;
        PrimeFinder *finder = new PrimeFinder(startCurrent, endCurrent, this);

        connect(finder, &PrimeFinder::primeFound, this, [this, i](int prime) {
            QMetaObject::invokeMethod(this, [this, i, prime]() {
                    update(i, prime);
                }, Qt::QueuedConnection);
        });
        connect(finder, &PrimeFinder::finished, this, &MainWindow::threadDone);

        finders.push_back(finder);
        finder->start();
        startCurrent = endCurrent + 1;
    }
}

void MainWindow::threadStop() {

    for (PrimeFinder *finder : finders) {
        finder->stop();
        finder->wait();
        delete finder;
    }
    finders.clear();
}

void MainWindow::threadDone() {
    activeThreads--;
    if (activeThreads == 0) {
    }
}

void MainWindow::clearInfo() {
    for (QTextEdit *edit : outputVec) {
        edit->clear();
    }
}

void MainWindow::update(int threadIndex, int prime) {
    outputVec[threadIndex]->append(QString::number(prime));
}

void MainWindow::closeEvent(QCloseEvent *event) {
    threadStop();
    event->accept();
}
