#ifndef PRIMEFINDER_H
#define PRIMEFINDER_H

#include <QThread>

class PrimeFinder : public QThread {
    Q_OBJECT

public:
    PrimeFinder(int sRange, int eRange, QObject *parent = nullptr);

public slots:
    void stop();

protected:
    void run() override;

private:
    int sRange;
    int eRange;
    bool isStopped;
    bool isPrime(int num);

signals:
    void primeFound(int prime);
    void finished();
};

#endif // PRIMEFINDER_H
