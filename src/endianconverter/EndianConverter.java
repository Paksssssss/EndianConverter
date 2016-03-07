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

    ArrayList<String> readFile(String filename) throws FileNotFoundException {
        Scanner input = new Scanner(new File(filename));
        ArrayList<String> inputList = new ArrayList();
        while (input.hasNext()) {
            inputList.add(input.nextLine());
        }
        return inputList;
    }

    void solve(ArrayList<String> inputs) {
        String currentString;
        for (int i = 0; i < inputs.size(); i++) {
            currentString = inputs.get(i);
            convert(currentString);
        }
    }

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
        for(int i = 0; i<words.size();i++){
            String word = words.get(i);
            for(int j = 0; j<words.get(i).length(); j++){
                list.chars.add(Character.toString(word.charAt(j)));
            }
            if(i!=words.size()-1){
                list.chars.add(" ");
            }
        }
        printBigEndian(list);
        System.out.println("");
        printLittleEndian(list);
    }

    ArrayList<Integer> getInt(String[] inputs) {
        ArrayList<Integer> ints = new ArrayList();
        for (int i = 0; i < inputs.length; i++) {
            try {
                ints.add(Integer.parseInt(String.valueOf(inputs[i])));
            } catch (NumberFormatException e) {
                //do nothing
            }
        }
        return ints;
    }

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
        for(int i = 0;i<list.nums.size();i++){
            System.out.println("0 | 0 | 0 | "+list.nums.get(i)+" | ");
        }
    }
    void printLittleEndian(intCharArray list){
        System.out.println("Little Endian: ");
        StringBuilder sb = new StringBuilder();
        for (int i = 0, ctr = 0; i < list.chars.size()||ctr<4;i++, ctr++) {
            if(ctr == 4){
                System.out.println(sb.reverse().toString());
                ctr = 0;
                sb = new StringBuilder();
            }
            if(ctr<4){
                if(i >= list.chars.size())
                    sb.append(" | 0");
                else{
                    sb.append(" | " + list.chars.get(i)); 
                }
            }
        }
        System.out.println(sb.reverse().toString());
        for(int i = 0;i<list.nums.size();i++){
            System.out.println("0 | 0 | 0 | "+list.nums.get(i)+" | ");
        }
    }
}


class intCharArray{
    ArrayList<Integer> nums = new ArrayList();
    ArrayList<String> chars = new ArrayList();
    intCharArray(){
        //initialization
    }
    intCharArray(ArrayList<Integer> nums,ArrayList<String> chars){
        this.nums = nums;
        this.chars = chars;
    }
}
