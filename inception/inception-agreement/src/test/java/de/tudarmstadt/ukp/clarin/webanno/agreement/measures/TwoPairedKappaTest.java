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
package de.tudarmstadt.ukp.clarin.webanno.agreement.measures;

import static de.tudarmstadt.ukp.clarin.webanno.agreement.measures.AgreementTestUtils.getCohenKappaAgreement;
import static de.tudarmstadt.ukp.clarin.webanno.curation.casdiff.CasDiff.doDiff;
import static de.tudarmstadt.ukp.clarin.webanno.curation.casdiff.LinkCompareBehavior.LINK_TARGET_AS_LABEL;
import static de.tudarmstadt.ukp.clarin.webanno.curation.casdiff.relation.RelationDiffAdapter.DEPENDENCY_DIFF_ADAPTER;
import static de.tudarmstadt.ukp.clarin.webanno.curation.casdiff.span.SpanDiffAdapter.POS_DIFF_ADAPTER;
import static java.util.Arrays.asList;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.factory.JCasFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.tudarmstadt.ukp.clarin.webanno.agreement.AgreementResult;
import de.tudarmstadt.ukp.clarin.webanno.agreement.AgreementUtils;
import de.tudarmstadt.ukp.clarin.webanno.agreement.results.coding.CodingAgreementResult;
import de.tudarmstadt.ukp.clarin.webanno.curation.casdiff.CasDiff;
import de.tudarmstadt.ukp.clarin.webanno.curation.casdiff.CasDiff.DiffResult;
import de.tudarmstadt.ukp.clarin.webanno.model.SourceDocument;
import de.tudarmstadt.ukp.clarin.webanno.security.model.User;
import de.tudarmstadt.ukp.clarin.webanno.tsv.WebannoTsv2Reader;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;

/**
 * Unit Test for Kappa Agreement. The example reads two TSV files with POS and DEP annotations for
 * two users and check the disagreement
 *
 */
public class TwoPairedKappaTest
{
    private User user1, user2, user3;
    private SourceDocument document;
    private CAS kappatestCas, kappaspandiff, kappaarcdiff, kappaspanarcdiff;

    @BeforeEach
    public void init() throws Exception
    {
        user1 = new User();
        user1.setUsername("user1");

        user2 = new User();
        user2.setUsername("user2");

        user3 = new User();
        user3.setUsername("user3");

        document = new SourceDocument();

        kappatestCas = JCasFactory.createJCas().getCas();
        CollectionReader reader1 = createReader(WebannoTsv2Reader.class,
                WebannoTsv2Reader.PARAM_SOURCE_LOCATION, "src/test/resources/",
                WebannoTsv2Reader.PARAM_PATTERNS, "kappatest.tsv");
        reader1.getNext(kappatestCas);

        kappaspandiff = JCasFactory.createJCas().getCas();
        CollectionReader reader2 = createReader(WebannoTsv2Reader.class,
                WebannoTsv2Reader.PARAM_SOURCE_LOCATION, "src/test/resources/",
                WebannoTsv2Reader.PARAM_PATTERNS, "kappaspandiff.tsv");
        reader2.getNext(kappaspandiff);

        kappaarcdiff = JCasFactory.createJCas().getCas();
        CollectionReader reader3 = createReader(WebannoTsv2Reader.class,
                WebannoTsv2Reader.PARAM_SOURCE_LOCATION, "src/test/resources/",
                WebannoTsv2Reader.PARAM_PATTERNS, "kappaarcdiff.tsv");
        reader3.getNext(kappaarcdiff);

        kappaspanarcdiff = JCasFactory.createJCas().getCas();
        CollectionReader reader4 = createReader(WebannoTsv2Reader.class,
                WebannoTsv2Reader.PARAM_SOURCE_LOCATION, "src/test/resources/",
                WebannoTsv2Reader.PARAM_PATTERNS, "kappaspanarcdiff.tsv");
        reader4.getNext(kappaspanarcdiff);
    }

    @Test
    public void testTwoUserSameAnnotation() throws Exception
    {
        Map<User, List<SourceDocument>> userDocs = new HashMap<>();
        userDocs.put(user1, asList(document));
        userDocs.put(user2, asList(document));

        Map<User, CAS> userCases = new HashMap<>();
        userCases.put(user1, kappatestCas);
        userCases.put(user2, kappatestCas);

        Map<SourceDocument, Map<User, CAS>> documentJCases = new HashMap<>();
        documentJCases.put(document, userCases);

        // Check against new impl
        CasDiff diff = doDiff(asList(POS_DIFF_ADAPTER), LINK_TARGET_AS_LABEL, convert(userCases));
        DiffResult result = diff.toResult();
        AgreementResult agreement = getCohenKappaAgreement(diff, POS.class.getName(), "PosValue",
                convert(userCases));

        // Asserts
        System.out.printf("Agreement: %s%n", agreement.toString());
        result.print(System.out);

        assertEquals(1.0d, agreement.getAgreement(), 0.000001);
        assertEquals(9, result.size());
        assertEquals(0, result.getDifferingConfigurationSets().size());
        assertEquals(0, result.getIncompleteConfigurationSets().size());
    }

    private Map<String, List<CAS>> convert(Map<User, CAS> aMap)
    {
        Map<String, List<CAS>> map = new LinkedHashMap<>();
        for (Entry<User, CAS> e : aMap.entrySet()) {
            map.put(e.getKey().getUsername(), asList(e.getValue()));
        }
        return map;
    }

