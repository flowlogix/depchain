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
package com.flowlogix.testcontainers.impl;

import com.flowlogix.testcontainers.ContainerInterface;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;
import java.util.Optional;
import static java.util.function.Predicate.not;

public class PayaraServerTestContainer implements ContainerInterface {
    private GenericContainer<?> payara;

    @SuppressWarnings("checkstyle:MagicNumber")
    @Override
    public ContainerInterface start() {
        if (payara == null && !Boolean.getBoolean("testcontainers.skip")) {
            Optional<String> imageName = Optional.ofNullable(System.getProperty("payara.imageName"))
                    .filter(not(String::isBlank));
            double memory = Double.parseDouble(System.getProperty("payara.memory", "1.5"));
            payara = new GenericContainer<>(DockerImageName.parse(imageName.orElse("payara/server-full")))
                    .withExposedPorts(4848, 8080, 8181, 9009)
                    .withCreateContainerCmdModifier(cmd -> cmd.getHostConfig().withMemory((long) memory + 1024 * 1024 * 1024))
                    .waitingFor(Wait.forLogMessage(".*Payara Server.*startup time.*\\n", 1));
            payara.start();
            System.out.printf("# Payara debugger location: %s:%d%n", payara.getHost(), payara.getMappedPort(9009));
            System.setProperty("adminHost", payara.getHost());
            System.setProperty("adminPort", Integer.toString(payara.getMappedPort(4848)));
            System.setProperty("httpPort", Integer.toString(payara.getMappedPort(8080)));
            System.setProperty("httpsPort", Integer.toString(payara.getMappedPort(8181)));
            if (System.getProperty("sslPort", "").isBlank()) {
                System.setProperty("sslPort", Integer.toString(payara.getMappedPort(8181)));
            }
        }
        return this;
    }

    @Override
    public ContainerInterface stop() {
        // do not stop container manually, as it's already stopped at this point by TestContainers
        payara = null;
        return this;
    }
}
