package org.proffart.pan.utils;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import org.proffart.pan.beans._User;

/**
 * 
 * @author Karen S.
 * @version 1.0.0
 * copyright Proffart 2014
 * 
 */
public class Translations {
	private static final String ENGLISH="eng";
	private static final String RUSSIAN="rus";
	private static final String DEFAULT_LANGUAGE=ENGLISH;
	private static String language;
	
	private static  boolean isRecursive=false;
	private static int recursiveCount=0;
	static Logger LOG = Logger.getLogger(Translations.class);
	
	private static String translationFilePath="//languages//";
	private static String translationFileDest=".properties";
	private static String translationFileName="languages";

	/**
	 * 
	 * @param keyWord
	 * @param user
	 * @return Translation
	 */
	public static String getFromTranslations(String keyWord,_User user)
	{
		if(!isRecursive)
		{
		language=user.getLanguage();
		}
		else
		{
			//Second time
			language=DEFAULT_LANGUAGE;
		}
		Properties langProp=new Properties();
		//if user doesn't contain language property 
		if(language==null)
		{
			LOG.warn("User object doesn't contain language, added default="+DEFAULT_LANGUAGE);
			language=DEFAULT_LANGUAGE;
		}
		String translationFile=translationFilePath+translationFileName;
		try
		{
			//loading property
			translationFile+="."+language+translationFileDest;
			langProp.load(Translations.class.getResourceAsStream(translationFile));
		}
		catch(IOException ex)
		{
			//cannot find property  file trying with default
			LOG.warn("Cannot load property file language="+language+", trying for default="+DEFAULT_LANGUAGE, ex);
			language=DEFAULT_LANGUAGE;
			translationFile+="."+language+translationFileDest;
			try {
				langProp.load(Translations.class.getResourceAsStream(translationFile));
			} catch (IOException e) {
				//cannot find property file,returning keyword
				LOG.warn("Cannot load property file default_language="+DEFAULT_LANGUAGE+", returning keyword", ex);
				return keyWord;
			}		
		}
		String transString= langProp.getProperty(keyWord);		
		//In case that can't find translation from default language
		if(language==DEFAULT_LANGUAGE  && transString==null)
		{
			LOG.warn("Can't find translation for DEFAULT_LANGUAGE="+DEFAULT_LANGUAGE+",Returning keyWord");
			return keyWord;
		}
		
		//In case that can't find translation , trying with default language (recursion)
		if(language!=DEFAULT_LANGUAGE && transString==null)
		{
			LOG.warn("Can't find translation for language="+language+", Going recursion");
			isRecursive=true;
			recursiveCount++;
			return getFromTranslations(keyWord, user);
		}
		//In case that recursion doesn't finish , it can't happen :D
		if(recursiveCount>2)
		{
			LOG.warn("Terminated getTransaltion  recursion, returnng keyword");
			return keyWord;
		}
		
		return transString;
	}

}
