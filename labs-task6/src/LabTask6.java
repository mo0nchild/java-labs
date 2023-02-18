import labs.helper.types.LabTaskException;

interface ICalculationFilterable { public boolean filter(int n, double iter); }

public abstract class LabTask6 {
    private final java.lang.Integer n_value;
    private final java.lang.Double e_value;

    public int getNValue() { return this.n_value; }
    public double getEValue() { return this.e_value; }

    public LabTask6(int n_param, double e_param) throws LabTaskException {
        super();
        if(n_param <= 0) throw new LabTaskException("Неверные параметры", this.getClass());

        this.e_value = e_param; this.n_value = n_param;
    }

    public double calculateByN(double x) {
        return calculationFunction((int n, double iter)->{ return n < this.n_value; }, x);
    }

    public double calculateByE(double x) {
        return calculationFunction((int n, double iter)->{ return Math.abs(iter) > e_value; }, x);
    }

    public double calculateByEDiv10(double x) {
        final var comparing_value = Math.abs(e_value / 10.0);
        return calculationFunction((int n, double iter) -> Math.abs(iter) > comparing_value, x);
    }

    abstract double calculateByMath(double x);
    abstract double calculationFunction(ICalculationFilterable helper, double x);

    protected static double factorial(double x) {
        if(x <= 0) return 1;
        return LabTask6.factorial(x - 1) * x;
    }

}

class CosCalculator extends LabTask6 {

    public CosCalculator(int n_param, double e_param) throws LabTaskException {
        super(n_param, e_param);
    }

    @Override
    double calculateByMath(double x) { return Math.cos(x); }


    @Override
    double calculationFunction(ICalculationFilterable helper, double x) {
        for(double result = 1, i = 1; true; i += 1) {

            final var sign = ((i % 2 == 1) ? -1 : 1);
            final var current = sign * (Math.pow(x, i * 2) / LabTask6.factorial(i * 2));

            if (!helper.filter((int)i, current)) return result;
            result += current;
        }
    }
}

class EXCalculator extends LabTask6 {

    public EXCalculator(int n_param, double e_param) throws LabTaskException {
        super(n_param, e_param);
    }

    @Override
    double calculateByMath(double x) { return Math.pow(Math.E, x); }

    @Override
    double calculationFunction(ICalculationFilterable helper, double x) {
        for(double result = 1, i = 1; true; i += 1) {
            final var current = (Math.pow(x, i) / LabTask6.factorial(i));

            if (!helper.filter((int)i, current)) return result;
            result += current;
        }
    }
}
