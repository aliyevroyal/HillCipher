package com.company;

import java.util.ArrayList;

public class Main {
    private static final String alphabet = "abcdefghijklmnopqrstuvwxyz";

    //................................................................................................................................................................................................
    //Main Method Starts
    public static void main(String[] args) {
        String plainText = "We have a meeting at nine oclock";
        String key = "abclmnxyz";

        encrypt(plainText, key);
    }

    //................................................................................................................................................................................................
    //Encrypt Method Starts, Encrypts PlainText to CipherText
    private static ArrayList<ArrayList<Integer>> encrypt(String plainText, String key) {
        /*System.out.println(createKeyMatrix(key));
        System.out.println(createPlainTextMatrix(plainText, key));
        System.out.println(findMod26Matrix(multiplyMatrices(createPlainTextMatrix(plainText, key), createKeyMatrix(key))));*/
        convertCipherTextMatrix(findMod26Matrix(multiplyMatrices(createPlainTextMatrix(plainText, key), createKeyMatrix(key))));
        return createPlainTextMatrix(plainText, key);
    }

    //................................................................................................................................................................................................
    //CreateKeyMatrix Method Starts, Converts Key to Matrix
    private static ArrayList<ArrayList<Integer>> createKeyMatrix(String key) {
        if (Math.sqrt(key.length()) % 1 == 0) {
            ArrayList<ArrayList<Integer>> keyMatrixArrayList = new ArrayList<>();
            ArrayList<Integer> vectorArrayList = new ArrayList<>();

            int keySquareMatrixDimension = (int) Math.sqrt(key.length());

            for (int firstCounter = 0; firstCounter < keySquareMatrixDimension; firstCounter = firstCounter + 1) {
                for (int secondCounter = 0; secondCounter < keySquareMatrixDimension; secondCounter = secondCounter + 1) {
                    vectorArrayList.add(alphabet.indexOf(key.charAt(0)));
                    key = key.substring(1);
                }
                keyMatrixArrayList.add(vectorArrayList);
                vectorArrayList = new ArrayList<>();
            }
            return keyMatrixArrayList;
        } else {
            System.out.println("Your key is not able to create a square matrix, please enter key with valid key length.");
            return null;
        }
    }

    //................................................................................................................................................................................................
    //CreatePlainTextMatrix Method Starts, Converts Plain Text to Matrix
    private static ArrayList<ArrayList<Integer>> createPlainTextMatrix(String plainText, String key) {
        plainText = plainText.toLowerCase();
        plainText = plainText.replaceAll(" ", "");

        ArrayList<ArrayList<Integer>> cipherTextMatrixArrayList = new ArrayList<>();
        ArrayList<Integer> plainTextVectorArrayList = new ArrayList<>();
        ArrayList<ArrayList<Integer>> plainTextMatrixArrayList = new ArrayList<>();

        int keySquareMatrixDimension = (int) Math.sqrt(key.length());
        int plainTextLength = plainText.length();

        if (plainTextLength % keySquareMatrixDimension != 0) {
            for (int firstCounter = 0; firstCounter < keySquareMatrixDimension - (plainTextLength % keySquareMatrixDimension); firstCounter = firstCounter + 1) {
                plainText = plainText + "a";
            }
            plainTextLength = plainText.length();
        }

        for (int firstCounter = 0; firstCounter < plainTextLength / keySquareMatrixDimension; firstCounter = firstCounter + 1) {
            for (int secondCounter = 0; secondCounter < keySquareMatrixDimension; secondCounter = secondCounter + 1) {
                plainTextVectorArrayList.add(alphabet.indexOf(plainText.charAt(0)));
                plainText = plainText.substring(1);
            }
            plainTextMatrixArrayList.add(plainTextVectorArrayList);
            plainTextVectorArrayList = new ArrayList<>();
        }
        return plainTextMatrixArrayList;
    }

    //................................................................................................................................................................................................
    //MultiplyMatrices Method Starts, Multiply Two Matrices with Different Dimensions
    private static ArrayList<ArrayList<Integer>> multiplyMatrices(ArrayList<ArrayList<Integer>> firstMatrixArraylist, ArrayList<ArrayList<Integer>> secondMatrixArraylist) {
        int theNumberOfRowsInFirstMatrixArraylist = 0;
        int theNumberOfColumnsInFirstMatrixArraylist = 0;
        int theNumberOfRowsInSecondMatrixArraylist = 0;
        int theNumberOfColumnsInSecondMatrixArraylist = 0;

        ArrayList<ArrayList<Integer>> cipherTextMatrixArrayList = new ArrayList<>();
        ArrayList<Integer> cipherTextVectorArrayList = new ArrayList<>();

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

        int[][] cipherTextMatrixArray = new int[theNumberOfRowsInFirstMatrixArraylist][theNumberOfColumnsInSecondMatrixArraylist];

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
                cipherTextVectorArrayList.add(cipherTextMatrixArray[firstCounter][secondCounter]);
            }
            cipherTextMatrixArrayList.add(cipherTextVectorArrayList);
            cipherTextVectorArrayList = new ArrayList<>();
        }
        return cipherTextMatrixArrayList;
    }

    //................................................................................................................................................................................................
    //FindMod26Matrix Method Starts, Finds All Mod 26 of All Elements of Matrix
    private static ArrayList<ArrayList<Integer>> findMod26Matrix(ArrayList<ArrayList<Integer>> cipherTextMatrixArrayList) {
        for (int firstCounter = 0; firstCounter < cipherTextMatrixArrayList.size(); firstCounter = firstCounter + 1) {
            for (int secondCounter = 0; secondCounter < cipherTextMatrixArrayList.get(firstCounter).size(); secondCounter = secondCounter + 1) {
                cipherTextMatrixArrayList.get(firstCounter).set(secondCounter, cipherTextMatrixArrayList.get(firstCounter).get(secondCounter) % 26);
            }
        }
        return cipherTextMatrixArrayList;
    }

    //................................................................................................................................................................................................
    //ConvertCipherTextMatrix Method Starts, Converts Cipher Text Matrix to String
    private static String convertCipherTextMatrix(ArrayList<ArrayList<Integer>> cipherTextMatrixArrayList) {
        String ciphetText = "";

        for (int firstCounter = 0; firstCounter < cipherTextMatrixArrayList.size(); firstCounter = firstCounter + 1) {
            for (int secondCounter = 0; secondCounter < cipherTextMatrixArrayList.get(firstCounter).size(); secondCounter = secondCounter + 1) {
                ciphetText = ciphetText + alphabet.charAt(cipherTextMatrixArrayList.get(firstCounter).get(secondCounter));
            }
        }
        System.out.println(cipherTextMatrixArrayList);
        System.out.println(ciphetText);
        return ciphetText;
    }

    //................................................................................................................................................................................................
    //Decrypt Method Starts, Decrypts CipherText to PlainText
    private static String decrypt(String cipherText, String key) {
        String plainText = null;

        return plainText;
    }
}
