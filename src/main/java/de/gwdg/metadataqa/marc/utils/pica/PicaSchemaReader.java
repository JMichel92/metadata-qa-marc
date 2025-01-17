package de.gwdg.metadataqa.marc.utils.pica;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PicaSchemaReader {

  private JSONParser parser = new JSONParser(JSONParser.MODE_RFC4627);
  private Map<String, PicaFieldDefinition> map = new HashMap<>();
  private PicaSchemaManager schema = new PicaSchemaManager();

  private PicaSchemaReader(String fileName) {
    try {
      readSchema(fileName);
    } catch (IOException | ParseException | URISyntaxException e) {
      e.printStackTrace();
    }
  }

  public static Map<String, PicaFieldDefinition> create(String filename) {
    PicaSchemaReader reader = new PicaSchemaReader(filename);
    return reader.map;
  }

  public static PicaSchemaManager createSchema(String filename) {
    PicaSchemaReader reader = new PicaSchemaReader(filename);
    return reader.getSchema();
  }

  private void readSchema(String fileName) throws IOException, ParseException, URISyntaxException {
    // Path tagsFile = FileUtils.getPath(fileName);
    Object obj = parser.parse(new FileReader(fileName));
    JSONObject jsonObject = (JSONObject) obj;
    JSONObject fields = (JSONObject) jsonObject.get("fields");
    for (String id : fields.keySet()) {
      JSONObject field = (JSONObject) fields.get(id);
      PicaTagDefinition tag = new PicaTagDefinition(
        (String) field.get("pica3"),         // pica3
        (String) field.get("tag"),           // picaplus
        (boolean) field.get("repeatable"),   // repeatable
        false,                               // sheet
        (String) field.get("label")          // label
      );
      tag.setId(id);
      tag.setDescriptionUrl((String) field.get("url"));
      tag.setModified((String) field.get("modified"));
      tag.setOccurrence((String) field.get("occurrence"));
      tag.setCounter((String) field.get("counter"));
      if (tag.getCounter() != null && tag.getOccurrence() != null) {
        System.err.println(id + " has both");
      }
      processSubfields(field, tag);
      PicaFieldDefinition definition = new PicaFieldDefinition(tag);
      addTag(definition);
      if (id.endsWith("/00")) {
        PicaFieldDefinition definition2 = definition.copyWithChangesId();
        addTag(definition2);
      }
    }
  }

  private void processSubfields(JSONObject field, PicaTagDefinition tag) {
    Object subfieldsRaw = field.get("subfields");
    List<SubfieldDefinition> subfieldDefinitions = new LinkedList<>();
    if (subfieldsRaw != null) {
      if (subfieldsRaw instanceof JSONObject) {
        JSONObject subfields = (JSONObject) subfieldsRaw;
        for (String key : subfields.keySet())
          processSubfield(subfields.get(key), subfieldDefinitions);
      } else if (subfieldsRaw instanceof JSONArray) {
        JSONArray subfields = (JSONArray) subfieldsRaw;
        for (var i = 0; i < subfields.size(); i++)
          processSubfield(subfields.get(i), subfieldDefinitions);
      }
    }
    tag.setSubfields(subfieldDefinitions);
  }

  private void processSubfield(Object o, List<SubfieldDefinition> subfieldDefinitions) {
    SubfieldDefinition definition = extractSubfield(o);
    if (definition != null)
      subfieldDefinitions.add(definition);
  }

  private SubfieldDefinition extractSubfield(Object o) {
    SubfieldDefinition definition = null;
    if (o instanceof JSONObject) {
      JSONObject subfield = (JSONObject) o;
      String code = (String) subfield.get("code");
      String label = (String) subfield.get("label");
      String cardinalityCode = ((boolean) subfield.get("repeatable")) ? Cardinality.Repeatable.getCode() : Cardinality.Nonrepeatable.getCode();
      definition = new SubfieldDefinition(code, label, cardinalityCode);
      for (String key : subfield.keySet()) {
        Object value = subfield.get(key);
        if (key.equals("code")) {
        } else if (key.equals("label")) {
        } else if (key.equals("repeatable")) {
        } else if (key.equals("modified")) {
          // skip
        } else if (key.equals("order")) {
          // skip
        } else if (key.equals("pica3")) {
          // skip
        } else {
          System.err.println("code: " + key);
        }
      }
    } else {
      System.err.println(o.getClass());
    }
    return definition;
  }

  private void addTag(PicaFieldDefinition definition) {
    /*
    String tag = definition.getTag();
    if (map.containsKey(tag)) {
      System.err.println("xxx Tag is already defined! " + definition.getTag() + " " + map.get(tag));
    }
    map.put(tag, definition);
     */
    schema.add(definition);
  }

  public Map<String, PicaFieldDefinition> getMap() {
    return map;
  }

  public PicaSchemaManager getSchema() {
    return schema;
  }
}
