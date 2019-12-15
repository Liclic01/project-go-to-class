package util.impl;

import util.Matcher;

/**
 * @author aburulev on 15.12.2019.
 */

public class MatcherImpl implements Matcher {
    private final char[] patternChars;

    private MatcherImpl(char[] patternChars) {
        this.patternChars = patternChars;
    }

    @Override
    public boolean matches(String name) {
        char[] nameChars = name.toCharArray();
        int nameLength = nameChars.length;
        int patternIndex = 0;

        for (int nameIndex = 0; nameIndex <= nameLength; nameIndex++) {
            if (patternIndex == patternChars.length) {
                return true;
            }
            char patternChar = patternChars[patternIndex];

            if (nameIndex == nameLength) {
                return patternChar == ' ';
            }

            if (patternChar == ' ') {
                return isLastWord(nameChars, nameLength, nameIndex);
            }

            if (patternChar == '*') {
                patternIndex++;
                continue;
            }

            if (Character.isUpperCase(patternChar)) {
                int index = name.indexOf(patternChar, nameIndex);
                if (index == -1) {
                    return false;
                }
                nameIndex = index;
                patternIndex++;
            } else {
                patternIndex = getPatternIndexForLowerCaseChars(patternIndex, nameIndex, patternChar, nameChars);
            }
        }
        return true;
    }

    private boolean isLastWord(char[] nameChars, int nameLength, int indexName) {
        for (int i = indexName; i < nameLength; i++) {
            if (Character.isUpperCase(nameChars[i])) {
                return false;
            }
        }
        return true;
    }

    private int getPatternIndexForLowerCaseChars(int patternIndex, int nameIndex, char patternChar, char[] nameChars) {
        return nameChars[nameIndex] == patternChar ? ++patternIndex : 0;
    }

    public static MatcherBuilder buildMatcher(String pattern) {
        return new MatcherBuilder(pattern);
    }

    public static class MatcherBuilder {
        private final String pattern;

        MatcherBuilder(String pattern) {
            this.pattern = pattern;
        }

        public Matcher build() {
            return new MatcherImpl(getPatternChars(pattern));
        }

        private char[] getPatternChars(String pattern) {
            return isLowerCasePattern(pattern) ? pattern.toUpperCase().toCharArray() : pattern.toCharArray();
        }

        private boolean isLowerCasePattern(String pattern) {
            return !pattern.chars()
                    .filter(Character::isUpperCase)
                    .findFirst()
                    .isPresent();
        }
    }
}

