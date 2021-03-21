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
package de.tudarmstadt.ukp.inception.recommendation.api.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class LearningRecordTest
{

    @Test
    public void thatTokenTextIsTruncated()
    {
        char[] charArray = new char[300];
        Arrays.fill(charArray, 'X');
        String longTokenText = new String(charArray);

        LearningRecord sut = new LearningRecord();
        sut.setTokenText(longTokenText);

        assertThat(sut.getTokenText()).as("TokenText has been truncated").hasSize(255);
    }
}
