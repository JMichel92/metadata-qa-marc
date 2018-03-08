package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Class of braille writing
 * https://www.loc.gov/marc/bibliographic/bd007f.html
 */
public class Tag007tactile03 extends ControlSubfieldDefinition {
	private static Tag007tactile03 uniqueInstance;

	private Tag007tactile03() {
		initialize();
		extractValidCodes();
	}

	public static Tag007tactile03 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag007tactile03();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Class of braille writing";
		id = "tag007tactile03";
		mqTag = "classOfBrailleWriting";
		positionStart = 3;
		positionEnd = 5;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007f.html";
		codes = Utils.generateCodes(
			" ", "No specified class of braille writing",
			"a", "Literary braille",
			"b", "Format code braille",
			"c", "Mathematics and scientific braille",
			"d", "Computer braille",
			"e", "Music braille",
			"m", "Multiple braille types",
			"n", "Not applicable",
			"u", "Unknown",
			"z", "Other",
			"||", "No attempt to code"
		);
		repeatableContent = true;
		unitLength = 1;
	}
}