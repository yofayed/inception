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
package de.tudarmstadt.ukp.clarin.webanno.support.logging;

import java.util.Collection;
import java.util.Collections;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.uima.util.Level;

@Plugin(name = "RingBufferAppender", category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE)
public class RingBufferAppender
    extends AbstractAppender
{
    private static final Collection<LogMessage> events = Collections
            .synchronizedCollection(new CircularFifoQueue<>(1000));

    public RingBufferAppender(String name, Filter filter)
    {
        super(name, filter, null, false, new Property[0]);
    }

    @PluginFactory
    public static RingBufferAppender createAppender(@PluginAttribute("name") String name,
            @PluginElement("Filter") Filter filter)
    {
        return new RingBufferAppender(name, filter);
    }

    @Override
    public void append(LogEvent aEvent)
    {
        LogLevel level;
        if (aEvent.getLevel().intLevel() >= Level.ERROR_INT) {
            level = LogLevel.ERROR;
        }
        else if (aEvent.getLevel().intLevel() >= Level.WARN_INT) {
            level = LogLevel.WARN;
        }
        else {
            level = LogLevel.INFO;
        }

        events.add(new LogMessage(null, level, aEvent.getMessage().getFormattedMessage()));
    }

    public static Collection<LogMessage> events()
    {
        return events;
    }
}
