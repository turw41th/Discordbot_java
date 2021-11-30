import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BotStartup {

    public static JDA apiv = null;

    public static void main(String[] args) throws LoginException, InterruptedException, IOException, URISyntaxException {


        ClassLoader classLoader = BotStartup.class.getClassLoader();

        //Following line only works with hard-coded, not changeable path to the file
        String token = new String(Files.readAllBytes(Paths.get("src/main/resources/token.txt")));

        /*
        URL tokenresource = classLoader.getResource("token.txt");

        String token = new String(Files.readAllBytes(Path.of(tokenresource.toURI())));
        */



        apiv = JDABuilder.createDefault(token)
                .addEventListeners(new MessageListener())
                .build().awaitReady();
    }
}