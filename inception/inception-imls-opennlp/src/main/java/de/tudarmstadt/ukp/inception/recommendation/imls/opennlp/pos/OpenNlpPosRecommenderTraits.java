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
package de.tudarmstadt.ukp.inception.recommendation.imls.opennlp.pos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import opennlp.tools.ml.AbstractTrainer;
import opennlp.tools.util.TrainingParameters;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenNlpPosRecommenderTraits
    implements Serializable
{
    private static final long serialVersionUID = -4514466471370195077L;

    private int trainingSetSizeLimit = Integer.MAX_VALUE;
    private int predictionLimit = Integer.MAX_VALUE;
    private int numThreads = 1;
    private double taggedTokensThreshold = 75.0;

    public int getNumThreads()
    {
        return numThreads;
    }

    public void setNumThreads(int aNumThreads)
    {
        numThreads = aNumThreads;
    }

    public int getTrainingSetSizeLimit()
    {
        return trainingSetSizeLimit;
    }

    public void setTrainingSetSizeLimit(int aTrainingSetSizeLimit)
    {
        trainingSetSizeLimit = aTrainingSetSizeLimit;
    }

    public int getPredictionLimit()
    {
        return predictionLimit;
    }

    public void setPredictionLimit(int aPredictionLimit)
    {
        predictionLimit = aPredictionLimit;
    }

    public double getTaggedTokensThreshold()
    {
        if (taggedTokensThreshold < 0.0) {
            return 0.0;
        }

        if (taggedTokensThreshold > 100.0) {
            return 100.0;
        }

        return taggedTokensThreshold;
    }

    public void setTaggedTokensThreshold(double aTaggedTokensThreshold)
    {
        taggedTokensThreshold = aTaggedTokensThreshold;
    }

    @JsonIgnore
    public TrainingParameters getParameters()
    {
        TrainingParameters parameters = TrainingParameters.defaultParams();
        parameters.put(AbstractTrainer.VERBOSE_PARAM, "false");
        parameters.put(TrainingParameters.THREADS_PARAM, numThreads);
        return parameters;
    }
}
