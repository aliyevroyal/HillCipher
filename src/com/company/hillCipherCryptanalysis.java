package com.company;

import java.util.ArrayList;

class hillCipherCryptanalysis {
    private static final String alphabet = "abcdefghijklmnopqrstuvwxyz";

    //................................................................................................................................................................................................
    //Main Method Starts
    public static void main(String[] args) {
        String knownPartOfPlainText = "We have a me";
        String knownPartOfCipherText = "pjgkonike";

        ArrayList<ArrayList<Double>> dividerFromMod26MatrixArrayList = new ArrayList<>();
        ArrayList<Double> dividerFromMod26VectorArrayList = new ArrayList<>();

        dividerFromMod26VectorArrayList.add(26.0);
        dividerFromMod26VectorArrayList.add(15.0);
        dividerFromMod26VectorArrayList.add(25.0);
        dividerFromMod26MatrixArrayList.add(dividerFromMod26VectorArrayList);
        dividerFromMod26VectorArrayList = new ArrayList<>();

        dividerFromMod26VectorArrayList.add(8.0);
        dividerFromMod26VectorArrayList.add(13.0);
        dividerFromMod26VectorArrayList.add(4.0);
        dividerFromMod26MatrixArrayList.add(dividerFromMod26VectorArrayList);
        dividerFromMod26VectorArrayList = new ArrayList<>();

        dividerFromMod26VectorArrayList.add(6.0);
        dividerFromMod26VectorArrayList.add(9.0);
        dividerFromMod26VectorArrayList.add(4.0);
        dividerFromMod26MatrixArrayList.add(dividerFromMod26VectorArrayList);

        ArrayList<ArrayList<Double>> cryptAnalysisKeyMatrixArrayLis = multiplyMatrices(invertMatrix(convertTextStringToMatrix(knownPartOfPlainText)), multiplyWithDividerAndSumWithRemainder(convertTextStringToMatrix(knownPartOfCipherText), dividerFromMod26MatrixArrayList));

        for (int firstCounter = 0; firstCounter < cryptAnalysisKeyMatrixArrayLis.size(); firstCounter = firstCounter + 1) {
            for (int secondCounter = 0; secondCounter < cryptAnalysisKeyMatrixArrayLis.get(firstCounter).size(); secondCounter = secondCounter + 1) {
                cryptAnalysisKeyMatrixArrayLis.get(firstCounter).set(secondCounter, round(cryptAnalysisKeyMatrixArrayLis.get(firstCounter).get(secondCounter), 0));
            }
        }

        System.out.println(convertMatrixToTextString(findMod26OfMatrix(cryptAnalysisKeyMatrixArrayLis)));
    }

    //................................................................................................................................................................................................
    //CreatePlainTextMatrix Method Starts, Converts Plain Text to Matrix
    private static ArrayList<ArrayList<Double>> convertTextStringToMatrix(String text) {
        text = text.toLowerCase();
        text = text.replaceAll(" ", "");

        ArrayList<Double> textVectorArrayList = new ArrayList<>();
        ArrayList<ArrayList<Double>> textMatrixArrayList = new ArrayList<>();

        int plainTextLength = (int) Math.sqrt(text.length());

        for (int firstCounter = 0; firstCounter < plainTextLength; firstCounter = firstCounter + 1) {
            for (int secondCounter = 0; secondCounter < plainTextLength; secondCounter = secondCounter + 1) {
                textVectorArrayList.add((double) alphabet.indexOf(text.charAt(0)));
                text = text.substring(1);
            }
            textMatrixArrayList.add(textVectorArrayList);
            textVectorArrayList = new ArrayList<>();
        }
        return textMatrixArrayList;
    }

