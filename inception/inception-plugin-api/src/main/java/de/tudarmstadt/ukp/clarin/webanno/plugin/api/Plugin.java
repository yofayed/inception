/*
 * Copyright 2019
 * Ubiquitous Knowledge Processing (UKP) Lab
 * Technische Universität Darmstadt
 *
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
package de.tudarmstadt.ukp.clarin.webanno.plugin.api;

import java.util.Set;

import org.pf4j.PluginWrapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;

public abstract class Plugin
    extends org.pf4j.Plugin
{
    private ApplicationContext applicationContext;

    public Plugin(PluginWrapper wrapper)
    {
        super(wrapper);
    }

    public final ApplicationContext getApplicationContext()
    {
        if (applicationContext == null) {
            applicationContext = createApplicationContext();
        }

        return applicationContext;
    }

    @Override
    public void stop()
    {
        // close applicationContext
        if ((applicationContext != null)
                && (applicationContext instanceof ConfigurableApplicationContext)) {
            ((ConfigurableApplicationContext) applicationContext).close();
        }
    }

    protected ApplicationContext createApplicationContext()
    {
        Plugin springPlugin = (Plugin) getWrapper().getPlugin();

        // Create an application context for this plugin using Spring annotated classes starting
        // with the plugin class
        AnnotationConfigApplicationContext pluginContext = new AnnotationConfigApplicationContext();
        pluginContext
                .setResourceLoader(new DefaultResourceLoader(getWrapper().getPluginClassLoader()));
        pluginContext.registerBean(ExportedComponentPostProcessor.class);
        pluginContext.register(springPlugin.getSources().stream().toArray(Class[]::new));

        // Attach the plugin application context to the main application context such that it can
        // access its beans for auto-wiring
        ApplicationContext parent = ((PluginManager) getWrapper().getPluginManager())
                .getApplicationContext();
        pluginContext.setParent(parent);

        // Initialize the context
        pluginContext.refresh();

        return pluginContext;
    }

    /**
     * @return a set of classes which are used to initialize the Spring application context for this
     *         plugin. These classes are not automatically exported to the main application context.
     *         In order to be exported, they must carry the {@link ExportedComponent annotation}.
     */
    public abstract Set<Class<?>> getSources();
}
