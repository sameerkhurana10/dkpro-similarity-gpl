package dkpro.similarity.algorithms.lexsub.uima;

import java.util.Map;

import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceSpecifier;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.ExternalResource;

import dkpro.similarity.algorithms.api.TextSimilarityMeasure;
import dkpro.similarity.algorithms.lexsub.BingSMTWrapper;
import dkpro.similarity.algorithms.lexsub.BingSMTWrapper.Language;
import dkpro.similarity.uima.resource.JCasTextSimilarityResourceBase;


public class BingSMTWrapperResource
	extends JCasTextSimilarityResourceBase
{
	public static final String PARAM_TEXT_SIMILARITY_RESOURCE = "TextSimilarityMeasure";
	@ExternalResource(key=PARAM_TEXT_SIMILARITY_RESOURCE)
	private TextSimilarityMeasure textSimilarityMeasure;
	
	public static final String PARAM_ORIGINAL_LANGUAGE = "OriginalLanguage";
	@ConfigurationParameter(name=PARAM_ORIGINAL_LANGUAGE, mandatory=true)
	private Language originalLanguage;
	
	public static final String PARAM_BRIDGE_LANGUAGE = "BridgeLanguage";
	@ConfigurationParameter(name=PARAM_BRIDGE_LANGUAGE, mandatory=true)
	private Language bridgeLanguage;

	@Override
	public boolean initialize(ResourceSpecifier aSpecifier,
			Map<String, Object> aAdditionalParams)
		throws ResourceInitializationException
	{
		if (!super.initialize(aSpecifier, aAdditionalParams)) {
	        return false;
	    }
		
		this.mode = TextSimilarityResourceMode.jcas;

		return true;
	}
	
	@Override
	public void afterResourcesInitialized()
		throws ResourceInitializationException
	{
		super.afterResourcesInitialized();
		
		measure = new BingSMTWrapper(textSimilarityMeasure, originalLanguage, bridgeLanguage);
	}
}
