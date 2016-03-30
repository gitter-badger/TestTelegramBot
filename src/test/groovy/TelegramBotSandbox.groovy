import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.TelegramBotAdapter
import com.pengrad.telegrambot.model.Chat
import com.pengrad.telegrambot.model.Update
import com.pengrad.telegrambot.model.request.ParseMode
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup
import org.yaml.snakeyaml.Yaml

def yaml = new Yaml().load(MaapBotConfig.getResourceAsStream("/maapbot.yaml"))

TelegramBot bot = TelegramBotAdapter.build(yaml.maapbot.telegramToken)

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



