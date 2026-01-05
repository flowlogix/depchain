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
package com.flowlogix.test;

import com.flowlogix.testcontainers.PayaraServerLifecycleExtension;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to activate Payara server lifecycle management for the annotated test class.
 * It integrates with JUnit 5, Arquillian and TestContainers to ensure that the
 * Payara server is started before any tests are run and stopped after all tests have completed.
 * <p>
 * Usage:
 * <pre>
 * &#64;PayaraServerLifecycle
 * public class MyArquillianIT {
 *     // Test methods here
 * }
 * </pre>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(PayaraServerLifecycleExtension.class)
@ExtendWith(ArquillianExtension.class)
@Inherited
@Documented
public @interface PayaraServerLifecycle {
}
