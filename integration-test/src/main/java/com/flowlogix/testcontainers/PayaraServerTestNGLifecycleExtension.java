/*
 * Copyright (C) 2011-2024 Flow Logix, Inc. All Rights Reserved.
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
import org.testng.ISuite;
import org.testng.ISuiteListener;
import java.util.Optional;
import java.util.function.Consumer;
import static com.flowlogix.testcontainers.ContainerInterface.POST_START_PROPERTY;
import static com.flowlogix.testcontainers.ContainerInterface.PRE_START_PROPERTY;

public class PayaraServerTestNGLifecycleExtension implements ISuiteListener {
    @Override
    public void onStart(ISuite suite) {
        if (!"Arquillian".equalsIgnoreCase(suite.getName())) {
            if (get(suite, getClass().getName(), ContainerInterface.class).isEmpty()) {
                @SuppressWarnings("unchecked")
                var preStart = get(suite, PRE_START_PROPERTY, Object.class)
                        .map(obj -> (Consumer<GenericContainer<?>>) obj);
                @SuppressWarnings("unchecked")
                var postStart = get(suite, POST_START_PROPERTY, Object.class)
                        .map(obj -> (Consumer<GenericContainer<?>>) obj);
                ContainerInterface.create(preStart.orElse(container -> { }), postStart.orElse(container -> { }))
                        .ifPresent(payaraTC -> suite.setAttribute(getClass().getName(), payaraTC));
            }
        }
    }

    @Override
    public void onFinish(ISuite suite) {
        get(suite, getClass().getName(), ContainerInterface.class).ifPresent(ContainerInterface::stop);
    }

    @SuppressWarnings("unchecked")
    private static <TT> Optional<TT> get(ISuite suite, String key, Class<TT> cls) {
        return Optional.ofNullable((TT) suite.getAttribute(key));
    }
}
