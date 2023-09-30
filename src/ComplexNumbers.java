import java.util.List;
import java.util.Random;

/**
 * This class generates all possible expressions with numbers from 1 to 9 and operations +, -, *, / and fractions
 */
public class ComplexNumbers {
    /**
     * Min count of numbers and operations in one expression (if DEPTH = 3, then 1 + 2 for example)
     */
    final int minDepth = 3;
    /**
     * Max count of numbers and operations in one expression (if DEPTH = 3, then 1 + 2 for example)
     */
    final int maxDepth;

    final boolean useDecimalNumbers = true;
    /**
     * Current depth of expression
     */
    public int copyDepth = 0;
    public boolean copedDepth = false; // Is depth copied?
    public String path = ""; // String with expression

    ComplexNumbers( int maxDepth){
        this.maxDepth = maxDepth;
    }

    public String[] generate (){ // This method generates expression
        String[] array = new String[2];
        array[0] = randomGenerateExpression(new Random().nextInt(minDepth, maxDepth + 1)); // Generate expression with random depth
        array[1] = Calculator.calculate(array[0]);
        return array;
    }

    public String randomGenerateExpression(int depth) { // This method generates all possible expressions with numbers from 1 to 9 and operations +, -, *, / and fractions
        if (!copedDepth)
        {
            copyDepth = depth;
            copedDepth = true;
            //path = "'" + depth + "' ";
            path = "";
        }
        if (copyDepth >0)
        {
            //generace čísel
            copyDepth -= 1; // Decrease depth
            int maxNumber = 100; // Max number for number
            int maxSqrtNumber = 20; // Max number for square root
            Random numberOrFraction = new Random(); // Random for choosing number or fraction
            Random rnd = new Random(); // Random for choosing number
            Random operators = new Random(); // Random for choosing operator
            int result1 = numberOrFraction.nextInt(10) + 1; // Choosing number or fraction
            double result2 = 0; // Result of number
            double scale = Math.pow(10, 3);
            switch (result1) {
                case 5: case 6: case 7:
                    if(useDecimalNumbers)
                    {
                        result2 = Math.round(rnd.nextDouble(maxNumber-1 + 1 ) + 1 * scale)/scale; path += ("," + result2 + ",");
                    }
                    else
                    {
                        result2 = rnd.nextInt(maxNumber-1 + 1 ) + 1; path += result2;
                    }
                    break;
                case 8:
                    result2 = rnd.nextInt(maxSqrtNumber - 1 + 1) + 1;
                    path += ("#" + result2 + "^2#");
                    break;
                case 9: //result2 = rnd.nextInt(maxNumber-1 + 1 ) + 1; path += ("sqrt(" + Math.pow(result2, 2) + ")");
                    int numbersInExp;
                    String path2 = "";
                    if (copyDepth > 0) {
                        numbersInExp = copyDepth;
                        if (copyDepth > 4)
                            numbersInExp = 4;
                    } else
                        numbersInExp = 1;
                    Random sqrtNumbers = new Random();
                    int choosing2 = sqrtNumbers.nextInt(numbersInExp - 1 + 1) + 1;
                    copyDepth -= choosing2;
                    path += "sqrt(";
                    path = createSquareRoot(choosing2, result2, path, path2, rnd, maxSqrtNumber, operators);
                    path += ")";
                    break;
                case 10:
                    path += ";(";
                    Random numberOfHigherNumbers = new Random();
                    int higherNumber;
                    if (copyDepth > 0)
                        higherNumber = copyDepth;
                    else
                        higherNumber = 1;
                    int choosing = numberOfHigherNumbers.nextInt(higherNumber-1 + 1 ) + 1;
                    copyDepth -= choosing;
                    for (int i = choosing; i > 0; i--) {
                        if (rnd.nextInt(2)+1 == 1) {
                            result2 = rnd.nextInt(maxNumber - 1 + 1) + 1;
                            path += result2;
                        }
                        else {
                            if (useDecimalNumbers == true)
                            {
                                result2 = Math.round(rnd.nextDouble(maxNumber - 1 + 1) + 1 * scale) / scale;
                                path += ("," + result2 + ",");
                            }
                            else
                            {

                                result2 = rnd.nextInt(maxNumber - 1 + 1) + 1;
                                path += result2;
                            }
                        }
                        path = getOperator(path, operators, i);
                    }
                    path += ")/";
                    result2 = rnd.nextInt(maxNumber-1 + 1 ) + 1;
                    path += result2;
                    path += ";";
                    break;
                default:
                    result2 = rnd.nextInt(maxNumber - 1 + 1) + 1;
                    path += result2;
                    break;
            }

            if (copyDepth > 0)
            {
                //operátory

                int operator = operators.nextInt(4) + 1;
                switch(operator)
                {
                    case 1: path+= "+"; break;
                    case 2: path+= "-"; break;
                    case 3: path+= "*"; break;
                    case 4: path+= ":"; break;
                    default: break;
                }
                randomGenerateExpression(copyDepth);
            }
        }

        return path;
    }

    private static String createSquareRoot(int choosing2, double result2, String path, String path2, Random rnd, int maxSqrtNumber, Random operators){
        Random chance = new Random();
        if (choosing2 > 1) {
            for (int i = choosing2; i > 0; i--) {
                result2 = rnd.nextInt(maxSqrtNumber - 1 + 1) + 1;
                if(chance.nextInt(100) <= 20)
                    path2 += (int) Math.pow(result2, 2);
                else
                    path2 += result2;
                path2 = getOperator(path2, operators, i);
            }
        } else {
            result2 = rnd.nextInt(maxSqrtNumber - 1 + 1) + 1;
            path2 += (int) Math.pow(result2, 2);
        }
        if (Calculator.isSquare(path2) &&  Double.parseDouble(Calculator.calculate(path2)) <= 400d) {
            path += path2;
            return path;
        }
        else {
            path2 = "";
            return createSquareRoot(choosing2, result2, path, path2, rnd, maxSqrtNumber, operators);
        }
    }

    static String getOperator(String path, Random operators, int i) {
        if ((i - 1) > 0) {
            int operator = operators.nextInt(3) + 1;
            switch (operator) {
                case 1:
                    path += "+";
                    break;
                case 2:
                    path += "-";
                    break;
                case 3:
                    path += "*";
                    break;
                case 4:
                    path += ":";
                    break;
                default:
                    path += "+";
                    break;
            }
        }
        return path;
    }
}
