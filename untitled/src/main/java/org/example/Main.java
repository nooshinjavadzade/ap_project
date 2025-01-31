package org.example;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
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

        age = getIntInput(cin, "Please enter your age: ");
        weight = getIntInput(cin, "Please enter your weight: ");
        height = getIntInput(cin, "Please enter your height: ");

        // دریافت مناسبت
        monasebat = getOccasionInput(cin);

        // دریافت شکل بدن
        body = getBodyShapeInput(cin);

        // ساخت شیء شخص
        person x = new person(age, height, weight, gender, body, monasebat);

        // بارگذاری استایل‌ها
        List<style> styles;
        try {
            styles = StyleLoader.loadStyles("C:/Users/Asus/Desktop/ap/project/fashion.json");
        } catch (IOException e) {
            System.out.println("Error loading styles: " + e.getMessage());
            return;
        }

        // پیدا کردن استایل‌های مطابق
        List<MatchedStyle> matchedStyles = new ArrayList<>();
        for (style style : styles) {
            int matchCount = 0;

            // بررسی تطابق‌ها
            if (style.getOccasion().equalsIgnoreCase(monasebat)) {
                matchCount++;
            }
            if (style.getBodyShape().equalsIgnoreCase(body)) {
                matchCount++;
            }
            if (age >= style.getMin() && age <= style.getMax()) {
                matchCount++;
            }

            // اگر حداقل یک معیار تطابق دارد، آن را اضافه کنید
            if (matchCount > 0) {
                matchedStyles.add(new MatchedStyle(style, matchCount));
            }
        }

        // مرتب‌سازی بر اساس تعداد تطابق‌ها
        matchedStyles.sort(Comparator.comparingInt(MatchedStyle::getMatchCount).reversed());

        // چاپ پنج استایل برتر
        System.out.println("The best styles for you:");
        for (int i = 0; i < Math.min(5, matchedStyles.size()); i++) {
            System.out.println((i+1) + "." + matchedStyles.get(i).getStyle());
        }
        System.out.println("Enter the style you want and I will tell you what items you should wear: ");
    }

    // متد برای دریافت ورودی عددی
    private static int getIntInput(Scanner cin, String prompt) {
        int value = 0;
        boolean valid = false;
        while (!valid) {
            System.out.print(prompt);
            try {
                value = cin.nextInt();
                cin.nextLine(); // Clear the buffer
                valid = true;
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                cin.nextLine(); // Clear invalid input
            }
        }
        return value;
    }

    // متد برای دریافت مناسبت
    private static String getOccasionInput(Scanner cin) {
        String occasion;
        while (true) {
            System.out.print("What occasion do you want to dress up for? ");
            System.out.println("everyday\nwedding\noffice\nsports\ngala\noutdoor\nwinter\nbeach\ngym\nparty\nfestival\nconcert\ndinner\ncasual\nschool\ndate\nhome\nrunway");
            occasion = cin.nextLine();
            if (!occasion.trim().isEmpty()) {
                break;
            } else {
                System.out.println("Please enter a valid occasion.");
            }
        }
        return occasion;
    }

    // متد برای دریافت شکل بدن
    private static String getBodyShapeInput(Scanner cin) {
        String body;
        while (true) {
            System.out.print("Please enter your body shape (rectangle, pear, apple, hourglass): ");
            System.out.println("(If you don't know what your body shape is, enter 'help')");
            body = cin.nextLine();
            if (body.equals("rectangle") || body.equals("pear") || body.equals("apple") || body.equals("hourglass")) {
                break;
            } else if (body.equals("help")) {
                String filePath = "C:\\\\Users\\\\Asus\\\\Desktop\\\\ap\\\\project\\\\bodyShape.txt";
                try (BufferedReader br = new BufferedReader(new FileReader(filePath))){
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                    }

                }catch (IOException e) {
                    System.out.println("Error reading file: " + e.getMessage());
                }
            } else {
                System.out.println("Please enter your body shape correctly.");
            }
        }
        return body;
    }
}

// کلاس برای نگهداری استایل و تعداد تطابق
class MatchedStyle {
    private style style;
    private int matchCount;

    public MatchedStyle(style style, int matchCount) {
        this.style = style;
        this.matchCount = matchCount;
    }

    public style getStyle() {
        return style;
    }

    public int getMatchCount() {
        return matchCount;
    }
}