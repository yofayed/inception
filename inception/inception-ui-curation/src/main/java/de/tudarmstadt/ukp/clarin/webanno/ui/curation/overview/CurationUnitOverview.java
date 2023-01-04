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
package de.tudarmstadt.ukp.clarin.webanno.ui.curation.overview;

import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;

import de.tudarmstadt.ukp.inception.rendering.editorstate.AnnotatorState;

public class CurationUnitOverview
    extends WebMarkupContainer
{
    private static final long serialVersionUID = -2352248253065970170L;

    public CurationUnitOverview(String aId, IModel<AnnotatorState> aState,
            IModel<List<CurationUnit>> curationUnits)
    {
        super(aId);

        setOutputMarkupPlaceholderTag(true);
        add(new ListView<CurationUnit>("unit", curationUnits)
        {
            private static final long serialVersionUID = 8539162089561432091L;

            @Override
            protected void populateItem(ListItem<CurationUnit> item)
            {
                item.add(new CurationUnitOverviewLink("label", item.getModel(), aState));
            }
        });
    }
}