    //................................................................................................................................................................................................
    //MultiplyMatrices Method Starts, Multiply Two Matrices with Different Dimensions
    private static ArrayList<ArrayList<Double>> multiplyMatrices(ArrayList<ArrayList<Double>> firstMatrixArraylist, ArrayList<ArrayList<Double>> secondMatrixArraylist) {
        int theNumberOfRowsInFirstMatrixArraylist = 0;
        int theNumberOfColumnsInFirstMatrixArraylist = 0;
        int theNumberOfRowsInSecondMatrixArraylist = 0;
        int theNumberOfColumnsInSecondMatrixArraylist = 0;

        ArrayList<ArrayList<Double>> multiplicationMatrixArrayList = new ArrayList<>();
        ArrayList<Double> multiplicationVectorArrayList = new ArrayList<>();

        for (int counter = 0; counter < firstMatrixArraylist.size(); counter = counter + 1) {
            theNumberOfRowsInFirstMatrixArraylist = theNumberOfRowsInFirstMatrixArraylist + 1;
        }

        for (int counter = 0; counter < firstMatrixArraylist.get(1).size(); counter = counter + 1) {
            theNumberOfColumnsInFirstMatrixArraylist = theNumberOfColumnsInFirstMatrixArraylist + 1;
        }

        for (int counter = 0; counter < secondMatrixArraylist.size(); counter = counter + 1) {
            theNumberOfRowsInSecondMatrixArraylist = theNumberOfRowsInSecondMatrixArraylist + 1;
        }

        for (int counter = 0; counter < secondMatrixArraylist.get(1).size(); counter = counter + 1) {
            theNumberOfColumnsInSecondMatrixArraylist = theNumberOfColumnsInSecondMatrixArraylist + 1;
        }

        double[][] cipherTextMatrixArray = new double[theNumberOfRowsInFirstMatrixArraylist][theNumberOfColumnsInSecondMatrixArraylist];

        for (int firstCounter = 0; firstCounter < theNumberOfRowsInFirstMatrixArraylist; ++firstCounter) {
            for (int secondCounter = 0; secondCounter < theNumberOfColumnsInSecondMatrixArraylist; ++secondCounter) {
                cipherTextMatrixArray[firstCounter][secondCounter] = 0;
                for (int thirdCounter = 0; thirdCounter < theNumberOfColumnsInFirstMatrixArraylist; ++thirdCounter) {
                    cipherTextMatrixArray[firstCounter][secondCounter] += firstMatrixArraylist.get(firstCounter).get(thirdCounter) * secondMatrixArraylist.get(thirdCounter).get(secondCounter);
                }
            }
        }

        for (int firstCounter = 0; firstCounter < theNumberOfRowsInFirstMatrixArraylist; ++firstCounter) {
            for (int secondCounter = 0; secondCounter < theNumberOfColumnsInSecondMatrixArraylist; ++secondCounter) {
                multiplicationVectorArrayList.add(cipherTextMatrixArray[firstCounter][secondCounter]);
            }
            multiplicationMatrixArrayList.add(multiplicationVectorArrayList);
            multiplicationVectorArrayList = new ArrayList<>();
        }

        return multiplicationMatrixArrayList;
    }

    //................................................................................................................................................................................................
    //FindMod26Matrix Method Starts, Finds All Mod 26 of All Elements of Matrix
    private static ArrayList<ArrayList<Double>> multiplyWithDividerAndSumWithRemainder(ArrayList<ArrayList<Double>> matrixArrayList, ArrayList<ArrayList<Double>> dividerFromMod26MatrixArrayList) {
        for (int firstCounter = 0; firstCounter < matrixArrayList.size(); firstCounter = firstCounter + 1) {
            for (int secondCounter = 0; secondCounter < matrixArrayList.get(firstCounter).size(); secondCounter = secondCounter + 1) {
                matrixArrayList.get(firstCounter).set(secondCounter, (matrixArrayList.get(firstCounter).get(secondCounter)) + dividerFromMod26MatrixArrayList.get(firstCounter).get(secondCounter) * 26);
            }
        }
        return matrixArrayList;
    }

