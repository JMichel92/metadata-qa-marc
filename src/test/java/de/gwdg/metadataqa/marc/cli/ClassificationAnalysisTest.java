package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import de.gwdg.metadataqa.marc.utils.ReadMarc;
import org.junit.Before;
import org.junit.Test;
import org.marc4j.marc.Record;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ClassificationAnalysisTest extends CliTestUtils {

  private String inputFile;
  private String outputDir;
  private List<String> outputFiles;

  @Before
  public void setUp() throws Exception {
    inputFile = getPath("src/test/resources/alephseq/alephseq-example3.txt");
    outputDir = getPath("src/test/resources/output");
    outputFiles = Arrays.asList(
      "classifications-by-records.csv",
      "classifications-by-schema.csv",
      "classifications-by-schema-subfields.csv",
      "classifications-collocations.csv",
      "classifications-histogram.csv",
      "classifications-frequency-examples.csv"
    );
  }

  @Test
  public void test() throws Exception {
    Path path = FileUtils.getPath("general/0001-01.mrc");
    Record marc4jRecord = ReadMarc.read(path.toString()).get(0);
    ClassificationAnalysis analysis = new ClassificationAnalysis(new String[]{});
    BibliographicRecord marcRecord = MarcFactory.createFromMarc4j(marc4jRecord);
    analysis.processRecord(marcRecord, 1);
  }

  @Test
  public void testFullProcess() throws Exception {
    clearOutput(outputDir, outputFiles);

    ClassificationAnalysis processor = new ClassificationAnalysis(new String[]{
      "--defaultRecordType", "BOOKS",
      "--marcVersion", "GENT",
      "--alephseq",
      "--outputDir", outputDir,
      inputFile
    });
    RecordIterator iterator = new RecordIterator(processor);
    iterator.start();

    for (String outputFile : outputFiles) {
      File output = new File(outputDir, outputFile);
      assertTrue(output.exists());
      output.delete();
    }
  }

  @Test
  public void main() throws IOException {
    clearOutput(outputDir, outputFiles);

    var args = new String[]{
      "--defaultRecordType", "BOOKS",
      "--marcVersion", "GENT",
      "--alephseq",
      "--outputDir", outputDir,
      inputFile
    };
    ClassificationAnalysis.main(args);

    File output = new File(outputDir, "classifications-by-records.csv");
    assertTrue(output.exists());
    String actual = Files.readString(output.toPath());
    assertEquals(
      "records-with-classification,count\n" +
      "true,1\n",
      actual);

    output = new File(outputDir, "classifications-by-schema.csv");
    assertTrue(output.exists());
    actual = Files.readString(output.toPath());
    assertEquals(
      "id,field,location,scheme,abbreviation,abbreviation4solr,recordcount,instancecount,type\n" +
      "3,082,$a,\"Dewey Decimal Classification\",\"ddc\",ddc,1,1,CLASSIFICATION_SCHEME\n" +
      "1,650,$2,\"Library of Congress subject headings (Washington, DC: LC, Cataloging Distribution Service)\",\"lcsh\",lcsh,1,4,SUBJECT_HEADING\n" +
      "2,650,$2,\"Faceted application of subject terminology (Dublin, Ohio: OCLC)\",\"fast\",fast,1,1,SUBJECT_HEADING\n",
      actual);

    output = new File(outputDir, "classifications-by-schema-subfields.csv");
    assertTrue(output.exists());
    actual = Files.readString(output.toPath());
    assertEquals(
      "id,subfields,count\n" +
      "3,a,1\n" +
      "1,a;2,4\n" +
      "2,a;0;2,1\n", actual);

    output = new File(outputDir, "classifications-collocations.csv");
    assertTrue(output.exists());
    actual = Files.readString(output.toPath());
    assertEquals("abbreviations,recordcount,percent\n" +
      "ddc;fast;lcsh,1,100.00%\n", actual);

    output = new File(outputDir, "classifications-histogram.csv");
    assertTrue(output.exists());
    actual = Files.readString(output.toPath());
    assertEquals(
      "count,frequency\n" +
      "6,1\n", actual);

    clearOutput(outputDir, outputFiles);
  }
}
