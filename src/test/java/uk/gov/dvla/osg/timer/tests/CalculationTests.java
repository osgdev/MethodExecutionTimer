package uk.gov.dvla.osg.timer.tests;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import uk.gov.dvla.osg.timer.ExecutionTimer;

class CalculationTests {
    
    @Test
    void ExecutionTimer_VoidMethod_PrintExecutionTime() {
        System.out.println();
        ExecutionTimer.timing(() -> System.out.println("Hello"));
    }

    @Test
    void ExecutionTimer_MethodReturnsString_PrintReturnedString() {
        System.out.println();
        String r = ExecutionTimer.timing(() -> methodCall("foo", "bar"));
        System.out.println(r);
    }
    
    @Test
    void ExecutionTimer_VoidMethodSeconds_PrintExecutionTime() {
        System.out.println();
        ExecutionTimer.timing(() -> System.out.println("Hello"), TimeUnit.SECONDS);
    }

    @Test
    void ExecutionTimer_MethodReturnsStringSeconds_PrintReturnedString() {
        System.out.println();
        String r = ExecutionTimer.timing(() -> methodCall("foo", "bar"), TimeUnit.SECONDS);
        System.out.println(r);
    }
    
    @Test
    void ExecutionTimer_testMethods_Expected() {
        System.out.println();
        ExecutionTimer.StartTimer();
        String str = methodCall("", "");
        System.out.println("Method called"+str);
        ExecutionTimer.printElapedTime(TimeUnit.SECONDS);
    }
    
    public String methodCall(String foo, String bar) {
      //Sleep 2 seconds
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return foo+bar;
    }
}
