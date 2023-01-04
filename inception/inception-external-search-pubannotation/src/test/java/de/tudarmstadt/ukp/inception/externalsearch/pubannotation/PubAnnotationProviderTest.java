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
package de.tudarmstadt.ukp.inception.externalsearch.pubannotation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import de.tudarmstadt.ukp.inception.externalsearch.ExternalSearchResult;
import de.tudarmstadt.ukp.inception.externalsearch.model.DocumentRepository;
import de.tudarmstadt.ukp.inception.externalsearch.pubannotation.model.PubAnnotationDocumentHandle;
import de.tudarmstadt.ukp.inception.externalsearch.pubannotation.traits.PubAnnotationProviderTraits;

@Tag("slow")
public class PubAnnotationProviderTest
{
    private PubAnnotationProvider sut;
    private DocumentRepository repo;
    private PubAnnotationProviderTraits traits;

    @BeforeEach
    public void setup()
    {
        sut = new PubAnnotationProvider();

        repo = new DocumentRepository("dummy", null);

        traits = new PubAnnotationProviderTraits();
    }

    @Test
    public void thatQueryWorks() throws Exception
    {
        List<PubAnnotationDocumentHandle> results = sut.query(traits, "binding");

        // System.out.println(results);

        assertThat(results).isNotEmpty();
    }

    @Test
    public void thatExecuteQueryWorks() throws Exception
    {
        List<ExternalSearchResult> results = sut.executeQuery(repo, traits, "binding");

        // System.out.println(results);

        assertThat(results).isNotEmpty();
    }

    @Test
    public void thatDocumentTextCanBeRetrieved() throws Exception
    {
        String text = sut.getDocumentText(repo, traits, "PMC", "1064873");

        // System.out.println(text);

        assertThat(text).isNotEmpty();
    }
}
