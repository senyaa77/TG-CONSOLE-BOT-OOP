package org.example.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DiscordStatusService {

    private static final String STATUS_URL = "https://discordstatus.com/api/v2/summary.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String getDiscordStatus() {
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(STATUS_URL))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonNode root = mapper.readTree(response.body());

            String overall = root.path("status").path("description").asText();

            StringBuilder sb = new StringBuilder();
            sb.append("🟦 *Discord Status*\n")
                    .append("Общее состояние: ").append(overall).append("\n\n")
                    .append("📌 *Компоненты:*\n");

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
            return "❌ Ошибка получения статуса Discord.";
        }
    }
}

