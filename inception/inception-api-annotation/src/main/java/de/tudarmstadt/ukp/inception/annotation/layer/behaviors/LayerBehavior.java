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
package de.tudarmstadt.ukp.inception.annotation.layer.behaviors;

import static java.util.Collections.emptyList;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.text.AnnotationFS;

import de.tudarmstadt.ukp.clarin.webanno.support.logging.LogMessage;
import de.tudarmstadt.ukp.inception.schema.adapter.TypeAdapter;
import de.tudarmstadt.ukp.inception.schema.layer.LayerSupport;

public interface LayerBehavior
{
    /**
     * Checks whether the given layer behavior is supported for the given layer type.
     * 
     * @param aLayerType
     *            a layer support.
     * @return whether the given layer is provided by the current layer support.
     */
    boolean accepts(LayerSupport<?, ?> aLayerType);

    /**
     * @param aAdapter
     *            the type adapter for the layer to validate
     * @param aCas
     *            the CAS to validate
     * @return if all annotations of this layer conform with the behavior configuration. This is
     *         usually called when a document is marked as finished to prevent invalid annotations
     *         ending up in the finished document.
     */
    default List<Pair<LogMessage, AnnotationFS>> onValidate(TypeAdapter aAdapter, CAS aCas)
    {
        return emptyList();
    }
}
