/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.collect.io;

import static com.opengamma.collect.TestHelper.assertThrows;
import static org.testng.Assert.assertEquals;

import java.util.Map;

import org.testng.annotations.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;

/**
 * Test {@link PropertySet}.
 */
@Test
public class PropertySetTest {

  public void test_EMPTY() {
    PropertySet test = PropertySet.EMPTY;

    assertEquals(test.isEmpty(), true);
    assertEquals(test.contains("unknown"), false);
    assertEquals(test.getValueList("unknown"), ImmutableList.of());
    assertThrows(() -> test.getValue("unknown"), IllegalArgumentException.class);
    assertEquals(test.toString(), "{}");
  }

  public void test_of_map() {
    Map<String, String> keyValues = ImmutableMap.of("a", "x", "b", "y");
    PropertySet test = PropertySet.of(keyValues);

    assertEquals(test.isEmpty(), false);
    assertEquals(test.contains("a"), true);
    assertEquals(test.getValue("a"), "x");
    assertEquals(test.getValueList("a"), ImmutableList.of("x"));
    assertEquals(test.contains("b"), true);
    assertEquals(test.getValue("b"), "y");
    assertEquals(test.getValueList("b"), ImmutableList.of("y"));
    assertEquals(test.contains("c"), false);
    assertEquals(test.keys(), ImmutableSet.of("a", "b"));
    assertEquals(test.asMap(), ImmutableListMultimap.of("a", "x", "b", "y"));
    assertEquals(test.getValueList("unknown"), ImmutableSet.of());

    assertThrows(() -> test.getValue("unknown"), IllegalArgumentException.class);
    assertEquals(test.toString(), "{a=[x], b=[y]}");
  }

  public void test_of_multimap() {
    Multimap<String, String> keyValues = ImmutableMultimap.of("a", "x", "a", "y", "b", "z");
    PropertySet test = PropertySet.of(keyValues);

    assertEquals(test.isEmpty(), false);
    assertEquals(test.contains("a"), true);
    assertThrows(() -> test.getValue("a"), IllegalArgumentException.class);
    assertEquals(test.getValueList("a"), ImmutableList.of("x", "y"));
    assertEquals(test.contains("b"), true);
    assertEquals(test.getValue("b"), "z");
    assertEquals(test.getValueList("b"), ImmutableList.of("z"));
    assertEquals(test.contains("c"), false);
    assertEquals(test.keys(), ImmutableSet.of("a", "b"));
    assertEquals(test.asMap(), ImmutableListMultimap.of("a", "x", "a", "y", "b", "z"));
    assertEquals(test.getValueList("unknown"), ImmutableSet.of());

    assertThrows(() -> test.getValue("unknown"), IllegalArgumentException.class);
    assertEquals(test.toString(), "{a=[x, y], b=[z]}");
  }

  //-------------------------------------------------------------------------
  public void test_combinedWith() {
    PropertySet base = PropertySet.of(ImmutableListMultimap.of("a", "x", "a", "y", "b", "y", "c", "z"));
    PropertySet other = PropertySet.of(ImmutableListMultimap.of("a", "aa", "b", "bb"));
    PropertySet expected = PropertySet.of(ImmutableListMultimap.of("a", "aa", "b", "bb", "c", "z"));
    assertEquals(base.combinedWith(other), expected);
  }

  public void test_combinedWith_emptyBase() {
    PropertySet base = PropertySet.of(ImmutableListMultimap.of("a", "x", "a", "y", "b", "y", "c", "z"));
    assertEquals(base.combinedWith(PropertySet.EMPTY), base);
  }

  public void test_combinedWith_emptyOther() {
    PropertySet base = PropertySet.of(ImmutableListMultimap.of("a", "x", "a", "y", "b", "y", "c", "z"));
    assertEquals(PropertySet.EMPTY.combinedWith(base), base);
  }

  //-------------------------------------------------------------------------
  public void test_equalsHashCode() {
    Map<String, String> keyValues = ImmutableMap.of("a", "x", "b", "y");
    PropertySet a1 = PropertySet.of(keyValues);
    PropertySet a2 = PropertySet.of(keyValues);
    PropertySet b = PropertySet.of(ImmutableMap.of("a", "x", "b", "z"));

    assertEquals(a1.equals(a1), true);
    assertEquals(a1.equals(a2), true);
    assertEquals(a1.equals(b), false);
    assertEquals(a1.equals(null), false);
    assertEquals(a1.equals(""), false);
    assertEquals(a1.hashCode(), a2.hashCode());
  }

}
