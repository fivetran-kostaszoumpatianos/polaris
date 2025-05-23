/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.polaris.core.config;

import java.util.List;
import org.apache.polaris.immutables.PolarisImmutable;
import org.immutables.value.Value;

/** Represents the result of a production readiness check. */
@PolarisImmutable
public interface ProductionReadinessCheck {

  ProductionReadinessCheck OK = ImmutableProductionReadinessCheck.builder().build();

  static ProductionReadinessCheck of(Error... errors) {
    return ImmutableProductionReadinessCheck.builder().addErrors(errors).build();
  }

  static ProductionReadinessCheck of(Iterable<? extends Error> errors) {
    return ImmutableProductionReadinessCheck.builder().addAllErrors(errors).build();
  }

  default boolean ready() {
    return getErrors().isEmpty();
  }

  @Value.Parameter(order = 1)
  List<Error> getErrors();

  @PolarisImmutable
  @SuppressWarnings("JavaLangClash")
  interface Error {

    static Error of(String message, String offendingProperty) {
      return ImmutableError.of(message, offendingProperty, false);
    }

    static Error ofSevere(String message, String offendingProperty) {
      return ImmutableError.of(message, offendingProperty, true);
    }

    @Value.Parameter(order = 1)
    String message();

    @Value.Parameter(order = 2)
    String offendingProperty();

    @Value.Parameter(order = 3)
    boolean severe();
  }
}
