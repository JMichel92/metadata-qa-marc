package de.gwdg.metadataqa.marc.analysis.validator;

import de.gwdg.metadataqa.marc.cli.parameters.CommonParameters;
import de.gwdg.metadataqa.marc.cli.utils.IgnorableFields;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;

import java.util.List;

public class ValidatorConfiguration {
  private MarcVersion marcVersion = CommonParameters.DEFAULT_MARC_VERSION;
  private boolean doSummary = false;
  private IgnorableFields ignorableFields;
  private List<ValidationErrorType> ignorableIssueTypes;

  public ValidatorConfiguration() {
  }

  public ValidatorConfiguration withMarcVersion(MarcVersion marcVersion) {
    this.marcVersion = marcVersion;
    return this;
  }

  public ValidatorConfiguration withDoSummary(boolean doSummary) {
    this.doSummary = doSummary;
    return this;
  }

  public ValidatorConfiguration withIgnorableFields(IgnorableFields ignorableFields) {
    this.ignorableFields = ignorableFields;
    return this;
  }

  public ValidatorConfiguration withIgnorableIssueTypes(List<ValidationErrorType> ignorableIssueTypes) {
    this.ignorableIssueTypes = ignorableIssueTypes;
    return this;
  }

  public MarcVersion getMarcVersion() {
    return marcVersion;
  }

  public boolean doSummary() {
    return doSummary;
  }

  public IgnorableFields getIgnorableFields() {
    return ignorableFields;
  }

  public List<ValidationErrorType> getIgnorableIssueTypes() {
    return ignorableIssueTypes;
  }
}
