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
package de.tudarmstadt.ukp.inception.htmleditor.docview;

import static de.tudarmstadt.ukp.inception.security.config.InceptionSecurityWebUIApiAutoConfiguration.BASE_VIEW_URL;

import java.security.Principal;

import org.springframework.http.ResponseEntity;

import de.tudarmstadt.ukp.clarin.webanno.model.SourceDocument;
import de.tudarmstadt.ukp.inception.externaleditor.xhtml.XHtmlXmlDocumentIFrameViewFactory;

/**
 * @deprecated Use {@link XHtmlXmlDocumentIFrameViewFactory} instead
 */
@Deprecated
public interface HtmlDocumentViewController
{
    String BASE_URL = BASE_VIEW_URL + "/html";

    String getDocumentUrl(SourceDocument aDoc);

    ResponseEntity<String> getDocument(long aProjectId, long aDocumentId, Principal aPrincipal)
        throws Exception;
}
