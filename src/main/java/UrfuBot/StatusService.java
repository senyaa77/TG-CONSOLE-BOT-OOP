package UrfuBot;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class StatusService {

    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Универсальный метод получения статуса сервиса.
     *
     * @param url          URL summary.json statuspage
     * @param title        Заголовок (например, "🟦 *Discord Status*")
     * @param errorMessage Сообщение об ошибке
     * @return Отформатированная строка для отправки в Telegram/Discord
     */
    public static String getServiceStatus(String url, String title, String errorMessage) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            JsonNode root = MAPPER.readTree(response.body());

            String overall = root.path("status").path("description").asText();

            StringBuilder sb = new StringBuilder();
            sb.append(title).append("\n")
                    .append("Общее состояние: ").append(overall).append("\n\n")
                    .append("📌 *Компоненты:*\n");

            // Компоненты
            for (JsonNode c : root.path("components")) {
                sb.append("• ")
                        .append(c.path("name").asText())
                        .append(" — ")
                        .append(c.path("status").asText())
                        .append("\n");
            }

            // Инциденты
            JsonNode incidents = root.path("incidents");
            if (incidents.isArray() && incidents.size() > 0) {
                sb.append("\n⚠️ *Активные инциденты:*\n");
                for (JsonNode inc : incidents) {
                    sb.append("• ")
                            .append(inc.path("name").asText())
                            .append(" — ")
                            .append(inc.path("status").asText())
                            .append("\n");
                }
            } else {
                sb.append("\n✔️ Активных инцидентов нет.");
            }

            return sb.toString();

        } catch (IOException | InterruptedException e) {
            return errorMessage;
        }
    }

    // Обёртки для конкретных сервисов:

    public static String getDiscordStatus() {
        return getServiceStatus(
                "https://discordstatus.com/api/v2/summary.json",
                "🟦 *Discord Status*",
                "❌ Ошибка получения статуса Discord."
        );
    }

    public static String getBrawlStarsStatus() {
        return getServiceStatus(
                "https://brawlstars.statuspage.io/api/v2/summary.json",
                "🟨 *Brawl Stars Status*",
                "❌ Ошибка получения статуса Brawl Stars."
        );
    }

    public static String getOtherGameStatus() {
        return getServiceStatus(
                "https://example.statuspage.io/api/v2/summary.json",
                "🟩 *Other Game Status*",
                "❌ Ошибка получения статуса Other Game."
        );
    }
}
