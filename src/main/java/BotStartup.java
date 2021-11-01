import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BotStartup {
    public static void main(String[] args) throws LoginException, InterruptedException, IOException, URISyntaxException {


        ClassLoader classLoader = BotStartup.class.getClassLoader();
        URL resource = classLoader.getResource("token.txt");

        String token = new String(Files.readAllBytes(Path.of(resource.toURI())));


        JDA api = JDABuilder.createDefault(token)
                .addEventListeners(new MessageListener())
                .build().awaitReady();

        //JDA.addEventListeners(new MessageListener());

    }
}
