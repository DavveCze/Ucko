public class MathGeneration {

    static int MAX_DEPTH = 10; // Count of numbers and operations in one expression (if DEPTH = 3, then 1 + 2 for example)

    private static String[] lastResult; // Last generated expression

    // This method generates expression on the basis of type
    static void generateExpression(EXPRESSION_TYPE type){
        switch (type){
            case SIMPLE:
                generateSimpleExpression();
                break;
            case COMPLEX:
                generateComplexExpression();
                break;
        }
    }

    // This method generates simple expression
    private static void generateSimpleExpression(){
        SimpleNumbers simpleNumbers = new SimpleNumbers(MAX_DEPTH); // Create object of class SimpleNumbers
        lastResult = simpleNumbers.generate(); // Generate expression
    }

    // This method generates complex expression
    private static void generateComplexExpression() {
        ComplexNumbers complexNumbers = new ComplexNumbers(MAX_DEPTH); // Create object of class ComplexNumbers
        lastResult = complexNumbers.generate(); // Generate expression
    }

    public static String getLastResult(){
        return lastResult[1]; // Return last generated result
    }

    public static String getLastExpression(){
        return lastResult[0] + " = " + lastResult[1]; // Return last generated expression (expression = result)
    }
}

// This enum contains types of expressions
enum EXPRESSION_TYPE {
    SIMPLE,
    COMPLEX
}