    //................................................................................................................................................................................................
    //ConvertCipherTextMatrix Method Starts, Converts Cipher Text Matrix to String
    private static String convertMatrixToTextString(ArrayList<ArrayList<Double>> textMatrixArrayList) {
        String text = "";
        Double nthElememntDouble;
        int nthElememntInt;

        for (int firstCounter = 0; firstCounter < textMatrixArrayList.size(); firstCounter = firstCounter + 1) {
            for (int secondCounter = 0; secondCounter < textMatrixArrayList.get(firstCounter).size(); secondCounter = secondCounter + 1) {
                nthElememntDouble = new Double(textMatrixArrayList.get(firstCounter).get(secondCounter));
                nthElememntInt = nthElememntDouble.intValue();
                text = text + alphabet.charAt(nthElememntInt);
            }
        }
        return text;
    }

    //................................................................................................................................................................................................
    //Inverts Square Matrix
    private static ArrayList<ArrayList<Double>> invertMatrix(ArrayList<ArrayList<Double>> matrixArrayList) {

        double[][] matrixArray = new double[matrixArrayList.size()][matrixArrayList.size()];

        for (int firstCounter = 0; firstCounter < matrixArrayList.size(); firstCounter = firstCounter + 1) {
            for (int secondCounter = 0; secondCounter < matrixArrayList.size(); secondCounter = secondCounter + 1) {
                matrixArray[firstCounter][secondCounter] = matrixArrayList.get(firstCounter).get(secondCounter);
            }
        }

        int matrixArrayListDimensions = matrixArrayList.size();
        double[][] backwardSubstitutionsMatrix = new double[matrixArrayListDimensions][matrixArrayListDimensions];
        double[][] ratiosMatrix = new double[matrixArrayListDimensions][matrixArrayListDimensions];
        int[] indexOfMatrix = new int[matrixArrayListDimensions];

        for (int counter = 0; counter < matrixArrayListDimensions; ++counter) {
            ratiosMatrix[counter][counter] = 1;
        }

        findGaussJordanElimination(matrixArray, indexOfMatrix);

        for (int firstCounter = 0; firstCounter < matrixArrayListDimensions - 1; ++firstCounter)
            for (int secondCounter = firstCounter + 1; secondCounter < matrixArrayListDimensions; ++secondCounter)
                for (int thirdCounter = 0; thirdCounter < matrixArrayListDimensions; ++thirdCounter)
                    ratiosMatrix[indexOfMatrix[secondCounter]][thirdCounter]
                            -= matrixArray[indexOfMatrix[secondCounter]][firstCounter] * ratiosMatrix[indexOfMatrix[firstCounter]][thirdCounter];

        for (int firstCounter = 0; firstCounter < matrixArrayListDimensions; ++firstCounter) {
            backwardSubstitutionsMatrix[matrixArrayListDimensions - 1][firstCounter] = ratiosMatrix[indexOfMatrix[matrixArrayListDimensions - 1]][firstCounter] / matrixArray[indexOfMatrix[matrixArrayListDimensions - 1]][matrixArrayListDimensions - 1];
            for (int secondCounter = matrixArrayListDimensions - 2; secondCounter >= 0; --secondCounter) {
                backwardSubstitutionsMatrix[secondCounter][firstCounter] = ratiosMatrix[indexOfMatrix[secondCounter]][firstCounter];
                for (int thirdCounter = secondCounter + 1; thirdCounter < matrixArrayListDimensions; ++thirdCounter) {
                    backwardSubstitutionsMatrix[secondCounter][firstCounter] -= matrixArray[indexOfMatrix[secondCounter]][thirdCounter] * backwardSubstitutionsMatrix[thirdCounter][firstCounter];
                }
                backwardSubstitutionsMatrix[secondCounter][firstCounter] /= matrixArray[indexOfMatrix[secondCounter]][secondCounter];
            }
        }

        ArrayList<ArrayList<Double>> invertedMatrixArrayList = new ArrayList<>();
        ArrayList<Double> invertedVectorArrayList = new ArrayList<>();

        for (int firstCounter = 0; firstCounter < backwardSubstitutionsMatrix.length; firstCounter = firstCounter + 1) {
            for (int secondCounter = 0; secondCounter < backwardSubstitutionsMatrix.length; secondCounter = secondCounter + 1) {
                invertedVectorArrayList.add(round(backwardSubstitutionsMatrix[firstCounter][secondCounter], 5));
            }
            invertedMatrixArrayList.add(invertedVectorArrayList);
            invertedVectorArrayList = new ArrayList<>();
        }

        return invertedMatrixArrayList;
    }

