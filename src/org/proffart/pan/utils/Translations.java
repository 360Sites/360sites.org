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
	
	static Logger LOG = Logger.getLogger(Translations.class);
	
	private static String translationFilePath="//languages//";
	private static String translationFileDest=".properties";
	private static String translationFileName="languages";

	
	public static String getFromTranslations(String keyWord,_User user)
	{
		String language=user.getLanguage();
		if(language==null)
		{
			LOG.warn("User doesn't contain language, added default="+DEFAULT_LANGUAGE);
			language=DEFAULT_LANGUAGE;
		}
		String translationFile=translationFilePath+translationFileName;
		Properties langProp=new Properties();
		try
		{
			translationFile+="."+language+translationFileDest;
			langProp.load(Translations.class.getResourceAsStream(translationFile));
		}
		catch(IOException ex)
		{
			LOG.warn("Cannot load property file language="+language+", trying for default="+DEFAULT_LANGUAGE, ex);
			translationFile+="."+DEFAULT_LANGUAGE+translationFileDest;
			try {
				langProp.load(Translations.class.getResourceAsStream(translationFile));
			} catch (IOException e) {
				LOG.warn("Cannot load property file language="+DEFAULT_LANGUAGE+", returning keyword", ex);
				return keyWord;
			}		
		}
		langProp.getProperty(keyWord);
		
		return langProp.getProperty(keyWord);
	}

}
