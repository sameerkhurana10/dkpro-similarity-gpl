package de.tudarmstadt.ukp.similarity.algorithms.lexsub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.apache.uima.jcas.JCas;
import org.uimafit.util.JCasUtil;

import de.tudarmstadt.langtech.substituter.MLSenseSubstituter;
import de.tudarmstadt.langtech.substituter.SenseSubstituter;
import de.tudarmstadt.langtech.substituter.Substitution;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.util.DKProContext;
import de.tudarmstadt.ukp.similarity.algorithms.api.JCasTextSimilarityMeasureBase;
import de.tudarmstadt.ukp.similarity.algorithms.api.SimilarityException;
import de.tudarmstadt.ukp.similarity.algorithms.api.TextSimilarityMeasure;


public class TWSISubstituteWrapper
	extends JCasTextSimilarityMeasureBase
{
	SenseSubstituter sensub;
	TextSimilarityMeasure measure;
	
	public TWSISubstituteWrapper(TextSimilarityMeasure measure)
	{		
		try {
			this.sensub = new MLSenseSubstituter(DKProContext.getContext().getWorkspace() + "/TWSI2/conf/TWSI2_config.conf");
		}
		catch (IOException e) {
			System.err.println("Unable to load TWSI.");
			e.printStackTrace();
		}
		this.measure = measure;
	}
	
	@Override
	public double getSimilarity(JCas jcas1, JCas jcas2)
		throws SimilarityException
	{
		List<String> subst1 = getSubstitutions(jcas1);
		List<String> subst2 = getSubstitutions(jcas2);
		
		return measure.getSimilarity(subst1, subst2);
	}
	
	public List<String> getSubstitutions(JCas jcas)
	{
		List<String> tokens = new ArrayList<String>();
		List<String> postags = new ArrayList<String>();;
		
		for (Token t : JCasUtil.select(jcas, Token.class))
		{
			try
			{
				tokens.add(t.getLemma().getValue().toLowerCase());
				postags.add(t.getPos().getPosValue());
			}
			catch (NullPointerException e) {
				System.err.println("Couldn't read lemma value for token \"" + t.getCoveredText() + "\"");
			}
		}
		
		return getSubstitutions(tokens, postags);
	}
	
	public List<String> getSubstitutions(List<String> tokens, List<String> postags)
	{	
		// Append BOS + EOS tags
		tokens.add(0, "%^%");
		postags.add(0, "BOS");
		tokens.add("%$%");
		postags.add("EOS");		
				
		// Sense substitutor operates on arrays
		String[] tokenArray = tokens.toArray(new String[tokens.size()]);
		String[] postagsArray = postags.toArray(new String[postags.size()]);
		
		List<String> resultList = new ArrayList<String>();
		
		for (int i = 0; i < tokens.size(); i++)
		{
			// System.out.println(postags.get(i) + " / " + tokens.get(i));
			
			// TWSI only operates on nouns
			if (postags.get(i).startsWith("NN"))
			{
				try
				{
					Substitution subst = sensub.getSubstitution(i, tokenArray, postagsArray);
					
					if (subst != null)
					{
						for (String[] substitution : subst.getSubstitutions())
						{
							//resultList.add(subst.getSense().replaceAll("@@", ""));
							resultList.add(substitution[0]);
						}
					}
					else
					{
						resultList.add(tokens.get(i)); 
					}
				}
				catch (InstantiationError e)
				{
					resultList.add(tokens.get(i)); 
				}
			} else {
				resultList.add(tokens.get(i));
			}
		} 
		
		// Remove BOF + EOF words
		resultList.remove(0);
		resultList.remove(resultList.size() - 1);
		
		System.out.println(resultList);
		
		return resultList;
	}

	@Override
	public double getSimilarity(Collection<String> stringList1,
			Collection<String> stringList2)
		throws SimilarityException
	{
		throw new SimilarityException(new NotImplementedException());
	}
	
	@Override
	public String getName()
	{
		return this.getClass().getSimpleName() + "_" + measure.getName();
	}

}