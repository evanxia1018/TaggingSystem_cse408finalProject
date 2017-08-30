package project;

import semilar.config.ConfigManager;
import semilar.data.Sentence;
import semilar.sentencemetrics.CorleyMihalceaComparer;
import semilar.tools.preprocessing.SentencePreprocessor;

/**
 * Created by evanxia on 6/17/17.
 */
public class SentenceSimilarityCalculator {
    //fields
    CorleyMihalceaComparer cmComparer = new CorleyMihalceaComparer(0.1f, false, "NONE", "par");
    //methods
    public float getSimilarity(String str1, String str2){
        // first of all set the semilar data folder path (ending with /).
        ConfigManager.setSemilarDataRootFolder("data/semilar-data/");
        Sentence sentence1;
        Sentence sentence2;
        SentencePreprocessor preprocessor = new SentencePreprocessor(SentencePreprocessor.TokenizerType.STANFORD,
                SentencePreprocessor.TaggerType.STANFORD, SentencePreprocessor.StemmerType.PORTER, SentencePreprocessor.ParserType.STANFORD);
        sentence1 = preprocessor.preprocessSentence(str1);
        sentence2 = preprocessor.preprocessSentence(str2);
        float result = cmComparer.computeSimilarity(sentence1, sentence2);
        //System.out.println(result);
        return result;
    }
}
