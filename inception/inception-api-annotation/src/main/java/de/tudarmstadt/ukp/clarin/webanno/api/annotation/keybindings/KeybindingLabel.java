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
package de.tudarmstadt.ukp.clarin.webanno.api.annotation.keybindings;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

public class KeybindingLabel
    extends Label
{
    private static final long serialVersionUID = -4797458000441245430L;

    public KeybindingLabel(String aId, IModel<KeyBinding> aModel)
    {
        super(aId, aModel.map(KeyBinding::asHtml));
    }

    public KeybindingLabel(String aId, KeyBinding aLabel)
    {
        super(aId, aLabel.asHtml());
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();
        setEscapeModelStrings(false); // SAFE - RENDERING ONLY CONTROLLED SET OF KEYS
    }
}
