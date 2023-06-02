package org.yablokovs;

import com.netflix.servo.DefaultMonitorRegistry;
import com.netflix.servo.monitor.BasicCounter;
import com.netflix.servo.monitor.Counter;
import com.netflix.servo.monitor.MonitorConfig;
import com.netflix.servo.publish.BasicMetricFilter;
import com.netflix.servo.publish.MetricObserver;
import com.netflix.servo.publish.MetricPoller;
import com.netflix.servo.publish.MonitorRegistryMetricPoller;
import com.netflix.servo.publish.PollRunnable;
import com.netflix.servo.publish.PollScheduler;
import com.netflix.servo.publish.graphite.GraphiteMetricObserver;

import java.util.concurrent.TimeUnit;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@Deprecated
public class MetricsServo {

    public static void basicCounter() {
        Counter counter = new BasicCounter(MonitorConfig.builder("test").build());
        MonitorConfig config = counter.getConfig();

        DefaultMonitorRegistry.getInstance().register(counter);

        String prefix = "servo";
        String addr = "localhost:2003";
        MetricObserver graphiteObserver = new GraphiteMetricObserver(prefix, addr);

        // Register the Graphite reporter
        // DefaultMonitorRegistry.getInstance().addObserver(graphiteObserver);


        // Set up the metric polling and reporting interval
        MetricPoller poller = new MonitorRegistryMetricPoller(DefaultMonitorRegistry.getInstance());
        PollRunnable task = new PollRunnable(poller, BasicMetricFilter.MATCH_ALL, graphiteObserver);
        PollScheduler scheduler = PollScheduler.getInstance();
        scheduler.start();
        scheduler.addPoller(task, 1, TimeUnit.MINUTES); // Adjust the reporting interval as needed


        assertEquals("counter should start with 0", 0, counter.getValue().intValue());

        counter.increment();

        assertEquals("counter should have increased by 1", 1, counter.getValue().intValue());

        counter.increment(-1);

        assertEquals("counter should have decreased by 1", 0, counter.getValue().intValue());
    }
}
