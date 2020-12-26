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

package org.camelcookbook.splitjoin.aggregate;

import java.util.HashSet;
import java.util.Set;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public class SetAggregationStrategy implements AggregationStrategy {
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        String body = newExchange.getIn().getBody(String.class);
        if (oldExchange == null) {
            Set<String> set = new HashSet<String>();
            set.add(body);
            newExchange.getIn().setBody(set);
            return newExchange;
        } else {
            Set<String> set = oldExchange.getIn().getBody(Set.class);
            set.add(body);
            return oldExchange;
        }
    }
}
