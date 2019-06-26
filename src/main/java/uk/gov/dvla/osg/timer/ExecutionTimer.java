package uk.gov.dvla.osg.timer;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility methods for timing Java method execution duration.
 * All output written to Log4J2 appender.
 * @author OSG
 * @version 1.0
 */
public class ExecutionTimer {
    
    private static final Logger LOGGER = LogManager.getLogger();
    private static long time;
    
    /**
     * Run immediately before execution of code to begin timing.
     */
    public static void startTimer() {
        LOGGER.trace("Timer Started!");
        ExecutionTimer.time = System.nanoTime();
    }
    
    /**
     * Prints the elapsed time to the logger in the unit amount specified, e.g. seconds or milliseconds.
     * @param unit TimeUnit for output. All Java TimeUnits are permitted.
     */
    public static void printElapedTime(TimeUnit unit) {
        print(unit, System.nanoTime() - ExecutionTimer.time);
    }

    
    /**
     * Executes a method and returns its value. The execution time for the method is printed to the log4j appender.
     * @param operation Method to perform passed as a Supplier.
     * @param unit TimeUnit for output. All Java TimeUnits are permitted.
     * @return The result returned from executing the method.
     */
    public static <R> R timing(Supplier<R> operation, TimeUnit unit) {
        startTimer();
        R result  = operation.get();
        print(unit, System.nanoTime() - ExecutionTimer.time);
        return result;
    }

    /**
     * Executes a method and returns its value. The execution time for the method is printed to the log4j appender.
     * <br/>Note: Execution time is printed in milliseconds. To specify a different TimeUnit, use the overloaded version of this method. 
     * @param operation Method to perform.
     * @return The result returned from executing the method.
     */
    public static <R> R timing(Supplier<R> operation) {
        return timing(operation, TimeUnit.MILLISECONDS);
    }
    
    /**
     * Executes a method that doesn't require a return value. The execution time for the method is printed to the log4j appender.
     * <br/>Note: Execution time is printed in milliseconds. To specify a different TimeUnit, use the overloaded version of this method. 
     * @param operation Method to perform.
     */
    public static void timing(Runnable operation) {
        ExecutionTimer.timing(() -> { 
            operation.run(); 
            return null; 
         });
    }

    /**
     * Executes a method that doesn't require a return value. The execution time for the method is printed to the log4j appender.
     * <br/>Note: Execution time is printed in milliseconds. To specify a different TimeUnit, use the overloaded version of this method. 
     * @param operation Method to perform.
     * @param unit TimeUnit for output. All Java TimeUnits are permitted.
     */
    public static void timing(Runnable operation, TimeUnit unit) {
        ExecutionTimer.timing(() -> { 
            operation.run(); 
            return null; 
         }, unit);
    }
    
    /**
     * Passes the elapsed time to the log4j logger.
     * @param unit TimeUnit to print in.
     * @param elapsedTime Value to print.
     */
    private static void print(TimeUnit unit, long elapsedTime) {
        switch (unit) {
        case DAYS:
            LOGGER.debug("Execution took {} Days", TimeUnit.NANOSECONDS.toDays(elapsedTime));
            break;
        case HOURS:
            LOGGER.debug("Execution took {} Hours", TimeUnit.NANOSECONDS.toHours(elapsedTime));
            break;
        case MICROSECONDS:
            LOGGER.debug("Execution took {} MicroSecs", TimeUnit.NANOSECONDS.toMicros(elapsedTime));
            break;
        case MILLISECONDS:
            LOGGER.debug("Execution took {} MilliSeconds", TimeUnit.NANOSECONDS.toMillis(elapsedTime));
            break;
        case MINUTES:
            LOGGER.debug("Execution took {} Minutes", TimeUnit.NANOSECONDS.toMinutes(elapsedTime));
            break;
        case NANOSECONDS:
            LOGGER.debug("Execution took {} NanoSeconds", elapsedTime);
            break;
        case SECONDS:
            LOGGER.debug("Execution took {} Seconds", TimeUnit.NANOSECONDS.toSeconds(elapsedTime));
            break;
        default:
            break;
       }
    }
}
