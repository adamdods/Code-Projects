#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QThread>
#include <QVector>
#include <QTextEdit>
#include <QLineEdit>
#include <QSpinBox>
#include <QPushButton>

class PrimeFinder;

class MainWindow : public QMainWindow {
    Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = nullptr);

protected:
    void closeEvent(QCloseEvent *event) override;

private:
    void ui();
    void threadStop();
    void clearInfo();
    QLineEdit *startInfo;
    QLineEdit *endInfo;
    QSpinBox *threadInfo;
    QPushButton *startButton;
    QVector<QTextEdit*> outputVec;
    QVector<PrimeFinder*> finders;
    int activeThreads;

private slots:
    void clickStart();
    void threadDone();
    void update(int threadIndex, int prime);
};

#endif // MAINWINDOW_H
