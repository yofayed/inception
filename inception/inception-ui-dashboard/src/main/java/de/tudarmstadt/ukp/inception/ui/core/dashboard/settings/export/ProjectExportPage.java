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
package de.tudarmstadt.ukp.inception.ui.core.dashboard.settings.export;

import static de.tudarmstadt.ukp.clarin.webanno.ui.core.page.ProjectPageBase.NS_PROJECT;
import static de.tudarmstadt.ukp.clarin.webanno.ui.core.page.ProjectPageBase.PAGE_PARAM_PROJECT;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.annotation.mount.MountPath;

import de.tudarmstadt.ukp.inception.project.export.settings.ProjectExportPanel;
import de.tudarmstadt.ukp.inception.ui.core.dashboard.settings.ProjectSettingsDashboardPageBase;

@MountPath(NS_PROJECT + "/${" + PAGE_PARAM_PROJECT + "}/settings/export2")
public class ProjectExportPage
    extends ProjectSettingsDashboardPageBase
{
    private static final long serialVersionUID = 5889016668816051716L;

    public ProjectExportPage(PageParameters aParameters)
    {
        super(aParameters);
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        add(new ProjectExportPanel("panel", getProjectModel()));
    }
}
