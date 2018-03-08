package de.gwdg.metadataqa.marc.definition.controlsubfields.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Form of item
 * https://www.loc.gov/marc/bibliographic/bd006.html
 */
public class Tag006map12 extends ControlSubfieldDefinition {
	private static Tag006map12 uniqueInstance;

	private Tag006map12() {
		initialize();
		extractValidCodes();
	}

	public static Tag006map12 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag006map12();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Form of item";
		id = "tag006map12";
		mqTag = "formOfItem";
		positionStart = 12;
		positionEnd = 13;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
		codes = Utils.generateCodes(
			" ", "None of the following",
			"a", "Microfilm",
			"b", "Microfiche",
			"c", "Microopaque",
			"d", "Large print",
			"f", "Braille",
			"o", "Online",
			"q", "Direct electronic",
			"r", "Regular print reproduction",
			"s", "Electronic",
			"|", "No attempt to code"
		);
	}
}