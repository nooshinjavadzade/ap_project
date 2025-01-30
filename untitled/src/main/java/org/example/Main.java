package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("hi!");
        String gender;
        int age = 0;
        int weight = 0;
        int height = 0;
        Scanner cin = new Scanner(System.in);
        while (true){
            System.out.println("Please enter your gender(male or female): ");
            gender = cin.nextLine();
            if(gender.equals("male")||gender.equals("female")){
                break;
            }
            else{
                System.out.println("Please enter your gender correctly.");
                continue;
            }
        }
        boolean flag1 = false;
        while (!flag1){
            System.out.print("Please enter your age: ");
            try {
                age = cin.nextInt();
                flag1 = true;
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
        boolean flag2 = false;
        while (!flag2){
            System.out.print("Please enter your weight: ");
            try {
                weight = cin.nextInt();
                flag2 = true;
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
        boolean flag3 = false;
        while (!flag3){
            System.out.print("Please enter your height: ");
            try {
                height = cin.nextInt();
                flag3 = true;
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }

    }
}