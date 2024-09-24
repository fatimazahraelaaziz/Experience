package fr.unice.scale.latencyaware.consumer;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;

public class Test {
    
    public static void main(String[] args) {
        Instant lastCommitTime = Instant.now();
        int time_to_commit = 1000;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy'T'HH:mm:ss.SSSSSS");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));  // Définir le fuseau horaire sur UTC


        while (true) {
            System.out.println("Current time: " + Instant.now());
            System.out.println("Last commit time: " + lastCommitTime);
            System.out.println("Sleeping for 1 seconds");
            System.out.println("****************************************** " + Duration.between(lastCommitTime, Instant.now()).toMillis());
             if (Math.abs(Duration.between(lastCommitTime, Instant.now()).toMillis()) >= time_to_commit) {
                      System.out.println("Committed offset at time " + simpleDateFormat.format(new Date(System.currentTimeMillis())));
                        lastCommitTime = Instant.now();
                    }  
            System.out.println("******************************************");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}