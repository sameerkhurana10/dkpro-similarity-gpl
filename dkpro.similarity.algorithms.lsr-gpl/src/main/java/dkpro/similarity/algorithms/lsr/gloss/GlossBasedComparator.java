/*******************************************************************************
 * Copyright 2012
 * Ubiquitous Knowledge Processing (UKP) Lab
 * Technische Universität Darmstadt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package dkpro.similarity.algorithms.lsr.gloss;

import de.tudarmstadt.ukp.dkpro.lexsemresource.LexicalSemanticResource;
import de.tudarmstadt.ukp.dkpro.lexsemresource.exception.LexicalSemanticResourceException;
import dkpro.similarity.algorithms.api.TextSimilarityMeasure;
import dkpro.similarity.algorithms.lexical.string.OverlapCoefficientSimMetricComparator;
import dkpro.similarity.algorithms.lsr.LexSemResourceComparator;


public abstract class GlossBasedComparator
	extends LexSemResourceComparator
{
    public static final double NOT_FOUND = -1.0;
    
    protected TextSimilarityMeasure overlapCoefficientComparator;
    
	public GlossBasedComparator(LexicalSemanticResource lsr)
		throws LexicalSemanticResourceException
	{
		super(lsr);

        overlapCoefficientComparator = new OverlapCoefficientSimMetricComparator();
    }
}