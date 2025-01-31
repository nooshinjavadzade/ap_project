package org.example;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("hi!");
        String gender;
        int age = 0;
        int weight = 0;
        int height = 0;
        String monasebat;
        String body;
        Scanner cin = new Scanner(System.in);
        while (true) {
            System.out.print("Please enter your gender (male or female): ");
            gender = cin.nextLine();
            if (gender.equals("male") || gender.equals("female")) {
                break;
            } else {
                System.out.println("Please enter your gender correctly.");
            }
        }

        boolean flag1 = false;
        while (!flag1) {
            System.out.print("Please enter your age: ");
            try {
                age = cin.nextInt();
                cin.nextLine();
                flag1 = true;
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                cin.nextLine();
            }
        }

        boolean flag2 = false;
        while (!flag2) {
            System.out.print("Please enter your weight: ");
            try {
                weight = cin.nextInt();
                cin.nextLine();
                flag2 = true;
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                cin.nextLine();
            }
        }
        boolean flag3 = false;
        while (!flag3) {
            System.out.print("Please enter your height: ");
            try {
                height = cin.nextInt();
                cin.nextLine();
                flag3 = true;
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                cin.nextLine();
            }
        }
        while (true) {
            System.out.print("What occasion do you want to dress up for? ");
            System.out.println("everyday\n" +
                    "wedding\n" +
                    "office\n" +
                    "sports\n" +
                    "gala\n" +
                    "outdoor\n" +
                    "winter\n" +
                    "beach\n" +
                    "gym\n" +
                    "party\n" +
                    "festival\n" +
                    "concert\n" +
                    "dinner\n" +
                    "casual\n" +
                    "school\n" +
                    "date\n" +
                    "home\n" +
                    "runway");
            System.out.println("Enter the desired option:");
            monasebat = cin.nextLine();
            if (!monasebat.trim().isEmpty()) {
                break;
            } else {
                System.out.println("Please enter a valid occasion.");
            }
        }
        while (true) {
            System.out.print("Please enter your body shape (rectangle, pear, apple, hourglass): ");
            body = cin.nextLine();
            if (body.equals("rectangle") || body.equals("pear") || body.equals("apple") || body.equals("hourglass")) {
                break;
            } else {
                System.out.println("Please enter your body shape correctly.");
            }
        }

        person x = new person(age, height, weight, gender, body, monasebat);
        List<style> styles;
        try {
            styles = StyleLoader.loadStyles("C:/Users/Asus/Desktop/ap/project/fashion.json");
        } catch (IOException e) {
            System.out.println("Error loading styles: " + e.getMessage());
            return;
        }

        List<style> matchedStyles = new ArrayList<>();
        for (style style : styles) {
            if (style.getOccasion().equalsIgnoreCase(monasebat) &&
                    style.getBodyShape().equalsIgnoreCase(body)) {
                matchedStyles.add(style);
            }
        }
        System.out.println("Matched Styles:");
        for (int i = 0; i < Math.min(5, matchedStyles.size()); i++) {
            System.out.println(matchedStyles.get(i));
        }

    }
}