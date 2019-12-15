package util.impl;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author aburulev on 15.12.2019.
 */
public class MatcherImplTest {

    @Test
    public void testMatches_simpleCases_returnTrue() {
        assertMatches("N", "NameUtilTest");
        assertMatches("NU", "NameUtilTest");
        assertMatches("NUT", "NameUtilTest");
        assertMatches("NaUT", "NameUtilTest");
        assertMatches("NaUtT", "NameUtilTest");
        assertMatches("NaUtT", "NameUtilTest");
        assertMatches("NaUtTe", "NameUtilTest");
        assertMatches("AACl", "AAClass");
        assertMatches("AACl", "AACtassAACl");
        assertMatches("ZZZ", "ZZZZZZZZZZ");
    }

    @Test
    public void testMatches_simpleCases_returnFalse() {
        assertDoesntMatch("F", "NameUtilTest");
        assertDoesntMatch("NF", "NameUtilTest");
        assertDoesntMatch("NUTF", "NameUtilTest");
        assertDoesntMatch("NamUiT", "NameUtilTest");
        assertDoesntMatch("NeUT", "NameUtilTest");
        assertDoesntMatch("NaUTa", "NameUtilTest");
        assertDoesntMatch("NaUtlT", "NameUtilTest");
        assertDoesntMatch("ZZz", "ZZZZZZZZZZ");
        assertDoesntMatch("ZzZ", "ZZZZZZZZZZ");
    }

    @Test
    public void testMatches_patternWithEndSpace_returnTrue() {
        assertMatches("NTe ", "NameUtilTest");
        assertMatches("NU ", "NameUtil");
        assertMatches("NUTest ", "NameUtilTest");
        assertMatches("NameUtilTest ", "NameUtilTest");
        assertMatches("NameUtilTes ", "NameUtilTest");
    }

    @Test
    public void testMatches_patternWithEndSpace_returnFalse() {
        assertDoesntMatch("NTer ", "NameUtilTest");
        assertDoesntMatch("NU ", "NameUtilTest");
    }

    @Test
    public void testMatches_patternWithEndSpaceAndWildcard_returnTrue() {
        assertMatches("N*Te ", "NameUtilTest");
        assertMatches("N**U ", "NameUtil");
        assertMatches("N*U*Test ", "NameUtilTest");
    }

    @Test
    public void testMatches_patternWithEndSpaceAndWildcard_returnFalse() {
        assertDoesntMatch("N*TeU ", "NameUtilTest");
        assertDoesntMatch("N*eU ", "NameUtil");
        assertDoesntMatch("N*U*Testing ", "NameUtilTest");
    }

    @Test
    public void testMatchesEmpty() {
        assertMatches("", "");
        assertMatches("", "asdfs");
    }

    @Test
    public void testMatches_patternIsLowerCase_returnTrue() {
        assertMatches("n", "NameUtilTest");
        assertMatches("nu", "NameUtilTest");
        assertMatches("nut", "NameUtilTest");
    }

    @Test
    public void testMatches_patternIsLowerCase_returnFalse() {
        assertDoesntMatch("nT", "NameUtilTest");
        assertDoesntMatch("nuT", "NameUtilTest");
        assertDoesntMatch("nUt", "NameUtilTest");
    }

    private static void assertMatches(String pattern, String name) {
        assertTrue(pattern + " doesn't match " + name + "!!!", MatcherImpl.buildMatcher(pattern).build().matches(name));
    }

    private static void assertDoesntMatch(String pattern, String name) {
        assertFalse(pattern + " matches " + name + "!!!", MatcherImpl.buildMatcher(pattern).build().matches(name));
    }

}