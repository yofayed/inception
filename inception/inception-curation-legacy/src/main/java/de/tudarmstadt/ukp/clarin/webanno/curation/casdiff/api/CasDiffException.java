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
package de.tudarmstadt.ukp.clarin.webanno.curation.casdiff.api;

import de.tudarmstadt.ukp.inception.schema.adapter.AnnotationException;

/**
 * Thrown if there is a problem during CAS diff computation.
 */
public class CasDiffException
    extends AnnotationException
{
    private static final long serialVersionUID = 1280015349963924638L;

    public CasDiffException()
    {
        super();
    }

    public CasDiffException(String message)
    {
        super(message);
    }

    public CasDiffException(String aMessage, Throwable aCause)
    {
        super(aMessage, aCause);
    }

    public CasDiffException(Throwable aCause)
    {
        super(aCause);
    }
}
