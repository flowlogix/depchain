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

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class SystemPropertyTest {
    @Test
    void multipleSystemProperties() {
        assertThat(getSystemProperty("payara.memory.gb", null,
                getSystemProperty("server.memory.gb", "5", "1.5")))
                .isEqualTo("5");
        assertThat(getSystemProperty("payara.memory.gb", "2",
                getSystemProperty("server.memory.gb", "5", "1.5")))
                .isEqualTo("2");
        assertThat(getSystemProperty("payara.memory.gb", "5",
                getSystemProperty("server.memory.gb", "", "1.5")))
                .isEqualTo("5");
        assertThat(getSystemProperty("payara.memory.gb", null,
                getSystemProperty("server.memory.gb", "", "1.5")))
                .isEqualTo("1.5");
        assertThat(getSystemProperty("payara.memory.gb", "5",
                getSystemProperty("server.memory.gb", "2", "1.5")))
                .isEqualTo("5");
    }

    private static String getSystemProperty(String key, String value, String defaultValue) {
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        } else {
            return value;
        }
    }
}
