import java.util.Random;

public class SimpleNumbers {
    final int minDepth = 3; // Min count of numbers and operations in one expression (if DEPTH = 3, then 1 + 2 for example)
    final int maxDepth; // Max count of numbers and operations in one expression (if DEPTH = 3, then 1 + 2 for example)

    final boolean useDecimalNumbers = true;

    public int copyDepth = 0;
    public boolean copedDepth = false;
    public String path = "";

    SimpleNumbers( int maxDepth){
        this.maxDepth = maxDepth;
    }
    SimpleNumbers(){
        this.maxDepth = 5;
    }
    public String[] generate (){
        String[] array = new String[2];
        array[0] = randomGenerateExpression(new Random().nextInt(minDepth, maxDepth + 1));
        array[1] = Calculator.calculate(array[0]);
        return array;
    }


    // This method generates all possible expressions with numbers from 1 to 9 and operations +, -, *, / and fractions
    public String randomGenerateExpression(int depth) {
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
            copyDepth -= 1;
            int maxNumber = 100; // Max number for number
            Random numberOrFraction = new Random();
            Random rnd = new Random();
            Random operators = new Random();
            int result1 = numberOrFraction.nextInt(10) + 1;
            double result2 = 0;
            double scale = Math.pow(10, 3);
            switch (result1)
            {
                case 7: case 8: case 9:
                    if(useDecimalNumbers)
                    {
                        result2 = Math.round(rnd.nextDouble(maxNumber-1 + 1 ) + 1 * scale)/scale; path += ("," + result2 + ",");
                    }
                    else
                    {
                        result2 = rnd.nextInt(maxNumber-1 + 1 ) + 1; path += result2;
                    }
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
                        path = ComplexNumbers.getOperator(path, operators, i);
                    }
                    path += ")/";
                    result2 = rnd.nextInt(maxNumber-1 + 1 ) + 1;
                    path += result2;
                    path += ";";
                    break;
                default: result2 = rnd.nextInt(maxNumber-1 + 1 ) + 1; path += result2; break;
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
}
