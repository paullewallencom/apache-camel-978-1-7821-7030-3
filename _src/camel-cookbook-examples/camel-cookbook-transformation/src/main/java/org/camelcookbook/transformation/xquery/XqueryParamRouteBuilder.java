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

package org.camelcookbook.transformation.xquery;

import org.apache.camel.builder.RouteBuilder;

import static org.apache.camel.component.xquery.XQueryBuilder.xquery;

public class XqueryParamRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:start")
            .transform(xquery("declare variable $in.headers.myParamValue as xs:integer external; <books value='{$in.headers.myParamValue}'>{for $x in /bookstore/book where $x/price>$in.headers.myParamValue order by $x/title return $x/title}</books>"));
    }
}
