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

    public static String getStatus(String serviceName, String statusUrl) {

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(statusUrl))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            JsonNode root = MAPPER.readTree(response.body());

            String overallStatus = root.path("status")
                    .path("description")
                    .asText("Неизвестно");

            StringBuilder sb = new StringBuilder();
            sb.append("🟦 *").append(serviceName).append(" Status*\n")
              .append("Общее состояние: ").append(overallStatus).append("\n\n")
              .append("📌 *Компоненты:*\n");

            for (JsonNode component : root.path("components")) {
                sb.append("• ")
                  .append(component.path("name").asText())
                  .append(" — ")
                  .append(component.path("status").asText())
                  .append("\n");
            }

            // Активные инциденты
            JsonNode incidents = root.path("incidents");
            boolean hasActiveIncidents = false;

            for (JsonNode inc : incidents) {
                if (!"resolved".equalsIgnoreCase(inc.path("status").asText())) {
                    if (!hasActiveIncidents) {
                        sb.append("\n⚠️ *Активные инциденты:*\n");
                        hasActiveIncidents = true;
                    }
                    sb.append("• ")
                      .append(inc.path("name").asText())
                      .append(" — ")
                      .append(inc.path("status").asText())
                      .append("\n");
                }
            }

            if (!hasActiveIncidents) {
                sb.append("\n✔️ Активных инцидентов нет.");
            }

            return sb.toString();

        } catch (IOException | InterruptedException e) {
            return "❌ Ошибка получения статуса " + serviceName + ".";
        }
    }
}
