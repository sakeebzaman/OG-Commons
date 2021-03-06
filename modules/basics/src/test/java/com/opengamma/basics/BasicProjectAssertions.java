/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.basics;

import com.opengamma.basics.currency.CurrencyAmount;
import com.opengamma.collect.CollectProjectAssertions;
import com.opengamma.collect.result.Result;

/**
 * Helper class to allow custom AssertJ assertions to be
 * accessible via the same static import as the standard
 * assertions.
 * <p>
 * Prefer to statically import {@link #assertThat(Result)}
 * from this class rather than {@link CurrencyAmountAssert#assertThat(CurrencyAmount)}.
 */
public class BasicProjectAssertions extends CollectProjectAssertions {

  /**
   * Create an {@code Assert} instance that enables
   * assertions on {@code CurrencyAmount} objects.
   *
   * @param amount  the amount to create an {@code Assert} for
   * @return an {@code Assert} instance
   */
  public static CurrencyAmountAssert assertThat(CurrencyAmount amount) {
    return CurrencyAmountAssert.assertThat(amount);
  }
}
