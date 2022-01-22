import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.GenericMessageReactionEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.internal.handle.GuildRoleCreateHandler;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MessageListener extends ListenerAdapter {

    Role role1;
    Role role2;

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        if (event.getAuthor().isBot()) return;

        //Message message = event.getMessage();
        String content = event.getMessage().getContentRaw();
        MessageChannel channel = event.getChannel();
        String msgAuthor = event.getMessage().getAuthor().getAsTag();

        System.out.println("Message author is:" + msgAuthor);

        if (content.equalsIgnoreCase("hello there") || content.equalsIgnoreCase("hello there!") ){
            channel.sendMessage("General Kenobi!").queue();
        }

        if (content.equalsIgnoreCase("$ping")){
            //MessageChannel channel = event.getChannel();
            channel.sendMessage("Pong!").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        }

        if (content.equalsIgnoreCase("$help")){
            channel.sendMessage("Possible Commands are:\n-$ping\n$bid").queue();
        }

//Personal money to bet on roulette. Just a test structure. Not optimal solution! Not for large scale use!

        //HashMap<String, Double> moneyAccounts = new HashMap<String, Double>();


// start of roulette command
        //checks if command format is correct
        if (content.equalsIgnoreCase("$bid") || content.equalsIgnoreCase("$bid help")){
            channel.sendMessage("Welcome to Roulette! Please enter a number between 0 and 36 or black or red after the command").queue();
        } else if (content.startsWith("$bid")){

/*
            //checks if user already exists in HashMap, creates it if not
            if (!moneyAccounts.containsKey(msgAuthor)){
                moneyAccounts.put(msgAuthor, 1000.0);
            }


            System.out.println(moneyAccounts);
*/


            // init variable bidparam to store the value of the user input. Default is -3
            int bidparam = -3;
            String[] arrOfStr = content.split(" ", 2);
            String bidNumber = arrOfStr[1];
            //String bidAmount = arrOfStr[2];

            //Double moneyIHave = moneyAccounts.get(msgAuthor);

            // checks if user entered black or red as keyword
            if (bidNumber.equalsIgnoreCase("black")){
                bidparam = -1;
            } else if (bidNumber.equalsIgnoreCase("red")){
                bidparam = -2;
            } else {
                // checks if user input is a number by trying to convert it to an integer
                try {
                    int bidInt = Integer.parseInt(bidNumber);
                    if (bidInt >= 0 && bidInt <= 36){
                        bidparam = bidInt;
                        //System.out.print(bidNumber);
                        //channel.sendMessage(bidNumber).queue();
                    } else {
                        channel.sendMessage("Please enter a number between 0 and 36 or black or red after the command").queue();
                    }
                } catch(NumberFormatException e){
                    channel.sendMessage("You have not entered a valid Integer!").queue();
                }
            }

            // bidparam is now an int between -2 and 36 which represents the user input value
            System.out.println("User entered: " + bidparam);

            Random rand = new Random();
            int rand_int = rand.nextInt(37);

            System.out.println("Random number: " + rand_int);

            List<Integer> redNumbers = List.of(32,19,21,25,34,27,36,30,23,5,16,1,14,9,18,7,12,3); //-2
            List<Integer> blackNumbers = List.of(15,4,2,17,6,13,11,8,10,24,33,20,31,22,29,28,35,26); //-1

            //Double wonMoney = 0.0;


            if (bidparam == rand_int){
                channel.sendMessage("Wow! You guessed the correct number! Your bid money has been multiplied by 36!").queue();
                //wonMoney = bidMoney * 36;
            } else if (bidparam == -1 && blackNumbers.contains(rand_int)){
                channel.sendMessage("You guessed the right colour! You have doubled your money!").queue();
                //wonMoney = bidMoney * 2;
            } else if (bidparam == -2 && redNumbers.contains(rand_int)){
                channel.sendMessage("You guessed the right colour! You have doubled your money!").queue();
                //wonMoney = bidMoney * 2;
            } else if (bidparam == -3){
                channel.sendMessage("oops, something went wrong").queue();
            } else {
                channel.sendMessage("Unfortunately, you didn't guess your number or colour right!").queue();
                //wonMoney = 0 - bidMoney;
            }

/*
            Double newBalance = moneyAccounts.get(msgAuthor) + wonMoney;
            moneyAccounts.replace(msgAuthor, newBalance);
            System.out.println(moneyAccounts.get(msgAuthor));

 */
        }


/*
        if (content.equalsIgnoreCase("$money")){
            System.out.println(moneyAccounts.get(msgAuthor));
            System.out.println(moneyAccounts);
            channel.sendMessage(moneyAccounts.get(msgAuthor).toString()).queue();
        }



*/

        if (content.equalsIgnoreCase("$listen")){
            channel.sendMessage("Add something after this command to let the bot listen to it!").queue();
        } else if (content.startsWith("$listen ")){
            String[] arrOfStr = content.split(" ", 2);
            String song = arrOfStr[1];
            BotStartup.apiv.getPresence().setActivity(Activity.listening(song));
        }



        if (content.equalsIgnoreCase("$addrole")){
            channel.sendMessage("Please select the role you want!\n\n\u2694\uFE0F\tFighter\n\uD83D\uDEE1\tTank").queue(message -> {
                message.addReaction("\u2694\uFE0F").queue();
                message.addReaction("\uD83D\uDEE1").queue();
            });

            //Role role = event.getGuild().getRolesByName("@everyone", true).get(0);

            if (event.getGuild().getRolesByName("fighter", true).isEmpty()){
                role1 = event.getGuild().createRole().setName("Fighter").setPermissions(0L).complete();
            } else {
                System.out.println("Role already exists");
            }
            if (event.getGuild().getRolesByName("tank", true).isEmpty()){
                role2 = event.getGuild().createRole().setName("Tank").setPermissions(0L).complete();
            } else {
                System.out.println("Role already exists");
            }

            System.out.println("Role1 ID is: " + role1.toString());
            System.out.println("Role2 ID is: " + role2.toString());
        }

    }

    public void onMessageReactionAdd(@Nonnull MessageReactionAddEvent event){
        if (event.getUser().isBot()) return;

        String reaction = event.getReactionEmote().toString();
        String reactAutor = event.getUser().getAsTag();
        String msgID = event.getMessageId();

        System.out.println(reactAutor + " reacted with " + reaction + " on " + msgID);
/*
        if (reaction.equalsIgnoreCase("RE:U+2694U+fe0f")){
            event.getGuild().addRoleToMember(event.getUserIdLong(), role1);
        }
*/
    }

    public void onMessageReactionRemove(@Nonnull MessageReactionRemoveEvent event){
        if (event.getUser().isBot()) return;

        String reaction = event.getReactionEmote().toString();
        String reactAutor = event.getUser().getAsTag();
        String msgID = event.getMessageId();

        System.out.println(reactAutor + " removed the reaction " + reaction + " on " + msgID);
    }

}
