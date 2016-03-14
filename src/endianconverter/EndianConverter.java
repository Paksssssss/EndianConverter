/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endianconverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author paks
 */
public class EndianConverter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        EndianConverter ec = new EndianConverter();
        ArrayList<String> inputs = new ArrayList();
        inputs = ec.readFile("/home/paks/NetBeansProjects/EndianConverter/src/endianconverter/input.txt");
        ec.solve(inputs);
    }

    //Reads input file
    ArrayList<String> readFile(String filename) throws FileNotFoundException {
        Scanner input = new Scanner(new File(filename));
        ArrayList<String> inputList = new ArrayList();
        while (input.hasNext()) {
            inputList.add(input.nextLine());
        }
        return inputList;
    }

    //iterates through the array created by the readfile(function)
    void solve(ArrayList<String> inputs) {
        String currentString;
        for (int i = 0; i < inputs.size(); i++) {
            currentString = inputs.get(i);
            convert(currentString);
        }
    }

    //creates a list of characters in the string and separates the integers
    void convert(String input) {
        intCharArray list = new intCharArray();
        String[] inputs = input.split(" ");
        ArrayList<String> words = new ArrayList();
        for (int i = 0; i < inputs.length; i++) {
            try {
                list.nums.add(Integer.parseInt(inputs[i]));
            } catch (NumberFormatException e) {
                words.add(inputs[i]);
            }
        }
        for (int i = 0; i < words.size(); i++) {
            String word = words.get(i);
            for (int j = 0; j < words.get(i).length(); j++) {
                list.chars.add(Character.toString(word.charAt(j)));
            }
            if (i != words.size() - 1) {
                list.chars.add(" ");
            }
        }
        ArrayList<String> trial = intToBinary(list.nums);
        ArrayList<ArrayList<Integer>> trial2 = convertBinaryArray(trial);
        list.finNums = trial2;
        printBigEndian(list);
        System.out.println("");
        printLittleEndian(list);
    }

    /* function for converting int to binary
     this function loops through the arraylist of integers and 
     converts them to binary strings. These Strings are stored in binaryList*/
    ArrayList<String> intToBinary(ArrayList<Integer> ints) {
        ArrayList<String> binaryList = new ArrayList();
        for (Integer i : ints) {
            String tempString = "";
            for (int tempInt = i; tempInt > 0; tempInt /= 2) {
                tempString = Integer.toString(tempInt % 2) + tempString;
            }
            binaryList.add(tempString);
        }
        return binaryList;
    }

    //converts binary strings to Int
    ArrayList<ArrayList<Integer>> convertBinaryArray(ArrayList<String> binaryList) {
        ArrayList<ArrayList<Integer>> intList = new ArrayList();

        for (String string : binaryList) {
            ArrayList<Integer> lineInts = new ArrayList();
            if (string.length() <= 8) {
                lineInts.add(binaryToInt(string));
            } else {
                int sign = string.length() - 1;
                String tempstr = "";
                for (int i = string.length() - 1, j = 1; i >= 0; i--,j++) {
                    tempstr = string.charAt(i) + tempstr;
                    if (j % 8 == 0) {
                        lineInts.add(binaryToInt(tempstr));
                        tempstr = "";
                    }
                }
                lineInts.add(binaryToInt(tempstr));
            }
            intList.add(lineInts);
        }
        return intList;
    }

    //binary to int
    Integer binaryToInt(String binaryString) {
        int sum = 0;
        for (int i = binaryString.length() - 1, pow = 0; i >= 0; i--, pow++) {
            sum += (int) (Integer.parseInt(Character.toString(binaryString.charAt(i))) * Math.pow(2, pow));
        }
        return sum;
    }

    // prints big endian 
    void printBigEndian(intCharArray list) {
        System.out.println("BIG ENDIAN: ");
        for (int i = 0, ctr = 0; i < list.chars.size() || ctr < 4; i++, ctr++) {
            if (ctr == 4) {
                System.out.println("");
                ctr = 0;
            }
            if (ctr < 4 && i >= list.chars.size()) {
                System.out.print("0 | ");
            } else {
                System.out.print(list.chars.get(i) + " | ");
            }
        }
        System.out.println("");
        for (int i = 0; i < list.finNums.size(); i++) {
            for(int k = 0; k < 4- list.finNums.get(i).size(); k++){
                System.out.print("0 | ");
            }
            for(int j = 0; j < list.finNums.get(i).size() ; j++){
                System.out.print( list.finNums.get(i).get(j) +" | ");
            }
            System.out.println("");
        }
    }

    //prints little endian
    void printLittleEndian(intCharArray list) {
        System.out.println("Little Endian: ");
        StringBuilder sb = new StringBuilder();
        for (int i = 0, ctr = 0; i < list.chars.size() || ctr < 4; i++, ctr++) {
            if (ctr == 4) {
                System.out.println(sb.reverse().toString());
                ctr = 0;
                sb = new StringBuilder();
            }
            if (ctr < 4) {
                if (i >= list.chars.size()) {
                    sb.append(" | 0");
                } else {
                    sb.append(" | " + list.chars.get(i));
                }
            }
        }
        System.out.println(sb.reverse().toString());
        for (int i = 0; i < list.finNums.size(); i++) {
            for(int k = 0; k < 4- list.finNums.get(i).size(); k++){
                System.out.print("0 | ");
            }
            for(int j = 0; j < list.finNums.get(i).size() ; j++){
                System.out.print( list.finNums.get(i).get(j) +" | ");
            }
            System.out.println("");
        }
    }
}

class intCharArray {

    ArrayList<Integer> nums = new ArrayList();
    ArrayList<String> chars = new ArrayList();
    ArrayList<ArrayList<Integer>> finNums = new ArrayList();

    intCharArray() {
        //initialization
    }

    intCharArray(ArrayList<Integer> nums, ArrayList<String> chars) {
        this.nums = nums;
        this.chars = chars;
    }
}
