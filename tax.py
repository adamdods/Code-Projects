def calculate_tax(income):
    # Tax brackets for 2024 (South Africa)
    tax_brackets = [
        (237100, 0.18, 0),           # Up to R237,100
        (370500, 0.26, 42678),       # R237,101 to R370,500
        (512800, 0.31, 77362),       # R370,501 to R512,800
        (673000, 0.36, 121475),      # R512,801 to R673,000
        (857900, 0.39, 179625),      # R673,001 to R857,900
        (1817000, 0.41, 251775),     # R857,901 to R1,817,000
        (float('inf'), 0.45, 784894) # Above R1,817,000
    ]
    
    tax = 0
    for bracket in tax_brackets:
        if income <= bracket[0]:
            # Calculate tax based on the bracket
            tax = bracket[2] + bracket[1] * (income - (tax_brackets[tax_brackets.index(bracket) - 1][0] if tax_brackets.index(bracket) > 0 else 0))
            break

    return tax

# Get the user's income
income = float(input("Enter your annual income (in Rands): R"))

# Calculate the tax
tax_to_pay = calculate_tax(income)

# Print the result
print(f"Your annual tax payable is: R{tax_to_pay:.2f}")
