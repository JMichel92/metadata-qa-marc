package de.gwdg.metadataqa.marc.definition.general.validator;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.MarcRecord;
import de.gwdg.metadataqa.marc.definition.ValidatorResponse;
import de.gwdg.metadataqa.marc.definition.tags.tags6xx.Tag650;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class ClassificationReferenceValidatorTest {

  @Test
  public void test() {
    MarcRecord record = new MarcRecord("test");
    DataField field = new DataField(Tag650.getInstance(), " ", "7",
      "8", "3\\p",
      "0", "(DE-588)4020758-4",
      "0", "http://d-nb.info/gnd/4020758-4",
      "0", "(DE-101)040207587",
      "a", "Gesundheitsberatung");
    field.setRecord(record);

    ValidatorResponse response = ClassificationReferenceValidator.validate(field);
    assertFalse(response.isValid());
  }

}
