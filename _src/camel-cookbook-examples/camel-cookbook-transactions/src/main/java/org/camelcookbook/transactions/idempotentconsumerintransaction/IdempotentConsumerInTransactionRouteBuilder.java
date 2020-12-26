/*
 * Copyright (C) Scott Cranton and Jakub Korab
 * https://github.com/CamelCookbook
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.camelcookbook.transactions.idempotentconsumerintransaction;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spi.IdempotentRepository;

public class IdempotentConsumerInTransactionRouteBuilder extends RouteBuilder {

    private final IdempotentRepository idempotentRepository;

    public IdempotentConsumerInTransactionRouteBuilder(IdempotentRepository idempotentRepository) {
        this.idempotentRepository = idempotentRepository;
    }

    @Override
    public void configure() throws Exception {
        from("direct:transacted").id("main")
            .transacted("PROPAGATION_REQUIRED")
            .setHeader("message", body())
            .to("sql:insert into audit_log (message) values (:#message)")
            .enrich("direct:invokeWs")
            .to("mock:out");

        from("direct:invokeWs").id("idempotentWs")
            .idempotentConsumer(header("messageId"), idempotentRepository)
                .to("mock:ws")
            .end();
    }
}
