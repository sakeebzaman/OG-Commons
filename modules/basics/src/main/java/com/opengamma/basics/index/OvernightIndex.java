/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.basics.index;

import java.time.LocalDate;

import org.joda.convert.FromString;
import org.joda.convert.ToString;

import com.opengamma.basics.date.DayCount;
import com.opengamma.basics.date.HolidayCalendar;
import com.opengamma.basics.date.Tenor;
import com.opengamma.collect.ArgChecker;
import com.opengamma.collect.named.ExtendedEnum;
import com.opengamma.collect.named.Named;

/**
 * An overnight index, such as Sonia or Eonia.
 * <p>
 * An index represented by this class relates to lending over one night.
 * The rate typically refers to "Today/Tomorrow" but might refer to "Tomorrow/Next".
 * <p>
 * The index is defined by four dates.
 * The fixing date is the date on which the index is to be observed.
 * The publication date is the date on which the fixed rate is actually published.
 * The effective date is the date on which the implied deposit starts.
 * The maturity date is the date on which the implied deposit ends.
 * <p>
 * The most common implementations are provided in {@link OvernightIndices}.
 * <p>
 * All implementations of this interface must be immutable and thread-safe.
 */
public interface OvernightIndex
    extends RateIndex, Named {

  /**
   * Obtains an {@code OvernightIndex} from a unique name.
   * 
   * @param uniqueName  the unique name
   * @return the index
   * @throws IllegalArgumentException if the name is not known
   */
  @FromString
  public static OvernightIndex of(String uniqueName) {
    ArgChecker.notNull(uniqueName, "uniqueName");
    return extendedEnum().lookup(uniqueName);
  }

  /**
   * Gets the extended enum helper.
   * <p>
   * This helper allows instances of {@code OvernightIndex} to be lookup up.
   * It also provides the complete set of available instances.
   * 
   * @return the extended enum helper
   */
  public static ExtendedEnum<OvernightIndex> extendedEnum() {
    return OvernightIndices.ENUM_LOOKUP;
  }

  //-------------------------------------------------------------------------
  /**
   * Gets the day count convention of the index.
   * 
   * @return the day count convention
   */
  public abstract DayCount getDayCount();

  /**
   * Gets the fixing calendar of the index.
   * <p>
   * The rate will be fixed on each business day in this calendar.
   * 
   * @return the currency pair of the index
   */
  public abstract HolidayCalendar getFixingCalendar();

  /**
   * Gets the tenor of the index.
   * 
   * @return the tenor
   */
  public abstract Tenor getTenor();

  //-------------------------------------------------------------------------
  /**
   * Calculates the publication date from the fixing date.
   * <p>
   * The fixing date is the date on which the index is to be observed.
   * The publication date is the date on which the fixed rate is actually published.
   * <p>
   * No error is thrown if the input date is not a valid fixing date.
   * Instead, the fixing date is moved to the next valid fixing date and then processed.
   * 
   * @param fixingDate  the fixing date
   * @return the publication date
   */
  public abstract LocalDate calculatePublicationFromFixing(LocalDate fixingDate);

  /**
   * Calculates the effective date from the fixing date.
   * <p>
   * The fixing date is the date on which the index is to be observed.
   * The effective date is the date on which the implied deposit starts.
   * <p>
   * No error is thrown if the input date is not a valid fixing date.
   * Instead, the fixing date is moved to the next valid fixing date and then processed.
   * 
   * @param fixingDate  the fixing date
   * @return the effective date
   */
  public abstract LocalDate calculateEffectiveFromFixing(LocalDate fixingDate);

  /**
   * Calculates the fixing date from the effective date.
   * <p>
   * The fixing date is the date on which the index is to be observed.
   * The effective date is the date on which the implied deposit starts.
   * <p>
   * No error is thrown if the input date is not a valid effective date.
   * Instead, the effective date is moved to the next valid effective date and then processed.
   * 
   * @param effectiveDate  the effective date
   * @return the fixing date
   */
  public abstract LocalDate calculateFixingFromEffective(LocalDate effectiveDate);

  /**
   * Calculates the maturity date from the effective date.
   * <p>
   * The effective date is the date on which the implied deposit starts.
   * The maturity date is the date on which the implied deposit ends.
   * <p>
   * No error is thrown if the input date is not a valid effective date.
   * Instead, the effective date is moved to the next valid effective date and then processed.
   * 
   * @param effectiveDate  the effective date
   * @return the maturity date
   */
  public abstract LocalDate calculateMaturityFromEffective(LocalDate effectiveDate);

  //-----------------------------------------------------------------------
  /**
   * Gets the number of days to add to the fixing date to obtain the publication date.
   * <p>
   * In most cases, the fixing rate is available on the fixing date.
   * In a few cases, publication of the fixing rate is delayed until the following business day.
   * This property is zero if publication is on the fixing date, or one if it is the next day.
   * 
   * @return the publication date offset
   */
  public abstract int getPublicationDateOffset();

  /**
   * Gets the number of days to add to the fixing date to obtain the effective date.
   * <p>
   * In most cases, the settlement date and start of the implied deposit is on the fixing date.
   * In a few cases, the settlement date is the following business day.
   * This property is zero if settlement is on the fixing date, or one if it is the next day.
   * Maturity is always one business day after the settlement date.
   * 
   * @return the effective date offset
   */
  public abstract int getEffectiveDateOffset();

  //-------------------------------------------------------------------------
  /**
   * Gets the name that uniquely identifies this index.
   * <p>
   * This name is used in serialization and can be parsed using {@link #of(String)}.
   * 
   * @return the unique name
   */
  @ToString
  @Override
  public String getName();

}
