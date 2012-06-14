/*******************************************************************************
 * Copyright 2012
 * Ubiquitous Knowledge Processing (UKP) Lab
 * Technische Universität Darmstadt
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl-3.0.txt
 *******************************************************************************/
package de.tudarmstadt.ukp.similarity.algorithms.lexical.string;

import uk.ac.shef.wit.simmetrics.similaritymetrics.DiceSimilarity;

public class DiceSimMetricComparator
	extends SimMetricComparator_ImplBase
{
    
    public DiceSimMetricComparator() {
        this.similarityMeasure = new DiceSimilarity();
    }
}