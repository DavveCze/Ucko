public class Calculator {
    /**
     * This method calculates the result of the expression
     * @param expression Expression
     * @return Result of the expression as String
     */
    public static String calculate(String expression) {
        double scale = Math.pow(10, 3);
        double d = Math.round(eval(expression.trim().replaceAll(":", "/"))*scale) / scale;

        return Double.toString(d);
    }

    /**
     * This method calculates the result of the expression
     * @param expression Expression
     * @return Result of the expression as double
     */
    private static double eval(String expression) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < expression.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (; ; ) {
                    if (eat('+')) x += parseTerm(); // Addition
                    else if (eat('-')) x -= parseTerm(); // Subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (; ; ) {
                    if (eat('*')) x *= parseFactor(); // Multiplication
                    else if (eat('/')) x /= parseFactor(); // Division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // Unary plus
                if (eat('-')) return -parseFactor(); // Unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // Parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // Numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(expression.substring(startPos, this.pos));
                } else if (eat(';')) { // Fraction
                    x=parseExpression();
                    eat(';');
                }
                else if (eat('s')){ // Square root
                    eat('q');
                    eat('r');
                    eat('t');
                    eat('(');
                    x = parseExpression();
                    eat(')');
                    x = Math.sqrt(x);
                } else if (eat('#')){ // Power
                    x = parseExpression();
                    eat('^');
                    eat('2');
                    eat('#');
                    x = Math.pow(x, 2);
                }
                else if (eat(',')){ // Decimal
                    x = parseExpression();
                    if(eat('.')) {
                        x = Double.parseDouble(expression.substring(startPos, this.pos));
                    }
                    eat(',');
                }
                else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }

                return x;
            }

        }.parse();

    }

    public static boolean isSquare(int n) {
        if (n <= 0)
            return false;

        int sqrt = (int) Math.sqrt(n);
        return sqrt * sqrt == n;
    }

    public static boolean isSquare(String expression) {
        return isSquare((int) eval(expression.trim().replaceAll(":", "/")));
    }

    public static boolean isDecimal(String n) {
        return n.substring(n.indexOf('.')).length() > 3;
    }
}

