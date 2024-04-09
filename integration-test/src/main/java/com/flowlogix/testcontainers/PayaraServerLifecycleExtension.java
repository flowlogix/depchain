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

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * For use with JUnit 5
 * {@code @ExtendWith(PayaraServerLifecycleExtension.class)}
 * Needs to go before ArquillianExtension for all test classes
 *
 * @author lprimak
 */
public class PayaraServerLifecycleExtension implements BeforeAllCallback, ExtensionContext.Store.CloseableResource {
    private static final PayaraServerTestContainer PAYARA_TC = new PayaraServerTestContainer();

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        PAYARA_TC.start();
        context.getRoot().getStore(ExtensionContext.Namespace.GLOBAL).put(this.getClass().getName(), this);
    }

    @Override
    public void close() throws Throwable {
        PAYARA_TC.stop();
    }
}
