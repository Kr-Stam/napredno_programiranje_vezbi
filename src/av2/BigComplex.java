package av2;

import java.math.BigDecimal;

public class BigComplex {
    private BigDecimal real;
    private BigDecimal imaginary;

    public BigComplex(BigDecimal real, BigDecimal imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public BigDecimal getReal() {
        return real;
    }

    public BigDecimal getImaginary() {
        return imaginary;
    }

    public void setReal(BigDecimal real) {
        this.real = real;
    }

    public void setImaginary(BigDecimal imaginary) {
        this.imaginary = imaginary;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(real);
        sb.append("");
        if(imaginary.signum() == -1)
            sb.append(" - ");
        else
            sb.append(" + ");
        sb.append(imaginary);
        sb.append("i");
        return sb.toString();
    }

    public static BigComplex add(BigComplex a, BigComplex b){
        return new BigComplex(a.real.add(b.real), a.imaginary.add(b.imaginary));
    }

    public static BigComplex subtract(BigComplex a, BigComplex b){
        return new BigComplex(a.real.subtract(b.real), a.imaginary.subtract(b.imaginary));
    }

    public static BigComplex multiply(BigComplex a, BigComplex b){
        return new BigComplex(a.real.multiply(b.real), a.imaginary.multiply(b.imaginary).add(a.real.multiply(b.imaginary)).add((a.imaginary).multiply(b.real)));
    }
}
