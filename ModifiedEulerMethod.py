import numpy as np

# Given differential equation
def f(x, y):
    return -3 * x**2 * y**2

# Analytical solution
def exact_solution(x):
    return 2 / (2*x**3 + 1)

# Modified Euler Method with two corrections
def modified_euler(f, x0, y0, x_end, h):
    n = int((x_end - x0) / h)  # Number of steps
    x = np.zeros(n + 1)
    y = np.zeros(n + 1)
    error = np.zeros(n + 1)

    x[0], y[0] = x0, y0

    for i in range(n):
        x[i+1] = x[i] + h
        # Predictor step (Euler method)
        y_pred = y[i] + h * f(x[i], y[i])
        # First corrector step
        y_corr1 = y[i] + (h / 2) * (f(x[i], y[i]) + f(x[i+1], y_pred))
        # Second corrector step
        y_corr2 = y[i] + (h / 2) * (f(x[i], y[i]) + f(x[i+1], y_corr1))
        
        y[i+1] = y_corr2  # Final corrected value
        
        # Compute the error
        error[i+1] = abs(y[i+1] - exact_solution(x[i+1]))

    return x, y, error

# Solve the equation for h = 0.2 and h = 0.1
x0, y0, x_end = 0, 2, 1
h_values = [0.2, 0.1]

for h in h_values:
    x, y, error = modified_euler(f, x0, y0, x_end, h)
    
    # Print results
    print(f"\nResults for step size h = {h}:")
    print(f"{'x':<8} {'y (Modified Euler)':<20} {'Exact y':<15} {'Error':<15}")
    print("-" * 60)
    
    for i in range(len(x)):
        print(f"{x[i]:<8.2f} {y[i]:<20.6f} {exact_solution(x[i]):<15.6f} {error[i]:<15.6f}")
