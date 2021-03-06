/**
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.basics.index;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.opengamma.collect.id.StandardId;

/**
 * Test {@link Index}.
 */
@Test
public class IndexTest {

  public void test_getStandardId() {
    assertEquals(IborIndices.GBP_LIBOR_3M.getStandardId(), StandardId.of("OG-Index", "GBP-LIBOR-3M"));
  }

}
