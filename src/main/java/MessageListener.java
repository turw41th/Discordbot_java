import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;
import java.util.Random;

public class MessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        if (event.getAuthor().isBot()) return;


        Message message = event.getMessage();
        String content = message.getContentRaw();
        MessageChannel channel = event.getChannel();

        if (content.equals("$ping"))
        {
            //MessageChannel channel = event.getChannel();
            channel.sendMessage("Pong!").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        }

        if (content.equalsIgnoreCase("$help")){
            channel.sendMessage("Possible Commands are:\n-$ping\n$bid").queue();
        }

        if (content.equalsIgnoreCase("hello there") || content.equalsIgnoreCase("hello there!") ){
            channel.sendMessage("General Kenobi!").queue();
        }

// start of roulette command
        //checks if command format is correct
        if (content.equalsIgnoreCase("$bid") || content.equalsIgnoreCase("$bid help")){
            channel.sendMessage("Welcome to Roulette! Please enter a number between 0 and 36 or black or red after the command").queue();
        } else if (content.startsWith("$bid")){
            // init variable bidparam to store the value of the user input. Default is -3
            int bidparam = -3;
            String[] arrOfStr = content.split(" ", 2);
            String bidNumber = arrOfStr[1];

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

            List<Integer> redNumbers = List.of(32,19,21,25,34,27,36,30,23,5,16,1,14,9,18,7,12,3);
            List<Integer> blackNumbers = List.of(15,4,2,17,6,13,11,8,10,24,33,20,31,22,29,28,35,26);

            //System.out.println(redNumbers.contains(27));

            //int[] redNumbers = new int[]{32,19,21,25,34,27,36,30,23,5,16,1,14,9,18,7,12,3};
            //int[] blackNumbers = new int[]{15,4,2,17,6,13,11,8,10,24,33,20,31,22,29,28,35,26};

            if (bidparam == rand_int){
                channel.sendMessage("Wow! You guessed the correct number! Your bid money has been multiplied by 36!").queue();
            } else if (bidparam == -1 && blackNumbers.contains(rand_int)){
                channel.sendMessage("You guessed the right colour! You have doubled your money!").queue();
            } else if (bidparam == -2 && redNumbers.contains(rand_int)){
                channel.sendMessage("You guessed the right colour! You have doubled your money!").queue();
            } else if (bidparam == -3){
                channel.sendMessage("oops, something went wrong").queue();
            } else {
                channel.sendMessage("Unfortunately, you didn't guess your number right!").queue();
            }

        }

    }
}
