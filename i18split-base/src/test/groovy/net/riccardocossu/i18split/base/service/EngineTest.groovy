package net.riccardocossu.i18split.base.service

import net.riccardocossu.i18split.base.csv.CsvOutputDriver
import static org.junit.Assert.*
import net.riccardocossu.i18split.base.config.ConfigKeys
import net.riccardocossu.i18split.base.csv.CsvInputDriver;
import net.riccardocossu.i18split.base.csv.CsvOutputDriver
import net.riccardocossu.i18split.base.properties.PropertiesInputDriver
import net.riccardocossu.i18split.base.properties.PropertiesOutputDriver;

import org.apache.commons.configuration.BaseConfiguration
import org.apache.commons.configuration.Configuration
import org.junit.Test

public class EngineTest {
	@Test
	def void readPropertiesAndWriteCsvShouldNotCrash() {
		Configuration conf  = new BaseConfiguration()
		conf.addProperty(PropertiesInputDriver.CONFIG_KEY_FILES_NAME, "messages")
		conf.addProperty(ConfigKeys.INPUT_BASE_PATH, "src/test/resources/engine/inPropertiesOutCsv")
		conf.addProperty(ConfigKeys.OUTPUT_BASE_PATH, System.getProperty("java.io.tmpdir"))
		conf.addProperty(CsvOutputDriver.FILE_NAME, "i18split.csv")
		conf.addProperty(ConfigKeys.INPUT_DRIVER, PropertiesInputDriver.SHORT_NAME)
		conf.addProperty(ConfigKeys.OUTPUT_DRIVER, CsvOutputDriver.SHORT_NAME)
		conf.addProperty(PropertiesInputDriver.CONFIG_KEY_MASTER_LOCALE, "en")
		Engine eng = new Engine(conf)
		eng.process()

	}

	@Test
	def void readCsvAndWritePropertiesShouldNotCrash() {
		Configuration conf  = new BaseConfiguration()
		conf.addProperty(ConfigKeys.INPUT_BASE_PATH, "src/test/resources/engine/inCsvOutProperties")
		conf.addProperty(ConfigKeys.OUTPUT_BASE_PATH, System.getProperty("java.io.tmpdir"))
		conf.addProperty(PropertiesOutputDriver.CONFIG_KEY_FILES_NAME, "i18split")
		conf.addProperty(CsvInputDriver.FILE_NAME, "i18split.csv")
		conf.addProperty(ConfigKeys.INPUT_DRIVER, CsvInputDriver.SHORT_NAME)
		conf.addProperty(ConfigKeys.OUTPUT_DRIVER, PropertiesOutputDriver.SHORT_NAME)
		Engine eng = new Engine(conf)
		eng.process()

	}


}