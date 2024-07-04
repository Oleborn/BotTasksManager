package telegramBot.bot.configLoader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ConfigLoader {
    public ConfigProperties loadConfig(String fileName) {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                System.out.println("Sorry, unable to find " + fileName);
                return null;
            }
            properties.load(input);

            return ConfigProperties.builder()
                    .token(properties.getProperty("token"))
                    .nameBot(properties.getProperty("nameBot"))
                    .build();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
