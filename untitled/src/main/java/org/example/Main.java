package org.example;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileReader;
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
        monasebat = getOccasionInput(cin);
        body = getBodyShapeInput(cin);
        person x = new person(age, height, weight, gender, body, monasebat);
        List<style> styles;
        try {
            styles = StyleLoader.loadStyles("C:/Users/Asus/Desktop/ap/project/fashion.json");
        } catch (IOException e) {
            System.out.println("Error loading styles: " + e.getMessage());
            return;
        }
        List<MatchedStyle> matchedStyles = new ArrayList<>();
        for (style style : styles) {
            int matchCount = 0;
            if (style.getOccasion().equalsIgnoreCase(monasebat)) {
                matchCount++;
            }
            if (style.getBodyShape().equalsIgnoreCase(body)) {
                matchCount++;
            }
            if (age >= style.getMin() && age <= style.getMax()) {
                matchCount++;
            }
            if (matchCount > 0) {
                matchedStyles.add(new MatchedStyle(style, matchCount));
            }
        }
        matchedStyles.sort(Comparator.comparingInt(MatchedStyle::getMatchCount).reversed());
        System.out.println("Best styles for you:");
        for (int i = 0; i < Math.min(5, matchedStyles.size()); i++) {
            style currentStyle = matchedStyles.get(i).getStyle();
            System.out.printf("%d: %s - %s%n", (i + 1), currentStyle.getName(), currentStyle.getDefinition());
        }
        int selectedStyleIndex = -1;
        while (true) {
            System.out.print("Please select a style by entering the corresponding number (1-5): ");
            selectedStyleIndex = cin.nextInt() - 1;
            cin.nextLine();
            if (selectedStyleIndex >= 0 && selectedStyleIndex < matchedStyles.size()) {
                break;
            } else {
                System.out.println("Invalid selection. Please select a number between 1 and " + matchedStyles.size());
            }
        }
        style selectedStyle = matchedStyles.get(selectedStyleIndex).getStyle();
        String request = String.format("a %s with weight %d and height %d and age %d and body shape %s and BMI %.2f for the occasion %s and style %s what items should be worn in what colors?",
                x.MorF(x.getGender()), x.getWeight(), x.getHeight(), x.getAge(), x.getBodyshape(), x.BMI(),x.getMonasebat() , selectedStyle.getName());
        String response = sendRequestToOpenAI(request);
        System.out.println(response);
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
    private static String sendRequestToOpenAI(String request) {
        String urlString = "http://localhost:11434/v1/chat/completions";
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            JSONObject json = new JSONObject();
            json.put("model", "llama3.2");
            JSONArray messages = new JSONArray();
            JSONObject message = new JSONObject();
            message.put("role", "user");
            message.put("content", request);
            messages.put(message);
            json.put("messages", messages);
            json.put("max_tokens", 1000);
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = json.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                StringBuilder response = new StringBuilder();
                try (Scanner scanner = new Scanner(conn.getInputStream())) {
                    while (scanner.hasNextLine()) {
                        response.append(scanner.nextLine());
                    }
                }
                // استخراج محتوای content از پاسخ JSON
                JSONObject jsonResponse = new JSONObject(response.toString());
                String content = jsonResponse.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
                return content; // فقط محتوای پیام را برمی‌گردانیم
            } else {
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