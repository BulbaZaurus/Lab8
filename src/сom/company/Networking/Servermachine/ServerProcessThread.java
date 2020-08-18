package сom.company.Networking.Servermachine;

import сom.company.Collection.Controller;
import сom.company.Collection.User;
import сom.company.Collection.UserStatus;

import сom.company.Networking.UserAuthorization;
import сom.company.Collection.command.CollectionCommand;
import сom.company.Networking.LogIn;
import сom.company.Networking.Message;
import сom.company.Networking.Registration;

import java.io.PrintStream;
import java.nio.channels.DatagramChannel;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Класс,отвечающий за многопоточную отправку
 */

    public class ServerProcessThread implements Runnable {
        private Message receivedMessage;
        private Connection connection;
        private DatagramChannel channel;
        private ForkJoinPool forkJoinPool;
        ReentrantLock reentrantLock=new ReentrantLock();

        public ServerProcessThread(Message receivedMessage, Connection connection, DatagramChannel channel, ForkJoinPool forkJoinPool) { ;
            this.receivedMessage = receivedMessage;
            this.connection = connection;
            this.channel = channel;
            this.forkJoinPool=ForkJoinPool.commonPool();
        }



        @Override
        public void run() {

            reentrantLock.lock();

            try {
                if (receivedMessage.getContent() instanceof LogIn) {
                    boolean isLoggedIn = UserAuthorization.TryLogIn(receivedMessage.getUser(), connection);
                    ForkJoinPool.commonPool().execute(  new ServerThreadSender(new Message(isLoggedIn ? UserStatus.LOGGED_IN : UserStatus.NONE, null, true, null,Controller.getTickets()), channel, receivedMessage.getSenderAddress(),Controller.getTickets()));
                    
                    return;
                }
                if (receivedMessage.getContent() instanceof Registration) {

                    boolean isSignedIn = UserAuthorization.TrySignIn(receivedMessage.getUser(), connection);
                    ForkJoinPool.commonPool().execute(new ServerThreadSender(new Message(isSignedIn ? UserStatus.SIGNED_IN : UserStatus.NONE, null, true, null,Controller.getTickets()), channel, receivedMessage.getSenderAddress(),Controller.getTickets()));

                    return;
                }
                String[] output = CommandToCollection((CollectionCommand) receivedMessage.getContent(), receivedMessage.getUser());
                ForkJoinPool.commonPool().execute(new ServerThreadSender(new Message(output, null, true, null,Controller.getTickets()), channel, receivedMessage.getSenderAddress(),Controller.getTickets()));

            } catch (StackOverflowError e){
            System.out.println("Переполнение стека");
            e.printStackTrace();
        }finally {
               reentrantLock.unlock();

           }

        }

        private String[] CommandToCollection(CollectionCommand command, User user){
            Controller.PrintWaitingInfo();

            PrintStream oldStream = System.out;
            List<String> output = new ArrayList<String>();
            PrintStream myStream = new PrintStream(System.out) {
                @Override
                public void println(String x) {
                    super.println(x);
                    output.add(x);
                }
            };
            System.setOut(myStream);

            Controller.ExecuteCommand(command,user);

            System.setOut(oldStream);
            String[] strA = new String[output.size()];
            output.toArray(strA);

            return strA;
        }
    }



