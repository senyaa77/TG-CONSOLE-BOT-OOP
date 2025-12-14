package UrfuBot;

import java.util.HashMap;

public class Users {

    HashMap<String, String> data;

    public Users() {
        data = new HashMap<>();

    }

    public Boolean HasOrCreate(String chatId) {
        if (!data.containsKey(chatId)) {
            data.put(chatId, "");
        }
        return true;
    }

    public String Get(String chatId) {
        return data.get(chatId);
    }

    public void Update(String chatId, String text) {
        data.replace(chatId, text);
    }

    public String Produce(String chatId, String text, String time) {
        if (HasOrCreate(chatId)) {
            Update(chatId, Get(chatId) + "\n" + text);
        }
        return "я запомнил";
    }
}
