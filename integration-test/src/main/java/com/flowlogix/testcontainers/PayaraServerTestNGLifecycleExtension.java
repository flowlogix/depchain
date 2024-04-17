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

import org.testng.ISuite;
import org.testng.ISuiteListener;
import java.util.ServiceLoader;

public class PayaraServerTestNGLifecycleExtension implements ISuiteListener {
    private static ContainerInterface payaraTC;

    @Override
    public void onStart(ISuite suite) {
        if (!"Arquillian".equalsIgnoreCase(suite.getName())) {
            payaraTC = ServiceLoader.load(ContainerInterface.class).findFirst()
                    .map(ContainerInterface::start).orElse(null);
        }
    }

    @Override
    public void onFinish(ISuite suite) {
        if (payaraTC != null) {
            payaraTC.stop();
        }
    }
}