    //................................................................................................................................................................................................
    //Rounds Double Variables
    private static double round(double variable, int index) {
        if (index < 0) {
            throw new IllegalArgumentException();
        }
        long factor = (long) Math.pow(10, index);
        variable = variable * factor;
        long tmp = Math.round(variable);
        return (double) tmp / factor;
    }

    //................................................................................................................................................................................................
    //Find Gauss Elemination of Matrix to Calculate Inverse of Matrix
    private static void findGaussJordanElimination(double[][] matrixArray, int[] indexOfElementArray) {
        int indexOfElementLength = indexOfElementArray.length;
        double[] indexOfElementLengthArray = new double[indexOfElementLength];

        for (int counter = 0; counter < indexOfElementLength; ++counter) {
            indexOfElementArray[counter] = counter;
        }

        for (int firstCounter = 0; firstCounter < indexOfElementLength; ++firstCounter) {
            double secondRescalingFactor = 0;
            for (int secondCounter = 0; secondCounter < indexOfElementLength; ++secondCounter) {
                double firstRescalingFactor = Math.abs(matrixArray[firstCounter][secondCounter]);
                if (firstRescalingFactor > secondRescalingFactor) {
                    secondRescalingFactor = firstRescalingFactor;
                }
            }
            indexOfElementLengthArray[firstCounter] = secondRescalingFactor;
        }

        int pivotingElement = 0;
        for (int firstCounter = 0; firstCounter < indexOfElementLength - 1; ++firstCounter) {
            double secondPivotingElement = 0;
            for (int secondCounter = firstCounter; secondCounter < indexOfElementLength; ++secondCounter) {
                double firstPivotingElement = Math.abs(matrixArray[indexOfElementArray[secondCounter]][firstCounter]);
                firstPivotingElement /= indexOfElementLengthArray[indexOfElementArray[secondCounter]];
                if (firstPivotingElement > secondPivotingElement) {
                    secondPivotingElement = firstPivotingElement;
                    pivotingElement = secondCounter;
                }
            }

            int pivotingOrder = indexOfElementArray[firstCounter];
            indexOfElementArray[firstCounter] = indexOfElementArray[pivotingElement];
            indexOfElementArray[pivotingElement] = pivotingOrder;
            for (int thirdCounter = firstCounter + 1; thirdCounter < indexOfElementLength; ++thirdCounter) {
                double thirdCounterTHElement = matrixArray[indexOfElementArray[thirdCounter]][firstCounter] / matrixArray[indexOfElementArray[firstCounter]][firstCounter];
                matrixArray[indexOfElementArray[thirdCounter]][firstCounter] = thirdCounterTHElement;
                for (int fourthCounter = firstCounter + 1; fourthCounter < indexOfElementLength; ++fourthCounter) {
                    matrixArray[indexOfElementArray[thirdCounter]][fourthCounter] -= thirdCounterTHElement * matrixArray[indexOfElementArray[firstCounter]][fourthCounter];
                }
            }
        }
    }

    //................................................................................................................................................................................................
    //FindMod26Matrix Method Starts, Finds All Mod 26 of All Elements of Matrix
    private static ArrayList<ArrayList<Double>> findMod26OfMatrix(ArrayList<ArrayList<Double>> matrixArrayList) {
        for (int firstCounter = 0; firstCounter < matrixArrayList.size(); firstCounter = firstCounter + 1) {
            for (int secondCounter = 0; secondCounter < matrixArrayList.get(firstCounter).size(); secondCounter = secondCounter + 1) {
                if (matrixArrayList.get(firstCounter).get(secondCounter) % 26 < 0) {
                    matrixArrayList.get(firstCounter).set(secondCounter, matrixArrayList.get(firstCounter).get(secondCounter) % 26 + 26);
                } else {
                    matrixArrayList.get(firstCounter).set(secondCounter, matrixArrayList.get(firstCounter).get(secondCounter) % 26);
                }
            }
        }
        return matrixArrayList;
    }
}