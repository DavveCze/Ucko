public class Main {
    public static void main(String[] args) {
        //System.out.println("Result: " + Calculator.calculate("1+1*5+;(2+1)/2;"));
        //System.out.println("Result: " + Calculator.calculate(";(#2^2#+5)/3;"));
        //System.out.println("Result: " + Calculator.calculate(",1.002,*;(,9.0,+9.0*,8.0,+8.0+,1.005,+1.005+,6.0,+6.0-,9.0,+9.0-,9.0,+9.0)/7.0;:4.0+2.0"));
        for (int i = 0; i < 1; i++) {
            MathGeneration.generateExpression(EXPRESSION_TYPE.SIMPLE);
            System.out.println(MathGeneration.getLastExpression());
        }
        /*System.out.println("\nSložité");
        for (int i = 0; i < 5; i++) {
            MathGeneration.generateExpression(EXPRESSION_TYPE.COMPLEX);
            System.out.println(MathGeneration.getLastExpression());
        }*/
    }
}