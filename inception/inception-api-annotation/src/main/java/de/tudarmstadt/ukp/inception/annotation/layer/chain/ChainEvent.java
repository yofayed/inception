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
package de.tudarmstadt.ukp.inception.annotation.layer.chain;

import org.apache.uima.cas.text.AnnotationFS;

import de.tudarmstadt.ukp.clarin.webanno.model.AnnotationLayer;
import de.tudarmstadt.ukp.clarin.webanno.model.SourceDocument;
import de.tudarmstadt.ukp.inception.annotation.events.AnnotationEvent;
import de.tudarmstadt.ukp.inception.rendering.model.Range;

public class ChainEvent
    extends AnnotationEvent
{
    private static final long serialVersionUID = 4408347442575210554L;

    private final AnnotationFS annotation;

    public ChainEvent(Object aSource, SourceDocument aDocument, String aUser,
            AnnotationLayer aLayer, AnnotationFS aAnnotation)
    {
        super(aSource, aDocument, aUser, aLayer);
        annotation = aAnnotation;
    }

    public AnnotationFS getAnnotation()
    {
        return annotation;
    }

    @Override
    public Range getAffectedRange()
    {
        return new Range(annotation);
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getSimpleName());
        builder.append(" [");
        if (getDocument() != null) {
            builder.append("docID=");
            builder.append(getDocument().getId());
            builder.append(", user=");
            builder.append(getUser());
            builder.append(", ");
        }
        builder.append("element=[");
        builder.append(annotation.getBegin());
        builder.append("-");
        builder.append(annotation.getEnd());
        builder.append("](");
        builder.append(annotation.getCoveredText());
        builder.append(")]");
        return builder.toString();
    }
}