    @Test
    public void testTwoUserDiffArcAnnotation() throws Exception
    {
        Map<User, List<SourceDocument>> userDocs = new HashMap<>();
        userDocs.put(user1, asList(document));
        userDocs.put(user2, asList(document));

        Map<User, CAS> userCases = new HashMap<>();
        userCases.put(user1, kappatestCas);
        userCases.put(user2, kappaarcdiff);

        Map<SourceDocument, Map<User, CAS>> documentJCases = new HashMap<>();
        documentJCases.put(document, userCases);

        // Check against new impl
        CasDiff diff = doDiff(asList(DEPENDENCY_DIFF_ADAPTER), LINK_TARGET_AS_LABEL,
                convert(userCases));
        DiffResult result = diff.toResult();
        AgreementResult agreement = getCohenKappaAgreement(diff, Dependency.class.getName(),
                "DependencyType", convert(userCases));

        // Asserts
        System.out.printf("Agreement: %s%n", agreement.toString());
        result.print(System.out);

        assertEquals(0.86153d, agreement.getAgreement(), 0.00001d);
        assertEquals(9, result.size());
        assertEquals(1, result.getDifferingConfigurationSets().size());
        assertEquals(0, result.getIncompleteConfigurationSets().size());
    }

    @Test
    public void testTwoUserDiffSpanAnnotation() throws Exception
    {
        Map<User, List<SourceDocument>> userDocs = new HashMap<>();
        userDocs.put(user1, asList(document));
        userDocs.put(user2, asList(document));

        Map<User, CAS> userCases = new HashMap<>();
        userCases.put(user1, kappatestCas);
        userCases.put(user2, kappaspandiff);

        Map<SourceDocument, Map<User, CAS>> documentJCases = new HashMap<>();
        documentJCases.put(document, userCases);

        // Check against new impl
        CasDiff diff = doDiff(asList(POS_DIFF_ADAPTER), LINK_TARGET_AS_LABEL, convert(userCases));
        DiffResult result = diff.toResult();
        AgreementResult agreement = getCohenKappaAgreement(diff, POS.class.getName(), "PosValue",
                convert(userCases));

        // Asserts
        System.out.printf("Agreement: %s%n", agreement.toString());
        result.print(System.out);

        assertEquals(0.86153d, agreement.getAgreement(), 0.00001d);
        assertEquals(9, result.size());
        assertEquals(1, result.getDifferingConfigurationSets().size());
        assertEquals(0, result.getIncompleteConfigurationSets().size());
    }

    @Test
    public void testTwoUserDiffArcAndSpanAnnotation() throws Exception
    {
        Map<User, List<SourceDocument>> userDocs = new HashMap<>();
        userDocs.put(user1, asList(document));
        userDocs.put(user2, asList(document));

        Map<User, CAS> userCases = new HashMap<>();
        userCases.put(user1, kappatestCas);
        userCases.put(user2, kappaspanarcdiff);

        Map<SourceDocument, Map<User, CAS>> documentJCases = new HashMap<>();
        documentJCases.put(document, userCases);

        // Check against new impl
        CasDiff diff = doDiff(asList(DEPENDENCY_DIFF_ADAPTER), LINK_TARGET_AS_LABEL,
                convert(userCases));
        DiffResult result = diff.toResult();
        CodingAgreementResult agreement = getCohenKappaAgreement(diff, Dependency.class.getName(),
                "DependencyType", convert(userCases));

        // Asserts
        System.out.printf("Agreement: %s%n", agreement.toString());
        result.print(System.out);
        AgreementUtils.dumpAgreementStudy(System.out, agreement);

        assertEquals(0.86153d, agreement.getAgreement(), 0.00001d);
        assertEquals(9, result.size());
        assertEquals(1, result.getDifferingConfigurationSets().size());
        assertEquals(0, result.getIncompleteConfigurationSets().size());
    }

    @Test
    public void testThreeUserDiffArcAndSpanAnnotation() throws Exception
    {
        Map<User, List<SourceDocument>> userDocs = new HashMap<>();
        userDocs.put(user1, asList(document));
        userDocs.put(user2, asList(document));
        userDocs.put(user3, asList(document));

        Map<User, CAS> userCases = new HashMap<>();
        userCases.put(user1, kappatestCas);
        userCases.put(user2, kappaspandiff);
        userCases.put(user3, kappaspanarcdiff);

        Map<SourceDocument, Map<User, CAS>> documentJCases = new HashMap<>();
        documentJCases.put(document, userCases);

        // Check against new impl
        CasDiff diff = doDiff(asList(POS_DIFF_ADAPTER, DEPENDENCY_DIFF_ADAPTER),
                LINK_TARGET_AS_LABEL, convert(userCases));
        DiffResult result = diff.toResult();

        Map<String, List<CAS>> user1and2 = convert(userCases);
        user1and2.remove("user3");
        AgreementResult agreement12 = getCohenKappaAgreement(diff, Dependency.class.getName(),
                "DependencyType", user1and2);

        Map<String, List<CAS>> user2and3 = convert(userCases);
        user2and3.remove("user1");
        AgreementResult agreement23 = getCohenKappaAgreement(diff, Dependency.class.getName(),
                "DependencyType", user2and3);

        Map<String, List<CAS>> user1and3 = convert(userCases);
        user1and3.remove("user2");
        AgreementResult agreement13 = getCohenKappaAgreement(diff, Dependency.class.getName(),
                "DependencyType", user1and3);

        // Asserts
        result.print(System.out);

        System.out.printf("New agreement 1/2: %s%n", agreement12.toString());
        System.out.printf("New agreement 2/3: %s%n", agreement23.toString());
        System.out.printf("New agreement 1/3: %s%n", agreement13.toString());
    }
}
