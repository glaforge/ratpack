/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ratpack.codahale.metrics.internal;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.google.inject.Provider;
import ratpack.launch.ServerConfig;

import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

/**
 * A Provider implementation that sets up a {@link ConsoleReporter} for a {@link MetricRegistry}.
 */
public class ConsoleReporterProvider implements Provider<ConsoleReporter> {
  private final MetricRegistry metricRegistry;
  private final ServerConfig serverConfig;

  @Inject
  public ConsoleReporterProvider(MetricRegistry metricRegistry, ServerConfig serverConfig) {
    this.metricRegistry = metricRegistry;
    this.serverConfig = serverConfig;
  }

  @Override
  public ConsoleReporter get() {
    ConsoleReporter reporter = ConsoleReporter.forRegistry(metricRegistry).build();
    String interval = serverConfig.getOther("metrics.scheduledreporter.interval", "30");
    reporter.start(Long.parseLong(interval), TimeUnit.SECONDS);
    return reporter;
  }
}

