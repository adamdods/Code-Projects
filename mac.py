# Chebyshev Economization of Polynomials and Error Analysis
# This program computes Chebyshev-economized polynomials of decreasing degree
# and compares their absolute errors to the original 6th-degree polynomial.

import numpy as np

x = np.linspace(-1, 1, 11)

P6 = x + x**2 + x**3/2 + x**4/6 + x**5/24 + x**6/120
T6 = 32 * x**6 - 48 * x**4 + 18 * x**2 - 1
a6 = 1 / 3840

P5 = P6 - a6 * T6
T5 = 16 * x**5 - 20 * x**3 + 5 * x
a5 = 1 / 384

P4 = P5 - a5 * T5
T4 = 8 * x**4 - 8 * x**2 + 1
a4 = 1 / 48

P3 = P4 - a4 * T4

errorP5 = np.abs(P6 - P5)
errorP4 = np.abs(P6 - P4)
errorP3 = np.abs(P6 - P3)

print("Chebyshev Economized Polynomials:")
print("P5(x): P6(x) - (1/3840) * T6(x)")
print("P4(x): P5(x) - (1/384) * T5(x)")
print("P3(x): P4(x) - (1/48) * T4(x)")
print("\nAbsolute Errors Compared to P6(x):")

for xi, e5, e4, e3 in zip(x, errorP5, errorP4, errorP3):
    print(f"x = {xi: .2f} | Error P5 = {e5:.8f} | Error P4 = {e4:.8f} | Error P3 = {e3:.8f}")

tol = 0.01
print("\nError Summary (compared to P6):")
print("P5:", "Good" if np.all(errorP5 < tol) else "Tolerance exceeded")
print("P4:", "Good" if np.all(errorP4 < tol) else "Tolerance exceeded")
print("P3:", "Good" if np.all(errorP3 < tol) else "Tolerance exceeded")