package net.riccardocossu.i18split.base.properties;

import au.com.bytecode.opencsv.CSV
import au.com.bytecode.opencsv.CSV.Builder;
import au.com.bytecode.opencsv.CSVWriteProc;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.IOException;

import org.apache.commons.configuration.Configuration;

import net.riccardocossu.i18split.base.config.ConfigKeys;
import net.riccardocossu.i18split.base.driver.OutputDriver;
import net.riccardocossu.i18split.base.model.DataRow;

public class PropertiesOutputDriver implements OutputDriver {

					private static final String GENERATED_BY = "Generated by i18plit"
	public static final String CONFIG_KEY_FILES_NAME = "i18split.output.properties.fileName.suffix"
	public static final String CONFIG_KEY_IS_XML = "i18split.output.properties.isXml"
	private String[] keys
	private String fileNameSuffix
	private boolean isXml = false
	private String dirOut
	private String encoding
	private Map<String,Properties> result
	private static final String SHORT_NAME = "properties.output"

	@Override
	public String[] init(Configuration configuration) {
		keys = configuration.getStringArray(ConfigKeys.INPUT_KEYS)
		fileNameSuffix = configuration.getString(CONFIG_KEY_FILES_NAME)
		isXml = configuration.getBoolean(CONFIG_KEY_IS_XML,false)
		dirOut = configuration.getString(ConfigKeys.OUTPUT_BASE_PATH)
		encoding = configuration.getString(ConfigKeys.OUTPUT_ENCODING,"UTF-8")
		result = [:]
		keys.each { k ->
			result[k] = new Properties()
		}
		return keys

	}

	@Override
	public String getShortName() {
		return SHORT_NAME
	}

	@Override
	public void close() throws IOException {
		keys.each { k ->
			Properties target = result[k]
			new File(dirOut, isXml ? "${fileNameSuffix}_${k}.xml".toString() : "${fileNameSuffix}_${k}.properties".toString()).withOutputStream { s ->
				if(isXml) {
					target.storeToXML(s, GENERATED_BY,encoding)
				} else {
					target.store(s, GENERATED_BY)
				}
			}
		}
	}

	@Override
	public void writeRow(DataRow data) {
		String key = data.key
		keys.each { k ->
			Properties target = result[k]
			target.setProperty(key,data.values[k])
		}


	}

}