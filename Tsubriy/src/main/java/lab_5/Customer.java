package lab_5;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import static jakarta.validation.Validation.buildDefaultValidatorFactory;


/**
 * Клас, що представляє замовника.
 */
@Getter
public class Customer {
    private String lastName;
    private String firstName;
    private String idDocument;
    private LocalDate birthDate;

    public Customer(String lastName, String firstName, String idDocument, LocalDate birthDate) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.idDocument = idDocument;
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "Customer [Last Name: " + lastName + ", First Name: " + firstName +
                ", ID Document: " + idDocument + ", Birth Date: " + birthDate + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(lastName, customer.lastName) &&
                Objects.equals(firstName, customer.firstName) &&
                Objects.equals(idDocument, customer.idDocument) &&
                Objects.equals(birthDate, customer.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName, firstName, idDocument, birthDate);
    }

    /**
     * Патерн Builder для класу Customer з валідацією полів.
     */
    public static class Builder {
        @NotNull(message = "Last name cannot be null")
        @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
        private String lastName;

        @NotNull(message = "First name cannot be null")
        @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
        private String firstName;

        @NotNull(message = "ID Document cannot be null")
        @Pattern(regexp = "\\w{8,12}", message = "ID Document must be 8-12 alphanumeric characters")
        private String idDocument;

        @NotNull(message = "Birth date cannot be null")
        @Past(message = "Birth date must be in the past")
        private LocalDate birthDate;

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setIdDocument(String idDocument) {
            this.idDocument = idDocument;
            return this;
        }

        public Builder setBirthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public Customer build() {
            ValidatorFactory factory = buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<Builder>> violations = validator.validate(this);

            if (!violations.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (ConstraintViolation<Builder> violation : violations) {
                    sb.append("\nField: ").append(violation.getPropertyPath())
                            .append("\nInvalid value: ").append(violation.getInvalidValue())
                            .append("\nProblem: ").append(violation.getMessage())
                            .append("\n");
                }
                throw new IllegalArgumentException(sb.toString());
            }
            return new Customer(lastName, firstName, idDocument, birthDate);
        }
    }
}