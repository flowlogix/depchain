/*
 * Copyright (C) 2011-2025 Flow Logix, Inc. All Rights Reserved.
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

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.GenericContainer;
import java.util.Optional;
import java.util.function.Consumer;
import static com.flowlogix.testcontainers.ContainerInterface.POST_START_PROPERTY;
import static com.flowlogix.testcontainers.ContainerInterface.PRE_START_PROPERTY;
import static org.jboss.arquillian.junit5.ArquillianExtension.RUNNING_INSIDE_ARQUILLIAN;

/**
 * For use with JUnit 5
 * {@code @ExtendWith(PayaraServerLifecycleExtension.class)}
 * Needs to go before ArquillianExtension for all test classes
 *
 * @author lprimak
 */
public class PayaraServerLifecycleExtension implements BeforeAllCallback, AutoCloseable {
    private Optional<ContainerInterface> payaraTC = Optional.empty();

    @Override
    @SuppressWarnings("unchecked")
    public void beforeAll(ExtensionContext context) throws Exception {
        boolean inContainer = Boolean.parseBoolean(context
                .getConfigurationParameter(RUNNING_INSIDE_ARQUILLIAN).orElse("false"));
        if (!inContainer) {
            Consumer<GenericContainer<?>> preStart = context.getRoot().getStore(ExtensionContext.Namespace.GLOBAL)
                    .computeIfAbsent(PRE_START_PROPERTY, key -> container -> { }, Consumer.class);
            Consumer<GenericContainer<?>> postStart = context.getRoot().getStore(ExtensionContext.Namespace.GLOBAL)
                    .computeIfAbsent(POST_START_PROPERTY, key -> container -> { }, Consumer.class);
            payaraTC = (Optional<ContainerInterface>)
                    context.getRoot().getStore(ExtensionContext.Namespace.GLOBAL)
                            .computeIfAbsent(this.getClass().getName(),
                                    key -> ContainerInterface.create(preStart, postStart));
        }
    }

    @Override
    public void close() {
        payaraTC.ifPresent(ContainerInterface::stop);
    }
}
