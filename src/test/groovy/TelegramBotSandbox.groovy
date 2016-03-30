import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.TelegramBotAdapter
import com.pengrad.telegrambot.model.Chat
import com.pengrad.telegrambot.model.Update
import com.pengrad.telegrambot.model.request.ParseMode
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup

TelegramBot bot = TelegramBotAdapter.build("205365091:AAHhR6iyhWwK9pdv0FEvrKiyng0yHeI4avc")
def resp = bot.getUpdates(0, 10, 100)


def executeTelegram = { Chat chat ->
    bot.sendMessage(
        chat.id(),
        "*Check* _this_ *out*",
        ParseMode.Markdown,
        false,
        null,
        new ReplyKeyboardMarkup(["Cool", "Hot"] as String[], ["Dusgusting", "It sucks"] as String[])
    )
}

def executeTelegram2 = { Chat chat ->
    bot.sendMessage(
        chat.id(),
        "*Check* _this_ *out*",
        ParseMode.Markdown,
        false,
        null,
        new ReplyKeyboardMarkup(["Cool", "Hot"] as String[], ["Dusgusting", "It sucks"] as String[])
    )
}

resp.updates().groupBy { it.message().chat() } each { chat, List<Update> messages ->
    executeTelegram chat
}

//executeTelegram(resp.updates().findAll { it.message().from().username() == "eshepelyuk" }.last().message().chat())



