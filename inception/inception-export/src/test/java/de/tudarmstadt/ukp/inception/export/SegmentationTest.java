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
package de.tudarmstadt.ukp.inception.export;

import static java.util.Arrays.asList;
import static org.apache.uima.fit.util.CasUtil.toText;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.jcas.JCas;
import org.junit.jupiter.api.Test;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

public class SegmentationTest
{
    @Test
    public void testSplitSentences() throws Exception
    {
        JCas jcas = JCasFactory.createText("I am one. I am two.", "en");

        DocumentImportExportServiceImpl.splitSentences(jcas.getCas());

        assertEquals(asList("I am one.", "I am two."), toText(select(jcas, Sentence.class)));
    }

    @Test
    public void testTokenize() throws Exception
    {
        JCas jcas = JCasFactory.createText("i am one.i am two.", "en");
        new Sentence(jcas, 0, 9).addToIndexes();
        ;
        new Sentence(jcas, 9, 18).addToIndexes();

        DocumentImportExportServiceImpl.tokenize(jcas.getCas());

        assertEquals(asList("i am one.", "i am two."), toText(select(jcas, Sentence.class)));
        assertEquals(asList("i", "am", "one", ".", "i", "am", "two", "."),
                toText(select(jcas, Token.class)));
    }
}
