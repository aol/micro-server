package app.metrics.com.oath.micro.server;

import java.io.PrintStream;
import java.util.Locale;
import java.util.SortedMap;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import lombok.Getter;

import com.codahale.metrics.Clock;
import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.ScheduledReporter;
import com.codahale.metrics.Timer;



public class TestReporter extends ScheduledReporter {

	@Getter
	private static volatile SortedMap<String, Timer> timer = new TreeMap<>();

	protected TestReporter(MetricRegistry registry, String name,
			MetricFilter filter, TimeUnit rateUnit, TimeUnit durationUnit) {
		super(registry, name, filter, rateUnit, durationUnit);
	
	}
	
	  
	public static Builder forRegistry(MetricRegistry registry) {
	        return new Builder(registry);
	    }

	@Override
	public void report(SortedMap<String, Gauge> gauges,
			SortedMap<String, Counter> counters,
			SortedMap<String, Histogram> histograms,
			SortedMap<String, Meter> meters, SortedMap<String, Timer> timers) {
		this.timer = timers;
		

	}

	 public static class Builder {
	        private final MetricRegistry registry;
	        private PrintStream output;
	        private Locale locale;
	        private Clock clock;
	        private TimeZone timeZone;
	        private TimeUnit rateUnit;
	        private TimeUnit durationUnit;
	        private MetricFilter filter;

	        private Builder(MetricRegistry registry) {
	            this.registry = registry;
	            this.output = System.out;
	            this.locale = Locale.getDefault();
	            this.clock = Clock.defaultClock();
	            this.timeZone = TimeZone.getDefault();
	            this.rateUnit = TimeUnit.SECONDS;
	            this.durationUnit = TimeUnit.MILLISECONDS;
	            this.filter = MetricFilter.ALL;
	        }

	        /**
	         * Write to the given {@link PrintStream}.
	         *
	         * @param output a {@link PrintStream} instance.
	         * @return {@code this}
	         */
	        public Builder outputTo(PrintStream output) {
	            this.output = output;
	            return this;
	        }

	        /**
	         * Format numbers for the given {@link Locale}.
	         *
	         * @param locale a {@link Locale}
	         * @return {@code this}
	         */
	        public Builder formattedFor(Locale locale) {
	            this.locale = locale;
	            return this;
	        }

	        /**
	         * Use the given {@link Clock} instance for the time.
	         *
	         * @param clock a {@link Clock} instance
	         * @return {@code this}
	         */
	        public Builder withClock(Clock clock) {
	            this.clock = clock;
	            return this;
	        }

	        /**
	         * Use the given {@link TimeZone} for the time.
	         *
	         * @param timeZone a {@link TimeZone}
	         * @return {@code this}
	         */
	        public Builder formattedFor(TimeZone timeZone) {
	            this.timeZone = timeZone;
	            return this;
	        }

	        /**
	         * Convert rates to the given time unit.
	         *
	         * @param rateUnit a unit of time
	         * @return {@code this}
	         */
	        public Builder convertRatesTo(TimeUnit rateUnit) {
	            this.rateUnit = rateUnit;
	            return this;
	        }

	        /**
	         * Convert durations to the given time unit.
	         *
	         * @param durationUnit a unit of time
	         * @return {@code this}
	         */
	        public Builder convertDurationsTo(TimeUnit durationUnit) {
	            this.durationUnit = durationUnit;
	            return this;
	        }

	        /**
	         * Only report metrics which match the given filter.
	         *
	         * @param filter a {@link MetricFilter}
	         * @return {@code this}
	         */
	        public Builder filter(MetricFilter filter) {
	            this.filter = filter;
	            return this;
	        }

	        /**
	         * Builds a {@link ConsoleReporter} with the given properties.
	         *
	         * @return a {@link ConsoleReporter}
	         */
	        public TestReporter build() {
	            return new TestReporter(registry,
	                                 //      output,
	            		 "name",
	                                     //  locale,
	                                     //  clock,
	                                      // timeZone,
	            		 this.filter,
	                                       rateUnit,
	                                       durationUnit
	                                       );
	        }
	    }
	
	
}

