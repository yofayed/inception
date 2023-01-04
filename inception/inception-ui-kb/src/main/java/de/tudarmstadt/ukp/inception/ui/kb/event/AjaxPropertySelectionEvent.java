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
package de.tudarmstadt.ukp.inception.ui.kb.event;

import org.apache.wicket.ajax.AjaxRequestTarget;

import de.tudarmstadt.ukp.inception.kb.graph.KBProperty;

/**
 * Event broadcast when a {@link KBProperty} is being selected.
 */
public class AjaxPropertySelectionEvent
    extends AjaxSelectionEvent
{

    private boolean redrawConceptandPropertyListPanels;
    private KBProperty newSelection;

    public AjaxPropertySelectionEvent(AjaxRequestTarget aTarget, KBProperty aNewSelection)
    {
        this(aTarget, aNewSelection, false);
    }

    public AjaxPropertySelectionEvent(AjaxRequestTarget aTarget, KBProperty aNewSelection,
            boolean aRedrawConceptandPropertyListPanels)
    {
        super(aTarget, aNewSelection != null ? aNewSelection.toKBHandle() : null);
        redrawConceptandPropertyListPanels = aRedrawConceptandPropertyListPanels;
        newSelection = aNewSelection;
    }

    public boolean isRedrawConceptandPropertyListPanels()
    {
        return redrawConceptandPropertyListPanels;
    }

    public void setRedrawConceptandPropertyListPanels(boolean aRedrawConceptandPropertyListPanels)
    {
        redrawConceptandPropertyListPanels = aRedrawConceptandPropertyListPanels;
    }

    public KBProperty getNewSelection()
    {
        return newSelection;
    }
}
