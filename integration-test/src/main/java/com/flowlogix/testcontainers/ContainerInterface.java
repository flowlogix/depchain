/*
 * Copyright (C) 2011-2026 Flow Logix, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.flowlogix.testcontainers;

import org.testcontainers.containers.GenericContainer;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.function.Consumer;

public interface ContainerInterface {
    /**
     * Pre-start property name
     * Called before container start
     * Type of {@code Consumer<GenericContainer<?>>}
     * @see #start(Consumer, Consumer)
     */
    String PRE_START_PROPERTY = ContainerInterface.class.getName() + ".preStart";
    /**
     * Post-start property name
     * Called after container start
     * Type of {@code Consumer<GenericContainer<?>>}
     * @see #start(Consumer, Consumer)
     */
    String POST_START_PROPERTY = ContainerInterface.class.getName() + ".postStart";
    ContainerInterface start(Consumer<GenericContainer<?>> preStart, Consumer<GenericContainer<?>> postStart);
    ContainerInterface stop();

    static Optional<ContainerInterface> create(Consumer<GenericContainer<?>> preStart,
                                               Consumer<GenericContainer<?>> postStart) {
        return ServiceLoader.load(ContainerInterface.class).findFirst()
                .map(ci -> ci.start(preStart, postStart));
    }
}
