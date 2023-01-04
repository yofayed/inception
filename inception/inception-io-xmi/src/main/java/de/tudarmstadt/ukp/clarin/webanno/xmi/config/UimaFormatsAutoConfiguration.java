/*
 * Licensed to the Technische Universität Darmstadt under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The Technische Universität Darmstadt 
 * licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.
 *  
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.tudarmstadt.ukp.clarin.webanno.xmi.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.tudarmstadt.ukp.clarin.webanno.xmi.BinaryCasFormatSupport;
import de.tudarmstadt.ukp.clarin.webanno.xmi.UimaInlineXmlFormatSupport;
import de.tudarmstadt.ukp.clarin.webanno.xmi.XmiFormatSupport;
import de.tudarmstadt.ukp.clarin.webanno.xmi.XmiXml11FormatSupport;

@Configuration
public class UimaFormatsAutoConfiguration
{
    @ConditionalOnProperty(prefix = "format.uima-binary-cas", name = "enabled", //
            havingValue = "true", matchIfMissing = true)
    @Bean
    public BinaryCasFormatSupport binaryCasFormatSupport()
    {
        return new BinaryCasFormatSupport();
    }

    @ConditionalOnProperty(prefix = "format.uima-xmi-xml1_1", name = "enabled", //
            havingValue = "true", matchIfMissing = true)
    @Bean
    public XmiXml11FormatSupport xmiXml11FormatSupport()
    {
        return new XmiXml11FormatSupport();
    }

    @ConditionalOnProperty(prefix = "format.uima-xmi", name = "enabled", //
            havingValue = "true", matchIfMissing = true)
    @Bean
    public XmiFormatSupport xmiFormatSupport()
    {
        return new XmiFormatSupport();
    }

    @ConditionalOnProperty(prefix = "format.uima-inline-xml", name = "enabled", //
            havingValue = "true", matchIfMissing = true)
    @Bean
    public UimaInlineXmlFormatSupport uimaInlineXmlFormatSupport()
    {
        return new UimaInlineXmlFormatSupport();
    }
}
