import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.TelegramBotAdapter
import org.yaml.snakeyaml.Yaml

def yaml = new Yaml().load(MaapBotConfig.getResourceAsStream("/maapbot.yaml"))
TelegramBot bot = TelegramBotAdapter.buildDebug(yaml.maapbot.telegramToken)
bot.setWebhook("https://maapbot.herokuapp.com/webhook")


