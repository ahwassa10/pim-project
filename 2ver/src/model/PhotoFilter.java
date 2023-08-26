package model;

import java.time.LocalDate;

/**
 * A PhotoFilter represents a search by date range and/or a search by tag query.
 * Passing a photo object to the match method will return true if the photo
 * matches the search query, or false if the photo
 * does not match.
 */
public class PhotoFilter {
    /**
     * Stores the beginning of the date range for the query.
     */
    private LocalDate startDate;

    /**
     * Stores the end of the date range for the query.
     */
    private LocalDate endDate;

    /**
     * Stores a query object representing a tag, or a conjunction or
     * disjunction of tags.
     */
    private Query query;

    /**
     * Specifies is the query is supposed to match just a single tag,
     * a conjunction of tags, or a disjunction of tags.
     */
    private enum QueryType {
        /**
         * A single tag query like Person=hatsune
         */
        SINGLE,

        /**
         * A conjunctive query like Person=hatsune and Location=vegas
         */
        AND,

        /**
         * A disjunctive query like Location=vegas or Location=france
         */
        OR
    }

    /**
     * A predicate used to test is a string represents a valid tag query.
     * This method uses regular expressions to check the string.
     *
     * @param query a possible tag query
     * @return true if the string is a valid query, false otherwise.
     */
    public static boolean isValid(String query) {
        return query != null && (
                query.matches(Query.orTagP) ||
                query.matches(Query.andTagP) ||
                query.matches(Query.singleTagP));
    }

    /**
     * An internal helper class used to represent a tag query.
     */
    private static class Query {
        /**
         * A regular expression used to match single (tag+value) queries like location=vegas.
         */
        private static final String singleTagP = "\\w+=\\w+";

        /**
         * A regular expression used to match conjunctive queries like location=italy AnD major=cs.
         */
        private static final String andTagP    = "\\w+=\\w+\\s+(?i)and(?i)\\s+\\w+=\\w+";

        /**
         * A regular expression used to match disjunction queries like location=vegas OR location=italy.
         */
        private static final String orTagP     = "\\w+=\\w+\\s+(?i)or(?i)\\s+\\w+=\\w+";

        /**
         * Stores the first parsed tag in conjunctive/disjunctive queries.
         */
        private Tag tag1;

        /**
         * Stores the second parsed tag in conjunctive/disjunctive queries.
         */
        private Tag tag2;

        /**
         * Stores the type of query parsed.
         */
        private QueryType type;

        /**
         * Creates a query object by applying the regular expressions above to
         * parse whether a string is a valid query, and then extracts tag
         * object from the string, combining everything into a single, fully
         * parsed package (a query object).
         *
         * @param query a string representing the tag query.
         */
        public Query(String query) {
            query = query.strip();

            if (query.matches(orTagP)) {
                type = QueryType.OR;

                String[] components = query.split("(?i)or");
                String[] tag1 = components[0].strip().split("=");
                String[] tag2 = components[1].strip().split("=");

                this.tag1 = new Tag(tag1[0], tag1[1]);
                this.tag2 = new Tag(tag2[0], tag2[1]);

            } else if (query.matches(andTagP)) {
                type = QueryType.AND;

                String[] components = query.split("(?i)and");
                String[] tag1 = components[0].strip().split("=");
                String[] tag2 = components[1].strip().split("=");

                this.tag1 = new Tag(tag1[0], tag1[1]);
                this.tag2 = new Tag(tag2[0], tag2[1]);

            } else if (query.matches(singleTagP)) {
                type = QueryType.SINGLE;

                String[] tag1 = query.split("=");
                this.tag1 = new Tag(tag1[0], tag1[1]);
                this.tag2 = null;

            } else {
                throw new IllegalArgumentException("Invalid Query Format");
            }
        }
    }

    /**
     * Given a photo object, returns true is the object matches the search criteria.
     * We are able to search on date range along, tag query alone, or both date
     * range and tag query.
     *
     * @param photo The photo object to test.
     * @return true is the photo matches, false otherwise.
     */
    public boolean match(Photo photo) {
        if (photo == null) {
            return false;
        }

        if (startDate != null && photo.getModifyDate().isBefore(startDate)) {
            return false;
        }

        if (endDate != null && photo.getModifyDate().isAfter(endDate)) {
            return false;
        }

        if (query == null) {
            return true;
        }

        if (query.type == QueryType.SINGLE) {
            return photo.getTagStore().containsNameValue(query.tag1.getName(), query.tag1.getValue());
        } else if (query.type == QueryType.OR) {
            return (photo.getTagStore().containsNameValue(query.tag1.getName(), query.tag1.getValue()) ||
                    photo.getTagStore().containsNameValue(query.tag2.getName(), query.tag2.getValue()));

        } else if (query.type == QueryType.AND) {
            return (photo.getTagStore().containsNameValue(query.tag1.getName(), query.tag1.getValue()) &&
                    photo.getTagStore().containsNameValue(query.tag2.getName(), query.tag2.getValue()));
        }
        return false;
    }

    /**
     * Creates a PhotoFilter object which can be used later to test if a photo
     * matches the search criteria.
     *
     * @param startDate The beginning of the date range.
     * @param endDate The end of the date range.
     * @param query The tag query (either single, disjunctive, or conjunctive).
     */
    public PhotoFilter(LocalDate startDate, LocalDate endDate, String query) {
        if (startDate != null) {
            this.startDate = startDate;
        }

        if (endDate != null) {
            // We want the end date to be inclusive, we make the end date the start of the next day.
            this.endDate = endDate;
        }

        if (query.equals("")) {
            this.query = null;

        } else if (isValid(query)) {
            this.query = new Query(query);

        } else {
            throw new IllegalArgumentException("Invalid Query Format");
        }
    }
}
