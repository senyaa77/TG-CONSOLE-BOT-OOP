


package UrfuBot;

import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;
import java.util.Random;

public class Challenge {

    private static final String[] CHALLENGES = {
            "📵 Не заходи в соцсети 1 час",
            "⏱ Работай 30 минут без телефона",
            "🔕 Отключи уведомления на 2 часа",
            "📖 Прочитай 10 страниц книги",
            "🧠 20 минут без экрана"
    };

    private static final String[] IMAGES = {
            "src/main/resources/images/mot1.jpg",
            "src/main/resources/images/mot2.jpg",
            "src/main/resources/images/mot3.webp",
            "src/main/resources/images/mot4.jpg",
            "src/main/resources/images/mot5.jpg"
    };

    public static SendPhoto getMotivation(Update update) {
        Random random = new Random();

        String challenge = CHALLENGES[random.nextInt(CHALLENGES.length)];
        String imagePath = IMAGES[random.nextInt(IMAGES.length)];

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(update.getMessage().getChatId().toString());
        sendPhoto.setPhoto(new InputFile(new File(imagePath))); // <-- ключевая поправка
        sendPhoto.setCaption("🔥 Челлендж дня:\n\n" + challenge);

        return sendPhoto;
    }
}

