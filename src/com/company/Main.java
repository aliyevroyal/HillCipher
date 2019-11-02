package com.company;

import java.util.ArrayList;

public class Main {
    private static final String alphabet = "abcdefghijklmnopqrstuvwxyz";

    //................................................................................................................................................................................................
    //Main Method Starts
    public static void main(String[] args) {
        String plainText = "We have a meeting at nine oclock";
        String key = "xiwgmbxzy";

        if (isKeyValid(plainText, key)) {
            System.out.println("Plain Text: " + plainText);
            System.out.println("Hill Cipher Encryption Starts...");
            System.out.println("Encrypted Text: " + encrypt(plainText, key));
            System.out.println("Hill Cipher Decryption Starts...");
            System.out.println("Decrypted Text: " + decrypt(multiplyMatrices(convertTextStringToMatrix(plainText, key), convertKeyStringToMatrix(key)), key));
        } else {
            System.out.println("Your key is not valid, please try another one...");
        }
    }

    //................................................................................................................................................................................................
    //Encrypt Method Starts, Encrypts PlainText to CipherText
    private static String encrypt(String plainText, String key) {
        return convertMatrixToTextString(findMod26OfMatrix(multiplyMatrices(convertTextStringToMatrix(plainText, key), convertKeyStringToMatrix(key))));
    }

    //................................................................................................................................................................................................
    //CreateKeyMatrix Method Starts, Converts Key to Matrix
    private static ArrayList<ArrayList<Double>> convertKeyStringToMatrix(String key) {
        if (Math.sqrt(key.length()) % 1 == 0) {
            ArrayList<ArrayList<Double>> keyMatrixArrayList = new ArrayList<>();
            ArrayList<Double> keyVectorArrayList = new ArrayList<>();

            int keySquareMatrixDimension = (int) Math.sqrt(key.length());

            for (int firstCounter = 0; firstCounter < keySquareMatrixDimension; firstCounter = firstCounter + 1) {
                for (int secondCounter = 0; secondCounter < keySquareMatrixDimension; secondCounter = secondCounter + 1) {
                    keyVectorArrayList.add((double) alphabet.indexOf(key.charAt(0)));
                    key = key.substring(1);
                }
                keyMatrixArrayList.add(keyVectorArrayList);
                keyVectorArrayList = new ArrayList<>();
            }
            return keyMatrixArrayList;
        } else {
            System.out.println("Your key is not able to create a square matrix, please enter key with valid key length.");
            return null;
        }
    }

    //................................................................................................................................................................................................
    //Checks If Key Is Valid
    private static Boolean isKeyValid(String plainText, String key) {
        plainText = plainText.toLowerCase();
        plainText = plainText.replaceAll(" ", "");

        return decrypt(multiplyMatrices(convertTextStringToMatrix(plainText, key), convertKeyStringToMatrix(key)), key).contains(plainText);
    }

    //................................................................................................................................................................................................
    //CreatePlainTextMatrix Method Starts, Converts Plain Text to Matrix
    private static ArrayList<ArrayList<Double>> convertTextStringToMatrix(String text, String key) {
        text = text.toLowerCase();
        text = text.replaceAll(" ", "");

        ArrayList<Double> textVectorArrayList = new ArrayList<>();
        ArrayList<ArrayList<Double>> textMatrixArrayList = new ArrayList<>();

        int keySquareMatrixDimension = (int) Math.sqrt(key.length());
        int plainTextLength = text.length();

        if (plainTextLength % keySquareMatrixDimension != 0) {
            for (int firstCounter = 0; firstCounter < keySquareMatrixDimension - (plainTextLength % keySquareMatrixDimension); firstCounter = firstCounter + 1) {
                text = text + "a";
            }
            plainTextLength = text.length();
        }

        for (int firstCounter = 0; firstCounter < plainTextLength / keySquareMatrixDimension; firstCounter = firstCounter + 1) {
            for (int secondCounter = 0; secondCounter < keySquareMatrixDimension; secondCounter = secondCounter + 1) {
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
    //Decrypt Method Starts, Decrypts CipherText to PlainText
    private static String decrypt(ArrayList<ArrayList<Double>> cipherTextMatrixArrayList, String key) {
        String plainText;

        double[][] keyMatrixArray = new double[convertKeyStringToMatrix(key).size()][convertKeyStringToMatrix(key).size()];
        ArrayList<ArrayList<Double>> invertedKeyMatrixArrayList = new ArrayList<>();
        ArrayList<Double> invertedKeyVectorArrayList = new ArrayList<>();

        ArrayList<Double> plaintTextVectorArrayList = new ArrayList<>();
        ArrayList<ArrayList<Double>> plaintTextMatrixArrayList = new ArrayList<>();

        for (int firstCounter = 0; firstCounter < convertKeyStringToMatrix(key).size(); firstCounter++) {
            for (int secondCounter = 0; secondCounter < convertKeyStringToMatrix(key).size(); secondCounter++) {
                keyMatrixArray[firstCounter][secondCounter] = convertKeyStringToMatrix(key).get(firstCounter).get(secondCounter);
            }
        }

        double[][] invertedKeyMatrixArray = invertMatrix(keyMatrixArray);

        for (int firstCounter = 0; firstCounter < invertedKeyMatrixArray.length; firstCounter++) {
            for (int secondCounter = 0; secondCounter < invertedKeyMatrixArray.length; ++secondCounter) {
                invertedKeyVectorArrayList.add(round(invertedKeyMatrixArray[firstCounter][secondCounter], 6));
            }
            invertedKeyMatrixArrayList.add(invertedKeyVectorArrayList);
            invertedKeyVectorArrayList = new ArrayList<>();
        }

        for (int firstCounter = 0; firstCounter < findMod26OfMatrix(multiplyMatrices(cipherTextMatrixArrayList, findMod26OfMatrix(invertedKeyMatrixArrayList))).size(); firstCounter = firstCounter + 1) {
            for (int secondCounter = 0; secondCounter < findMod26OfMatrix(multiplyMatrices(cipherTextMatrixArrayList, findMod26OfMatrix(invertedKeyMatrixArrayList))).get(firstCounter).size(); secondCounter = secondCounter + 1) {
                if ((int) round(findMod26OfMatrix(multiplyMatrices(cipherTextMatrixArrayList, findMod26OfMatrix(invertedKeyMatrixArrayList))).get(firstCounter).get(secondCounter), 0) == 26) {
                    plaintTextVectorArrayList.add(0.0);
                } else {
                    plaintTextVectorArrayList.add(round(findMod26OfMatrix(multiplyMatrices(cipherTextMatrixArrayList, findMod26OfMatrix(invertedKeyMatrixArrayList))).get(firstCounter).get(secondCounter), 0));
                }
            }
            plaintTextMatrixArrayList.add(plaintTextVectorArrayList);
            plaintTextVectorArrayList = new ArrayList<>();
        }

        plainText = convertMatrixToTextString(plaintTextMatrixArrayList);
        return plainText;
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
    //Inverts Square Matrix
    private static double[][] invertMatrix(double[][] matrixArray) {
        int matrixArrayListDimensions = matrixArray.length;
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
        return backwardSubstitutionsMatrix;
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
}