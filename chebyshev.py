#Converts a series expansion to a Chebyshev series and compares it with a power series approximation.
# Uses Chebyshev polynomials to approximate a function and compares it with a power series approximation.
import numpy as np
import matplotlib.pyplot as plt
from numpy.polynomial import Chebyshev, Polynomial

def f(x):
    return np.sqrt(1 + x / 5)

def powerSeries(x):
    return 1 + x / 10 - x**2 / 200

def chebyshev_approx(x):
    return chebSeries(x)

domain = [-1, 1]
chebFit = Chebyshev.fit(np.linspace(-1, 1, 200), f(np.linspace(-1, 1, 200)), deg=2,
domain=domain)
chebSeries = chebFit.convert()

xValues = np.linspace(-1, 1, 1000)
exactValues = f(xValues)
powerValues = powerSeries(xValues)
chebValues = chebyshev_approx(xValues)

powerError = np.abs(exactValues - powerValues)
chebError = np.abs(exactValues - chebValues)

maxPowerError = np.max(powerError)
maxChebError = np.max(chebError)

np.set_printoptions(suppress=True)
print("Chebyshev Series for up to T2:")
print(chebSeries)

# Find locations of max errors
powerMax = np.argmax(powerError)
chebMax = np.argmax(chebError)
powerMaxX = xValues[powerMax]
chebMaxX = xValues[chebMax]

print(f"\nMaximum error of power series on [-1,1] for T2: {maxPowerError:.10f} at x = {powerMaxX:.5f}")
print(f"Maximum error of Chebyshev series on [-1,1] for T2: {maxChebError:.10f} at x = {chebMaxX:.5f}")

# Plot: Approximations vs exact function
plt.figure(figsize=(10, 5))
plt.plot(xValues, exactValues, label='Exact $\\sqrt{1 + x/5}$', linewidth=2)
plt.plot(xValues, powerValues, '--', label='Power Series (deg=2)', linewidth=2)
plt.plot(xValues, chebValues, '-.', label='Chebyshev Approx (deg=2)', linewidth=2)
plt.title("Function Approximations")
plt.xlabel("x")
plt.ylabel("f(x)")
plt.legend()
plt.grid(True)

# Plot: Errors
plt.figure(figsize=(10, 5))
plt.plot(xValues, powerError, '--', label='Power Series Error', linewidth=2)
plt.plot(xValues, chebError, '-.', label='Chebyshev Error', linewidth=2)
plt.title("Approximation Errors")
plt.xlabel("x")
plt.ylabel("Absolute Error")
plt.legend()
plt.grid(True)

plt.show()