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
package de.tudarmstadt.ukp.clarin.webanno.webapp.remoteapi.webhooks;

import java.util.ArrayList;
import java.util.List;

public class Webhook
{
    private String url;
    private String secret;
    private String authHeader;
    private String authHeaderValue;
    private boolean enabled = true;
    private List<String> topics = new ArrayList<>();
    private boolean verifyCertificates = true;

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String aUrl)
    {
        url = aUrl;
    }

    public String getSecret()
    {
        return secret;
    }

    public void setSecret(String aSecret)
    {
        secret = aSecret;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean aEnabled)
    {
        enabled = aEnabled;
    }

    public List<String> getTopics()
    {
        return topics;
    }

    public void setTopics(List<String> aTopics)
    {
        topics = aTopics;
    }

    public boolean isVerifyCertificates()
    {
        return verifyCertificates;
    }

    public void setVerifyCertificates(boolean aVerifyCertificates)
    {
        verifyCertificates = aVerifyCertificates;
    }

    public String getAuthHeader()
    {
        return authHeader;
    }

    public void setAuthHeader(String aAuthHeader)
    {
        authHeader = aAuthHeader;
    }

    public String getAuthHeaderValue()
    {
        return authHeaderValue;
    }

    public void setAuthHeaderValue(String aAuthHeaderValue)
    {
        authHeaderValue = aAuthHeaderValue;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Webhook [url=");
        builder.append(url);
        builder.append("]");
        return builder.toString();
    }
}
