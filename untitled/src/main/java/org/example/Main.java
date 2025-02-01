package org.example;

import org.json.JSONArray;
import org.json.JSONObject; // اطمینان حاصل کنید که این کتابخانه را اضافه کرده‌اید
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
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

        // دریافت اطلاعات کاربر
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
        System.out.println("Matched Styles:");
        for (int i = 0; i < Math.min(5, matchedStyles.size()); i++) {
            System.out.println((i + 1) + ": " + matchedStyles.get(i).getStyle().getName());
        }

        // انتخاب استایل توسط کاربر
        int selectedStyleIndex = -1;
        while (true) {
            System.out.print("Please select a style by entering the corresponding number (1-5): ");
            selectedStyleIndex = cin.nextInt() - 1; // تبدیل به ایندکس
            cin.nextLine(); // Clear the buffer
            if (selectedStyleIndex >= 0 && selectedStyleIndex < matchedStyles.size()) {
                break;
            } else {
                System.out.println("Invalid selection. Please select a number between 1 and " + matchedStyles.size());
            }
        }

        // ساخت جمله درخواست با استفاده از کلاس person
        style selectedStyle = matchedStyles.get(selectedStyleIndex).getStyle();
        String request = String.format("A %s with a height of %d and a weight of %d, with a %s body shape and a BMI of %.2f that is %s, what items and colors of %s style should she wear for a %s?",
                x.MorF(x.getGender()), x.getHeight(), x.getWeight(), x.getBodyshape(), x.BMI(), x.body(x.BMI()), selectedStyle.getName(), x.getMonasebat());

        // ارسال درخواست به API اولاما
        String response = sendRequestToOpenAI(request);
        System.out.println("Response from OpenAI: " + response);
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
            body = cin.nextLine();
            if (body.equals("rectangle") || body.equals("pear") || body.equals("apple") || body.equals("hourglass")) {
                break;
            } else {
                System.out.println("Please enter your body shape correctly.");
            }
        }
        return body;
    }

    // متد برای ارسال درخواست به OpenAI
    private static String sendRequestToOpenAI(String request) {
        String urlString = "http://localhost:11434/v1/chat/completions"; // آدرس API اولاما

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // ساخت JSON با استفاده از JSONObject
            JSONObject json = new JSONObject();
            json.put("model", "llama3.2"); // مدل شما
            JSONArray messages = new JSONArray();
            JSONObject message = new JSONObject();
            message.put("role", "user");
            message.put("content", request);
            messages.put(message);
            json.put("messages", messages);
            json.put("max_tokens", 150); // یا هر مقدار دیگری که نیاز دارید

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = json.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // بررسی کد وضعیت HTTP
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // خواندن پاسخ
                StringBuilder response = new StringBuilder();
                try (Scanner scanner = new Scanner(conn.getInputStream())) {
                    while (scanner.hasNextLine()) {
                        response.append(scanner.nextLine());
                    }
                }
                return response.toString();
            } else {
                // خواندن بدنه خطا
                StringBuilder errorResponse = new StringBuilder();
                try (Scanner scanner = new Scanner(conn.getErrorStream())) {
                    while (scanner.hasNextLine()) {
                        errorResponse.append(scanner.nextLine());
                    }
                }
                return "Error: Received HTTP response code " + responseCode + ". Message: " + errorResponse.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error communicating with OpenAI API.";
        }
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