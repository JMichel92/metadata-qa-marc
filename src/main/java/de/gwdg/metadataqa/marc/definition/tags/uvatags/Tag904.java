package de.gwdg.metadataqa.marc.definition.tags.uvatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Local Fixed Field - Maps
 */
public class Tag904 extends DataFieldDefinition {
  private static Tag904 uniqueInstance;

  private Tag904() {
    initialize();
    postCreation();
  }

  public static Tag904 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag904();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "904";
    label = "Local Fixed Field - Maps";
    mqTag = "LocalFixedFieldMaps";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = null;

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "General Cartographic Elements", "NR",
      "b", "Coded Physical data", "NR",
      "c", "Coded dates", "NR",
      "d", "Cartographic Scales", "NR",
      "e", "Type of document", "NR"
    );

    getSubfield("a").setMqTag("GeneralCartographicElements");
    getSubfield("b").setMqTag("CodedPhysicalData");
    getSubfield("c").setMqTag("CodedDates");
    getSubfield("d").setMqTag("CartographicScales");
    getSubfield("e").setMqTag("TypeOfDocument");
  }
}
