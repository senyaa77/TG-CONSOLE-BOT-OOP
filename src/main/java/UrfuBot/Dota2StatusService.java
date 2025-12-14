package UrfuBot;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Dota2StatusService {
    private static final String STATUS_URL = "https://crowbar.steampowered.com/status/summary.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String getDota2Status() {
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(STATUS_URL))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonNode root = mapper.readTree(response.body());

            StringBuilder sb = new StringBuilder();
            sb.append("🎮 *Dota 2 Status*\n\n");

            // Основной статус Steam услуг
            JsonNode services = root.path("services");

            sb.append("📌 *Сервисы Steam:*\n");
            services.fields().forEachRemaining(entry -> {
                sb.append("• ")
                        .append(entry.getKey())
                        .append(" — ")
                        .append(entry.getValue().path("status").asText())
                        .append("\n");
            });

            // Серверы игр (включая Dota 2)
            sb.append("\n🌍 *Игровые сервера:*\n");
            JsonNode games = root.path("games");

            games.fields().forEachRemaining(entry -> {
                sb.append("• ")
                        .append(entry.getKey())
                        .append(" — ")
                        .append(entry.getValue().path("status").asText())
                        .append("\n");
            });

            // Dota 2 может быть в поле `games -> dota2`
            JsonNode dotaNode = games.path("dota2");
            if (!dotaNode.isMissingNode()) {
                sb.append("\n🟥 *Dota 2 сервера:*\n")
                        .append("Состояние: ")
                        .append(dotaNode.path("status").asText("unknown"))
                        .append("\n");
            }

            return sb.toString();

        } catch (IOException | InterruptedException e) {
            return "❌ Ошибка получения статуса Dota 2.";
        }
    }
}
