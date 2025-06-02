#include "primefinder.h"

PrimeFinder::PrimeFinder(int sRange, int eRange, QObject *parent)
    : QThread(parent), sRange(sRange), eRange(eRange), isStopped(false) {}

void PrimeFinder::run() {
    for (int num = sRange; num <= eRange && !isStopped; ++num) {
        if (isPrime(num)) {
            emit primeFound(num);
        }
    }
    emit finished();
}

void PrimeFinder::stop() {
    isStopped = true;
}

bool PrimeFinder::isPrime(int num) {
    if (num < 2) return false;
    for (int i = 2; i * i <= num; ++i) {
        if (num % i == 0) return false;
    }
    return true;
}
