package dkpro.similarity.uima.resource.ml;

import java.io.File;
import java.util.Map;

import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceSpecifier;

import dkpro.similarity.algorithms.ml.ClassifierSimilarityMeasure;
import dkpro.similarity.algorithms.ml.ClassifierSimilarityMeasure.WekaClassifier;
import dkpro.similarity.uima.resource.JCasTextSimilarityResourceBase;


public class ClassifierResource
	extends JCasTextSimilarityResourceBase
{	
	public static final String PARAM_CLASSIFIER = "Classifier";
	@ConfigurationParameter(name=PARAM_CLASSIFIER, mandatory=true)
	private WekaClassifier classifier;
	
	public static final String PARAM_TRAIN_ARFF = "TRAIN_ARFF";
	@ConfigurationParameter(name=PARAM_TRAIN_ARFF, mandatory=true)
	private File trainArff;
	
	public static final String PARAM_TEST_ARFF = "TEST_ARFF";
	@ConfigurationParameter(name=PARAM_TEST_ARFF, mandatory=true)
	private File testArff;
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public boolean initialize(ResourceSpecifier aSpecifier, Map aAdditionalParams)
        throws ResourceInitializationException
    {
        if (!super.initialize(aSpecifier, aAdditionalParams)) {
            return false;
        }
        
        try {
        	this.setMode(TextSimilarityResourceMode.jcas);
            measure = new ClassifierSimilarityMeasure(classifier, trainArff, testArff);
        }
        catch (Exception e) {
            throw new ResourceInitializationException(e);
        }
        
        return true;
    }
}
