import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.pengrad.telegrambot.model.Update

def JSON = """
{"update_id":815792933,"message":{"message_id":99,"from":{"id":199391923,"first_name":"Evgeny","last_name":"Shepelyuk","username":"eshepelyuk"},"chat":{"id":199391923,"first_name":"Evgeny","last_name":"Shepelyuk","username":"eshepelyuk","type":"private"},"date":1459156305,"text":"Hot"}}
"""

def update = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY) readValue(JSON, Update)
println update