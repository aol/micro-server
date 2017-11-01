package com.aol.micro.server;

import java.util.Map;
import java.util.function.Supplier;

public interface StatsSupplier extends Supplier<Map<String, Map<String, String>>> {

}
